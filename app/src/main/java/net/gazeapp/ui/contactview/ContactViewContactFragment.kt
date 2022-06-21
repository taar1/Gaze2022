package net.gazeapp.ui.contactview

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import needle.Needle
import net.gazeapp.R
import net.gazeapp.data.GazeDatabase.Companion.getDatabase
import net.gazeapp.data.dao.*
import net.gazeapp.data.model.*
import net.gazeapp.databinding.ContactviewTabContactBinding
import net.gazeapp.helpers.Const
import net.gazeapp.utilities.GazeTools
import java.util.*
import javax.inject.Inject

class ContactViewContactFragment : Fragment() {

    @Inject
    lateinit var tools: GazeTools

    private var mContact: Contact? = null
    private var mPhone: List<Phone>? = null
    private var mEmail: List<Email>? = null
    private var mAddress: List<Address>? = null
    private var mWebsite: List<Website>? = null
    private var contactDao: ContactDao? = null
    private var phoneDao: PhoneDao? = null
    private var emailDao: EmailDao? = null
    private var addressDao: AddressDao? = null
    private var websiteDao: WebsiteDao? = null

    var outerLayout: ConstraintLayout? = null
    var noInformationCard: CardView? = null
    var addressCard: CardView? = null
    var phoneCard: CardView? = null
    var emailCard: CardView? = null
    var websiteCard: CardView? = null
    var addressLayout: LinearLayout? = null
    var addressContentLayout: LinearLayout? = null
    var phoneLayout: LinearLayout? = null
    var phoneContentLayout: LinearLayout? = null
    var emailLayout: LinearLayout? = null
    var emailContentLayout: LinearLayout? = null
    var websiteLayout: LinearLayout? = null
    var websiteContentLayout: LinearLayout? = null

    lateinit var viewBinding: ContactviewTabContactBinding

