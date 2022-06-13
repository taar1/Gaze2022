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

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import net.gazeapp.R
import net.gazeapp.data.model.Address
import net.gazeapp.data.model.Email
import net.gazeapp.data.model.Phone
import net.gazeapp.data.model.Website
import net.gazeapp.databinding.ContactviewTabContactBinding
import java.util.*

@AndroidEntryPoint
class TabContactFragmentKt : Fragment(R.layout.contactview_tab_contact) {
    private val MY_PERMISSIONS_REQUEST_CALL_PHONE = 1

    private var mPhone: List<Phone>? = null
    private var mEmail: List<Email>? = null
    private var mAddress: List<Address>? = null
    private var mWebsite: List<Website>? = null

    private lateinit var outerLayout: ConstraintLayout
    private lateinit var infoCardsGroup: Group
    private lateinit var noInfoCard: CardView
    private lateinit var addressCard: CardView
    private lateinit var phoneCard: CardView
    private lateinit var emailCard: CardView
    private lateinit var websiteCard: CardView
    private lateinit var addressLayout: LinearLayout
    private lateinit var addressContentLayout: LinearLayout
    private lateinit var phoneLayout: LinearLayout
    private lateinit var phoneContentLayout: LinearLayout
    private lateinit var emailLayout: LinearLayout
    private lateinit var emailContentLayout: LinearLayout
    private lateinit var websiteLayout: LinearLayout
    private lateinit var websiteContentLayout: LinearLayout

    private var contactId: Int = 0
    private lateinit var viewBinding: ContactviewTabContactBinding

