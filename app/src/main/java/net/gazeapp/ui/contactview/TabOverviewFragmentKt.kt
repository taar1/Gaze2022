package net.gazeapp.ui.contactview

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import net.gazeapp.R
import net.gazeapp.data.model.*
import net.gazeapp.databinding.ContactviewTabOverviewBinding
import net.gazeapp.helpers.SnackBarType
import net.gazeapp.utilities.BuildSpecificValues
import net.gazeapp.utilities.GazeTools
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class TabOverviewFragmentKt : Fragment(R.layout.contactview_tab_overview) {

    @Inject
    lateinit var tools: GazeTools

    private lateinit var outerLayout: ConstraintLayout
    private lateinit var viewPadding1: View
    private lateinit var viewPadding2: View
    private lateinit var viewPadding3: View

    private lateinit var infoCardsGroup: Group
    private lateinit var noInfoCard: CardView
    private lateinit var infoCard: CardView
    private lateinit var contactName: TextView
    private lateinit var nicknames: TextView
    private lateinit var metInPersonIcon: ImageView
    private lateinit var additionalInfo: TextView
    private lateinit var additionalInfoHint: TextView
    private lateinit var age: TextView
    private lateinit var ageHint: TextView
    private lateinit var birthdate: TextView
    private lateinit var height: TextView
    private lateinit var heightHint: TextView
    private lateinit var weight: TextView
    private lateinit var weightHint: TextView
    private lateinit var endowmentTextView: TextView
    private lateinit var endowmentHint: TextView
    private lateinit var noteCard: CardView
    private lateinit var notesTextView: TextView
    private lateinit var labelsLayout: LinearLayout

    private lateinit var additionalInfoGroup: Group
    private lateinit var ageGroup: Group
    private lateinit var bodyGroup: Group
    private lateinit var endowmentGroup: Group

    private var contactId: Int = 0

    private var nicknameList: List<Nickname>? = null
    private var notes: Note? = null
    private var mNicknamesJoined: String? = ""
    private var mAge: String? = ""
    private var mBirthdate: String? = ""
    private var mHeight: String? = ""
    private var mWeight: String? = ""
    private var mSexyInfo: String? = ""
    private var mNote: String? = ""

    private lateinit var viewBinding: ContactviewTabOverviewBinding

    companion object {
        private const val TAG = "OverviewFragmentKt+"
        private const val CONTACT_ID = "contactId"

        fun newInstance(contactId: Int) = TabOverviewFragmentKt().apply {
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
        viewBinding = ContactviewTabOverviewBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewBindings()

        viewModel.contactWithDetails.observe(viewLifecycleOwner, { contactWithDetails ->
            Log.d(TAG, "XXXXX onViewCreated: contactWithDetails: $contactWithDetails")

            contactWithDetails.apply {
                prepareData(
                    contactWithDetails.contact,
                    contactWithDetails.body,
                    contactWithDetails.personal,
                    contactWithDetails.nicknames,
                    contactWithDetails.endowment,
                    contactWithDetails.xxx,
                    contactWithDetails.note
                )
            }
        })

        viewModel.hasEntries.observe(viewLifecycleOwner, { hasEntries ->
            Log.d(TAG, "XXXXX onViewCreated: hasEntries ${hasEntries}")
            if (hasEntries) {
                noInfoCard.visibility = View.GONE
                infoCardsGroup.visibility = View.VISIBLE
            } else {
                noInfoCard.visibility = View.VISIBLE
                infoCardsGroup.visibility = View.GONE
            }
        })
    }

    private fun initViewBindings() {
        infoCardsGroup = viewBinding.infoCardsGroup
        outerLayout = viewBinding.outerLayout
        noInfoCard = viewBinding.noInfoCard
        infoCard = viewBinding.infoCard
        viewPadding1 = viewBinding.viewPadding1
        viewPadding2 = viewBinding.viewPadding2
        viewPadding3 = viewBinding.viewPadding3
        contactName = viewBinding.contactName
        nicknames = viewBinding.nicknames
        metInPersonIcon = viewBinding.metInPersonIcon
        additionalInfo = viewBinding.additionalInfo
        additionalInfoHint = viewBinding.additionalInfoHint
        age = viewBinding.age
        ageHint = viewBinding.ageHint
        birthdate = viewBinding.birthdate
        height = viewBinding.height
        heightHint = viewBinding.heightHint
        weight = viewBinding.weight
        weightHint = viewBinding.weightHint
        endowmentTextView = viewBinding.endowment
        endowmentHint = viewBinding.endowmentHint
        noteCard = viewBinding.noteCard
        notesTextView = viewBinding.notes
        labelsLayout = viewBinding.labelsLayout
        additionalInfoGroup = viewBinding.additionalInfoGroup
        ageGroup = viewBinding.ageGroup
        bodyGroup = viewBinding.bodyGroup
        endowmentGroup = viewBinding.endowmentGroup
    }

    private fun prepareData(
        contact: Contact,
        body: Body?,
        personal: Personal?,
        nicknameList: List<Nickname>?,
        endowment: Endowment?,
        xxx: Xxx?,
        notes: Note?
    ) {
        // CONTACT NAME
        if (contact.contactName?.isNotEmpty() == true) {
            contactName.text = contact.contactName?.uppercase(Locale.getDefault())
        } else {
            contactName.text = "???"
        }

        // ADDITIONAL INFO
        additionalInfoGroup.visibility = View.GONE
        contact.additionalInfo?.let {
            additionalInfo.text = contact.additionalInfo
            additionalInfoGroup.visibility = View.VISIBLE
        }

        // MET IN PERSON
        if (contact.isMetInPerson) {
            metInPersonIcon.visibility = View.VISIBLE

            metInPersonIcon.setOnClickListener {
                tools.showMaterialSnackBar(
                    outerLayout,
                    getString(R.string.met_in_person_text, contact.contactName),
                    SnackBarType.SUCCESS,
                    2000
                )
            }
        } else {
            metInPersonIcon.visibility = View.GONE
        }

        // NICKNAMES
        if (nicknameList?.isNotEmpty() == true) {
            val nicknameNameList: List<String> = nicknameList.map { it.nickname }
            mNicknamesJoined = nicknameNameList.joinToString(", ")
        }

        // AGE
        if (personal?.age != 0 && personal?.age != null) {
            mAge = getString(R.string.x_years_old, personal.age)
        }

        if (personal?.birthdate != null) {
            mBirthdate =
                tools.formatDateToPhoneLocale(personal.birthdate, false)
            mAge = getString(R.string.x_years_old, tools.getAge(personal.birthdate))
        }

        // HEIGHT
        if (body?.height != 0 && body?.height != null) {
            mHeight = tools.convertHeightFromCentimetersToReadableFormat(
                body.height, tools.useMetricSystem()
            )
        }

        // WEIGHT
        if (body?.weight != 0 && body?.weight != null) {
            mWeight = tools.convertWeightFromGramsToReadableFormat(
                body.weight, tools.useMetricSystem()
            )
        }

        // ENDOWMENT
        val endowmentRoleList = ArrayList<String?>()
        if (endowment?.length != 0 && endowment?.length != null) {
            endowmentRoleList.add(
                tools.convertFromMillimetersToReadableFormat(
                    endowment.length,
                    tools.useMetricSystem()
                )
            )
        }

        if (!xxx?.sexRole.isNullOrEmpty()) {
            endowmentRoleList.add(xxx?.sexRole)
        }

        // SEXY INFO
        mSexyInfo = endowmentRoleList.joinToString(" / ")

        // NOTES
        mNote = notes?.note.toString()

        updateUI()
    }

    private fun updateUI() {
        // NICKNAMES
        if (mNicknamesJoined!!.isNotEmpty()) {
            nicknames.text = mNicknamesJoined
        } else {
            nicknames.visibility = View.GONE
        }

        // AGE
        age.visibility = View.GONE
        if (mAge != null) {
            age.text = mAge
            age.visibility = View.VISIBLE
        }

        // BRITHDATE
        if (mBirthdate!!.isNotEmpty()) {
            birthdate.text = mBirthdate
        } else {
            birthdate.visibility = View.GONE
        }
        if (age.visibility == View.GONE && birthdate.visibility == View.GONE) {
            ageGroup.visibility = View.GONE
            ageHint.visibility = View.GONE
        }

        // HEIGHT
        if (mHeight!!.isNotEmpty()) {
            height.text = mHeight
        } else {
            height.visibility = View.GONE
            heightHint.visibility = View.GONE
        }

        // WEIGHT
        if (mWeight!!.isNotEmpty()) {
            weight.text = mWeight
        } else {
            weight.visibility = View.GONE
            weightHint.visibility = View.GONE
        }
        if (height.visibility != View.GONE && weight.visibility == View.GONE) {
            height.setPadding(0, 0, 0, 0)
        }
        if (height.visibility == View.GONE && weight.visibility != View.GONE) {
            weight.setPadding(0, 0, 0, 0)
        }
        if (height.visibility == View.GONE && weight.visibility == View.GONE) {
            bodyGroup.visibility = View.GONE
        }

        // ENDOWMENT
        if (mSexyInfo!!.isNotEmpty()) {
            endowmentTextView.text = mSexyInfo
        } else {
            endowmentGroup.visibility = View.GONE
            endowmentHint.visibility = View.GONE
        }
        if (!BuildSpecificValues.SHOW_XRATED) {
            endowmentGroup.visibility = View.GONE
        }

        // NOTES
        if (mNote!!.isNotEmpty()) {
            notesTextView.text = mNote
        } else {
            noteCard.visibility = View.GONE
        }

        /*
          DIVIDERS
         */
        // Show divider ADDITIONAL INFO & AGE
        if (additionalInfoGroup.visibility == View.GONE && ageGroup.visibility != View.GONE) {
            val tmpPadding =
                Math.round(resources.getDimension(R.dimen.contact_view_card_inner_padding))
            ageGroup.setPadding(tmpPadding, 140, tmpPadding, tmpPadding)
        }
        if (additionalInfoGroup.visibility != View.GONE && ageGroup.visibility != View.GONE) {
            viewPadding1.visibility = View.VISIBLE
        }

        // Show divider ADDITIONAL INFO & BODY INFO
        if (additionalInfoGroup.visibility != View.GONE && bodyGroup.visibility != View.GONE && ageGroup.visibility == View.GONE) {
            viewPadding1.visibility = View.VISIBLE
        }

        // Show divider ADDITIONAL INFO & ENDOWMENT
        if (additionalInfoGroup.visibility != View.GONE && bodyGroup.visibility != View.GONE && ageGroup.visibility == View.GONE && endowmentGroup.visibility != View.GONE) {
            viewPadding1.visibility = View.VISIBLE
        }

        // Show divider AGE & BODY INFO
        if (ageGroup.visibility != View.GONE && bodyGroup.visibility != View.GONE) {
            viewPadding2.visibility = View.VISIBLE
        }

        // Show divider AGE & ENDOWMENT
        if (ageGroup.visibility != View.GONE && bodyGroup.visibility != View.GONE && endowmentGroup.visibility != View.GONE) {
            viewPadding2.visibility = View.VISIBLE
        }
        if (ageGroup.visibility != View.GONE && endowmentGroup.visibility != View.GONE) {
            viewPadding3.visibility = View.VISIBLE
        }

        // Show divider BODY INFO & ENDOWMENT
        if (bodyGroup.visibility != View.GONE && endowmentGroup.visibility != View.GONE) {
            viewPadding3.visibility = View.VISIBLE
        }

        // Show "no information" text
        if (additionalInfoGroup.visibility == View.GONE && ageGroup.visibility == View.GONE && bodyGroup.visibility == View.GONE && endowmentGroup.visibility == View.GONE && noteCard.visibility == View.GONE) {
            noInfoCard.visibility = View.VISIBLE
        } else {
            noInfoCard.visibility = View.GONE
        }
    }


}