    companion object {
        private const val TAG = "ContactViewContactFragm"
        private const val MY_PERMISSIONS_REQUEST_CALL_PHONE = 1

        fun newInstance(): ContactViewContactFragment {
            return ContactViewContactFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = ContactviewTabContactBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch {
            loadData()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binViews()
    }

    private fun binViews() {
        outerLayout = viewBinding.outerLayout
        noInformationCard = viewBinding.noInfoCard
        addressCard = viewBinding.addressCard
        phoneCard = viewBinding.phoneCard
        emailCard = viewBinding.emailCard
        websiteCard = viewBinding.websiteCard
        addressLayout = viewBinding.addressLayout
        addressContentLayout = viewBinding.addressContentLayout
        phoneLayout = viewBinding.phoneLayout
        phoneContentLayout = viewBinding.phoneContentLayout
        emailLayout = viewBinding.emailLayout
        emailContentLayout = viewBinding.emailContentLayout
        websiteLayout = viewBinding.websiteLayout
        websiteContentLayout = viewBinding.websiteContentLayout
    }

    private suspend fun loadData() {
        withContext(Dispatchers.IO) {
            contactDao = getDatabase(requireActivity()).contactDao
            phoneDao = getDatabase(requireActivity()).phoneDao
            emailDao = getDatabase(requireActivity()).emailDao
            addressDao = getDatabase(requireActivity()).addressDao
            websiteDao = getDatabase(requireActivity()).websiteDao
            mPhone = phoneDao!!.getPhonesByContactId(mContact!!.id)
            mEmail = emailDao!!.getEmailsByContactId(mContact!!.id)
            mAddress = addressDao!!.getAddressesByContactId(mContact!!.id)
            mWebsite = websiteDao!!.getWebsitesByContactId(mContact!!.id)

            withContext(Dispatchers.Main) {
                updateUI()
            }
        }
    }

    private fun updateUI() {
        noInformationCard!!.visibility = View.GONE
        outerLayout!!.minimumHeight = DisplayMetrics().heightPixels

        // PHONE NUMBERS
        var deletePhoneLayout = false
        try {
            if (mPhone!!.isNotEmpty()) {
                val iter = mPhone!!.iterator()
                while (iter.hasNext()) {
                    val phone = iter.next()
                    Log.d(TAG, "Phone: " + phone.phoneNumber)
                    val etParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )

                    val hintText = TextView(activity)
                    hintText.layoutParams = etParams
                    hintText.setTextAppearance(R.style.Hint)
                    hintText.alpha = 0.5f
                    hintText.text = phone.phoneNumberType

                    val phoneText = TextView(activity)
                    phoneText.layoutParams = etParams
                    phoneText.setTextAppearance(R.style.Regular)
                    phoneText.setPadding(0, 0, 0, tools.innerCardPaddingInPixels)
                    phoneText.text = phone.phoneNumber
                    phoneText.setOnClickListener { arg0: View? ->
                        val permissionCheck: Int = ContextCompat.checkSelfPermission(
                            requireActivity(), Manifest.permission.CALL_PHONE
                        )
                        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(
                                requireActivity(),
                                arrayOf(Manifest.permission.CALL_PHONE),
                                MY_PERMISSIONS_REQUEST_CALL_PHONE
                            )
                        } else {
                            makePhoneCall(phone.phoneNumber.replace("-".toRegex(), ""))
                        }
                    }
                    phoneContentLayout!!.addView(hintText)
                    phoneContentLayout!!.addView(phoneText)
                    if (!iter.hasNext()) {
                        phoneText.setPadding(0, 0, 0, 0)
                    }
                }
            } else {
                deletePhoneLayout = true
            }
        } catch (e: Exception) {
            deletePhoneLayout = true
        }
        if (deletePhoneLayout) {
            phoneCard!!.visibility = View.GONE
        }

        // EMAIL ADDRESSES
        var deleteEmailLayout = false
        try {
            if (!mEmail!!.isEmpty()) {
                val iter = mEmail!!.iterator()
                while (iter.hasNext()) {
                    val email = iter.next()
                    Log.d(TAG, "Email: " + email.email)
                    val etParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    val hintText = TextView(activity)
                    hintText.layoutParams = etParams
                    hintText.setTextAppearance(R.style.Hint)
                    hintText.alpha = 0.5f
                    hintText.text = email.emailType
                    val emailText = TextView(activity)
                    emailText.layoutParams = etParams
                    emailText.setTextAppearance(R.style.Regular)
                    emailText.setPadding(0, 0, 0, tools.innerCardPaddingInPixels)
                    emailText.text = email.email
                    emailText.setOnClickListener {
                        val emailIntent: Intent = Intent(
                            Intent.ACTION_SENDTO,
                            Uri.fromParts("mailto", email.email, null)
                        )
                        startActivity(
                            Intent.createChooser(
                                emailIntent, getString(R.string.send_email_with).uppercase(
                                    Locale.getDefault()
                                ) + "..."
                            )
                        )
                    }
                    emailContentLayout!!.addView(hintText)
                    emailContentLayout!!.addView(emailText)
                    if (!iter.hasNext()) {
                        emailText.setPadding(0, 0, 0, 0)
                    }
                }
            } else {
                deleteEmailLayout = true
            }
        } catch (e: Exception) {
            deleteEmailLayout = true
        }
        if (deleteEmailLayout) {
            emailCard!!.visibility = View.GONE
        }

        // ADDRESSES
        var deleteAddressLayout = false
        try {
            if (mAddress!!.isNotEmpty()) {
                val iter = mAddress!!.iterator()
                while (iter.hasNext()) {
                    val address = iter.next()
                    Log.d(TAG, "Address: " + address.address)
                    val etParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    val hintText = TextView(activity)
                    hintText.layoutParams = etParams
                    hintText.setTextAppearance(R.style.Hint)
                    hintText.alpha = 0.5f
                    hintText.text = address.addressType
                    val addressText = TextView(activity)
                    addressText.layoutParams = etParams
                    addressText.setTextAppearance(R.style.Regular)
                    addressText.setPadding(0, 0, 0, tools.innerCardPaddingInPixels)
                    addressText.text = address.address
                    addressContentLayout!!.addView(hintText)
                    addressContentLayout!!.addView(addressText)
                    addressContentLayout!!.setOnClickListener {
                        val gmmIntentUri: Uri = Uri.parse("geo:0,0?q=" + address.address)
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        startActivity(mapIntent)
                    }
                    if (!iter.hasNext()) {
                        addressText.setPadding(0, 0, 0, 0)
                    }
                }
            } else {
                deleteAddressLayout = true
            }
        } catch (e: Exception) {
            deleteAddressLayout = true
        }
        if (deleteAddressLayout) {
            addressCard!!.visibility = View.GONE
        }

        // WEBSITES
        var deleteWebsiteLayout = false
        try {
            if (mWebsite!!.isNotEmpty()) {
                val iter = mWebsite!!.iterator()
                while (iter.hasNext()) {
                    val website = iter.next()
                    Log.d(TAG, "Website: " + website.website)
                    val etParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    if ((website.websiteType != null) && (website.websiteType!!.trim { it <= ' ' }
                            .isNotEmpty())) {
                        val hintText = TextView(activity)
                        hintText.layoutParams = etParams
                        hintText.setTextAppearance(R.style.Hint)
                        hintText.alpha = 0.5f
                        hintText.text = website.websiteType
                        websiteContentLayout!!.addView(hintText)
                    }
                    val websiteUrl = TextView(activity)
                    websiteUrl.layoutParams = etParams
                    websiteUrl.setTextAppearance(R.style.Regular)
                    websiteUrl.setPadding(0, 0, 0, tools.innerCardPaddingInPixels)
                    websiteUrl.text = website.website
                    websiteUrl.setOnClickListener(View.OnClickListener {
                        var url = website.website
                        if (!url.startsWith("http://") && !url.startsWith("https://")) {
                            url = "http://$url"
                        }
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(browserIntent)
                    })
                    websiteContentLayout!!.addView(websiteUrl)
                    if ((website.description != null) && (website.description!!.trim { it <= ' ' }
                            .isNotEmpty())) {
                        val description = TextView(activity)
                        description.layoutParams = etParams
                        description.setTextAppearance(R.style.Hint)
                        description.isAllCaps = false
                        description.setPadding(
                            0,
                            0,
                            0,
                            tools.innerCardPaddingInPixels
                        )
                        websiteUrl.setPadding(0, 0, 0, 0)
                        description.alpha = 0.5f
                        description.text = website.description
                        websiteContentLayout!!.addView(description)
                    }
                    if (!iter.hasNext()) {
                        websiteUrl.setPadding(0, 0, 0, 0)
                    }
                }
                val viewLayoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, Math.round(
                        resources.getDimension(R.dimen.lower_ad_banner_height)
                    )
                )
                val lowerPaddingView = View(activity)
                lowerPaddingView.layoutParams = viewLayoutParams
                addToGrayLayout(lowerPaddingView)
            } else {
                deleteWebsiteLayout = true
            }
        } catch (e: Exception) {
            deleteWebsiteLayout = true
        }
        if (deleteWebsiteLayout) {
            websiteCard!!.visibility = View.GONE
        }

