package net.gazeapp.ui.contactview

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import net.gazeapp.R
import net.gazeapp.data.model.Encounter
import net.gazeapp.databinding.CardIncludeEncounterKtBinding
import net.gazeapp.databinding.ContactviewTabEncountersKtBinding
import net.gazeapp.utilities.BuildSpecificValues
import net.gazeapp.utilities.GazeTools
import java.text.SimpleDateFormat
import javax.inject.Inject

class TabEncountersFragmentKt : Fragment(R.layout.contactview_tab_encounters_kt) {

    @Inject
    lateinit var tools: GazeTools

    private lateinit var grayLayout: LinearLayout
    private lateinit var noInfoCard: CardView

    private var showNoInformationCard = false

    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy")

    private var contactId: Int = 0
    private lateinit var viewBinding: ContactviewTabEncountersKtBinding

    companion object {
        private const val TAG = "TabEncountersFragmentKt"
        private const val CONTACT_ID = "contactId"

        fun newInstance(contactId: Int) = TabEncountersFragmentKt().apply {
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
        viewBinding = ContactviewTabEncountersKtBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewBindings()

        viewModel.contactWithDetails.observe(viewLifecycleOwner, { contactWithDetails ->
            contactWithDetails.apply {
                updateUI(contactWithDetails.encounters)
            }
        })

        viewModel.hasEntries.observe(viewLifecycleOwner, { hasEntries ->
            if (hasEntries) {
                noInfoCard.visibility = View.GONE
            } else {
                noInfoCard.visibility = View.VISIBLE
            }
        })
    }

    private fun initViewBindings() {
        noInfoCard = viewBinding.noInfoCard
        grayLayout = viewBinding.grayLayout
    }


    private fun updateUI(encounterList: List<Encounter>?) {
        if (encounterList?.isNotEmpty() == true) {
            for (encounter in encounterList) {
                val cardBinding =
                    CardIncludeEncounterKtBinding.inflate(requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)

                val card: CardView = cardBinding.encounterCard
                val ratingBar: RatingBar = cardBinding.ratingBar
                val meetDate: TextView = cardBinding.meetDate
                val meetLocationHint: TextView = cardBinding.meetLocationHint
                val meetLocation: TextView = cardBinding.meetLocation
                val locationDivier: View = cardBinding.locationDivier
                val googleMapsLink: TextView = cardBinding.googleMapsLink
                val myRoleHint: TextView = cardBinding.myRoleHint
                val myRole: TextView = cardBinding.myRole
                val hisRoleHint: TextView = cardBinding.hisRoleHint
                val hisRole: TextView = cardBinding.hisRole
                val safeSexHint: TextView = cardBinding.safeSexHint
                val safeSex: TextView = cardBinding.safeSex
                val myLoadHint: TextView = cardBinding.myLoadHint
                val myLoad: TextView = cardBinding.myLoad
                val hisLoadHint: TextView = cardBinding.hisLoadHint
                val hisLoad: TextView = cardBinding.hisLoad
                val remarksHint: TextView = cardBinding.remarksHint
                val remarks: TextView = cardBinding.remarks

                val cardLayoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                // Margins outer card from gray background
                cardLayoutParams.setMargins(
                    resources.getDimension(R.dimen.margin_standard).toInt(),
                    resources.getDimension(R.dimen.margin_standard).toInt(),
                    resources.getDimension(R.dimen.margin_standard).toInt(),
                    0
                )

                card.layoutParams = cardLayoutParams

                meetLocation.visibility = View.GONE
                googleMapsLink.visibility = View.GONE
                myRoleHint.visibility = View.GONE
                myRole.visibility = View.GONE
                hisRoleHint.visibility = View.GONE
                hisRole.visibility = View.GONE
                myLoadHint.visibility = View.GONE
                myLoad.visibility = View.GONE
                hisLoadHint.visibility = View.GONE
                hisLoad.visibility = View.GONE
                remarksHint.visibility = View.GONE
                remarks.visibility = View.GONE

                if (!encounter.meetLocation.isNullOrEmpty()) {
                    meetLocation.text = encounter.meetLocation?.trim { it <= ' ' }
                    meetLocation.visibility = View.VISIBLE
                }

                if (!encounter.googleMapsLink.isNullOrEmpty()) {
                    googleMapsLink.text = encounter.googleMapsLink?.trim { it <= ' ' }
                    googleMapsLink.visibility = View.VISIBLE
                }

                if (googleMapsLink.visibility == View.GONE && meetLocation.visibility == View.GONE) {
                    meetLocationHint.visibility = View.GONE
                    locationDivier.visibility = View.GONE
                }

                meetDate.text = tools.formatDateToPhoneLocale(encounter.meetDate, true)

                if (!encounter.myRole.isNullOrEmpty()) {
                    myRole.text = encounter.myRole
                    myRoleHint.visibility = View.VISIBLE
                    myRole.visibility = View.VISIBLE
                }

                if (!encounter.hisRole.isNullOrEmpty()) {
                    hisRole.text = encounter.hisRole
                    hisRoleHint.visibility = View.VISIBLE
                    hisRole.visibility = View.VISIBLE
                }

                if (encounter.isSafeSex) {
                    safeSex.text = getString(R.string.yes)
                } else {
                    safeSex.text = getString(R.string.no)
                }

                // TODO add checkbox "unsure" or something
                if (!encounter.sureAboutSafeSex) {
                    safeSex.text = getString(R.string.unsure)
                }

                if (!encounter.myLoadWentTo.isNullOrEmpty()) {
                    myLoad.text = encounter.myLoadWentTo
                    myLoadHint.visibility = View.VISIBLE
                    myLoad.visibility = View.VISIBLE
                }

                if (!encounter.hisLoadWentTo.isNullOrEmpty()) {
                    hisLoad.text = encounter.hisLoadWentTo
                    hisLoadHint.visibility = View.VISIBLE
                    hisLoad.visibility = View.VISIBLE
                }

                if (!encounter.remarks.isNullOrEmpty()) {
                    remarks.text = encounter.remarks
                    remarksHint.visibility = View.VISIBLE
                    remarks.visibility = View.VISIBLE
                }

                val rating = encounter.rating.toFloat() / 2
                if (rating == 0f) {
                    ratingBar.visibility = View.INVISIBLE
                } else {
                    ratingBar.rating = rating
                }

                if (!BuildSpecificValues.SHOW_XRATED) {
                    safeSexHint.visibility = View.GONE
                    safeSex.visibility = View.GONE
                    myRoleHint.visibility = View.GONE
                    myRole.visibility = View.GONE
                    hisRoleHint.visibility = View.GONE
                    hisRole.visibility = View.GONE
                    myLoadHint.visibility = View.GONE
                    myLoad.visibility = View.GONE
                    hisLoadHint.visibility = View.GONE
                    hisLoad.visibility = View.GONE
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


}