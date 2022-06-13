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

package net.gazeapp.ui.contactview;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

import net.gazeapp.R;
import net.gazeapp.data.GazeDatabase;
import net.gazeapp.data.dao.EndowmentDao;
import net.gazeapp.data.dao.FetishDao;
import net.gazeapp.data.dao.HealthDao;
import net.gazeapp.data.dao.XxxDao;
import net.gazeapp.data.model.Contact;
import net.gazeapp.data.model.Endowment;
import net.gazeapp.data.model.Fetish;
import net.gazeapp.data.model.Health;
import net.gazeapp.data.model.Xxx;
import net.gazeapp.helpers.Const;
import net.gazeapp.utilities.GazeTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import needle.Needle;

public class ContactViewXxxStuffFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    private ContactViewWithViewPagerTabActivity activity;
    private Contact mContact;

    @BindView(R.id.fragment_root)
    FrameLayout fragmentRoot;
    @BindView(R.id.view_padding_1)
    View viewPadding1;
    @BindView(R.id.view_padding_2)
    View viewPadding2;
    @BindView(R.id.view_padding_3)
    View viewPadding3;
    @BindView(R.id.scroll)
    ObservableScrollView scrollView;
    @BindView(R.id.gray_layout)
    LinearLayout grayLayout;

    @BindView(R.id.length_hint)
    TextView lengthHint;
    @BindView(R.id.length)
    TextView length;
    @BindView(R.id.diameter_hint)
    TextView diameterHint;
    @BindView(R.id.diameter)
    TextView diameter;
    @BindView(R.id.girth_hint)
    TextView girthHint;
    @BindView(R.id.girth)
    TextView girth;
    @BindView(R.id.cut_hint)
    TextView cutUncutHint;
    @BindView(R.id.cut)
    TextView cutUncut;
    @BindView(R.id.feels_like_hint)
    TextView feelsLikeHint;
    @BindView(R.id.feels_like)
    TextView feelsLike;
    @BindView(R.id.sexual_orientation_hint)
    TextView sexualOrientationHint;
    @BindView(R.id.sexual_orientation)
    TextView sexualOrientation;
    @BindView(R.id.sex_role_hint)
    TextView sexRoleHint;
    @BindView(R.id.sex_role)
    TextView sexRole;
    @BindView(R.id.safe_sex_hint)
    TextView safeSexHint;
    @BindView(R.id.safe_sex)
    TextView safeSex;
    @BindView(R.id.swallows_hint)
    TextView swallowsHint;
    @BindView(R.id.swallows)
    TextView swallows;
    @BindView(R.id.takes_up_bum_hint)
    TextView upTheBumHint;
    @BindView(R.id.takes_up_bum)
    TextView upTheBum;
    @BindView(R.id.fetishes_hint)
    TextView fetishesHint;
    @BindView(R.id.fetishes)
    TextView fetishes;
    @BindView(R.id.hiv_hint)
    TextView hivHint;
    @BindView(R.id.hiv)
    TextView hiv;
    @BindView(R.id.hcv_hint)
    TextView hcvHint;
    @BindView(R.id.hcv)
    TextView hcv;

    @BindView(R.id.no_info_layout)
    LinearLayout noInformationLayout;

    @BindView(R.id.endowment_layout)
    LinearLayout endowmentLayout;
    @BindView(R.id.sex_layout)
    LinearLayout sexLayout;
    @BindView(R.id.safe_sex_layout)
    LinearLayout safeSexLayout;
    @BindView(R.id.health_layout)
    LinearLayout healthLayout;

    private int paddingBottomPx;

    private String mLength;
    private String mDiameter;
    private String mGirth;
    private String mCutUncut;
    private String mFeelsLike;
    private String mSexualOrientation;
    private String mSexRole;
    private String mSafeSex;
    private String mSwallows;
    private String mUpTheBum;
    private String mFetishesStr;
    private String mHiv;
    private String mHcv;

    private HealthDao healthDao;
    private EndowmentDao endowmentDao;
    private XxxDao xxxDao;
    private FetishDao fetishDao;

    private Endowment mEndowment;
    private Xxx mXxx;
    private Health mHealth;
    private List<Fetish> mFetishes;

    private GazeTools tools;

    public ContactViewXxxStuffFragment() {
    }

    public static ContactViewXxxStuffFragment newInstance() {
        ContactViewXxxStuffFragment fragment = new ContactViewXxxStuffFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contactview_tab_xxx, container, false);
        ButterKnife.bind(this, view);

        activity = (ContactViewWithViewPagerTabActivity) getActivity();
        mContact = activity.getContact();

        tools = new GazeTools(activity);

        paddingBottomPx = activity.getInnerCardPaddingInPixels();

        prepareUI();
        loadData();

        return view;
    }

    private void loadData() {
        Needle.onBackgroundThread().execute(() -> {
            healthDao = GazeDatabase.Companion.getDatabase(activity).getHealthDao();
            endowmentDao = GazeDatabase.Companion.getDatabase(activity).getEndowmentDao();
            fetishDao = GazeDatabase.Companion.getDatabase(activity).getFetishDao();
            xxxDao = GazeDatabase.Companion.getDatabase(activity).getXxxDao();
            fetishDao = GazeDatabase.Companion.getDatabase(activity).getFetishDao();

            mEndowment = endowmentDao.getEndowmentByContactId(mContact.getId());
            mFetishes = fetishDao.getFetishes(mContact.getId());
            mXxx = xxxDao.getXxx(mContact.getId());
            mHealth = healthDao.getHealthsByContactId(mContact.getId());

            prepareData();
        });
    }

    private void prepareUI() {
        Needle.onMainThread().execute(() -> {
            viewPadding1.setVisibility(View.INVISIBLE);
            viewPadding2.setVisibility(View.INVISIBLE);
            viewPadding3.setVisibility(View.INVISIBLE);
            noInformationLayout.setVisibility(View.GONE);

            grayLayout.setMinimumHeight(activity.getDisplayHeight(false, true));
        });

    }

    private void prepareData() {
        // LENGTH
        try {
            if (mEndowment.getLength() > 0) {
                mLength = tools.convertFromMillimetersToReadableFormat(mEndowment.getLength(), tools.useMetricSystem());
            }
        } catch (Exception ne) {
            mLength = null;
        }

        // DIAMETER
        try {
            if (mEndowment.getDiameter() > 0) {
                mDiameter = tools.convertFromMillimetersToReadableFormat(mEndowment.getDiameter(), tools.useMetricSystem());
            }
        } catch (Exception ne) {
            mDiameter = null;
        }

        // GIRTH
        try {
            if (mEndowment.getGirth() > 0) {
                mGirth = tools.convertFromMillimetersToReadableFormat(mEndowment.getGirth(), tools.useMetricSystem());
            }
        } catch (Exception ne) {
            mGirth = null;
        }

        // CUT OR UNCUT
        try {

            if (mEndowment.isCut() == 0) {
                mCutUncut = getString(R.string.uncut);
            } else if (mEndowment.isCut() == 1) {
                mCutUncut = getString(R.string.cut);
            } else if (mEndowment.isCut() == 2) {
                mCutUncut = null;
            }
        } catch (Exception ne) {
            mCutUncut = null;
        }

        // FEELS LIKE
        try {
            if (mEndowment.getFeelsLike() > 0) {
                mFeelsLike = tools.convertFromMillimetersToReadableFormat(mEndowment.getFeelsLike(), tools.useMetricSystem());
            }
        } catch (Exception ne) {
            mFeelsLike = null;
        }

        // SEXUAL ORIENTATION
        try {
            mSexualOrientation = mXxx.getSexualOrientation();
        } catch (Exception ne) {
            mSexualOrientation = null;
        }

        // SEX ROLE
        try {
            mSexRole = mXxx.getSexRole();
        } catch (Exception ne) {
            mSexRole = null;
        }

        // SAFE SEX
        try {
            mSafeSex = mXxx.getSafeSex();
        } catch (Exception ne) {
            mSafeSex = null;
        }

        // SWALLOWS
        try {
            int swallowsLoads = mXxx.getSwallowsLoads();
            switch (swallowsLoads) {
                case 0:
                    mSwallows = getString(R.string.no);
                    break;
                case 1:
                    mSwallows = getString(R.string.yes);
                    break;
                case 2:
                    mSwallows = getString(R.string.unknown_plain);
                    break;
                case 3:
                    mSwallows = "";
                    break;
                default:
                    mSwallows = getString(R.string.unknown_plain);
            }
        } catch (Exception ne) {
            mSwallows = "";
            Log.e(TAG, "swallowsLoads Exception: " + ne.getLocalizedMessage());
        }

        // TAKES UP THE BUM
        try {
            int takesLoads = mXxx.getTakesLoadsUpTheBum();
            switch (takesLoads) {
                case 0:
                    mUpTheBum = getString(R.string.no);
                    break;
                case 1:
                    mUpTheBum = getString(R.string.yes);
                    break;
                case 2:
                    mUpTheBum = getString(R.string.unknown_plain);
                    break;
                case 3:
                    mUpTheBum = "";
                    break;
                default:
                    mUpTheBum = getString(R.string.unknown_plain);
            }
        } catch (Exception ne) {
            mUpTheBum = "";
            Log.e(TAG, "mUpTheBum Exception: " + ne.getLocalizedMessage());
        }

        // FETISHES
        ArrayList<String> fetishesList = new ArrayList<>();
        try {
            if (!mFetishes.isEmpty()) {
                for (Fetish fetish : mFetishes) {
                    Log.d(TAG, "FetishEntity: " + fetish.getFetish());

                    // TODO get localized fetish string
                    fetishesList.add(fetish.getFetish());
                }
                mFetishesStr = TextUtils.join(", ", fetishesList);
            }
        } catch (Exception ne) {
            mFetishesStr = null;
        }

        // HIV
        try {
            if (mHealth.getHiv().length() > 0) {
                mHiv = mHealth.getHiv();
            }
        } catch (Exception ne) {
            mHiv = null;
        }

        //HCV
        try {
            if (mHealth.getHcv().length() > 0) {
                mHcv = mHealth.getHcv();
            }
        } catch (Exception ne) {
            mHcv = null;
        }

        updateUI();
    }

    private void updateUI() {
        Needle.onMainThread().execute(() -> {
            noInformationLayout.setVisibility(View.GONE);

            // LENGTH
            if ((mLength != null) && (mLength.length() > 0)) {
                length.setText(mLength);

                length.setPaddingRelative(0, 0, 0, 0);
            } else {
                lengthHint.setVisibility(View.GONE);
                length.setVisibility(View.GONE);

            }

            // DIAMETER
            if ((mDiameter != null) && (mDiameter.length() > 0)) {
                diameter.setText(mDiameter);

                length.setPaddingRelative(0, 0, 0, paddingBottomPx);
                diameter.setPaddingRelative(0, 0, 0, 0);
            } else {
                diameterHint.setVisibility(View.GONE);
                diameter.setVisibility(View.GONE);
            }

            // GIRTH
            if ((mGirth != null) && (mGirth.length() > 0)) {
                girth.setText(mGirth);

                length.setPaddingRelative(0, 0, 0, paddingBottomPx);
                diameter.setPaddingRelative(0, 0, 0, paddingBottomPx);
                girth.setPaddingRelative(0, 0, 0, 0);
            } else {
                girthHint.setVisibility(View.GONE);
                girth.setVisibility(View.GONE);
            }

            // CUT OR UNCUT
            if ((mCutUncut != null) && (mCutUncut.length() > 0)) {
                cutUncut.setText(mCutUncut);

                length.setPaddingRelative(0, 0, 0, paddingBottomPx);
                diameter.setPaddingRelative(0, 0, 0, paddingBottomPx);
                girth.setPaddingRelative(0, 0, 0, paddingBottomPx);
                cutUncut.setPaddingRelative(0, 0, 0, 0);
            } else {
                cutUncutHint.setVisibility(View.GONE);
                cutUncut.setVisibility(View.GONE);
            }

            // FEELS LIKE
            if ((mFeelsLike != null) && (mFeelsLike.length() > 0)) {
                feelsLike.setText(mFeelsLike);

                length.setPaddingRelative(0, 0, 0, paddingBottomPx);
                diameter.setPaddingRelative(0, 0, 0, paddingBottomPx);
                girth.setPaddingRelative(0, 0, 0, paddingBottomPx);
                cutUncut.setPaddingRelative(0, 0, 0, paddingBottomPx);
                feelsLike.setPaddingRelative(0, 0, 0, 0);
            } else {
                feelsLikeHint.setVisibility(View.GONE);
                feelsLike.setVisibility(View.GONE);
            }

            if ((length.getVisibility() == View.GONE) && (diameter.getVisibility() == View.GONE) && (girth.getVisibility() == View.GONE) && (cutUncut.getVisibility() == View.GONE) && (feelsLike.getVisibility() == View.GONE)) {
                endowmentLayout.setVisibility(View.GONE);
            }

            // SEXUAL ORIENTATION
            if ((mSexualOrientation != null) && (mSexualOrientation.length() > 0)) {
                sexualOrientation.setText(mSexualOrientation);

                sexualOrientation.setPaddingRelative(0, 0, 0, 0);
            } else {
                sexualOrientationHint.setVisibility(View.GONE);
                sexualOrientation.setVisibility(View.GONE);
            }

            // SEX ROLE
            if ((mSexRole != null) && (mSexRole.length() > 0)) {
                sexRole.setText(mSexRole);

                sexualOrientation.setPaddingRelative(0, 0, 0, paddingBottomPx);
                sexRole.setPaddingRelative(0, 0, 0, 0);
            } else {
                sexRoleHint.setVisibility(View.GONE);
                sexRole.setVisibility(View.GONE);
            }

            if ((sexualOrientation.getVisibility() == View.GONE) && (sexRole.getVisibility() == View.GONE)) {
                sexLayout.setVisibility(View.GONE);
            }

            // SAFE SEX
            if ((mSafeSex != null) && (mSafeSex.length() > 0)) {
                safeSex.setText(mSafeSex);

                safeSex.setPaddingRelative(0, 0, 0, 0);
            } else {
                safeSexHint.setVisibility(View.GONE);
                safeSex.setVisibility(View.GONE);
            }

            // SWALLOWS
            if ((mSwallows != null) && (mSwallows.length() > 0)) {
                swallows.setText(mSwallows);

                safeSex.setPaddingRelative(0, 0, 0, paddingBottomPx);
                swallows.setPaddingRelative(0, 0, 0, 0);
            } else {
                swallowsHint.setVisibility(View.GONE);
                swallows.setVisibility(View.GONE);
            }

            // TAKES UP THE BUM
            if ((mUpTheBum != null) && (mUpTheBum.length() > 0)) {
                upTheBum.setText(mUpTheBum);

                safeSex.setPaddingRelative(0, 0, 0, paddingBottomPx);
                swallows.setPaddingRelative(0, 0, 0, paddingBottomPx);
                upTheBum.setPaddingRelative(0, 0, 0, 0);
            } else {
                upTheBumHint.setVisibility(View.GONE);
                upTheBum.setVisibility(View.GONE);
            }

            // FETISHES
            if ((mFetishesStr != null) && (mFetishesStr.length() > 0)) {
                fetishes.setText(mFetishesStr);

                safeSex.setPaddingRelative(0, 0, 0, paddingBottomPx);
                swallows.setPaddingRelative(0, 0, 0, paddingBottomPx);
                upTheBum.setPaddingRelative(0, 0, 0, paddingBottomPx);
                fetishes.setPaddingRelative(0, 0, 0, 0);
            } else {
                fetishesHint.setVisibility(View.GONE);
                fetishes.setVisibility(View.GONE);
            }

            if ((safeSex.getVisibility() == View.GONE) && (swallows.getVisibility() == View.GONE) && (upTheBum.getVisibility() == View.GONE) && (fetishes.getVisibility() == View.GONE)) {
                safeSexLayout.setVisibility(View.GONE);
            }

            // HIV
            if ((mHiv != null) && (mHiv.length() > 0)) {
                hiv.setText(mHiv);

                hiv.setPaddingRelative(0, 0, 0, 0);
            } else {
                hivHint.setVisibility(View.GONE);
                hiv.setVisibility(View.GONE);
            }

            // HCV
            if ((mHcv != null) && (mHcv.length() > 0)) {
                hcv.setText(mHcv);

                hiv.setPaddingRelative(0, 0, 0, paddingBottomPx);
                hcv.setPaddingRelative(0, 0, 0, 0);
            } else {
                hcvHint.setVisibility(View.GONE);
                hcv.setVisibility(View.GONE);
            }

            if ((hiv.getVisibility() == View.GONE) && (hcv.getVisibility() == View.GONE)) {
                healthLayout.setVisibility(View.GONE);
            }

            // DIVIDERS
            // Show divider ENDOWMENT & SEX INFO
            if ((endowmentLayout.getVisibility() == View.VISIBLE) && (sexLayout.getVisibility() == View.VISIBLE)) {
                viewPadding1.setVisibility(View.VISIBLE);
            }

            // Show divider ENDOWMENT & SAFE SEX
            if ((endowmentLayout.getVisibility() == View.VISIBLE) && (sexLayout.getVisibility() == View.GONE) && (safeSexLayout.getVisibility() == View.VISIBLE)) {
                viewPadding1.setVisibility(View.VISIBLE);
            }

            // Show divider ENDOWMENT & HEALTH
            if ((endowmentLayout.getVisibility() == View.VISIBLE) && (sexLayout.getVisibility() == View.GONE) && (safeSexLayout.getVisibility() == View.GONE) && (healthLayout.getVisibility() == View.VISIBLE)) {
                viewPadding1.setVisibility(View.VISIBLE);
            }

            // Show divider SEX INFO & SAFE SEX
            if ((sexLayout.getVisibility() == View.VISIBLE) && (safeSexLayout.getVisibility() == View.VISIBLE)) {
                viewPadding2.setVisibility(View.VISIBLE);
            }

            // Show divider SEX INFO & HEALTH
            if ((sexLayout.getVisibility() == View.VISIBLE) && (safeSexLayout.getVisibility() == View.GONE) && (healthLayout.getVisibility() == View.VISIBLE)) {
                viewPadding2.setVisibility(View.VISIBLE);
            }

            // Show divider SAFE SEX & HEALTH
            if ((safeSexLayout.getVisibility() == View.VISIBLE) && (healthLayout.getVisibility() == View.VISIBLE)) {
                viewPadding3.setVisibility(View.VISIBLE);
            }

            // Show "no information" text
            if ((endowmentLayout.getVisibility() == View.GONE) && (sexLayout.getVisibility() == View.GONE) && (safeSexLayout.getVisibility() == View.GONE) && (healthLayout.getVisibility() == View.GONE)) {
                int noInformationLayoutCorrectHeight = Math.round(activity.getDisplayHeight(false, false) - tools.convertPxToDp(Const.HEIGHT_TAB_BAR_AND_AD_BANNER));

                noInformationLayout.setVisibility(View.VISIBLE);
                noInformationLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, noInformationLayoutCorrectHeight));
            }

//        updateScrollview();
        });
    }

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
//
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
//        // Also pass this event to parent Activity
//        ContactViewWithViewPagerTabActivity parentActivity = (ContactViewWithViewPagerTabActivity) getActivity();
//        if (parentActivity != null) {
//            parentActivity.onScrollChanged(scrollY, scrollView);
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        mContact = activity.getContact();
    }
}
