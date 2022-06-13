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

import static net.gazeapp.R.dimen.contact_view_card_outer_margin;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

import net.gazeapp.R;
import net.gazeapp.data.GazeDatabase;
import net.gazeapp.data.dao.WorkDao;
import net.gazeapp.data.model.Contact;
import net.gazeapp.data.model.Work;
import net.gazeapp.event.WorkAddedEditedEvent;
import net.gazeapp.helpers.Const;
import net.gazeapp.helpers.SnackBarType;
import net.gazeapp.utilities.GazeTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import needle.Needle;

public class ContactViewWorkFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    private final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    private ContactViewWithViewPagerTabActivity activity;
    private Contact mContact;

    private View view;

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

    private boolean showNoInformationCard;
    private String mEmployer;
    private String mFinalDurationText;

    private WorkDao workDao;

    private List<Work> mWork;
    private ViewGroup container;

    private GazeTools tools;

    public ContactViewWorkFragment() {
    }

    public static ContactViewWorkFragment newInstance() {
        ContactViewWorkFragment fragment = new ContactViewWorkFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contactview_tab_work, container, false);
        ButterKnife.bind(this, view);

        activity = (ContactViewWithViewPagerTabActivity) getActivity();
        mContact = activity.getContact();
        tools = new GazeTools(activity);

        this.container = container;
        this.showNoInformationCard = false;

        loadData();
        return view;
    }

    private void loadData() {
        Needle.onBackgroundThread().execute(() -> {
            workDao = GazeDatabase.Companion.getDatabase(getActivity()).getWorkDao();

            mWork = workDao.getWorksByContactId(mContact.getId());

            updateUI();
        });
    }

    private void updateUI() {
        Needle.onMainThread().execute(() -> {

            if (!mWork.isEmpty()) {

                for (Work aMWork : mWork) {
                    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = inflater.inflate(R.layout.card_include_work, container);

                    LinearLayout.LayoutParams cardLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    cardLayoutParams.setMargins((int) getResources().getDimension(contact_view_card_outer_margin), (int) getResources().getDimension(contact_view_card_outer_margin), (int) getResources().getDimension(contact_view_card_outer_margin), (int) getResources().getDimension(contact_view_card_outer_margin));
                    CardView card = v.findViewById(R.id.work_card);
                    card.setLayoutParams(cardLayoutParams);

                    TextView employer = v.findViewById(R.id.employer);
                    TextView duration = v.findViewById(R.id.duration);
                    TextView profession = v.findViewById(R.id.profession);
                    TextView professionHint = v.findViewById(R.id.profession_hint);
                    final TextView address = v.findViewById(R.id.office_address);
                    TextView addressHint = v.findViewById(R.id.office_address_hint);
                    final TextView phone = v.findViewById(R.id.phone);
                    TextView phoneHint = v.findViewById(R.id.phone_hint);
                    final TextView email = v.findViewById(R.id.email);
                    TextView emailHint = v.findViewById(R.id.email_hint);
                    TextView salary = v.findViewById(R.id.salary);
                    TextView salaryHint = v.findViewById(R.id.salary_hint);
                    TextView frequency = v.findViewById(R.id.frequency);
                    TextView notes = v.findViewById(R.id.notes);
                    TextView notesHint = v.findViewById(R.id.notes_hint);

                    final Work workObj = aMWork;

                    mEmployer = workObj.getEmployer();
                    if (mEmployer != null && mEmployer.length() > 0) {
                        employer.setText(mEmployer.trim());
                    } else {
                        employer.setText(R.string.unknown_plain);
                    }

                    Date started = workObj.getStarted();
                    Date ended = workObj.getEnded();
                    String startedStr = null;
                    String endedStr = null;

                    if (started != null) {
                        startedStr = tools.formatDateToPhoneLocaleMonthAndYearOnly(started, false);
                        Log.d(TAG, "started: " + startedStr);
                    }

                    if (ended != null) {
                        endedStr = tools.formatDateToPhoneLocaleMonthAndYearOnly(ended, false);
                        Log.d(TAG, "ended: " + endedStr);
                    }

                    // Only ENDED date is set
                    if ((started == null) && (ended != null)) {
                        mFinalDurationText = getResources().getString(R.string.until) + " " + endedStr;
                    }

                    // Only STARTED date is set. Job is CURRENT JOB.
                    if ((started != null) && (ended == null)) {
                        mFinalDurationText = startedStr + " - " + getResources().getString(R.string.until_current);
                    }

                    // Both STARTED & ENDED dates are set.
                    if ((started != null) && (ended != null)) {
                        mFinalDurationText = startedStr + " - " + endedStr;
                    }

                    if ((started == null) && (ended == null)) {
                        duration.setVisibility(View.GONE);
                    } else {
                        duration.setText(mFinalDurationText);
                    }

                    String professionStr = workObj.getProfession();
                    if ((professionStr != null) && (professionStr.length() > 0)) {
                        profession.setText(professionStr.trim());
                    } else {
                        profession.setVisibility(View.GONE);
                        professionHint.setVisibility(View.GONE);
                    }

                    final String addressStr = workObj.getEmployerAddress();
                    if ((addressStr != null) && (addressStr.length() > 0)) {
                        address.setText(addressStr.trim());
                        address.setOnClickListener(view -> {
                            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + addressStr);
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);
                        });
                    } else {
                        address.setVisibility(View.GONE);
                        addressHint.setVisibility(View.GONE);
                    }

                    String phoneStr = workObj.getPhone();
                    if ((phoneStr != null) && (phoneStr.length() > 0)) {
                        phone.setText(phoneStr.trim());
                        phone.setOnClickListener(arg0 -> {
                            int permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE);
                            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                            } else {
                                makePhoneCall(phone.getText().toString().replaceAll("-", ""));
                            }
                        });
                    } else {
                        phone.setVisibility(View.GONE);
                        phoneHint.setVisibility(View.GONE);
                    }

                    final String emailStr = workObj.getEmail();
                    if ((emailStr != null) && (emailStr.length() > 0)) {
                        email.setText(emailStr.trim());
                        email.setOnClickListener(arg0 -> {
                            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", emailStr.trim(), null));
                            startActivity(Intent.createChooser(emailIntent, getString(R.string.send_email_with).toUpperCase() + "..."));
                        });
                    } else {
                        email.setVisibility(View.GONE);
                        emailHint.setVisibility(View.GONE);
                    }

                    int salaryInt = workObj.getSalary();
                    NumberFormat nf = NumberFormat.getNumberInstance();
                    String currency = workObj.getCurrency();
                    if (salaryInt > 0) {
                        String moneyString = nf.format(salaryInt);
                        if ((currency != null) && (currency.trim().length() > 0)) {
                            salary.setText(moneyString + " " + currency);
                        } else {
                            salary.setText(moneyString);
                        }
                    } else {
                        salary.setVisibility(View.GONE);
                        salaryHint.setVisibility(View.GONE);
                        frequency.setVisibility(View.GONE);
                    }

                    String frequencyStr = workObj.getFrequency();
                    if ((frequencyStr != null) && (frequencyStr.trim().length() > 0)) {
                        frequency.setText(frequencyStr);
                    } else {
                        frequency.setVisibility(View.GONE);
                    }

                    String notesStr = workObj.getNotes();
                    if ((notesStr != null) && (notesStr.length() > 0)) {
                        notes.setText(notesStr.trim());
                    } else {
                        notes.setVisibility(View.GONE);
                        notesHint.setVisibility(View.GONE);
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

            int noInformationLayoutCorrectHeight = Math.round(activity.getDisplayHeight(false, false) - tools.convertPxToDp(Const.HEIGHT_TAB_BAR_AND_AD_BANNER));

            if (showNoInformationCard) {
                noInfoCard.setVisibility(View.VISIBLE);
                noInformationLayout.setVisibility(View.VISIBLE);
                noInformationLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(noInformationLayoutCorrectHeight)));
            } else {
                noInfoCard.setVisibility(View.GONE);
            }

            //        updateScrollview();

        });


    }

    private void addToGrayLayout(View v) {
        Needle.onMainThread().execute(() -> grayLayout.addView(v));
    }

    private void clearGrayLayout() {
        Needle.onMainThread().execute(() -> grayLayout.removeAllViews());
    }

    private void makePhoneCall(String phoneNumber) {
        String phone_no = phoneNumber.replaceAll("-", "");
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.setData(Uri.parse("tel:" + phone_no));
        startActivity(callIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.

                // TODO make phone call directly here. at the moment the user has to click twice.
                //makePhoneCall("000");
            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                Toast.makeText(activity, R.string.error_no_permission_for_email, Toast.LENGTH_LONG).show();
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
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
//        ObservableScrollView scrollView = view.findViewById(R.id.scroll);
//
//        // Also pass this event to parent Activity
//        ContactViewWithViewPagerTabActivity parentActivity =
//                (ContactViewWithViewPagerTabActivity) getActivity();
//        if (parentActivity != null) {
//            parentActivity.onScrollChanged(scrollY, scrollView);
//        }
//    }

    @Subscribe
    public void onEvent(WorkAddedEditedEvent event) {
        Log.d(TAG, "WorkAddedEditedEvent received");

        Work work = event.getWork();
        if (event.getAction() == WorkAddedEditedEvent.Action.ADD) {
            if ((work.getEmployer() != null) || (work.getProfession() != null)) {
                Needle.onBackgroundThread().execute(() -> {
                    workDao.update(work);
                    mWork = workDao.getWorksByContactId(mContact.getId());

                    clearGrayLayout();
                    updateUI();
                });

                tools.showMaterialSnackBar(fragmentRoot, getString(R.string.success_work_added, work.getProfession()), SnackBarType.SUCCESS);
            } else {
                tools.showMaterialSnackBar(fragmentRoot, getString(R.string.error_work_added_updated), SnackBarType.ERROR);
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


    @Override
    public void onResume() {
        super.onResume();
        mContact = activity.getContact();
    }
}