        // Show "no information" text
        if (deletePhoneLayout && deleteEmailLayout && deleteWebsiteLayout) {
            val noInformationLayoutCorrectHeight = Math.round(
                DisplayMetrics().heightPixels - tools.convertPxToDp(
                    Const.HEIGHT_TAB_BAR_AND_AD_BANNER
                )
            )
            noInformationCard!!.visibility = View.VISIBLE
        } else {
            noInformationCard!!.visibility = View.GONE
        }
    }

    fun addToGrayLayout(v: View?) {
        Needle.onMainThread().execute { outerLayout!!.addView(v) }
    }

    private fun makePhoneCall(phoneNumber: String) {
        val phone_no = phoneNumber.replace("-".toRegex(), "")
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        callIntent.data = Uri.parse("tel:$phone_no")
        startActivity(callIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CALL_PHONE -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    // TODO make phone call directly here. at the moment the user has to click twice.
                    //makePhoneCall("000");
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(
                        activity,
                        R.string.error_no_permission_for_email,
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
        }
    }

    //    @Override
    //    protected void updateFlexibleSpace(int scrollY) {
    //        // Sometimes scrollable.getCurrentScrollY() and the real scrollY has different values.
    //        // As a workaround, we should call scrollVerticallyTo() to make sure that they match.
    //        Scrollable s = getScrollable();
    //        s.scrollVerticallyTo(scrollY);
    //
    //        // If scrollable.getCurrentScrollY() and the real scrollY has the same values,
    //        // calling scrollVerticallyTo() won't invoke scroll (or onScrollChanged()), so we call it here.
    //        // Calling this twice is not a problem as long as updateFlexibleSpace(int, View) has idempotence.
    //        updateFlexibleSpace(scrollY, getView());
    //    }
    //
    //    @Override
    //    protected void updateFlexibleSpace(int scrollY, View view) {
    //        ObservableScrollView scrollView = (ObservableScrollView) view.findViewById(R.id.scroll);
    //
    //        // Also pass this event to parent Activity
    //        ContactViewWithViewPagerTabActivity parentActivity =
    //                (ContactViewWithViewPagerTabActivity) activity;
    //        if (parentActivity != null) {
    //            parentActivity.onScrollChanged(scrollY, scrollView);
    //        }
    //    }
    //
    //    private void updateScrollview() {
    //        scrollView.setTouchInterceptionViewGroup(fragmentRoot);
    //
    //        // Scroll to the specified offset after layout
    //        Bundle args = getArguments();
    //        if (args != null && args.containsKey(ARG_SCROLL_Y)) {
    //            final int scrollY = args.getInt(ARG_SCROLL_Y, 0);
    //            ScrollUtils.addOnGlobalLayoutListener(scrollView, new Runnable() {
    //                @Override
    //                public void run() {
    //                    scrollView.scrollTo(0, scrollY);
    //                }
    //            });
    //            updateFlexibleSpace(scrollY, view);
    //        } else {
    //            updateFlexibleSpace(0, view);
    //        }
    //
    //        scrollView.setScrollViewCallbacks(this);
    //    }


}