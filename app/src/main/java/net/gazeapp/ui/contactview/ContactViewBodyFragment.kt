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
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView
import dagger.hilt.android.AndroidEntryPoint
import needle.Needle
import net.gazeapp.data.GazeDatabase.Companion.getDatabase
import net.gazeapp.data.model.Body
import net.gazeapp.data.model.BodyType
import net.gazeapp.data.model.Contact
import net.gazeapp.data.model.Ethnicity
import net.gazeapp.databinding.ContactviewTabBodyBinding
import net.gazeapp.helpers.Const
import net.gazeapp.utilities.GazeTools
import java.lang.Deprecated
import javax.inject.Inject

@AndroidEntryPoint
@Deprecated()
// "Use TabBodyFragment.kt"
class ContactViewBodyFragment : Fragment() {

    @Inject
    lateinit var tools: GazeTools

    private var activity: ContactViewWithViewPagerTabActivity? = null
    private var mContact: Contact? = null
    private var mBody: Body? = null
    private var mBodytypeList: List<BodyType>? = null
    private var mEthnicityList: List<Ethnicity>? = null


    lateinit var fragmentRoot: FrameLayout
    lateinit var scrollView: ObservableScrollView
    lateinit var noInformationLayout: LinearLayout
    lateinit var grayLayout: LinearLayout
    lateinit var height: TextView
    lateinit var heightHint: TextView
    lateinit var weight: TextView
    lateinit var weightHint: TextView
    lateinit var ethnicity: TextView
    lateinit var ethnicityHint: TextView
    lateinit var bodyType: TextView
    lateinit var bodyTypeHint: TextView
    lateinit var bodyHair: TextView
    lateinit var bodyHairHint: TextView
    lateinit var eyeColor: TextView
    lateinit var eyeColorHint: TextView
    lateinit var hairColor: TextView
    lateinit var hairColorHint: TextView
    lateinit var hairStyle: TextView
    lateinit var hairStyleHint: TextView
    lateinit var bodyLayout: LinearLayout
    lateinit var eyeLayout: LinearLayout
    lateinit var hairLayout: LinearLayout
    lateinit var viewPadding1: View
    lateinit var viewPadding2: View


    private var mHeight: String? = null
    private var mWeight: String? = null
    private var mEthnicities: String? = null
    private var mBodyTypes: String? = null
    private var mBodyHair: String? = null
    private var mEyeColor: String? = null
    private var mHairColor: String? = null
    private var mHairStyle: String? = null

    lateinit var binding: ContactviewTabBodyBinding

