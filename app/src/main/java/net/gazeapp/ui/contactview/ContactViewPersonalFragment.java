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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import net.gazeapp.R;
import net.gazeapp.data.GazeDatabase;
import net.gazeapp.data.dao.DrugDao;
import net.gazeapp.data.dao.PersonalDao;
import net.gazeapp.data.model.Contact;
import net.gazeapp.data.model.Drug;
import net.gazeapp.data.model.Personal;

import java.util.List;

import needle.Needle;

@Deprecated
public class ContactViewPersonalFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    private ContactViewWithViewPagerTabActivity activity;
    private Contact mContact;
    private Personal mPersonal;
    private List<Drug> mDrugs;
    private PersonalDao personalDao;
    private DrugDao drugDao;

//    @BindView(R.id.fragment_root)
//    FrameLayout fragmentRoot;
//    @BindView(R.id.scroll)
//    ObservableScrollView scrollView;
//    @BindView(R.id.no_info_layout)
//    LinearLayout noInformationLayout;
//    @BindView(R.id.gray_layout)
//    LinearLayout grayLayout;
//
//    @BindView(R.id.personal_layout)
//    LinearLayout personalLayout;
//    @BindView(R.id.relationship_layout)
//    LinearLayout relationshipLayout;
//    @BindView(R.id.drugs_layout)
//    LinearLayout drugsLayout;
//
//    @BindView(R.id.out_hint)
//    TextView isOutHint;
//    @BindView(R.id.out)
//    TextView isOut;
//    @BindView(R.id.appearance_layout)
//    LinearLayout appearanceLayout;
//    @BindView(R.id.bar_masculine)
//    View barActive;
//    @BindView(R.id.bar_feminine)
//    View barInactive;
//    @BindView(R.id.relationship_hint)
//    TextView relationshipHint;
//    @BindView(R.id.relationship)
//    TextView relationship;
//    @BindView(R.id.children_hint)
//    TextView childrenHint;
//    @BindView(R.id.children)
//    TextView children;
//    @BindView(R.id.smoking_hint)
//    TextView smokingHint;
//    @BindView(R.id.smoking)
//    TextView smoking;
//    @BindView(R.id.alcohol_hint)
//    TextView alcoholHint;
//    @BindView(R.id.alcohol)
//    TextView alcohol;
//    @BindView(R.id.drugs_hint)
//    TextView drugsHint;
//    @BindView(R.id.drugs)
//    TextView drugs;
//
//    @BindView(R.id.view_padding_1)
//    View viewPadding1;
//    @BindView(R.id.view_padding_2)
//    View viewPadding2;

    public ContactViewPersonalFragment() {
    }

    public static ContactViewPersonalFragment newInstance() {
        ContactViewPersonalFragment fragment = new ContactViewPersonalFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contactview_tab_personal, container, false);
