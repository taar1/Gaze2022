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
import net.gazeapp.R
import net.gazeapp.data.model.Endowment
import net.gazeapp.data.model.Fetish
import net.gazeapp.data.model.Health
import net.gazeapp.data.model.Xxx
import net.gazeapp.databinding.ContactviewTabXxxKtBinding
import net.gazeapp.utilities.GazeTools
import javax.inject.Inject

class TabXxxStuffFragmentKt : Fragment(R.layout.contactview_tab_xxx_kt) {
    @Inject
    lateinit var tools: GazeTools

    private lateinit var outerLayout: ConstraintLayout
    private lateinit var noInfoCard: CardView
    private lateinit var infoCard: CardView
    private lateinit var endowmentCard: CardView
    private lateinit var behaviorCard: CardView
    private lateinit var healthCard: CardView

    private lateinit var lengthHint: TextView
    private lateinit var length: TextView
    private lateinit var diameterHint: TextView
    private lateinit var diameter: TextView
    private lateinit var girthHint: TextView
    private lateinit var girth: TextView
    private lateinit var cutUncutHint: TextView
    private lateinit var cutUncut: TextView
    private lateinit var feelsLikeHint: TextView
    private lateinit var feelsLike: TextView
    private lateinit var sexualOrientationHint: TextView
    private lateinit var sexualOrientation: TextView
    private lateinit var sexRoleHint: TextView
    private lateinit var sexRole: TextView
    private lateinit var safeSexHint: TextView
    private lateinit var safeSex: TextView
    private lateinit var swallowsHint: TextView
    private lateinit var swallows: TextView
    private lateinit var upTheBumHint: TextView
    private lateinit var upTheBum: TextView
    private lateinit var fetishesHint: TextView
    private lateinit var fetishes: TextView
    private lateinit var hivHint: TextView
    private lateinit var hiv: TextView
    private lateinit var hcvHint: TextView
    private lateinit var hcv: TextView
    private lateinit var covidHint: TextView
    private lateinit var covid: TextView
    private lateinit var covidVaccineDate: TextView

    private var mLength: String? = null
    private var mDiameter: String? = null
    private var mGirth: String? = null
    private var mCutUncut: String? = null
    private var mFeelsLike: String? = null
    private var mSexualOrientation: String? = null
    private var mSexRole: String? = null
    private var mSafeSex: String? = null
    private var mSwallows: String? = null
    private var mUpTheBum: String? = null
    private var mFetishesStr: String? = null
    private var mHiv: String? = null
    private var mHcv: String? = null
    private var hadCovid: Int? = 2 // 0=no, 1=yes, 2=unknown
    private var isCovidVaccinated: Int? = 2 // 0=no, 1=yes, 2=unknown
    private var covidVaccineDateString: String? = null

    private var contactId: Int = 0
    private lateinit var viewBinding: ContactviewTabXxxKtBinding

    private val paddingBottomPx = 0

