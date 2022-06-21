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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import net.gazeapp.R
import net.gazeapp.R.dimen
import net.gazeapp.data.model.Work
import net.gazeapp.databinding.CardIncludeWorkKtBinding
import net.gazeapp.databinding.ContactviewTabWorkKtBinding
import net.gazeapp.utilities.GazeTools
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

class TabWorkFragmentKt : Fragment(R.layout.contactview_tab_work_kt) {

    @Inject
    lateinit var tools: GazeTools



    private lateinit var grayLayout: LinearLayout
    private lateinit var noInfoCard: CardView

    private var showNoInformationCard = false

    private var mFinalDurationText: String? = null

    private var contactId: Int = 0
    private lateinit var viewBinding: ContactviewTabWorkKtBinding

    companion object {
        private const val TAG = "TabWorkFragmentKt"
        private const val CONTACT_ID = "contactId"
        private const val MY_PERMISSIONS_REQUEST_CALL_PHONE = 1

        fun newInstance(contactId: Int) = TabWorkFragmentKt().apply {
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
        viewBinding = ContactviewTabWorkKtBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewBindings()

        viewModel.contactWithDetails.observe(viewLifecycleOwner) { contactWithDetails ->
            contactWithDetails.apply {
                updateUI(contactWithDetails.work)
            }
        }

        viewModel.hasEntries.observe(viewLifecycleOwner) { hasEntries ->
            if (hasEntries) {
                noInfoCard.visibility = View.GONE
            } else {
                noInfoCard.visibility = View.VISIBLE
            }
        }
    }

    private fun initViewBindings() {
        noInfoCard = viewBinding.noInfoCard
        grayLayout = viewBinding.grayLayout
    }

    private fun updateUI(workList: List<Work>?) {
        if (workList?.isNotEmpty() == true) {

            for ((_, _, professionStr, employer1, addressStr, started, ended, phoneStr, _, emailStr, salaryInt, currency, frequencyStr, notesStr) in workList) {
                val cardBinding =
                    CardIncludeWorkKtBinding.inflate(requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)

                val card: CardView = cardBinding.workCard
                val employer: TextView = cardBinding.employer
                val duration: TextView = cardBinding.duration
                val profession: TextView = cardBinding.profession
                val professionHint: TextView = cardBinding.professionHint
                val address: TextView = cardBinding.officeAddress
                val addressHint: TextView = cardBinding.officeAddressHint
                val phone: TextView = cardBinding.phone
                val phoneHint: TextView = cardBinding.phoneHint
                val email: TextView = cardBinding.email
                val emailHint: TextView = cardBinding.emailHint
                val salary: TextView = cardBinding.salary
                val salaryHint: TextView = cardBinding.salaryHint
                val frequency: TextView = cardBinding.frequency
                val notes: TextView = cardBinding.notes
                val notesHint: TextView = cardBinding.notesHint

                val cardLayoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                // Margins outer card from gray background
                cardLayoutParams.setMargins(
                    resources.getDimension(dimen.margin_standard).toInt(),
                    resources.getDimension(dimen.margin_standard).toInt(),
                    resources.getDimension(dimen.margin_standard).toInt(),
                    0
                )

                card.layoutParams = cardLayoutParams

                if (employer1 != null && employer1.isNotEmpty()) {
                    employer.text = employer1.trim { it <= ' ' }
                } else {
                    employer.setText(R.string.unknown_plain)
                }
                var startedStr: String? = null
                var endedStr: String? = null
                if (started != null) {
                    startedStr = tools.formatDateToPhoneLocaleMonthAndYearOnly(started, false)
                    Log.d(TAG, "started: $startedStr")
                }
                if (ended != null) {
                    endedStr = tools.formatDateToPhoneLocaleMonthAndYearOnly(ended, false)
                    Log.d(TAG, "ended: $endedStr")
                }

                // Only ENDED date is set
                if (started == null && ended != null) {
                    mFinalDurationText = resources.getString(R.string.until) + " " + endedStr
                }

                // Only STARTED date is set. Job is CURRENT JOB.
                if (started != null && ended == null) {
                    mFinalDurationText =
                        startedStr + " - " + resources.getString(R.string.until_current)
                }

                // Both STARTED & ENDED dates are set.
                if (started != null && ended != null) {
                    mFinalDurationText = "$startedStr - $endedStr"
                }
                if (started == null && ended == null) {
                    duration.visibility = View.GONE
                } else {
                    duration.text = mFinalDurationText
                }
                if (professionStr != null && professionStr.isNotEmpty()) {
                    profession.text = professionStr.trim { it <= ' ' }
                } else {
                    profession.visibility = View.GONE
                    professionHint.visibility = View.GONE
                }

                if (addressStr != null && addressStr.isNotEmpty()) {
                    address.text = addressStr.trim { it <= ' ' }
                    address.setOnClickListener {
                        val gmmIntentUri = Uri.parse("geo:0,0?q=$addressStr")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        startActivity(mapIntent)
                    }
                } else {
                    address.visibility = View.GONE
                    addressHint.visibility = View.GONE
                }

                if (phoneStr != null && phoneStr.isNotEmpty()) {
                    phone.text = phoneStr.trim { it <= ' ' }
                    phone.setOnClickListener {
                        val permissionCheck = ContextCompat.checkSelfPermission(
                            requireActivity(),
                            Manifest.permission.CALL_PHONE
                        )
                        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(
                                requireActivity(),
                                arrayOf(Manifest.permission.CALL_PHONE),
                                MY_PERMISSIONS_REQUEST_CALL_PHONE
                            )
                        } else {
                            makePhoneCall(phone.text.toString().replace("-".toRegex(), ""))
                        }
                    }
                } else {
                    phone.visibility = View.GONE
                    phoneHint.visibility = View.GONE
                }

                if (emailStr != null && emailStr.isNotEmpty()) {
                    email.text = emailStr.trim { it <= ' ' }
                    email.setOnClickListener {
                        val emailIntent = Intent(
                            Intent.ACTION_SENDTO,
                            Uri.fromParts("mailto", emailStr.trim { it <= ' ' }, null)
                        )
                        startActivity(
                            Intent.createChooser(
                                emailIntent,
                                getString(R.string.send_email_with).uppercase(Locale.getDefault()) + "..."
                            )
                        )
                    }
                } else {
                    email.visibility = View.GONE
                    emailHint.visibility = View.GONE
                }

                val nf = NumberFormat.getNumberInstance()
                if (salaryInt > 0) {
                    val moneyString = nf.format(salaryInt.toLong())
                    if (currency != null && currency.trim { it <= ' ' }.isNotEmpty()) {
                        salary.text = "$moneyString $currency"
                    } else {
                        salary.text = moneyString
                    }
                } else {
                    salary.visibility = View.GONE
                    salaryHint.visibility = View.GONE
                    frequency.visibility = View.GONE
                }
                if (frequencyStr != null && frequencyStr.trim { it <= ' ' }.isNotEmpty()) {
                    frequency.text = frequencyStr
                } else {
                    frequency.visibility = View.GONE
                }
                if (notesStr != null && notesStr.isNotEmpty()) {
                    notes.text = notesStr.trim { it <= ' ' }
                } else {
                    notes.visibility = View.GONE
                    notesHint.visibility = View.GONE
                }

                grayLayout.addView(cardBinding.root)
            }
        } else {
            showNoInformationCard = true
        }

        if (showNoInformationCard) {
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
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) { // If request is cancelled, the result arrays are empty.
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.

                // TODO make phone call directly here. at the moment the user has to click twice.
                //makePhoneCall("000");
            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                Toast.makeText(
                    requireActivity(), R.string.error_no_permission_for_email, Toast.LENGTH_LONG
                ).show()
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}