/*
 * Copyright 2014 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.gazeapp.ui.contactview

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.facebook.ads.AdSize
import com.facebook.ads.AdView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayoutMediator
import needle.Needle
import needle.UiRelatedTask
import net.gazeapp.R
import net.gazeapp.callbacks.MediaListLoadCallback
import net.gazeapp.data.GazeDatabase.Companion.getDatabase
import net.gazeapp.data.GazeImage
import net.gazeapp.data.dao.ContactDao
import net.gazeapp.data.dao.ContactLabelDao
import net.gazeapp.data.dao.LabelDao
import net.gazeapp.data.model.Contact
import net.gazeapp.data.model.Encounter
import net.gazeapp.data.model.Media
import net.gazeapp.data.model.Work
import net.gazeapp.databinding.ContactDetailViewPagerLayoutBinding
import net.gazeapp.dialog.AddEditEncounterDialog
import net.gazeapp.dialog.AddEditWorkDialog
import net.gazeapp.dialog.FreeVersionDialogs
import net.gazeapp.helpers.Const
import net.gazeapp.helpers.Preferences
import net.gazeapp.helpers.SnackBarType
import net.gazeapp.utilities.GazeTools
import net.gazeapp.utilities.MediaTools
import net.gazeapp.utilities.SpecificValues
import java.io.File
import java.util.*
import javax.inject.Inject

class ContactViewWithViewPagerTabActivity : AppCompatActivity() {

    @Inject
    lateinit var tools: GazeTools

    @Inject
    lateinit var mediaTools: MediaTools

    lateinit var outerLayout: CoordinatorLayout
    lateinit var fragmentContainer: FrameLayout
    lateinit var mPager: ViewPager2

    //    @BindView(R.id.sliding_tabs)
//    lateinit var mSlidingTabLayout: SlidingTabLayout
    lateinit var contactMainPic: ImageView
    lateinit var floatingActionButton: FloatingActionButton
    lateinit var facebookBanner: LinearLayout

    private fun bindViews() {
        mPager = binding.viewPager
        outerLayout = binding.outerLayout
        fragmentContainer = binding.fragmentContainer
        contactMainPic = binding.mainPic
        floatingActionButton = binding.floatingActionButton
        facebookBanner = binding.bannerContainer
    }


    private var adView: AdView? = null


    var freeVersionDialogs: FreeVersionDialogs? = null
    var contactId = 0
    var position = 0
    var contactViewNavigationAdapter: ContactViewNavigationAdapter? = null

    private var mFlexibleSpaceHeight = 0
    private var mTabHeight = 0
    private var mTabTitles: List<String>? = null
    lateinit var contact: Contact
    var visibleFragmentPosition = 0
    private var contactDao: ContactDao? = null
    private var labelDao: LabelDao? = null
    private var contactLabelDao: ContactLabelDao? = null

    lateinit var binding: ContactDetailViewPagerLayoutBinding

    companion object {
        private const val TAG = "ContactViewWithViewPage"
        const val ARG_SCROLL_Y = "ARG_SCROLL_Y"
        private const val FRAGMENT_GALLERY = 1
        private const val FRAGMENT_WORK = 4
        private const val FRAGMENT_ENCOUNTERS = 7
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ContactDetailViewPagerLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindViews()

        contactId = intent.getIntExtra("contactId", 0)
        position = intent.getIntExtra("position", 0)
        freeVersionDialogs = FreeVersionDialogs(this)

        floatingActionButton.setOnClickListener { clickedFloatingActionButton() }

        loadData()

        TabLayoutMediator(binding.tabLayout, mPager) { tab, position ->
            tab.text = "REPLACE"
        }.attach()
    }

    private fun loadData() {
        Needle.onBackgroundThread().execute {
            contactDao = getDatabase(this).contactDao
            labelDao = getDatabase(this).labelDao
            contactLabelDao = getDatabase(this).contactLabelDao
            contact = contactDao!!.getContact(contactId)
            updateUI()
        }
    }

    private fun updateUI() {
        Needle.onMainThread().execute {
            mTabTitles = tabTitles

            contactViewNavigationAdapter = ContactViewNavigationAdapter(this, tabTitles)

            // Contact View Tabs
//            adapter = NavigationAdapter(
//                supportFragmentManager, position
//            )
//            adapter!!.setData(mTabTitles, position)

            mPager.adapter = contactViewNavigationAdapter
            mFlexibleSpaceHeight =
                resources.getDimensionPixelSize(R.dimen.flexible_space_image_height)
            mTabHeight = resources.getDimensionPixelSize(R.dimen.tab_height)

//            mSlidingTabLayout.setCustomTabView(
//                R.layout.tab_indicator_contact_view,
//                android.R.id.text1
//            )
//            mSlidingTabLayout.setSelectedIndicatorColors(resources.getColor(R.color.accent, null))
//            mSlidingTabLayout.setDistributeEvenly(true)

            mPager.currentItem = position
            mPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    Log.d(TAG, "Visible Fragment Position: $position")
                    visibleFragmentPosition = position
                    setFloatingActionButtonImage()
                }
            })

            initFacebookAds()

            // Is Pro User? Display Ads?
            if (tools.isProUser()) {
                // If Pro user wants ads, show them now.

                if (tools.getBoolean(
                        Preferences.IS_ADS_ENABLED,
                        Preferences.IS_ADS_ENABLED_DEFAULT_VALUE
                    )
                ) {
                    adView!!.loadAd()
                    facebookBanner.visibility = View.VISIBLE
                } else {
                    facebookBanner.visibility = View.GONE
                }
            } else {
                adView!!.loadAd()
                facebookBanner.visibility = View.VISIBLE
            }
        }
    }

    private fun initFacebookAds() {
        adView =
            AdView(this, Const.FACEBOOK_AUDIENCE_NETWORK_NATIVE_BANNER_ID, AdSize.BANNER_HEIGHT_50)
        facebookBanner.addView(adView)
    }

    private fun clickedFloatingActionButton() {

        val pageTitle = tabTitles[visibleFragmentPosition]
        when {
            pageTitle.equals(
                applicationContext.resources.getString(R.string.tab_encounters),
                ignoreCase = true
            ) -> {
                val addEditWorkDialog = AddEditEncounterDialog(
                    Encounter(contact.id), contact, this, false, supportFragmentManager
                )
                addEditWorkDialog.showDialog()
            }
            pageTitle.equals(
                applicationContext.resources.getString(R.string.tab_professional),
                ignoreCase = true
            ) -> {
                val addEditWorkDialog = AddEditWorkDialog(
                    Work(contact.id), contact, this, false
                )
                addEditWorkDialog.showDialog()
            }
            pageTitle.equals(
                applicationContext.resources.getString(R.string.tab_gallery),
                ignoreCase = true
            ) -> {
                // TODO FIXME show albumselector
                // TODO FIXME show albumselector
                // TODO FIXME show albumselector
                // TODO FIXME show albumselector
                //            Intent intent = new Intent(this, AlbumSelectActivity.class);
                //            if (!GazeTools.isProUser()) {
                //                intent.putExtra(Const.INTENT_EXTRA_LIMIT, GazeTools.getMaxImageSavesFreeVersion());
                //            }
                //            startActivityForResult(intent, Const.REQUEST_CODE);
            }
            else -> {
                // TODO FIXME hier wohl zum fragment gehen...
//                val intent = Intent(this, EditContactActivity::class.java)
//                intent.putExtra("contact", contact)
//                startActivityForResult(intent, Const.ACTIVITY_FOR_RESULT_EDIT_CONTACT_CODE)
            }
        }

//        if (visibleFragmentPosition == FRAGMENT_GALLERY) {
//            Intent intent = new Intent(this, AlbumSelectActivity.class);
//            if (!GazeTools.isProUser()) {
//                intent.putExtra(Constants.INTENT_EXTRA_LIMIT, GazeTools.getMaxImageSavesFreeVersion());
//            }
//            startActivityForResult(intent, Constants.REQUEST_CODE);
//        } else if (visibleFragmentPosition == FRAGMENT_WORK) {
//            AddEditWorkDialog addEditWorkDialog = new AddEditWorkDialog(new Work(), contact, this, false);
//            addEditWorkDialog.showDialog();
//        } else if (visibleFragmentPosition == FRAGMENT_ENCOUNTERS) {
//            AddEditEncounterDialog addEditWorkDialog = new AddEditEncounterDialog(new Encounter(), contact, this, false);
//            addEditWorkDialog.showDialog();
//        } else {
//            EditContactActivity_.intent(this).contact(contact).startForResult(Const.ACTIVITY_FOR_RESULT_EDIT_CONTACT_CODE);
//        }
    }

    private fun setFloatingActionButtonImage() {

        val pageTitle = tabTitles[visibleFragmentPosition]

        when {
            pageTitle.equals(
                applicationContext.resources.getString(R.string.tab_encounters),
                ignoreCase = true
            ) -> {
                // ENCOUNTERS
                floatingActionButton.setImageResource(R.drawable.ic_add)
            }
            pageTitle.equals(
                applicationContext.resources.getString(R.string.tab_professional),
                ignoreCase = true
            ) -> {
                // WORK / PROFESSION
                floatingActionButton.setImageResource(R.drawable.ic_add)
            }
            pageTitle.equals(
                applicationContext.resources.getString(R.string.tab_gallery),
                ignoreCase = true
            ) -> {
                // GALLERY
                floatingActionButton.setImageResource(R.drawable.ic_add_photo)
            }
            else -> {
                floatingActionButton.setImageResource(R.drawable.ic_edit)
            }
        }
    }

    private val tabTitles: List<String>
        get() {
            val tabTitles: MutableList<String> = ArrayList()
            tabTitles.add(
                applicationContext.resources.getString(R.string.tab_overview)
                    .uppercase(Locale.getDefault())
            )
            tabTitles.add(
                applicationContext.resources.getString(R.string.tab_gallery)
                    .uppercase(Locale.getDefault())
            )
            tabTitles.add(
                applicationContext.resources.getString(R.string.tab_contact)
                    .uppercase(Locale.getDefault())
            )
            tabTitles.add(
                applicationContext.resources.getString(R.string.tab_personal)
                    .uppercase(Locale.getDefault())
            )
            tabTitles.add(
                applicationContext.resources.getString(R.string.tab_professional)
                    .uppercase(Locale.getDefault())
            )
            tabTitles.add(
                applicationContext.resources.getString(R.string.tab_body)
                    .uppercase(Locale.getDefault())
            )
            if (SpecificValues.SHOW_XRATED) {
                tabTitles.add(
                    applicationContext.resources.getString(R.string.tab_sex)
                        .uppercase(Locale.getDefault())
                )
            }
            tabTitles.add(
                applicationContext.resources.getString(R.string.tab_encounters)
                    .uppercase(Locale.getDefault())
            )
            tabTitles.add(
                applicationContext.resources.getString(R.string.tab_social_media)
                    .uppercase(Locale.getDefault())
            )
            return tabTitles
        }

    //    @UiThread
    //    void translateTab(int scrollY, boolean animated) {
    //        int flexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
    //        int tabHeight = getResources().getDimensionPixelSize(tab_height);
    //
    //        File mainPic = mediaTools.getFileFromInternalStorage(contact, Const.GALLERY_PUBLIC, ContactViewWithViewPagerTabActivity.this);
    //        if (mainPic == null) {
    //            if (contact.getContactName().equalsIgnoreCase(Const.FIRST_GAZE_CONTACT_NAME)) {
    //                Glide.with(ContactViewWithViewPagerTabActivity.this)
    //                        .load(R.drawable.josh)
    //                        .asBitmap()
    //                        .placeholder(R.drawable.silhouette)
    //                        .into(new BitmapImageViewTarget(contactMainPic) {
    //                            @Override
    //                            protected void setResource(Bitmap resource) {
    //                                //Play with bitmap
    //                                super.setResource(resource);
    //                            }
    //                        });
    //            }
    //        } else {
    //            Glide.with(ContactViewWithViewPagerTabActivity.this)
    //                    .load(mainPic)
    //                    .asBitmap()
    //                    .placeholder(R.drawable.silhouette)
    //                    .into(new BitmapImageViewTarget(contactMainPic) {
    //                        @Override
    //                        protected void setResource(Bitmap resource) {
    //                            //Play with bitmap
    //                            super.setResource(resource);
    //                        }
    //                    });
    //        }
    //
    //        // Translate overlay and image
    //        float flexibleRange = flexibleSpaceImageHeight - getActionBarSize();
    //        int minOverlayTransitionY = tabHeight - overlayView.getHeight();
    //        ViewHelper.setTranslationY(overlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
    //        ViewHelper.setTranslationY(contactMainPic, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));
    //
    //        // Change alpha of overlay
    //        ViewHelper.setAlpha(overlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));
    //
    //        // If tabs are moving, cancel it to start a new animation.
    //        ViewPropertyAnimator.animate(mSlidingTabLayout).cancel();
    //        // Tabs will move between the top of the screen to the bottom of the image.
    //        float translationY = ScrollUtils.getFloat(-scrollY + mFlexibleSpaceHeight - mTabHeight, 0, mFlexibleSpaceHeight - mTabHeight);
    //        if (animated) {
    //            // Animation will be invoked only when the current tab is changed.
    //            ViewPropertyAnimator.animate(mSlidingTabLayout)
    //                    .translationY(translationY)
    //                    .setDuration(200)
    //                    .start();
    //        } else {
    //            // When Fragments' scroll, translate tabs immediately (without animation).
    //            ViewHelper.setTranslationY(mSlidingTabLayout, translationY);
    //        }
    //    }

    private fun propagateScroll(scrollY: Int) {
        // Set scrollY for the fragments that are not created yet
        contactViewNavigationAdapter!!.setScrollY(scrollY)

        // Set scrollY for the active fragments
        for (i in 0 until contactViewNavigationAdapter!!.itemCount) {
            // Skip current item
            if (i == mPager.currentItem) {
                continue
            }

            // Skip destroyed or not created item
            // TODO FIXME: FlexibleSpaceWithImageBaseFragment.java rausgelöscht da Deprecated Error...
            // TODO FIXME: FlexibleSpaceWithImageBaseFragment.java rausgelöscht da Deprecated Error...
            val contactDetailsFragment =
                contactViewNavigationAdapter!!.createFragment(i)
//            val contactDetailsFragment =
//                contactViewNavigationAdapter!!.createFragment(i) as FlexibleSpaceWithImageBaseFragment<*>
//            val view = contactDetailsFragment.view ?: continue
//            contactDetailsFragment.setScrollY(scrollY, mFlexibleSpaceHeight)
//            contactDetailsFragment.updateFlexibleSpace(scrollY)
        }
    }

    private fun updateContactAndResetAdapter() {
        Needle.onBackgroundThread().execute(object : UiRelatedTask<Int>() {
            override fun doWork(): Int {
                contact = contactDao!!.getContact(contactId)

                // Inform ContactViewGalleryFragment.java to refresh its gallery with the newly added images
//                    EventBus.getDefault().post(new MediaAddedEvent());
                return 0
            }

            override fun thenDoUiRelatedWork(result: Int) {
                contactViewNavigationAdapter = ContactViewNavigationAdapter(
                    this@ContactViewWithViewPagerTabActivity,
                    tabTitles
                )
                contactViewNavigationAdapter!!.notifyDataSetChanged()

                // TODO FIXME die linie muss vielleicht wieder rein wenn es nicht refreshen sollte...
                //contactViewNavigationAdapter!!.setData(mTabTitles, position)
                mPager.adapter = contactViewNavigationAdapter
            }
        })
    }

    fun getMediaList(mediaListLoadCallback: MediaListLoadCallback?) {
        // TODO FIXME move to viewmodel
        //mediaTools.getMediaListFromDatabase(contactId, Const.GALLERY_PUBLIC, mediaListLoadCallback);
    }


    private val resultContract = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(), activityResultRegistry
    ) { activityResult: ActivityResult ->
        val resultCode = activityResult.resultCode
        val data = intent.data

        // If requestCode ACTIVITY_FOR_RESULT_EDIT_CONTACT_CODE go to: onEditContactResult()
        if (resultCode != Const.ACTIVITY_FOR_RESULT_EDIT_CONTACT_CODE) {

            // Returning here from ADDING IMAGES or SAVE BUTTON in EDIT MODE
            if (resultCode == RESULT_CANCELED) {
                // User dismissed his changes of the Contact. Do nothing.
            } else if (resultCode == RESULT_OK) {

                // Update the view with the newly made changes.
//                updateUI();
//                GazeTools.showMaterialSnackBar(this, viewPager, getString(R.string.success_contact_edited, contact.getContactName()), SnackBarType.SUCCESS, 1000);

                // The array list has the image paths of the selected images
                val images: ArrayList<GazeImage>? =
                    intent.getParcelableArrayListExtra(Const.INTENT_EXTRA_IMAGES)

                // NEW IMAGES HAVE BEEN ADDED TO THE CONTACT CARD.
                if (images != null && images.size > 0) {
                    val usedFileNames = ArrayList<String>()
                    var pass: Boolean
                    for ((_, name, path) in images) {
                        val imageCount = mediaTools.countMediaFromLocalStorage(contact)
                        pass = imageCount < tools.maxImageSavesFreeVersion
                        if (tools.isProUser()) {
                            pass = true
                        }
                        if (pass) {
                            val imageFile = File(path)
                            val media = Media(
                                contact.id
                            )
                            media.isInPrivateGallery = Const.GALLERY_PUBLIC
                            media.originalFileName = name
                            media.fullPath = path
                            val usedFileName =
                                mediaTools.copyFileToPrivateStorage(imageFile, media)
                            if (usedFileName == null) {
                                tools.showMaterialSnackBar(
                                    mPager,
                                    resources.getQuantityString(
                                        R.plurals.error_copying_failed,
                                        images.size
                                    ),
                                    SnackBarType.ERROR
                                )
                            } else {
                                media.usedFileName = usedFileName
                                usedFileNames.add(usedFileName)
                                Log.d(TAG, "Copy Image: " + imageFile.absolutePath + " success.")
                            }
                        } else {
                            freeVersionDialogs!!.showImageSavesPerContactLimitReached()
                            break
                        }
                    }

                    // TODO FIXME move this to viewmodel
                    // TODO FIXME move this to viewmodel
//                    String currentMainPic = contact.getMainPicFileName(); // 0 = not set
//                    if (currentMainPic == null && usedFileNames.size() > 0) {
//                        contact.setMainPicFileName(usedFileNames.get(0));
//
//                        Glide.with(ContactViewWithViewPagerTabActivity.this).load(mediaTools.getFileFromInternalStorage(contact, Const.GALLERY_PUBLIC)).placeholder(R.drawable.silhouette).into(contactMainPic);
//                    }
                    updateContactAndResetAdapter()
                }
            } else if (resultCode == Const.RESULT_UPDATE_FAILED) {
                // Display Error Message that updating the data has failed.
                tools.showMaterialSnackBar(
                    mPager,
                    getString(R.string.error_updating_contact),
                    SnackBarType.ERROR
                )
            }
        }
        refreshContactData()
    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        // If requestCode ACTIVITY_FOR_RESULT_EDIT_CONTACT_CODE go to: onEditContactResult()
//        if (requestCode != Const.ACTIVITY_FOR_RESULT_EDIT_CONTACT_CODE) {
//
//            // Returning here from ADDING IMAGES or SAVE BUTTON in EDIT MODE
//            if (resultCode == RESULT_CANCELED) {
//                // User dismissed his changes of the Contact. Do nothing.
//            } else if (resultCode == RESULT_OK) {
//
//                // Update the view with the newly made changes.
////                updateUI();
////                GazeTools.showMaterialSnackBar(this, viewPager, getString(R.string.success_contact_edited, contact.getContactName()), SnackBarType.SUCCESS, 1000);
//
//                // The array list has the image paths of the selected images
//                val images: List<GazeImage>? =
//                    data!!.getParcelableArrayListExtra<Parcelable>(Const.INTENT_EXTRA_IMAGES)
//
//                // NEW IMAGES HAVE BEEN ADDED TO THE CONTACT CARD.
//                if (images != null && images.size > 0) {
//                    val usedFileNames = ArrayList<String>()
//                    var pass: Boolean
//                    for ((_, name, path) in images) {
//                        val imageCount = mediaTools.countMediaFromLocalStorage(contact)
//                        pass = imageCount < tools.maxImageSavesFreeVersion
//                        if (tools.isProUser()) {
//                            pass = true
//                        }
//                        if (pass) {
//                            val imageFile = File(path)
//                            val media = Media(
//                                contact.id
//                            )
//                            media.isInPrivateGallery = Const.GALLERY_PUBLIC
//                            media.originalFileName = name
//                            media.fullPath = path
//                            val usedFileName =
//                                mediaTools.copyFileToPrivateStorage(imageFile, media)
//                            if (usedFileName == null) {
//                                tools.showMaterialSnackBar(
//                                    mPager,
//                                    resources.getQuantityString(
//                                        R.plurals.error_copying_failed,
//                                        images.size
//                                    ),
//                                    SnackBarType.ERROR
//                                )
//                            } else {
//                                media.usedFileName = usedFileName
//                                usedFileNames.add(usedFileName)
//                                Log.d(TAG, "Copy Image: " + imageFile.absolutePath + " success.")
//                            }
//                        } else {
//                            freeVersionDialogs!!.showImageSavesPerContactLimitReached()
//                            break
//                        }
//                    }
//
//                    // TODO FIXME move this to viewmodel
//                    // TODO FIXME move this to viewmodel
////                    String currentMainPic = contact.getMainPicFileName(); // 0 = not set
////                    if (currentMainPic == null && usedFileNames.size() > 0) {
////                        contact.setMainPicFileName(usedFileNames.get(0));
////
////                        Glide.with(ContactViewWithViewPagerTabActivity.this).load(mediaTools.getFileFromInternalStorage(contact, Const.GALLERY_PUBLIC)).placeholder(R.drawable.silhouette).into(contactMainPic);
////                    }
//                    updateContactAndResetAdapter()
//                }
//            } else if (resultCode == Const.RESULT_UPDATE_FAILED) {
//                // Display Error Message that updating the data has failed.
//                tools.showMaterialSnackBar(
//                    mPager,
//                    getString(R.string.error_updating_contact),
//                    SnackBarType.ERROR
//                )
//            }
//        }
//        refreshContactData()
//    }

//    class NavigationAdapter  //        NavigationAdapter(FragmentManager fm, List<String> tabTitles, int position) {
//    //            super(fm);
//    //
//    //            mTabTitles = tabTitles;
//    //            mPosition = position;
//    //        }
//        (fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {
//        private val TAG = javaClass.simpleName
//        private var mScrollY = 0
//        private var mTabTitles: List<String>? = null
//        private var mPosition = 0
//        fun setData(tabTitles: List<String>?, position: Int) {
//            mTabTitles = tabTitles
//            mPosition = position
//        }
//
//        fun setScrollY(scrollY: Int) {
//            mScrollY = scrollY
//        }
//
//        fun setArguments(scrollY: Int) {
//            if (0 <= scrollY) {
////                Bundle args = new Bundle();
////                args.putInt(ARG_SCROLL_Y, scrollY);
//                setArguments(scrollY)
//            }
//        }
//
//        override fun getItem(position: Int): Fragment {
//            Log.d(TAG, "getItem POSITION: $position")
//            var f: Fragment? = null
//            when (position) {
//                0 -> {
//                    // OVERVIEW VIEW
//                    f = ContactViewOverviewFragment()
//                }
//                1 -> {
//                    // GALLERY VIEW
//                    f = ContactViewGalleryFragment()
//                }
//                2 -> {
//                    // CONTACT (EMAIL, PHONE, ADDRESSES)
//                    f = ContactViewContactFragment()
//                }
//                3 -> {
//                    // PERSONAL
//                    f = ContactViewPersonalFragment()
//                }
//                4 -> {
//                    // WORK
//                    f = ContactViewWorkFragment()
//                }
//                5 -> {
//                    // BODY VIEW
//                    f = ContactViewBodyFragment()
//                }
//            }
//            if (SpecificValues.SHOW_XRATED) {
//                when (position) {
//                    6 -> {
//                        // XXX
//                        f = ContactViewXxxStuffFragment()
//                    }
//                    7 -> {
//                        // ENCOUNTERS
//                        f = ContactViewEncountersFragment()
//                    }
//                    8 -> {
//                        // SOCIAL MEDIA
//                        f = ContactViewSocialMediaFragment()
//                    }
//                }
//            } else {
//                if (position == 6) {
//                    // ENCOUNTERS
//                    f = ContactViewEncountersFragment()
//                } else if (position == 7) {
//                    // SOCIAL MEDIA
//                    f = ContactViewSocialMediaFragment()
//                }
//            }
//            val args = Bundle()
//            //            args.putInt(RecentContactsTabRecyclerViewFragment.ARG_INITIAL_POSITION, 1);
////            analyticsBundle.putString(FirebaseAnalytics.Param.DESTINATION, "Recent Contacts Fragment");
//            f!!.arguments = args
//            return f
//        }
//
//        override fun getCount(): Int {
//            return mTabTitles!!.size
//        }
//
//        override fun getPageTitle(position: Int): CharSequence? {
//            return mTabTitles!![position]
//        }
//    }

    private fun refreshContactData() {
        Needle.onBackgroundThread().execute(object : UiRelatedTask<Int>() {
            override fun doWork(): Int {
                contact = contactDao!!.getContact(contactId)
                return 0
            }

            override fun thenDoUiRelatedWork(result: Int) {
                contactMainPic.load(
                    mediaTools.getFileFromInternalStorage(
                        contact, Const.GALLERY_PUBLIC
                    )
                ) {
                    crossfade(true)
                    placeholder(R.drawable.silhouette)
                }
            }
        })
    }

    fun getDisplayHeight(minusTabBarHeight: Boolean, minusAdBannerHeight: Boolean): Int {
        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)
        val width = metrics.widthPixels
        var height = metrics.heightPixels
        if (minusTabBarHeight) {
            val tabBarHeightInPixels = resources.getDimensionPixelSize(R.dimen.tab_height)
            // Also subtract another 10dp (which is the margin of the cardview)
            val carViewOuterMarginInPixels =
                resources.getDimensionPixelSize(R.dimen.contact_view_card_empty_tab_top_margin)
            height = height - tabBarHeightInPixels - carViewOuterMarginInPixels
        }
        if (minusAdBannerHeight) {
            val adBannerHeight =
                resources.getDimensionPixelSize(R.dimen.lower_padding_gray_view_for_contact_view_card)
            height -= adBannerHeight
        }
        return height
    }

    val innerCardPaddingInPixels: Int
        get() = resources.getDimensionPixelOffset(R.dimen.contact_view_card_inner_padding)

    //    @Subscribe
    //    public void onEvent(MediaAddedEvent result) {
    //        // New Images have been added. Reload the Fragments so that the gallery can refresh.
    //        // Technically this does not have to be an Event. Could also just call the method directly...
    //        if (result.isSucceeded()) {
    //            mPagerAdapter = new NavigationAdapter(getSupportFragmentManager(), mTabTitles, position);
    //            mPager.setAdapter(mPagerAdapter);
    //        }
    //    }
    //
    //    @Override
    //    protected void onStart() {
    //        super.onStart();
    //        if (!EventBus.getDefault().isRegistered(this)) {
    //            EventBus.getDefault().register(this);
    //        }
    //    }
    override fun onDestroy() {
        super.onDestroy()
        if (adView != null) {
            adView!!.destroy()
        }
        // TODO FIXME can maybe be removed. Tried this out to avoid memory leaks but forums say this will not solve the problem, just delay it.
//        Glide.get(this).clearMemory(); // call this method manual
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//        }
    }


}