    companion object {
        private const val TAG = "TabBodyFragmentKt"
        private const val CONTACT_ID = "contactId"

        fun newInstance(contactId: Int) = TabXxxStuffFragmentKt().apply {
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
        viewBinding = ContactviewTabXxxKtBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewBindings()
        initUi()

        viewModel.contactWithDetails.observe(viewLifecycleOwner, { contactWithDetails ->
            contactWithDetails.apply {
                prepareData(
                    contactWithDetails.health,
                    contactWithDetails.endowment,
                    contactWithDetails.fetishes,
                    contactWithDetails.xxx
                )
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
        outerLayout = viewBinding.outerLayout
        noInfoCard = viewBinding.noInfoCard
        infoCard = viewBinding.infoCard
        endowmentCard = viewBinding.endowmentCard
        behaviorCard = viewBinding.behaviorCard
        healthCard = viewBinding.healthCard

        lengthHint = viewBinding.lengthHint
        length = viewBinding.length
        diameterHint = viewBinding.diameterHint
        diameter = viewBinding.diameter
        girthHint = viewBinding.girthHint
        girth = viewBinding.girth
        cutUncutHint = viewBinding.cutHint
        cutUncut = viewBinding.cut
        feelsLikeHint = viewBinding.feelsLikeHint
        feelsLike = viewBinding.feelsLike
        sexualOrientationHint = viewBinding.sexualOrientationHint
        sexualOrientation = viewBinding.sexualOrientation
        sexRoleHint = viewBinding.sexRoleHint
        sexRole = viewBinding.sexRole
        safeSexHint = viewBinding.safeSexHint
        safeSex = viewBinding.safeSex
        swallowsHint = viewBinding.swallowsHint
        swallows = viewBinding.swallows
        upTheBumHint = viewBinding.takesUpBumHint
        upTheBum = viewBinding.takesUpBum
        fetishesHint = viewBinding.fetishesHint
        fetishes = viewBinding.fetishes
        hivHint = viewBinding.hivHint
        hiv = viewBinding.hiv
        hcvHint = viewBinding.hcvHint
        hcv = viewBinding.hcv
        covidHint = viewBinding.covidHint
        covid = viewBinding.covid
        covidVaccineDate = viewBinding.covidVaccineDate
    }

    private fun initUi() {
        noInfoCard.visibility = View.GONE
        infoCard.visibility = View.VISIBLE
    }

    private fun prepareData(
        health: Health?,
        endowment: Endowment?,
        fetishes: List<Fetish>?,
        xxx: Xxx?
    ) {

        // LENGTH
        endowment?.length.let { length ->
            mLength = tools.convertFromMillimetersToReadableFormat(
                length!!, tools.useMetricSystem()
            )
        }

        // DIAMETER
        endowment?.diameter.let { diameter ->
            mDiameter = tools.convertFromMillimetersToReadableFormat(
                diameter!!, tools.useMetricSystem()
            )
        }

        // GIRTH
        endowment?.girth.let { girth ->
            mGirth = tools.convertFromMillimetersToReadableFormat(
                girth!!, tools.useMetricSystem()
            )
        }

        // CUT OR UNCUT
        when (endowment?.isCut) {
            0 -> {
                mCutUncut = getString(R.string.uncut)
            }
            1 -> {
                mCutUncut = getString(R.string.cut)
            }
            2 -> {
                mCutUncut = null
            }
        }

        // FEELS LIKE
        endowment?.feelsLike.let { feelsLike ->
            mFeelsLike = tools.convertFromMillimetersToReadableFormat(
                feelsLike!!, tools.useMetricSystem()
            )
        }

        mSexualOrientation = xxx?.sexualOrientation
        mSexRole = xxx?.sexRole
        mSafeSex = xxx?.safeSex

        // SWALLOWS
        val swallowsLoads = xxx?.swallowsLoads
        mSwallows = when (swallowsLoads) {
            0 -> getString(R.string.no)
            1 -> getString(R.string.yes)
            2 -> getString(R.string.unknown_plain)
            3 -> ""
            else -> getString(R.string.unknown_plain)
        }

        // TAKES UP THE BUM
        val takesLoads = xxx?.takesLoadsUpTheBum
        mUpTheBum = when (takesLoads) {
            0 -> getString(R.string.no)
            1 -> getString(R.string.yes)
            2 -> getString(R.string.unknown_plain)
            3 -> ""
            else -> getString(R.string.unknown_plain)
        }

        // FETISHES
        if (fetishes?.isNotEmpty() == true) {
            val fetishList: List<String> = fetishes.map { it.fetish }
            mFetishesStr = fetishList.joinToString(", ")
        }

        mHiv = health?.hiv
        mHcv = health?.hcv

        hadCovid = health?.hadCovid19
        isCovidVaccinated = health?.isCovid19Vaccinated
        covidVaccineDateString =
            tools.formatDateToPhoneLocale(health?.covid19VaccinationDate, true)

        updateUI()
    }

    private fun updateUI() {
        noInfoCard.visibility = View.GONE

        // LENGTH
        if (mLength?.isNotEmpty() == true) {
            length.text = mLength
//            length.setPaddingRelative(0, 0, 0, 0)
        } else {
            lengthHint.visibility = View.GONE
            length.visibility = View.GONE
        }

        // DIAMETER
        if (mDiameter?.isNotEmpty() == true) {
            diameter.text = mDiameter
//            length.setPaddingRelative(0, 0, 0, paddingBottomPx)
//            diameter.setPaddingRelative(0, 0, 0, 0)
        } else {
            diameterHint.visibility = View.GONE
            diameter.visibility = View.GONE
        }

        // GIRTH
        if (mGirth?.isNotEmpty() == true) {
            girth.text = mGirth
//            length.setPaddingRelative(0, 0, 0, paddingBottomPx)
//            diameter.setPaddingRelative(0, 0, 0, paddingBottomPx)
//            girth.setPaddingRelative(0, 0, 0, 0)
        } else {
            girthHint.visibility = View.GONE
            girth.visibility = View.GONE
        }

        // CUT OR UNCUT
        if (mCutUncut?.isNotEmpty() == true) {
            cutUncut.text = mCutUncut
//            length.setPaddingRelative(0, 0, 0, paddingBottomPx)
//            diameter.setPaddingRelative(0, 0, 0, paddingBottomPx)
//            girth.setPaddingRelative(0, 0, 0, paddingBottomPx)
//            cutUncut.setPaddingRelative(0, 0, 0, 0)
        } else {
            cutUncutHint.visibility = View.GONE
            cutUncut.visibility = View.GONE
        }

        // FEELS LIKE
        if (mFeelsLike?.isNotEmpty() == true) {
            feelsLike.text = mFeelsLike
//            length.setPaddingRelative(0, 0, 0, paddingBottomPx)
//            diameter.setPaddingRelative(0, 0, 0, paddingBottomPx)
//            girth.setPaddingRelative(0, 0, 0, paddingBottomPx)
//            cutUncut.setPaddingRelative(0, 0, 0, paddingBottomPx)
//            feelsLike.setPaddingRelative(0, 0, 0, 0)
        } else {
            feelsLikeHint.visibility = View.GONE
            feelsLike.visibility = View.GONE
        }
        if (length.visibility == View.GONE && diameter.visibility == View.GONE && girth.visibility == View.GONE && cutUncut.visibility == View.GONE && feelsLike.visibility == View.GONE) {
            endowmentCard.visibility = View.GONE
        }

        // SEXUAL ORIENTATION
        if (mSexualOrientation?.isNotEmpty() == true) {
            sexualOrientation.text = mSexualOrientation
//            sexualOrientation.setPaddingRelative(0, 0, 0, 0)
        } else {
            sexualOrientationHint.visibility = View.GONE
            sexualOrientation.visibility = View.GONE
        }

        if (sexualOrientation.visibility == View.GONE) {
            infoCard.visibility = View.GONE
        }

        // SEX ROLE
        if (mSexRole?.isNotEmpty() == true) {
            sexRole.text = mSexRole
//            sexualOrientation.setPaddingRelative(0, 0, 0, paddingBottomPx)
//            sexRole.setPaddingRelative(0, 0, 0, 0)
        } else {
            sexRoleHint.visibility = View.GONE
            sexRole.visibility = View.GONE
        }

        // SAFE SEX
        if (mSafeSex?.isNotEmpty() == true) {
            safeSex.text = mSafeSex
//            safeSex.setPaddingRelative(0, 0, 0, 0)
        } else {
            safeSexHint.visibility = View.GONE
            safeSex.visibility = View.GONE
        }

        // SWALLOWS
        if (mSwallows?.isNotEmpty() == true) {
            swallows.text = mSwallows
//            safeSex.setPaddingRelative(0, 0, 0, paddingBottomPx)
//            swallows.setPaddingRelative(0, 0, 0, 0)
        } else {
            swallowsHint.visibility = View.GONE
            swallows.visibility = View.GONE
        }

        // TAKES UP THE BUM
        if (mUpTheBum?.isNotEmpty() == true) {
            upTheBum.text = mUpTheBum
//            safeSex.setPaddingRelative(0, 0, 0, paddingBottomPx)
//            swallows.setPaddingRelative(0, 0, 0, paddingBottomPx)
//            upTheBum.setPaddingRelative(0, 0, 0, 0)
        } else {
            upTheBumHint.visibility = View.GONE
            upTheBum.visibility = View.GONE
        }

        // FETISHES
        if (mFetishesStr?.isNotEmpty() == true) {
            fetishes.text = mFetishesStr
//            safeSex.setPaddingRelative(0, 0, 0, paddingBottomPx)
//            swallows.setPaddingRelative(0, 0, 0, paddingBottomPx)
//            upTheBum.setPaddingRelative(0, 0, 0, paddingBottomPx)
//            fetishes.setPaddingRelative(0, 0, 0, 0)
        } else {
            fetishesHint.visibility = View.GONE
            fetishes.visibility = View.GONE
        }

        if (safeSex.visibility == View.GONE && swallows.visibility == View.GONE && upTheBum.visibility == View.GONE && fetishes.visibility == View.GONE && sexRole.visibility == View.GONE) {
            behaviorCard.visibility = View.GONE
        }

        // HIV
        if (mHiv?.isNotEmpty() == true) {
            hiv.text = mHiv
//            hiv.setPaddingRelative(0, 0, 0, 0)
        } else {
            hivHint.visibility = View.GONE
            hiv.visibility = View.GONE
        }

        // HCV
        if (mHcv?.isNotEmpty() == true) {
            hcv.text = mHcv
//            hiv.setPaddingRelative(0, 0, 0, paddingBottomPx)
//            hcv.setPaddingRelative(0, 0, 0, 0)
        } else {
            hcvHint.visibility = View.GONE
            hcv.visibility = View.GONE
        }

        // COVID-19
        covidHint.visibility = View.GONE
        covid.visibility = View.GONE
        covidVaccineDate.visibility = View.GONE

        if (isCovidVaccinated == 1) {
            covidHint.visibility = View.VISIBLE
            covid.visibility = View.VISIBLE
            covid.text = getString(R.string.covid_19_vaccinated)

            if (covidVaccineDateString?.isNotEmpty() == true) {
                covidVaccineDate.visibility = View.VISIBLE
                covidVaccineDate.text =
                    getString(R.string.covid_19_vaccination_date, covidVaccineDateString)
            } else {
                covidVaccineDate.visibility = View.GONE
            }
        } else {
            if (hadCovid == 0) {
                covidHint.visibility = View.VISIBLE
                covid.visibility = View.VISIBLE
                covid.text = getString(R.string.negative)
            } else if (hadCovid == 1) {
                covidHint.visibility = View.VISIBLE
                covid.visibility = View.VISIBLE
                covid.text = getString(R.string.covid_19_positive)

                // TODO Health.covid19InfectionDate feld anzeigen, falls bekannt/ausgefüllt
            }
        }

        if (hiv.visibility == View.GONE && hcv.visibility == View.GONE && covid.visibility == View.GONE) {
            healthCard.visibility = View.GONE
        }

        // Show "no information" text
        if (infoCard.visibility == View.GONE && endowmentCard.visibility == View.GONE && behaviorCard.visibility == View.GONE && healthCard.visibility == View.GONE) {
            noInfoCard.visibility = View.VISIBLE
        } else {
            noInfoCard.visibility = View.GONE
        }
    }
}