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

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

import net.gazeapp.R;
import net.gazeapp.data.GazeDatabase;
import net.gazeapp.data.dao.BodyDao;
import net.gazeapp.data.dao.ContactLabelDao;
import net.gazeapp.data.dao.EndowmentDao;
import net.gazeapp.data.dao.NicknameDao;
import net.gazeapp.data.dao.NoteDao;
import net.gazeapp.data.dao.PersonalDao;
import net.gazeapp.data.dao.XxxDao;
import net.gazeapp.data.model.Body;
import net.gazeapp.data.model.Contact;
import net.gazeapp.data.model.Endowment;
import net.gazeapp.data.model.Label;
import net.gazeapp.data.model.Nickname;
import net.gazeapp.data.model.Note;
import net.gazeapp.data.model.Personal;
import net.gazeapp.data.model.Xxx;
import net.gazeapp.search.ListLabelledContactsActivity;
import net.gazeapp.ui.widget.FlowLayout;
import net.gazeapp.ui.widget.StylizedLabel;
import net.gazeapp.utilities.BuildSpecificValues;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import needle.Needle;

@Deprecated
public class ContactViewOverviewFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    private ContactViewWithViewPagerTabActivity activity;
    private Contact mContact;

    private List<Label> mContactLabels;
    private Personal mPersonal;
    private Body mBody;
    private List<Nickname> mNickname;
    private Endowment mEndowment;
    private Xxx mXxx;
    private Note mNotes;

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

    //    @BindView(R.id.additional_info_layout)
//    LinearLayout additionalInfoLayout;
    @BindView(R.id.info_card)
    CardView infoCard;
    @BindView(R.id.contact_name)
    TextView contactName;
    @BindView(R.id.nicknames)
    TextView nicknames;
    @BindView(R.id.met_in_person_icon)
    ImageView metInPersonIcon;
    @BindView(R.id.additional_info)
    TextView additionalInfo;
    @BindView(R.id.additional_info_hint)
    TextView additionalInfoHint;
    //    @BindView(R.id.age_info_layout)
//    LinearLayout ageInfoLayout;
    @BindView(R.id.age)
    TextView age;
    @BindView(R.id.age_hint)
    TextView ageHint;
    @BindView(R.id.birthdate)
    TextView birthdate;
    //    @BindView(R.id.body_info_layout)
//    LinearLayout bodyInfoLayout;
    @BindView(R.id.height)
    TextView height;
    @BindView(R.id.height_hint)
    TextView heightHint;
    @BindView(R.id.weight)
    TextView weight;
    @BindView(R.id.weight_hint)
    TextView weightHint;
    //    @BindView(R.id.endowment_info_layout)
//    LinearLayout endowmentlInfoLayout;
    @BindView(R.id.endowment)
    TextView endowment;
    @BindView(R.id.endowment_hint)
    TextView endowmentHint;
    @BindView(R.id.note_card)
    CardView noteCard;
    @BindView(R.id.notes)
    TextView notes;
    //    @BindView(R.id.no_information)
    TextView noInformationLayout;
    @BindView(R.id.labels_layout)
    LinearLayout labelsLayout;
    @BindView(R.id.flow_layout)
    FlowLayout flowLayout;

    private String mNicknamesJoined;
    private String mAdditionalInfo;
    private String mAge;
    private String mBirthdate;
    private String mHeight;
    private String mWeight;
    private String mSexyInfo;
    private String mNote;

    private ContactLabelDao contactLabelDao;
    private PersonalDao personalDao;
    private BodyDao bodyDao;
    private EndowmentDao endowmentDao;
    private XxxDao xxxDao;
    private NicknameDao nicknameDao;
    private NoteDao noteDao;

    public ContactViewOverviewFragment() {
    }

    public static ContactViewOverviewFragment newInstance() {
        ContactViewOverviewFragment fragment = new ContactViewOverviewFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contactview_tab_overview, container, false);
        ButterKnife.bind(this, view);

        activity = (ContactViewWithViewPagerTabActivity) getActivity();
        mContact = activity.getContact();

