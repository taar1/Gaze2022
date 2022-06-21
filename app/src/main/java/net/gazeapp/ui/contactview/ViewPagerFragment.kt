package net.gazeapp.ui.contactview

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.facebook.ads.AdSize
import com.facebook.ads.AdView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import net.gazeapp.R
import net.gazeapp.data.model.Contact
import net.gazeapp.data.model.ContactWithDetails
import net.gazeapp.databinding.ContactDetailViewPagerLayoutBinding
import net.gazeapp.dialog.FreeVersionDialogs
import net.gazeapp.helpers.Const
import net.gazeapp.helpers.Preferences
import net.gazeapp.utilities.GazeTools
import net.gazeapp.utilities.MediaTools
import net.gazeapp.utilities.SpecificValues
import javax.inject.Inject

@AndroidEntryPoint
class ViewPagerFragment : Fragment(R.layout.contact_detail_view_pager_layout) {

    @Inject
    lateinit var tools: GazeTools

    @Inject
    lateinit var mediaTools: MediaTools

    private lateinit var viewBinding: ContactDetailViewPagerLayoutBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2

    lateinit var toolbar: Toolbar
    lateinit var collapsingToolbar: CollapsingToolbarLayout
    lateinit var facebookBanner: LinearLayout
    lateinit var adView: AdView
    lateinit var contactMainPic: ImageView
    lateinit var floatingActionButton: FloatingActionButton

    var freeVersionDialogs: FreeVersionDialogs? = null

    var contactId: Int = 0
    var position: Int = 0

    var contact: Contact? = null

    companion object {
        private const val TAG = "ContactDetailViewFragment"
        private const val CONTACT_ID = "contactId"
        private const val POSITION = "position"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        arguments?.getInt(CONTACT_ID)?.let {
            contactId = it
        }

        // If position is set, jump to that tab (like the gallery) automatically
        arguments?.getInt(POSITION)?.let {
            position = it
        }
    }

    val viewModel: ContactViewModel by viewModels {
        ContactViewModelFactory(
            contactId, Application()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = ContactDetailViewPagerLayoutBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPagerAdapter = ViewPagerAdapter(requireActivity(), contactId)
        viewPagerAdapter.tabTitles = tabTitles

        freeVersionDialogs = FreeVersionDialogs(requireContext())
        initViewBindings()

        viewModel.contactWithDetails.observe(viewLifecycleOwner) { contactWithDetails ->
            initPaging()
            updateUI(contactWithDetails)
        }

        viewModel.hasEntries.observe(viewLifecycleOwner) { hasNoEntries ->
            viewPager.isEnabled = !hasNoEntries
        }
    }

    private fun initViewBindings() {
        viewPager = viewBinding.viewPager
        facebookBanner = viewBinding.bannerContainer
        collapsingToolbar = viewBinding.collapsingToolbar
        toolbar = viewBinding.toolbar
        contactMainPic = viewBinding.mainPic
        floatingActionButton = viewBinding.floatingActionButton
    }

    private fun initPaging() {
        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(viewBinding.tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        viewPager.currentItem = position
    }

    private fun updateUI(contactWithDetails: ContactWithDetails) {
        initFacebookAds()

        contactWithDetails.media?.let { mList ->
            for (media in mList) {
                if (contactWithDetails.contact.mainPicId == media.id) {
                    contactMainPic.load(media.fullPath) {
                        placeholder(R.drawable.silhouette)
                    }
                    break
                }
            }
        }

        // Is Pro User? Display Ads?
        if (tools.getBoolean(Preferences.IS_PRO_USER, Preferences.IS_PRO_USER_DEFAULT_VALUE)
        ) {
            // If Pro user wants ads, show them now.
            if (tools.getBoolean(
                    Preferences.IS_ADS_ENABLED, Preferences.IS_ADS_ENABLED_DEFAULT_VALUE
                )
            ) {
                adView.loadAd()
                facebookBanner.visibility = View.VISIBLE
            } else {
                facebookBanner.visibility = View.GONE
            }
        } else {
            adView.loadAd()
            facebookBanner.visibility = View.VISIBLE
        }
    }

    private fun initFacebookAds() {
        adView = AdView(
            requireContext(),
            Const.FACEBOOK_AUDIENCE_NETWORK_NATIVE_BANNER_ID,
            AdSize.BANNER_HEIGHT_50
        )
        facebookBanner.addView(adView)
    }

    private val tabTitles: List<String>
        get() {
            val tabTitles: MutableList<String> = ArrayList()
            tabTitles.add(
                requireContext().resources.getString(R.string.tab_overview).uppercase()
            )
            tabTitles.add(
                requireContext().resources.getString(R.string.tab_gallery).uppercase()
            )
            tabTitles.add(
                requireContext().resources.getString(R.string.tab_contact).uppercase()
            )
            tabTitles.add(
                requireContext().resources.getString(R.string.tab_personal).uppercase()
            )
            tabTitles.add(
                requireContext().resources.getString(R.string.tab_professional).uppercase()
            )
            tabTitles.add(requireContext().resources.getString(R.string.tab_body).uppercase())
            if (SpecificValues.SHOW_XRATED) {
                tabTitles.add(
                    requireContext().resources.getString(R.string.tab_sex).uppercase()
                )
            }
            tabTitles.add(
                requireContext().resources.getString(R.string.tab_encounters).uppercase()
            )
            tabTitles.add(
                requireContext().resources.getString(R.string.tab_social_media).uppercase()
            )
            return tabTitles
        }

    override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }

}