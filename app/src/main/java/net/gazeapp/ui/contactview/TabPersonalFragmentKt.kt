package net.gazeapp.ui.contactview

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import net.gazeapp.R
import net.gazeapp.data.model.Drug
import net.gazeapp.data.model.Personal
import net.gazeapp.databinding.ContactviewTabPersonalKtBinding
import net.gazeapp.utilities.GazeTools
import net.gazeapp.utilities.SpecificValues
import javax.inject.Inject

@AndroidEntryPoint
class TabPersonalFragmentKt : Fragment(R.layout.contactview_tab_personal_kt) {

    @Inject
    lateinit var tools: GazeTools

    private var mPersonal: Personal? = null
    private var mDrugs: List<Drug>? = null

    private lateinit var outerLayout: ConstraintLayout
    private lateinit var infoLayout: ConstraintLayout
    private lateinit var noInfoCard: CardView
    private lateinit var generalCard: CardView
    private lateinit var relationshipCard: CardView
    private lateinit var drugsCard: CardView
    private lateinit var appearanceBar: LinearLayout
    private lateinit var isOutHint: TextView
    private lateinit var isOut: TextView
    private lateinit var barMasculine: View
    private lateinit var barFeminine: View
    private lateinit var relationshipHint: TextView
    private lateinit var relationship: TextView
    private lateinit var childrenHint: TextView
    private lateinit var children: TextView
    private lateinit var smokingHint: TextView
    private lateinit var smoking: TextView
    private lateinit var alcoholHint: TextView
    private lateinit var alcohol: TextView
    private lateinit var drugsHint: TextView
    private lateinit var drugs: TextView

    private var contactId: Int = 0
    private lateinit var viewBinding: ContactviewTabPersonalKtBinding

    companion object {
        private const val TAG = "ContactViewPersonalFragmentKt"
        private const val CONTACT_ID = "contactId"

        fun newInstance(contactId: Int) = TabPersonalFragmentKt().apply {
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
        viewBinding = ContactviewTabPersonalKtBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewBindings()

        viewModel.contactWithDetails.observe(viewLifecycleOwner, { contactWithDetails ->
            contactWithDetails.apply {
                mPersonal = contactWithDetails.personal
                mDrugs = contactWithDetails.drugs

                updateUI(mPersonal, mDrugs)
            }
        })

        viewModel.hasEntries.observe(viewLifecycleOwner, { hasEntries ->
            if (hasEntries) {
                noInfoCard.visibility = View.GONE
                infoLayout.visibility = View.VISIBLE
            } else {
                noInfoCard.visibility = View.VISIBLE
                infoLayout.visibility = View.GONE
            }
        })
    }

    private fun initViewBindings() {
        outerLayout = viewBinding.outerLayout
        infoLayout = viewBinding.infoLayout
        noInfoCard = viewBinding.noInfoCard
        generalCard = viewBinding.generalCard
        relationshipCard = viewBinding.relationshipCard
        drugsCard = viewBinding.drugsCard
        isOutHint = viewBinding.outHint
        isOut = viewBinding.out
        appearanceBar = viewBinding.appearanceBar
        barMasculine = viewBinding.barMasculine
        barFeminine = viewBinding.barFeminine
        relationshipHint = viewBinding.relationshipHint
        relationship = viewBinding.relationship
        childrenHint = viewBinding.childrenHint
        children = viewBinding.children
        smokingHint = viewBinding.smokingHint
        smoking = viewBinding.smoking
        alcoholHint = viewBinding.alcoholHint
        alcohol = viewBinding.alcohol
        drugsHint = viewBinding.drugsHint
        drugs = viewBinding.drugs
    }

    fun updateUI(mPersonal: Personal?, mDrugs: List<Drug>?) {
        noInfoCard.visibility = View.GONE
        infoLayout.visibility = View.VISIBLE

        // IS OUT?
        var deleteIsOutLayout = false
        try {
            when (mPersonal?.isOut) {
                0 -> isOut.setText(R.string.no)
                1 -> isOut.setText(R.string.yes)
                2 -> isOut.setText(R.string.unknown)
                else -> deleteIsOutLayout = true
            }
        } catch (e: Exception) {
            deleteIsOutLayout = true
        }
        if (deleteIsOutLayout) {
            isOutHint.visibility = View.GONE
            isOut.visibility = View.GONE
        }

        // MASCULINE / FEMININE
        var deleteAppearanceLayout = false
        val effeminateLevel = mPersonal?.effeminate
        if (effeminateLevel == 0) {
            deleteAppearanceLayout = true
        } else {
            val effeminateFloat = effeminateLevel?.toFloat()
            val paramBarActive = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 100 - effeminateFloat!!
            )
            barMasculine.layoutParams = paramBarActive
            val paramBarInactive = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, effeminateFloat
            )
            barFeminine.layoutParams = paramBarInactive
        }
        if (!SpecificValues.SHOW_XRATED) {
            deleteAppearanceLayout = true
        }
        if (deleteAppearanceLayout) {
            appearanceBar.visibility = View.GONE
        }
        if (isOut.visibility == View.GONE && appearanceBar.visibility == View.GONE) {
            generalCard.visibility = View.GONE
        }