    companion object {
        private const val TAG = "ContactViewBodyFragment"

        fun newInstance(): ContactViewBodyFragment {
            return ContactViewBodyFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ContactviewTabBodyBinding.inflate(inflater, container, false)

        activity = getActivity() as ContactViewWithViewPagerTabActivity?
        mContact = activity!!.contact

        bindViews()

        loadData()
        return view
    }

    private fun bindViews() {
        fragmentRoot = binding.fragmentRoot
        scrollView = binding.scroll
        noInformationLayout = binding.incNoInfo.noInfoLayout
        grayLayout = binding.grayLayout
        height = binding.height
        heightHint = binding.heightHint
        weight = binding.weight
        weightHint = binding.weightHint
        ethnicity = binding.ethnicity
        ethnicityHint = binding.ethnicityHint
        bodyType = binding.bodyType
        bodyTypeHint = binding.bodyTypeHint
        bodyHair = binding.bodyHair
        bodyHairHint = binding.bodyHairHint
        eyeColor = binding.eyeColor
        eyeColorHint = binding.eyeColorHint
        hairColor = binding.hairColor
        hairColorHint = binding.hairColorHint
        hairStyle = binding.hairStyle
        hairStyleHint = binding.hairStyleHint
        bodyLayout = binding.bodyLayout
        eyeLayout = binding.eyeLayout
        hairLayout = binding.hairLayout
        viewPadding1 = binding.viewPadding1
        viewPadding2 = binding.viewPadding2
    }

    private fun loadData() {
        Needle.onBackgroundThread().execute {
            val bodyDao = getDatabase(activity!!).bodyDao
            val bodyTypeDao = getDatabase(activity!!).bodyTypeDao
            val ethnicityDao = getDatabase(activity!!).ethnicityDao
            mBody = bodyDao.getBody(mContact!!.id)
            mBodytypeList = bodyTypeDao.getBodyTypes(mContact!!.id)
            mEthnicityList = ethnicityDao.getEthnicities(mContact!!.id)
            updateUI()
        }
    }

    fun updateUI() {
        Needle.onMainThread().execute {
            viewPadding1.visibility = View.GONE
            viewPadding2.visibility = View.GONE
            noInformationLayout.visibility = View.GONE
            grayLayout.minimumHeight = activity!!.getDisplayHeight(false, true)
            prepareData()
        }
    }

    private fun prepareData() {
        Needle.onBackgroundThread().execute {

            mBody?.let {
                // HEIGHT
                mHeight = if (it.height > 0) {
                    tools.convertHeightFromCentimetersToReadableFormat(
                        it.height,
                        tools.useMetricSystem()
                    )
                } else {
                    null
                }

                // WEIGHT
                mWeight = if (it.weight > 0) {
                    tools.convertWeightFromGramsToReadableFormat(
                        it.weight,
                        tools.useMetricSystem()
                    )
                } else {
                    null
                }
            }


            // ETHNICITIES
            val ethnicityList = ArrayList<String?>()
            mEthnicities = try {
                for ((_, _, _, ethnicity1) in mEthnicityList!!) {
                    Log.d(TAG, "Ethnicity: $ethnicity1")

                    // TODO use translated ethnicities
                    ethnicityList.add(ethnicity1)
                }
                TextUtils.join(", ", ethnicityList)
            } catch (ne: Exception) {
                null
            }

            // BODY TYPES
            val bodyTypeList = ArrayList<String?>()
            mBodyTypes = try {
                for ((_, _, _, bodyType1) in mBodytypeList!!) {
                    Log.d(TAG, "BodyType: $bodyType1")

                    // TODO use translated bodytypes
                    bodyTypeList.add(bodyType1)
                }
                TextUtils.join(", ", bodyTypeList)
            } catch (ne: Exception) {
                null
            }

            // BODY HAIR
            mBodyHair = try {
                mBody!!.bodyHair
            } catch (ne: Exception) {
                null
            }

            // EYE COLOR
            mEyeColor = try {
                mBody!!.eyeColor
            } catch (ne: Exception) {
                null
            }

            // HAIR COLOR
            mHairColor = try {
                mBody!!.hairColor
            } catch (ne: Exception) {
                null
            }

            // HAIR STYLE
            mHairStyle = try {
                mBody!!.hairStyle
            } catch (ne: Exception) {
                null
            }
            updateLayout()
        }
    }

    private fun updateLayout() {
        Needle.onMainThread().execute {
            if (mHeight != null && mHeight!!.length > 0) {
                height.text = mHeight
                height.setPaddingRelative(0, 0, 0, 0)
            } else {
                height.visibility = View.GONE
                heightHint.visibility = View.GONE
            }
            if (mWeight.isNullOrBlank()) {
                weight.text = mWeight
                height.setPaddingRelative(0, 0, 0, activity!!.innerCardPaddingInPixels)
                weight.setPaddingRelative(0, 0, 0, 0)
            } else {
                weight.visibility = View.GONE
                weightHint.visibility = View.GONE
            }
            if (mBodyTypes.isNullOrBlank()) {
                bodyType.text = mBodyTypes
                height.setPaddingRelative(0, 0, 0, activity!!.innerCardPaddingInPixels)
                weight.setPaddingRelative(0, 0, 0, activity!!.innerCardPaddingInPixels)
                bodyType.setPaddingRelative(0, 0, 0, 0)
            } else {
                bodyType.visibility = View.GONE
                bodyTypeHint.visibility = View.GONE
            }
            if (mBodyHair.isNullOrBlank()) {
                bodyHair.text = mBodyHair
                height.setPaddingRelative(0, 0, 0, activity!!.innerCardPaddingInPixels)
                weight.setPaddingRelative(0, 0, 0, activity!!.innerCardPaddingInPixels)
                bodyType.setPaddingRelative(0, 0, 0, activity!!.innerCardPaddingInPixels)
                bodyHair.setPaddingRelative(0, 0, 0, 0)
            } else {
                bodyHair.visibility = View.GONE
                bodyHairHint.visibility = View.GONE
            }
            if (mEthnicities.isNullOrBlank()) {
                ethnicity.text = mEthnicities
                height.setPaddingRelative(0, 0, 0, activity!!.innerCardPaddingInPixels)
                weight.setPaddingRelative(0, 0, 0, activity!!.innerCardPaddingInPixels)
                bodyType.setPaddingRelative(0, 0, 0, activity!!.innerCardPaddingInPixels)
                bodyHair.setPaddingRelative(0, 0, 0, activity!!.innerCardPaddingInPixels)
                ethnicity.setPaddingRelative(0, 0, 0, 0)
            } else {
                ethnicity.visibility = View.GONE
                ethnicityHint.visibility = View.GONE
            }
            if (height.visibility == View.GONE && weight.visibility == View.GONE && bodyType.visibility == View.GONE && bodyHair.visibility == View.GONE) {
                bodyLayout.visibility = View.GONE
            }
            if (mEyeColor.isNullOrBlank()) {
                eyeColor.text = mEyeColor
            } else {
                eyeLayout.visibility = View.GONE
                eyeColorHint.visibility = View.GONE
            }
            if (mHairColor.isNullOrBlank()) {
                hairColor.text = mHairColor
            } else {
                hairColor.visibility = View.GONE
                hairColorHint.visibility = View.GONE
            }
            if (mHairStyle.isNullOrBlank()) {
                hairStyle.text = mHairStyle
            } else {
                hairStyle.visibility = View.GONE
                hairStyleHint.visibility = View.GONE
            }
            if (hairColor.visibility == View.GONE && hairStyle.visibility == View.GONE) {
                hairLayout.visibility = View.GONE
            }

            /*             DIVIDERS            */
            // Show divider BODY & EYES
            if (bodyLayout.visibility == View.VISIBLE && eyeLayout.visibility == View.VISIBLE) {
                viewPadding1.visibility = View.VISIBLE
            }

            // Show divider BODY & HAIR
            if (bodyLayout.visibility == View.VISIBLE && eyeLayout.visibility == View.GONE && hairLayout.visibility == View.VISIBLE) {
                viewPadding1.visibility = View.VISIBLE
            }

            // Show divider EYE & HAIR
            if (eyeLayout.visibility == View.VISIBLE && hairLayout.visibility == View.VISIBLE) {
                viewPadding2.visibility = View.VISIBLE
            }

            // Show "no information" text
            if (bodyLayout.visibility == View.GONE && eyeLayout.visibility == View.GONE && hairLayout.visibility == View.GONE) {
                val noInformationLayoutCorrectHeight = Math.round(
                    activity!!.getDisplayHeight(
                        minusTabBarHeight = false,
                        minusAdBannerHeight = false
                    ) - tools.convertPxToDp(Const.HEIGHT_TAB_BAR_AND_AD_BANNER.toFloat())
                )
                noInformationLayout.visibility = View.VISIBLE
                noInformationLayout.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    noInformationLayoutCorrectHeight
                )
            } else {
                noInformationLayout.visibility = View.GONE
            }

//                updateScrollview();
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
    //                (ContactViewWithViewPagerTabActivity) getActivity();
    //        if (parentActivity != null) {
    //            parentactivity!!.onScrollChanged(scrollY, scrollView);
    //        }
    //    }
    //
    //    @UiThread
    //    void updateScrollview() {
    //        scrollView.setTouchInterceptionViewGroup(fragmentRoot);
    //
    //        // Scroll to the specified offset after layout
    //        Bundle args = getArguments();
    //        if (args != null && args.containsKey(ARG_SCROLL_Y)) {
    //            final int scrollY = args.getInt(ARG_SCROLL_Y, 0);
    //            ScrollUtils.addOnGlobalLayoutListener(scrollView, new Runnable() {
    //                @Override
    //                public void run() {
    //                    // TODO FIXME crashes here often since splitting code to Background and UI methods...
    //                    try {
    //                        scrollView.scrollTo(0, scrollY);
    //                    } catch (Exception e) {
    //                        Log.e(TAG, "Failed to Scroll to Y: " + e.getLocalizedMessage());
    //                    }
    //                }
    //            });
    //            updateFlexibleSpace(scrollY, view);
    //        } else {
    //            updateFlexibleSpace(0, view);
    //        }
    //
    //        scrollView.setScrollViewCallbacks(this);
    //    }
    override fun onResume() {
        super.onResume()
        mContact = activity!!.contact
    }

}