//        grayLayout.setMinimumHeight(activity.getDisplayHeight(false, true));

        loadData();

        return view;
    }

    private void loadData() {
        Needle.onBackgroundThread().execute(() -> {

            contactLabelDao = GazeDatabase.Companion.getDatabase(getActivity()).getContactLabelDao();
            personalDao = GazeDatabase.Companion.getDatabase(getActivity()).getPersonalDao();
            bodyDao = GazeDatabase.Companion.getDatabase(getActivity()).getBodyDao();
            endowmentDao = GazeDatabase.Companion.getDatabase(getActivity()).getEndowmentDao();
            xxxDao = GazeDatabase.Companion.getDatabase(getActivity()).getXxxDao();
            nicknameDao = GazeDatabase.Companion.getDatabase(getActivity()).getNicknameDao();
            noteDao = GazeDatabase.Companion.getDatabase(getActivity()).getNoteDao();

            mContactLabels = contactLabelDao.getLabelsWithContactId(mContact.getId());
            mPersonal = personalDao.getPersonal(mContact.getId());
            mBody = bodyDao.getBody(mContact.getId());
            mNickname = nicknameDao.getNicknamesByContactId(mContact.getId());
            mEndowment = endowmentDao.getEndowmentByContactId(mContact.getId());
            mXxx = xxxDao.getXxx(mContact.getId());
            mNotes = noteDao.getNote(mContact.getId());

            prepareData();
        });
    }

    private void prepareData() {

        Needle.onBackgroundThread().execute(() -> {
            // NICKNAMES
            try {
                if (!mNickname.isEmpty()) {
                    List<String> list = new ArrayList<>();

                    for (Nickname nickname : mNickname) {
                        list.add(nickname.getNickname());
                        Log.d(TAG, "Nickname: " + nickname.getNickname());
                    }

                    mNicknamesJoined = TextUtils.join(", ", list);
                } else {
                    mNicknamesJoined = null;
                }
            } catch (Exception e) {
                mNicknamesJoined = null;
            }

            // ADDITIONAL INFO
            try {
                mAdditionalInfo = mContact.getAdditionalInfo();
            } catch (Resources.NotFoundException ne) {
                mAdditionalInfo = null;
            }

            // AGE
            try {
                mAge = getString(R.string.x_years_old, mPersonal.getAge());
                if (mPersonal.getAge() == 0) {
                    mAge = null;
                }
            } catch (Exception ne) {
                mAge = null;
            }

//            try {
//                mBirthdate = GazeTools.formatDateToPhoneLocale(mPersonal.getBirthdate(), activity, false);
//                mAge = getString(R.string.x_years_old, GazeTools.getAge(mPersonal.getBirthdate()));
//            } catch (Exception ne) {
//                mBirthdate = null;
//            }

            // HEIGHT
//            try {
//                if (mBody.getHeight() > 0) {
//                    mHeight = GazeTools.convertHeightFromCentimetersToReadableFormat(mBody.getHeight(), GazeTools.useMetricSystem(), activity);
//                } else {
//                    mHeight = null;
//                }
//            } catch (Exception ne) {
//                mHeight = null;
//            }

            // WEIGHT
//            try {
//                if (mBody.getWeight() > 0) {
//                    mWeight = GazeTools.convertWeightFromGramsToReadableFormat(mBody.getWeight(), GazeTools.useMetricSystem(), activity);
//                } else {
//                    mWeight = null;
//                }
//            } catch (Exception ne) {
//                mWeight = null;
//            }

            // ENDOWMENT
            ArrayList<String> endowmentRoleList = new ArrayList<>();
            String endowmentLength = null;

//            if (mEndowment != null) {
//                endowmentLength = GazeTools.convertFromMillimetersToReadableFormat(mEndowment.getLength(), GazeTools.useMetricSystem(), activity);
//            }
            if ((endowmentLength != null) && (endowmentLength.length() > 3)) {
                endowmentRoleList.add(endowmentLength);
            }
            if (mXxx != null) {
                if ((mXxx.getSexRole() != null) && (mXxx.getSexRole().length() > 0)) {
                    endowmentRoleList.add(mXxx.getSexRole());
                }
            }

            String sexyInfoImploded = TextUtils.join(" / ", endowmentRoleList);
            if (sexyInfoImploded.trim().length() < 3) {
                mSexyInfo = null;
            } else {
                mSexyInfo = sexyInfoImploded;
            }

            // NOTES
            try {
                mNote = mNotes.getNote();
            } catch (Exception e) {
                mNote = null;
            }

            updateUI();
        });

    }

    private void updateUI() {

        Needle.onMainThread().execute(() -> {

            // MET IN PERSON
//            if (mContact.isMetInPerson()) {
//                metInPersonIcon.setVisibility(View.VISIBLE);
//                metInPersonIcon.setOnClickListener(v -> GazeTools.showMaterialSnackBar(activity, fragmentRoot, getString(R.string.met_in_person_text, mContact.getContactName()), SnackBarType.SUCCESS, 2000));
//            } else {
//                metInPersonIcon.setVisibility(View.GONE);
//            }

            // LABELS
            for (final Label contactLabel : mContactLabels) {
                StylizedLabel stylizedLabel = new StylizedLabel(activity);
                stylizedLabel.setTitle(contactLabel.getLabel());
                stylizedLabel.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                stylizedLabel.setOnClickListener(v -> {
                    ArrayList<Integer> labelList = new ArrayList<>();
                    labelList.add(contactLabel.getId());

                    Intent intent = new Intent(activity, ListLabelledContactsActivity.class);
                    intent.putExtra("labelIds", labelList);
                    startActivity(intent);
                });

                flowLayout.addView(stylizedLabel);
            }

            String contactNameStr = mContact.getContactName();
            if (contactNameStr != null) {
                contactName.setText(contactNameStr.toUpperCase());
            } else {
                contactName.setText("???");
            }

            // NICKNAMES
            if ((mNicknamesJoined != null) && (mNicknamesJoined.length() > 0)) {
                nicknames.setText(mNicknamesJoined);

//                additionalInfoLayout.setPadding((int) getResources().getDimension(R.dimen.contact_view_card_inner_padding), 140, 0, (int) getResources().getDimension(R.dimen.contact_view_card_inner_padding));
            } else {
//            contactName.setPadding((int) getResources().getDimension(R.dimen.activity_vertical_margin), (int) getResources().getDimension(R.dimen.activity_horizontal_margin), (int) getResources().getDimension(R.dimen.activity_vertical_margin), (int) getResources().getDimension(R.dimen.activity_horizontal_margin));
                nicknames.setVisibility(View.GONE);
            }

            // ADDITIONAL INFO
            if ((mAdditionalInfo != null) && (mAdditionalInfo.length() > 0)) {
                additionalInfo.setText(mAdditionalInfo);
            } else {
//                additionalInfoLayout.setVisibility(View.GONE);
            }

            // AGE
            if ((mAge != null) && (mAge.length() > 0)) {
                age.setText(mAge);
            } else {
                age.setVisibility(View.GONE);
            }

            // BRITHDATE
            if ((mBirthdate != null) && (mBirthdate.length() > 0)) {
                birthdate.setText(mBirthdate);
            } else {
                birthdate.setVisibility(View.GONE);
            }

            if (age.getVisibility() == View.GONE && birthdate.getVisibility() == View.GONE) {
//                ageInfoLayout.setVisibility(View.GONE);
                ageHint.setVisibility(View.GONE);
            }

            // HEIGHT
            if ((mHeight != null) && (mHeight.length() > 0)) {
                height.setText(mHeight);
            } else {
                height.setVisibility(View.GONE);
                heightHint.setVisibility(View.GONE);
            }

            // WEIGHT
            if ((mWeight != null) && (mWeight.length() > 0)) {
                weight.setText(mWeight);
            } else {
                weight.setVisibility(View.GONE);
                weightHint.setVisibility(View.GONE);
            }

            if (height.getVisibility() != View.GONE && weight.getVisibility() == View.GONE) {
                height.setPadding(0, 0, 0, 0);
            }

            if (height.getVisibility() == View.GONE && weight.getVisibility() != View.GONE) {
                weight.setPadding(0, 0, 0, 0);
            }

            if (height.getVisibility() == View.GONE && weight.getVisibility() == View.GONE) {
//                bodyInfoLayout.setVisibility(View.GONE);
            }

            // ENDOWMENT
            if ((mSexyInfo != null) && (mSexyInfo.length() > 0)) {
                endowment.setText(mSexyInfo);
            } else {
//                endowmentlInfoLayout.setVisibility(View.GONE);
                endowmentHint.setVisibility(View.GONE);
            }
            if (!BuildSpecificValues.SHOW_XRATED) {
//                endowmentlInfoLayout.setVisibility(View.GONE);
            }

            // NOTES
            if ((mNote != null) && (mNote.length() > 0)) {
                notes.setText(mNote);
            } else {
                noteCard.setVisibility(View.GONE);
            }

            /*
              DIVIDERS
             */
            // Show divider ADDITIONAL INFO & AGE
//            if (additionalInfoLayout.getVisibility() == View.GONE && ageInfoLayout.getVisibility() != View.GONE) {
//                int tmpPadding = Math.round(getResources().getDimension(R.dimen.contact_view_card_inner_padding));
//                ageInfoLayout.setPadding(tmpPadding, 140, tmpPadding, tmpPadding);
//            }
//
//            if (additionalInfoLayout.getVisibility() != View.GONE && ageInfoLayout.getVisibility() != View.GONE) {
//                viewPadding1.setVisibility(View.VISIBLE);
//            }
//
//            // Show divider ADDITIONAL INFO & BODY INFO
//            if (additionalInfoLayout.getVisibility() != View.GONE && bodyInfoLayout.getVisibility() != View.GONE && ageInfoLayout.getVisibility() == View.GONE) {
//                viewPadding1.setVisibility(View.VISIBLE);
//            }
//
//            // Show divider ADDITIONAL INFO & ENDOWMENT
//            if (additionalInfoLayout.getVisibility() != View.GONE && bodyInfoLayout.getVisibility() != View.GONE && ageInfoLayout.getVisibility() == View.GONE && endowmentlInfoLayout.getVisibility() != View.GONE) {
//                viewPadding1.setVisibility(View.VISIBLE);
//            }
//
//            // Show divider AGE & BODY INFO
//            if (ageInfoLayout.getVisibility() != View.GONE && bodyInfoLayout.getVisibility() != View.GONE) {
//                viewPadding2.setVisibility(View.VISIBLE);
//            }
//
//            // Show divider AGE & ENDOWMENT
//            if (ageInfoLayout.getVisibility() != View.GONE && bodyInfoLayout.getVisibility() != View.GONE && endowmentlInfoLayout.getVisibility() != View.GONE) {
//                viewPadding2.setVisibility(View.VISIBLE);
//            }
//
//            if (ageInfoLayout.getVisibility() != View.GONE && endowmentlInfoLayout.getVisibility() != View.GONE) {
//                viewPadding3.setVisibility(View.VISIBLE);
//            }
//
//            // Show divider BODY INFO & ENDOWMENT
//            if (bodyInfoLayout.getVisibility() != View.GONE && endowmentlInfoLayout.getVisibility() != View.GONE) {
//                viewPadding3.setVisibility(View.VISIBLE);
//            }

            // Show "no information" text
//            if (additionalInfoLayout.getVisibility() == View.GONE && ageInfoLayout.getVisibility() == View.GONE && bodyInfoLayout.getVisibility() == View.GONE && endowmentlInfoLayout.getVisibility() == View.GONE && noteCard.getVisibility() == View.GONE) {
//                int noInformationLayoutCorrectHeight = Math.round(activity.getDisplayHeight(false, false) - GazeTools.convertPxToDp( Const.HEIGHT_TAB_BAR_AND_AD_BANNER));
//
//                noInformationLayout.setVisibility(View.VISIBLE);
//                noInformationLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, noInformationLayoutCorrectHeight));
//            } else {
//                LinearLayout.LayoutParams viewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Math.round(getResources().getDimension(R.dimen.lower_ad_banner_height)));
//                View lowerPaddingView = new View(activity);
//                lowerPaddingView.setLayoutParams(viewLayoutParams);
//                addToGrayLayout(lowerPaddingView);
//
//                noInformationLayout.setVisibility(View.GONE);
//            }

            //        updateScrollview();
        });

    }

    private void addToGrayLayout(View v) {
//        grayLayout.addView(v);
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