        // RELATIONSHIP
        var deleteRelationshipLayout = false
        if (mPersonal.relationshipStatus?.trim { it <= ' ' }?.isNotEmpty() == true) {
            relationship.text = mPersonal.relationshipStatus
        } else {
            deleteRelationshipLayout = true
        }
        if (deleteRelationshipLayout) {
            relationshipHint.visibility = View.GONE
            relationship.visibility = View.GONE
        }

        // CHILDREN
//        var deleteChildrenLayout = false
//        if (mPersonal.children?.trim { it <= ' ' }?.isNotEmpty() == true) {
//            children.text = mPersonal.children
//        } else {
//            deleteChildrenLayout = true
//        }
//        if (deleteChildrenLayout) {
//            childrenHint.visibility = View.GONE
//            children.visibility = View.GONE
//        }
//        if (relationship.visibility == View.GONE && children.visibility == View.GONE) {
//            relationshipCard.visibility = View.GONE
//        }

        // SMOKING
        var deleteSmokingLayout = false
        if (mPersonal.smoking?.trim { it <= ' ' }?.isNotEmpty() == true) {
            smoking.text = mPersonal.smoking
        } else {
            deleteSmokingLayout = true
        }
        if (deleteSmokingLayout) {
            smokingHint.visibility = View.GONE
            smoking.visibility = View.GONE
        }

        // ALCOHOL
        var deleteAlcoholLayout = false
        if (mPersonal.drinking?.trim { it <= ' ' }?.isNotEmpty() == true) {
            alcohol.text = mPersonal.drinking
        } else {
            deleteAlcoholLayout = true
        }
        if (deleteAlcoholLayout) {
            alcoholHint.visibility = View.GONE
            alcohol.visibility = View.GONE
        }

        // DRUGS
        var deleteDrugsLayout = false
        val drugsList = ArrayList<String?>()
        if (mDrugs?.isNotEmpty() == true) {
            for (drug in mDrugs) {
                Log.d(TAG, "Drug: " + drug.drug)

                // TODO get localized Drugs string
                drugsList.add(drug.drug)
            }
            val drugsStr = TextUtils.join(", ", drugsList)

            if (drugsStr.isEmpty()) {
                deleteDrugsLayout = true
            } else {
                drugs.text = drugsStr
            }
        } else {
            deleteDrugsLayout = true
        }
        if (deleteDrugsLayout) {
            drugsHint.visibility = View.GONE
            drugs.visibility = View.GONE
        }
        if (smoking.visibility == View.GONE && alcohol.visibility == View.GONE && drugs.visibility == View.GONE) {
            drugsCard.visibility = View.GONE
        }

        // Show "no information" text
        if (generalCard.visibility == View.GONE && relationshipCard.visibility == View.GONE && drugsCard.visibility == View.GONE) {
            noInfoCard.visibility = View.VISIBLE
        } else {
            noInfoCard.visibility = View.GONE
        }
    }
}