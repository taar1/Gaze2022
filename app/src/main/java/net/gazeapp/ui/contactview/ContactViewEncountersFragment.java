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

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

import net.gazeapp.R;
import net.gazeapp.data.GazeDatabase;
import net.gazeapp.data.dao.ContactDao;
import net.gazeapp.data.dao.EncounterDao;
import net.gazeapp.data.model.Contact;
import net.gazeapp.data.model.Encounter;
import net.gazeapp.event.EncounterAddedEditedEvent;
import net.gazeapp.utilities.BuildSpecificValues;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import needle.Needle;

@Deprecated
/* Use TabEncountersFragment.kt */
public class ContactViewEncountersFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    private ContactViewWithViewPagerTabActivity activity;
    private Contact mContact;

    private boolean showNoInformationCard;

    private ViewGroup container;
    private ContactDao contactDao;
    private EncounterDao encounterDao;
    private List<Encounter> mEncounter;

    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    @BindView(R.id.fragment_root)
    FrameLayout fragmentRoot;
    @BindView(R.id.scroll)
    ObservableScrollView scrollView;
    @BindView(R.id.no_info_layout)
    LinearLayout noInformationLayout;
    @BindView(R.id.gray_layout)
    LinearLayout grayLayout;
    @BindView(R.id.no_info_card)
    CardView noInfoCard;

    public ContactViewEncountersFragment() {
    }

    public static ContactViewEncountersFragment newInstance() {
        ContactViewEncountersFragment fragment = new ContactViewEncountersFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contactview_tab_encounters, container, false);
        ButterKnife.bind(this, view);

        this.container = container;

        activity = (ContactViewWithViewPagerTabActivity) getActivity();
        mContact = activity.getContact();

        grayLayout.setMinimumHeight(activity.getDisplayHeight(false, true));

        loadData();

        return view;
    }

    private void loadData() {
        Needle.onBackgroundThread().execute(() -> {
            contactDao = GazeDatabase.Companion.getDatabase(getActivity()).getContactDao();
            encounterDao = GazeDatabase.Companion.getDatabase(getActivity()).getEncounterDao();

            mEncounter = encounterDao.getEncountersByContactId(mContact.getId());

            updateUI();
        });
    }

    private void updateUI() {
        Needle.onMainThread().execute(() -> {
            showNoInformationCard = false;

            if (!mEncounter.isEmpty()) {

                for (Encounter aMEncounter : mEncounter) {
                    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = inflater.inflate(R.layout.card_include_encounter, container);

                    LinearLayout.LayoutParams cardLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    cardLayoutParams.setMargins((int) getResources().getDimension(R.dimen.contact_view_card_outer_margin), (int) getResources().getDimension(R.dimen.contact_view_card_outer_margin), (int) getResources().getDimension(R.dimen.contact_view_card_outer_margin), (int) getResources().getDimension(R.dimen.contact_view_card_outer_margin));
                    CardView card = v.findViewById(R.id.encounter_card);
                    card.setLayoutParams(cardLayoutParams);

                    RatingBar ratingBar = v.findViewById(R.id.rating_bar);
                    TextView meetDate = v.findViewById(R.id.meet_date);
                    TextView meetLocationHint = v.findViewById(R.id.meet_location_hint);
                    TextView meetLocation = v.findViewById(R.id.meet_location);
                    TextView googleMaps = v.findViewById(R.id.google_maps_link);
                    TextView myRoleHint = v.findViewById(R.id.my_role_hint);
                    TextView myRole = v.findViewById(R.id.my_role);
                    TextView hisRoleHint = v.findViewById(R.id.his_role_hint);
                    TextView hisRole = v.findViewById(R.id.his_role);
                    TextView safeSexHint = v.findViewById(R.id.safe_sex_hint);
                    TextView safeSex = v.findViewById(R.id.safe_sex);
                    TextView myLoadHint = v.findViewById(R.id.my_load_hint);
                    TextView myLoad = v.findViewById(R.id.my_load);
                    TextView hisLoadHint = v.findViewById(R.id.his_load_hint);
                    TextView hisLoad = v.findViewById(R.id.his_load);
                    TextView remarksHint = v.findViewById(R.id.remarks_hint);
                    TextView remarks = v.findViewById(R.id.remarks);

                    if (!BuildSpecificValues.SHOW_XRATED) {
                        myRoleHint.setVisibility(View.GONE);
                        myRole.setVisibility(View.GONE);
                        hisRoleHint.setVisibility(View.GONE);
                        hisRole.setVisibility(View.GONE);
                        safeSexHint.setVisibility(View.GONE);
                        safeSex.setVisibility(View.GONE);
                        myLoadHint.setVisibility(View.GONE);
                        myLoad.setVisibility(View.GONE);
                        hisLoadHint.setVisibility(View.GONE);
                        hisLoad.setVisibility(View.GONE);
                    }

                    final Encounter encounter = aMEncounter;

                    String meetLocationStr = encounter.getMeetLocation();
                    if (meetLocationStr != null && meetLocationStr.length() > 0) {
                        meetLocation.setText(meetLocationStr.trim());
                    } else {
                        meetLocation.setVisibility(View.GONE);
                    }

                    String googleMapsLinkStr = encounter.getGoogleMapsLink();
                    if (googleMapsLinkStr != null && googleMapsLinkStr.length() > 0) {
                        googleMaps.setText(googleMapsLinkStr.trim());
                    } else {
                        googleMaps.setVisibility(View.GONE);
                    }

                    if (googleMaps.getVisibility() == View.GONE) {
                        meetLocation.setPadding(0, 0, 0, (int) getResources().getDimension(R.dimen.margin_standard));

                        if (meetLocation.getVisibility() == View.GONE) {
                            meetLocationHint.setVisibility(View.GONE);
                        }
                    }

                    if (encounter.getMeetDate() != null) {
//                        meetDate.setText(GazeTools.formatDateToPhoneLocale(encounter.getMeetDate(), activity, true));
                    }

                    String myRoleStr = encounter.getMyRole();
                    if ((myRoleStr != null) && (myRoleStr.length() > 0)) {
                        myRole.setText(myRoleStr);
                    } else {
                        myRoleHint.setVisibility(View.GONE);
                        myRole.setVisibility(View.GONE);
                    }

                    String hisRoleStr = encounter.getHisRole();
                    if ((hisRoleStr != null) && (hisRoleStr.length() > 0)) {
                        hisRole.setText(hisRoleStr);
                    } else {
                        hisRoleHint.setVisibility(View.GONE);
                        hisRole.setVisibility(View.GONE);
                    }

//                    int hadSafeSex = encounter.isSafeSex();
//                    if (hadSafeSex == 1) {
//                        safeSex.setText(getString(R.string.yes));
//                    } else if (hadSafeSex == 0) {
//                        safeSex.setText(getString(R.string.no));
//                    } else if (hadSafeSex == 3) {
//                        safeSex.setText(getString(R.string.unsure));
//                        // TODO add checkbox "unsure" or something
//                    }

                    String myLoadStr = encounter.getMyLoadWentTo();
                    if ((myLoadStr != null) && (myLoadStr.length() > 0)) {
                        myLoad.setText(myLoadStr);
                    } else {
                        myLoadHint.setVisibility(View.GONE);
                        myLoad.setVisibility(View.GONE);
                    }

                    String hisLoadStr = encounter.getHisLoadWentTo();
                    if ((hisLoadStr != null) && (hisLoadStr.length() > 0)) {
                        hisLoad.setText(hisLoadStr);
                    } else {
                        hisLoadHint.setVisibility(View.GONE);
                        hisLoad.setVisibility(View.GONE);
                    }

                    String remarksStr = encounter.getRemarks();
                    if ((remarksStr != null) && (remarksStr.length() > 0)) {
                        remarks.setText(remarksStr);
                    } else {
                        remarksHint.setVisibility(View.GONE);
                        remarks.setVisibility(View.GONE);
                    }

                    float rating = (float) encounter.getRating() / 2;
                    if (rating == 0) {
                        ratingBar.setVisibility(View.INVISIBLE);
                    } else {
                        ratingBar.setRating(rating);
                    }

                    addToGrayLayout(v);
                }

                LinearLayout.LayoutParams viewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Math.round(getResources().getDimension(R.dimen.lower_ad_banner_height)));
                View lowerPaddingView = new View(activity);
                lowerPaddingView.setLayoutParams(viewLayoutParams);
                addToGrayLayout(lowerPaddingView);
            } else {
                showNoInformationCard = true;
            }

            updateLayout();
        });


    }

    private void addToGrayLayout(View v) {
        Needle.onMainThread().execute(() -> grayLayout.addView(v));
    }

    private void clearGrayLayout() {
        Needle.onMainThread().execute(() -> grayLayout.removeAllViews());
    }

    private void updateLayout() {
        Needle.onMainThread().execute(() -> {
            if (showNoInformationCard) {
//                int noInformationLayoutCorrectHeight = Math.round(activity.getDisplayHeight(false, false) - GazeTools.convertPxToDp( Const.HEIGHT_TAB_BAR_AND_AD_BANNER));

                noInfoCard.setVisibility(View.VISIBLE);
                noInformationLayout.setVisibility(View.VISIBLE);
//                noInformationLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, noInformationLayoutCorrectHeight));
            } else {
                noInfoCard.setVisibility(View.GONE);
                noInformationLayout.setVisibility(View.GONE);
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

    @Subscribe
    public void onEvent(EncounterAddedEditedEvent event) {
        Log.d(TAG, "EncounterAddedEditedEvent received");
        Encounter encounter = event.getEncounter();
        if (event.getAction() == EncounterAddedEditedEvent.Action.ADD) {
            if (encounter.getMeetLocation() != null) {

                encounterDao.insert(encounter);

                mEncounter = encounterDao.getEncountersByContactId(mContact.getId());
                clearGrayLayout();

                grayLayout.setMinimumHeight(activity.getDisplayHeight(false, true));
                updateUI();

//                GazeTools.showMaterialSnackBar(activity, fragmentRoot, getString(R.string.success_encounter_added, GazeTools.formatDateToPhoneLocale(encounter.getMeetDate(), getContext(), true)), SnackBarType.SUCCESS);
            } else {
//                GazeTools.showMaterialSnackBar(activity, fragmentRoot, getString(R.string.error_encounter_added_updated), SnackBarType.ERROR);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