    companion object {
        private const val TAG = "ContactViewContactFragmentKt+"
        private const val CONTACT_ID = "contactId"

        fun newInstance(contactId: Int) = TabContactFragmentKt().apply {
            arguments = bundleOf(CONTACT_ID to contactId)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        arguments?.getInt(CONTACT_ID)?.let {
            contactId = it
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
        viewBinding = ContactviewTabContactBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewBindings()

        viewModel.contactWithDetails.observe(viewLifecycleOwner) { contactWithDetails ->
            Log.d(TAG, "XXXXX onViewCreated: contactWithDetails: $contactWithDetails")

            contactWithDetails.apply {
                mPhone = contactWithDetails.phones
                mEmail = contactWithDetails.emails
                mAddress = contactWithDetails.addresses
                mWebsite = contactWithDetails.websites

                updateUI(mPhone, mEmail, mAddress, mWebsite)
            }
        }

        viewModel.hasEntries.observe(viewLifecycleOwner) { hasEntries ->
            if (hasEntries) {
                noInfoCard.visibility = View.GONE
                infoCardsGroup.visibility = View.VISIBLE
            } else {
                noInfoCard.visibility = View.VISIBLE
                infoCardsGroup.visibility = View.GONE
            }
        }
    }

    private fun initViewBindings() {
        outerLayout = viewBinding.outerLayout
        infoCardsGroup = viewBinding.infoCardsGroup
        noInfoCard = viewBinding.noInfoCard
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


    fun updateUI(
        mPhone: List<Phone>?,
        mEmail: List<Email>?,
        mAddress: List<Address>?,
        mWebsite: List<Website>?
    ) {
        noInfoCard.visibility = View.GONE

        // PHONE NUMBERS
        var deletePhoneLayout = false
        try {
            if (mPhone?.isNotEmpty() == true) {
                val iter = mPhone.iterator()
                while (iter.hasNext()) {
                    val phone = iter.next()
                    Log.d(TAG, "Phone: " + phone.phoneNumber)

                    val etParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )

                    val hintText = TextView(activity)
                    hintText.layoutParams = etParams
                    hintText.setTextAppearance(R.style.TextHint)
                    hintText.alpha = 0.5f
                    hintText.text = phone.phoneNumberType

                    val phoneText = TextView(activity)
                    phoneText.layoutParams = etParams
                    phoneText.setTextAppearance(R.style.Text.Regular)
                    phoneText.setPadding(0, 0, 0, 48)
                    phoneText.text = phone.phoneNumber

                    phoneText.setOnClickListener {
                        val permissionCheck: Int = ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.CALL_PHONE
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
                    phoneContentLayout.addView(hintText)
                    phoneContentLayout.addView(phoneText)
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
            phoneCard.visibility = View.GONE
        }


        // EMAIL ADDRESSES
        var deleteEmailLayout = false
        try {
            if (mEmail?.isNotEmpty() == true) {
                val iter = mEmail.iterator()
                while (iter.hasNext()) {
                    val email = iter.next()
                    Log.d(TAG, "Email: " + email.email)

                    val etParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )

                    val hintText = TextView(activity)
                    hintText.layoutParams = etParams
                    hintText.setTextAppearance(R.style.TextHint)
                    hintText.alpha = 0.5f
                    hintText.text = email.emailType

                    val emailText = TextView(activity)
                    emailText.layoutParams = etParams
                    emailText.setTextAppearance(R.style.Text.Regular)
                    emailText.setPadding(0, 0, 0, 48)
                    emailText.text = email.email

                    emailText.setOnClickListener {
                        val emailIntent = Intent(
                            Intent.ACTION_SENDTO,
                            Uri.fromParts("mailto", email.email, null)
                        )
                        startActivity(
                            Intent.createChooser(
                                emailIntent,
                                getString(R.string.send_email_with).uppercase(Locale.getDefault()) + "..."
                            )
                        )
                    }
                    emailContentLayout.addView(hintText)
                    emailContentLayout.addView(emailText)
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
            emailCard.visibility = View.GONE
        }

        // ADDRESSES
        var deleteAddressLayout = false
        try {
            if (mAddress?.isNotEmpty() == true) {
                val iter = mAddress.iterator()
                while (iter.hasNext()) {
                    val address = iter.next()
                    Log.d(TAG, "Address: " + address.address)
                    val etParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    val hintText = TextView(activity)
                    hintText.layoutParams = etParams
                    hintText.setTextAppearance(R.style.TextHint)
                    hintText.alpha = 0.5f
                    hintText.text = address.addressType

                    val addressText = TextView(activity)
                    addressText.layoutParams = etParams
                    addressText.setTextAppearance(R.style.Text.Regular)
                    addressText.setPadding(0, 0, 0, 48)
                    addressText.text = address.address

                    addressContentLayout.addView(hintText)
                    addressContentLayout.addView(addressText)

                    addressContentLayout.setOnClickListener {
                        val gmmIntentUri: Uri = Uri.parse("geo:0,0?q=" + address.address)
                        val mapIntent: Intent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
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
            addressCard.visibility = View.GONE
        }

        // WEBSITES
        var deleteWebsiteLayout = false
        try {
            if (mWebsite?.isNotEmpty() == true) {
                val iter = mWebsite.iterator()
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
                        hintText.setTextAppearance(R.style.TextHint)
                        hintText.alpha = 0.5f
                        hintText.text = website.websiteType
                        websiteContentLayout.addView(hintText)
                    }

                    val websiteUrl = TextView(activity)
                    val description = TextView(activity)
                    websiteUrl.layoutParams = etParams
                    websiteUrl.setTextAppearance(R.style.Text.Regular)
                    websiteUrl.setPadding(0, 0, 0, 48)
                    websiteUrl.text = website.website
                    websiteUrl.setOnClickListener {
                        var url = website.website
                        if (!url.startsWith("http://") && !url.startsWith("https://")) {
                            url = "http://$url"
                        }
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(browserIntent)
                    }
                    websiteContentLayout.addView(websiteUrl)
                    if ((website.description != null) && (website.description!!.trim { it <= ' ' }
                            .isNotEmpty())) {
                        description.layoutParams = etParams
                        description.setTextAppearance(R.style.TextHint)
                        description.isAllCaps = false
                        description.setPadding(0, 0, 0, 48)
                        websiteUrl.setPadding(0, 0, 0, 0)
                        description.alpha = 0.5f
                        description.text = website.description
                        websiteContentLayout.addView(description)
                    }
                    if (!iter.hasNext()) {
                        websiteUrl.setPadding(0, 0, 0, 0)
                        description.setPadding(0, 0, 0, 0)
                    }
                }
            } else {
                deleteWebsiteLayout = true
            }
        } catch (e: Exception) {
            deleteWebsiteLayout = true
        }
        if (deleteWebsiteLayout) {
            websiteCard.visibility = View.GONE
        }

        // Show "no information" text
        if (deletePhoneLayout && deleteEmailLayout && deleteWebsiteLayout) {
            noInfoCard.visibility = View.VISIBLE
        } else {
            noInfoCard.visibility = View.GONE
        }
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

}