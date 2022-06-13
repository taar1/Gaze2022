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

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import net.gazeapp.R
import net.gazeapp.data.model.Body
import net.gazeapp.data.model.BodyType
import net.gazeapp.data.model.Ethnicity
import net.gazeapp.databinding.ContactviewTabBodyKtBinding
import net.gazeapp.utilities.GazeTools
import javax.inject.Inject

@AndroidEntryPoint
class TabBodyFragmentKt : Fragment(R.layout.contactview_tab_body_kt) {

    @Inject
    lateinit var tools: GazeTools

    private lateinit var outerLayout: ConstraintLayout
    private lateinit var noInfoCard: CardView
    private lateinit var bodyCard: CardView
    private lateinit var eyeCard: CardView
    private lateinit var hairCard: CardView

    private lateinit var heightTv: TextView
    private lateinit var heightHintTv: TextView
    private lateinit var weightTv: TextView
    private lateinit var weightHintTv: TextView
    private lateinit var ethnicityTv: TextView
    private lateinit var ethnicityHintTv: TextView
    private lateinit var bodyTypeTv: TextView
    private lateinit var bodyTypeHintTv: TextView
    private lateinit var bodyHairTv: TextView
    private lateinit var bodyHairHintTv: TextView
    private lateinit var eyeColorTv: TextView
    private lateinit var eyeColorHintTv: TextView
    private lateinit var hairColorTv: TextView
    private lateinit var hairColorHintTv: TextView
    private lateinit var hairStyleTv: TextView
    private lateinit var hairStyleHintTv: TextView

    private var heightString: String? = null
    private var weightString: String? = null
    private var ethnicitiesString: String? = null
    private var bodyTypesString: String? = null
    private var bodyHairString: String? = null
    private var eyeColorString: String? = null
    private var hairColorString: String? = null
    private var hairStyleString: String? = null

    private var contactId: Int = 0
    private lateinit var viewBinding: ContactviewTabBodyKtBinding

    companion object {
        private const val TAG = "TabBodyFragmentKt"
        private const val CONTACT_ID = "contactId"

        fun newInstance(contactId: Int) = TabBodyFragmentKt().apply {
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
        viewBinding = ContactviewTabBodyKtBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewBindings()
        initUi()

        viewModel.contactWithDetails.observe(viewLifecycleOwner) { contactWithDetails ->
            contactWithDetails.apply {
                prepareData(
                    contactWithDetails.body,
                    contactWithDetails.bodyTypes,
                    contactWithDetails.ethnicities
                )
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
        outerLayout = viewBinding.outerLayout
        noInfoCard = viewBinding.noInfoCard
        bodyCard = viewBinding.bodyCard
        heightTv = viewBinding.height
        heightHintTv = viewBinding.heightHint
        weightTv = viewBinding.weight
        weightHintTv = viewBinding.weightHint
        ethnicityTv = viewBinding.ethnicity
        ethnicityHintTv = viewBinding.ethnicityHint
        bodyTypeTv = viewBinding.bodyType
        bodyTypeHintTv = viewBinding.bodyTypeHint
        bodyHairTv = viewBinding.bodyHair
        bodyHairHintTv = viewBinding.bodyHairHint
        eyeCard = viewBinding.eyesCard
        eyeColorTv = viewBinding.eyeColor
        eyeColorHintTv = viewBinding.eyeColorHint
        hairCard = viewBinding.hairCard
        hairColorTv = viewBinding.hairColor
        hairColorHintTv = viewBinding.hairColorHint
        hairStyleTv = viewBinding.hairStyle
        hairStyleHintTv = viewBinding.hairStyleHint
    }

    private fun initUi() {
        noInfoCard.visibility = View.GONE
    }

    private fun prepareData(
        bodyData: Body?,
        bodytypeData: List<BodyType>?,
        ethnicitiesData: List<Ethnicity>?
    ) {

        // HEIGHT
        if (bodyData?.height != null) {
            heightString = tools.convertHeightFromCentimetersToReadableFormat(
                bodyData.height, tools.useMetricSystem()
            )
        }

        // WEIGHT
        if (bodyData?.weight != null && bodyData.weight > 0) {
            weightString = tools.convertWeightFromGramsToReadableFormat(
                bodyData.weight, tools.useMetricSystem()
            )
        }

        // ETHNICITIES
        if (ethnicitiesData != null && ethnicitiesData.isNotEmpty()) {
            val ethnicityList: List<String> = ethnicitiesData.map { it.ethnicity }
            ethnicitiesString = ethnicityList.joinToString(", ")
        }

        // BODY TYPES
        // TODO use translated bodytypes
        // TODO use body type Ids (in db table) instead of hardcoded strings
        if (bodytypeData != null && bodytypeData.isNotEmpty()) {
            val bodytypeList: List<String> = bodytypeData.map { it.bodyType.toString() }
            bodyTypesString = bodytypeList.joinToString(", ")
        }

        // BODY HAIR
        bodyHairString = bodyData?.bodyHair

        // EYE COLOR
        eyeColorString = bodyData?.eyeColor

        // HAIR COLOR
        hairColorString = bodyData?.hairColor

        // HAIR STYLE
        hairStyleString = bodyData?.hairStyle

        updateLayout()
    }

    private fun updateLayout() {
        if (heightString?.isNotEmpty() == true) {
            heightTv.text = heightString
        } else {
            heightTv.visibility = View.GONE
            heightHintTv.visibility = View.GONE
        }
        if (weightString?.isNotEmpty() == true) {
            weightTv.text = weightString
        } else {
            weightTv.visibility = View.GONE
            weightHintTv.visibility = View.GONE
        }
        if (bodyTypesString?.isNotEmpty() == true) {
            bodyTypeTv.text = bodyTypesString
        } else {
            bodyTypeTv.visibility = View.GONE
            bodyTypeHintTv.visibility = View.GONE
        }
        if (bodyHairString?.isNotEmpty() == true) {
            bodyHairTv.text = bodyHairString
        } else {
            bodyHairTv.visibility = View.GONE
            bodyHairHintTv.visibility = View.GONE
        }
        if (ethnicitiesString?.isNotEmpty() == true) {
            ethnicityTv.text = ethnicitiesString
        } else {
            ethnicityTv.visibility = View.GONE
            ethnicityHintTv.visibility = View.GONE
        }
        if (heightTv.visibility == View.GONE && weightTv.visibility == View.GONE && bodyTypeTv.visibility == View.GONE && bodyHairTv.visibility == View.GONE) {
            bodyCard.visibility = View.GONE
        }
        if (eyeColorString?.isNotEmpty() == true) {
            eyeColorTv.text = eyeColorString
        } else {
            eyeCard.visibility = View.GONE
            eyeColorHintTv.visibility = View.GONE
        }
        if (hairColorString?.isNotEmpty() == true) {
            hairColorTv.text = hairColorString
        } else {
            hairColorTv.visibility = View.GONE
            hairColorHintTv.visibility = View.GONE
        }
        if (hairStyleString?.isNotEmpty() == true) {
            hairStyleTv.text = hairStyleString
        } else {
            hairStyleTv.visibility = View.GONE
            hairStyleHintTv.visibility = View.GONE
        }
        if (hairColorTv.visibility == View.GONE && hairStyleTv.visibility == View.GONE) {
            hairCard.visibility = View.GONE
        }

        // Show "no information" text
        if (bodyCard.visibility == View.GONE && eyeCard.visibility == View.GONE && hairCard.visibility == View.GONE) {
            noInfoCard.visibility = View.VISIBLE
        } else {
            noInfoCard.visibility = View.GONE
        }
    }
}