//        ButterKnife.bind(this, view);

        activity = (ContactViewWithViewPagerTabActivity) getActivity();
        mContact = activity.getContact();

        loadData();

        return view;
    }

    private void loadData() {
        Needle.onBackgroundThread().execute(() -> {
            personalDao = GazeDatabase.Companion.getDatabase(activity).getPersonalDao();
            drugDao = GazeDatabase.Companion.getDatabase(activity).getDrugDao();

            mPersonal = personalDao.getPersonal(mContact.getId());
            mDrugs = drugDao.getDrugsByContactId(mContact.getId());

            updateUI();
        });
    }

    public void updateUI() {
//        Needle.onMainThread().execute(() -> {
//            noInformationLayout.setVisibility(View.GONE);
//            viewPadding1.setVisibility(View.INVISIBLE);
//            viewPadding2.setVisibility(View.INVISIBLE);
//
//            grayLayout.setMinimumHeight(activity.getDisplayHeight(false, true));
//
//            // IS OUT?
//            boolean deleteIsOutLayout = false;
//            try {
//                switch (mPersonal.isOut()) {
//                    case 0:
//                        isOut.setText(R.string.no);
//                        break;
//                    case 1:
//                        isOut.setText(R.string.yes);
//                        break;
//                    case 2:
//                        isOut.setText(R.string.unknown);
//                        break;
//                    default:
//                        deleteIsOutLayout = true;
//                }
//                isOut.setPaddingRelative(0, 0, 0, 0);
//            } catch (Exception e) {
//                deleteIsOutLayout = true;
//            }
//            if (deleteIsOutLayout) {
//                isOutHint.setVisibility(View.GONE);
//                isOut.setVisibility(View.GONE);
//            }
//
//            // MASCULINE / FEMININE
//            boolean deleteAppearanceLayout = false;
//            try {
//                int effeminateLevel = mPersonal.getEffeminate();
//                if (effeminateLevel == 0) {
//                    deleteAppearanceLayout = true;
//                } else {
//                    float effeminateFloat = (float) effeminateLevel;
//                    LinearLayout.LayoutParams paramBarActive = new LinearLayout.LayoutParams(
//                            ViewGroup.LayoutParams.MATCH_PARENT,
//                            ViewGroup.LayoutParams.MATCH_PARENT, 100 - effeminateFloat);
//                    barActive.setLayoutParams(paramBarActive);
//
//                    LinearLayout.LayoutParams paramBarInactive = new LinearLayout.LayoutParams(
//                            ViewGroup.LayoutParams.MATCH_PARENT,
//                            ViewGroup.LayoutParams.MATCH_PARENT, effeminateFloat);
//                    barInactive.setLayoutParams(paramBarInactive);
//
//                    isOut.setPaddingRelative(0, 0, 0, activity.getInnerCardPaddingInPixels());
//                    appearanceLayout.setPaddingRelative(0, 0, 0, 0);
//                }
//            } catch (Exception e) {
//                deleteAppearanceLayout = true;
//            }
//            if (!SpecificValues.SHOW_XRATED) {
//                deleteAppearanceLayout = true;
//            }
//            if (deleteAppearanceLayout) {
//                appearanceLayout.setVisibility(View.GONE);
//                isOut.setPaddingRelative(0, 0, 0, 0);
//            }
//
//            if ((isOut.getVisibility() == View.GONE) && (appearanceLayout.getVisibility() == View.GONE)) {
//                personalLayout.setVisibility(View.GONE);
//            }
//
//            // RELATIONSHIP
//            boolean deleteRelationshipLayout = false;
//            try {
//                if (mPersonal.getRelationshipStatus().trim().length() > 0) {
//                    relationship.setText(mPersonal.getRelationshipStatus());
//
//                    relationship.setPaddingRelative(0, 0, 0, 0);
//                } else {
//                    deleteRelationshipLayout = true;
//                }
//            } catch (Exception e) {
//                deleteRelationshipLayout = true;
//            }
//            if (deleteRelationshipLayout) {
//                relationshipHint.setVisibility(View.GONE);
//                relationship.setVisibility(View.GONE);
//            }
//
//            // CHILDREN
//            // TODO FIXME re-add children
////            boolean deleteChildrenLayout = false;
////            try {
////                if (mPersonal.getChildren().trim().length() > 0) {
////                    children.setText(mPersonal.getChildren());
////
////                    relationship.setPaddingRelative(0, 0, 0, activity.getInnerCardPaddingInPixels());
////                    children.setPaddingRelative(0, 0, 0, 0);
////                } else {
////                    deleteChildrenLayout = true;
////                }
////            } catch (Exception e) {
////                deleteChildrenLayout = true;
////            }
////            if (deleteChildrenLayout) {
////                childrenHint.setVisibility(View.GONE);
////                children.setVisibility(View.GONE);
////            }
//
//            if ((relationship.getVisibility() == View.GONE) && (children.getVisibility() == View.GONE)) {
//                relationshipLayout.setVisibility(View.GONE);
//            }
//
//            // SMOKING
//            boolean deleteSmokingLayout = false;
//            try {
//                if (mPersonal.getSmoking().trim().length() > 0) {
//                    smoking.setText(mPersonal.getSmoking());
//
//                    smoking.setPaddingRelative(0, 0, 0, 0);
//                } else {
//                    deleteSmokingLayout = true;
//                }
//            } catch (Exception e) {
//                deleteSmokingLayout = true;
//            }
//            if (deleteSmokingLayout) {
//                smokingHint.setVisibility(View.GONE);
//                smoking.setVisibility(View.GONE);
//            }
//
//            // ALCOHOL
//            boolean deleteAlcoholLayout = false;
//            try {
//                if (mPersonal.getDrinking().trim().length() > 0) {
//                    alcohol.setText(mPersonal.getDrinking());
//
//                    smoking.setPaddingRelative(0, 0, 0, activity.getInnerCardPaddingInPixels());
//                    alcohol.setPaddingRelative(0, 0, 0, 0);
//                } else {
//                    deleteAlcoholLayout = true;
//                }
//            } catch (Exception e) {
//                deleteAlcoholLayout = true;
//            }
//            if (deleteAlcoholLayout) {
//                alcoholHint.setVisibility(View.GONE);
//                alcohol.setVisibility(View.GONE);
//            }
//
//
//            // DRUGS
//            boolean deleteDrugsLayout = false;
//            ArrayList<String> drugsList = new ArrayList<>();
//            String drugsStr = "";
//            try {
//                if (!mDrugs.isEmpty()) {
//                    for (Drug drug : mDrugs) {
//                        Log.d(TAG, "Drug: " + drug.getDrug());
//
//                        // TODO get localized Drugs string
//                        drugsList.add(drug.getDrug());
//                    }
//                    drugsStr = TextUtils.join(", ", drugsList);
//
//                    if (drugsStr.length() < 1) {
//                        deleteDrugsLayout = true;
//                    } else {
//                        drugs.setText(drugsStr);
//
//                        smoking.setPaddingRelative(0, 0, 0, activity.getInnerCardPaddingInPixels());
//                        alcohol.setPaddingRelative(0, 0, 0, activity.getInnerCardPaddingInPixels());
//                        drugs.setPaddingRelative(0, 0, 0, 0);
//                    }
//                } else {
//                    deleteDrugsLayout = true;
//                }
//
//            } catch (Exception ne) {
//                deleteDrugsLayout = true;
//            }
//            if (deleteDrugsLayout) {
//                drugsHint.setVisibility(View.GONE);
//                drugs.setVisibility(View.GONE);
//            }
//
//            if ((smoking.getVisibility() == View.GONE) && (alcohol.getVisibility() == View.GONE) && (drugs.getVisibility() == View.GONE)) {
//                drugsLayout.setVisibility(View.GONE);
//            }
//
//            // Show divider PERSONAL & RELATIONSHIP
//            if ((personalLayout.getVisibility() == View.VISIBLE) && (relationshipLayout.getVisibility() == View.VISIBLE)) {
//                viewPadding1.setVisibility(View.VISIBLE);
//            }
//
//            // Show divider PERSONAL & DRUG
//            if ((personalLayout.getVisibility() == View.VISIBLE) && (relationshipLayout.getVisibility() == View.GONE) && (drugsLayout.getVisibility() == View.VISIBLE)) {
//                viewPadding1.setVisibility(View.VISIBLE);
//            }
//
//            // Show divider RELATIONSHIP & DRUG
//            if ((relationshipLayout.getVisibility() == View.VISIBLE) && (drugsLayout.getVisibility() == View.VISIBLE)) {
//                viewPadding2.setVisibility(View.VISIBLE);
//            }
//
//            // Show "no information" text
//            if ((personalLayout.getVisibility() == View.GONE) && (relationshipLayout.getVisibility() == View.GONE) && (drugsLayout.getVisibility() == View.GONE)) {
////                int noInformationLayoutCorrectHeight = Math.round(activity.getDisplayHeight(false, false) - GazeTools.convertPxToDp(Const.HEIGHT_TAB_BAR_AND_AD_BANNER));
//
//                noInformationLayout.setVisibility(View.VISIBLE);
////                noInformationLayout.setLayoutParams(new android.widget.LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, noInformationLayoutCorrectHeight));
//            }
//
////        updateScrollview();
//        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            // TODO do something
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
//            parentActivity.onScrollChanged(scrollY, scrollView);
//        }
//    }
//
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

    @Override
    public void onResume() {
        super.onResume();
        mContact = activity.getContact();
    }
}
