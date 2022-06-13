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
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

import net.gazeapp.R;
import net.gazeapp.data.GazeDatabase;
import net.gazeapp.data.dao.SocialMediaDao;
import net.gazeapp.data.model.Contact;
import net.gazeapp.data.model.SocialMedia;
import net.gazeapp.helpers.Const;
import net.gazeapp.utilities.GazeTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import needle.Needle;

public class ContactViewSocialMediaFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    private ContactViewWithViewPagerTabActivity activity;
    private Contact mContact;
    private SocialMedia mSocialMedia;


    @BindView(R.id.socialnetworks_inner_layout)
    LinearLayout socialmediaInnerLayout;
    @BindView(R.id.messengers_inner_layout)
    LinearLayout messengersInnerLayout;
    @BindView(R.id.dating_inner_layout)
    LinearLayout datingInnerLayout;
    @BindView(R.id.gaming_inner_layout)
    LinearLayout gamingInnerLayout;

    @BindView(R.id.messenger_card)
    CardView messengerCard;
    @BindView(R.id.socialmedia_card)
    CardView socialMediaCard;
    @BindView(R.id.dating_card)
    CardView datingCard;
    @BindView(R.id.gaming_card)
    CardView gamingCard;

    @BindView(R.id.fragment_root)
    FrameLayout fragmentRoot;
    @BindView(R.id.scroll)
    ObservableScrollView scrollView;
    @BindView(R.id.no_info_card)
    CardView noInformationCard;
    @BindView(R.id.no_info_layout)
    LinearLayout noInformationLayout;
    @BindView(R.id.gray_layout)
    LinearLayout grayLayout;

    private int innerPadding;

    //    private String mAppleIdText;
    private String mFacebookText;
    private String mFlickrText;
    private String mGooglePlusText;
    private String mInstagramText;
    private String mLinkedinText;
    private String mPinterestText;
    private String mPornhubText;
    private String mRedtubeText;
    private String mSpotifyText;
    private String mTumblrText;
    private String mTwitterText;
    private String mVimeoText;
    private String mXhamsterText;
    private String mXtubeText;
    private String mXingText;
    private String mYoutubeText;
    private String mLineText;
    private String skypeText;
    private String snapchatText;
    private String viberText;
    private String wechatText;
    private String whatsappText;
    private String adam4adamText;
    private String bbrtText;
    private String boyahoyText;
    private String dudesnudeText;
    private String gaycomText;
    private String gaydarText;
    private String grindrText;
    private String growlrText;
    private String hornetText;
    private String jackdText;
    private String manhuntText;
    private String planetromeoText;
    private String reconText;
    private String scruffText;
    private String miiverseText;
    private String tinderText;
    private String nintendonetworkText;
    private String playstationnetworkText;
    private String xboxgamertagText;
    private SocialMediaDao socialMediaDao;

    private GazeTools tools;

    public ContactViewSocialMediaFragment() {
    }

    public static ContactViewSocialMediaFragment newInstance() {
        ContactViewSocialMediaFragment fragment = new ContactViewSocialMediaFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contactview_tab_socialmedia_container, container, false);
        ButterKnife.bind(this, view);

        activity = (ContactViewWithViewPagerTabActivity) getActivity();
        mContact = activity.getContact();
        tools = new GazeTools(activity);

        loadData();

        return view;
    }

    private void loadData() {
        Needle.onBackgroundThread().execute(() -> {

            socialMediaDao = GazeDatabase.Companion.getDatabase(activity).getSocialMediaDao();
            mSocialMedia = socialMediaDao.getSocialMedia(mContact.getId());

            prepareData();
        });
    }

    private void prepareData() {
        if (mSocialMedia != null) {

            // SOCIAL NETWORKS
            // SOCIAL NETWORKS
            // SOCIAL NETWORKS
            // SOCIAL NETWORKS
            try {
                mFacebookText = mSocialMedia.getFacebook().trim();
                Log.d(TAG, "FACEBOOK: " + mFacebookText);
            } catch (Exception e) {
                mFacebookText = null;
            }

            try {
                mFlickrText = mSocialMedia.getFlickr().trim();
                Log.d(TAG, "FLICKR: " + mFlickrText);
            } catch (Exception e) {
                mFlickrText = null;
            }

            try {
                mInstagramText = mSocialMedia.getInstagram().trim();
                Log.d(TAG, "INSTAGRAM: " + mInstagramText);
            } catch (Exception e) {
                mInstagramText = null;
            }

            try {
                mLinkedinText = mSocialMedia.getLinkedIn().trim();
                Log.d(TAG, "LINKEDIN: " + mLinkedinText);
            } catch (Exception e) {
                mLinkedinText = null;
            }

            try {
                mPinterestText = mSocialMedia.getPinterest().trim();
                Log.d(TAG, "PINTEREST: " + mPinterestText);
            } catch (Exception e) {
                mPinterestText = null;
            }

            try {
                mPornhubText = mSocialMedia.getPornhub().trim();
                Log.d(TAG, "PORNHUB: " + mPornhubText);
            } catch (Exception e) {
                mPornhubText = null;
            }

            try {
                mRedtubeText = mSocialMedia.getRedtube();
                Log.d(TAG, "REDTUBE: " + mRedtubeText);
            } catch (Exception e) {
                mRedtubeText = null;
            }

            try {
                mSpotifyText = mSocialMedia.getSpotify().trim();
                Log.d(TAG, "SPOTIFY: " + mSpotifyText);
            } catch (Exception e) {
                mSpotifyText = null;
            }

            try {
                mTumblrText = mSocialMedia.getTumblr().trim();
                Log.d(TAG, "TUMBLR: " + mTumblrText);
            } catch (Exception e) {
                mTumblrText = null;
            }

            try {
                mTwitterText = mSocialMedia.getTwitter().trim();
                Log.d(TAG, "TWITTER: " + mTwitterText);
            } catch (Exception e) {
                mTwitterText = null;
            }

            try {
                mVimeoText = mSocialMedia.getVimeo().trim();
                Log.d(TAG, "VIMEO: " + mVimeoText);
            } catch (Exception e) {
                mVimeoText = null;
            }

            try {
                mXingText = mSocialMedia.getXing().trim();
                Log.d(TAG, "XING: " + mXingText);
            } catch (Exception e) {
                mXingText = null;
            }

            try {
                mXhamsterText = mSocialMedia.getXhamster().trim();
                Log.d(TAG, "XHAMSTER: " + mXhamsterText);
            } catch (Exception e) {
                mXhamsterText = null;
            }

            try {
                mXtubeText = mSocialMedia.getXtube().trim();
                Log.d(TAG, "XTUBE: " + mXtubeText);
            } catch (Exception e) {
                mXtubeText = null;
            }

            try {
                mYoutubeText = mSocialMedia.getYoutube().trim();
                Log.d(TAG, "YOUTUBE: " + mYoutubeText);
            } catch (Exception e) {
                mYoutubeText = null;
            }

            // MESSENGERS
            // MESSENGERS
            // MESSENGERS
            // MESSENGERS
            try {
                mLineText = mSocialMedia.getLineMessenger().trim();
                Log.d(TAG, "LINE: " + mLineText);
            } catch (Exception e) {
                mLineText = null;
            }

            try {
                skypeText = mSocialMedia.getSkype().trim();
                Log.d(TAG, "SKYPE: " + skypeText);

            } catch (Exception e) {
                skypeText = null;
            }

            try {
                snapchatText = mSocialMedia.getSnapChat().trim();
                Log.d(TAG, "SNAPCHAT: " + snapchatText);

            } catch (Exception e) {
                snapchatText = null;
            }

            try {
                viberText = mSocialMedia.getViber().trim();
                Log.d(TAG, "VIBER: " + viberText);

            } catch (Exception e) {
                viberText = null;
            }

            try {
                wechatText = mSocialMedia.getWeChat().trim();
                Log.d(TAG, "WECHAT: " + wechatText);

            } catch (Exception e) {
                wechatText = null;
            }

            try {
                whatsappText = mSocialMedia.getWhatsapp().trim();
                Log.d(TAG, "WHATSAPP: " + whatsappText);

            } catch (Exception e) {
                whatsappText = null;
            }


            // DATING APPS
            // DATING APPS
            // DATING APPS
            // DATING APPS
            try {
                adam4adamText = mSocialMedia.getAdam4adam().trim();
                Log.d(TAG, "ADAM4ADAM: " + adam4adamText);
            } catch (Exception e) {
                adam4adamText = null;
            }

            try {
                bbrtText = mSocialMedia.getBbrt().trim();
                Log.d(TAG, "BBRT: " + bbrtText);
            } catch (Exception e) {
                bbrtText = null;
            }

            try {
                boyahoyText = mSocialMedia.getBoyahoy().trim();
                Log.d(TAG, "BOYAHOY: " + boyahoyText);
            } catch (Exception e) {
                boyahoyText = null;
            }

            try {
                dudesnudeText = mSocialMedia.getDudesnude().trim();
                Log.d(TAG, "DUDESNUDE: " + dudesnudeText);
            } catch (Exception e) {
                dudesnudeText = null;
            }

            try {
                gaycomText = mSocialMedia.getGaycom().trim();
                Log.d(TAG, "GAY.COM: " + gaycomText);
            } catch (Exception e) {
                gaycomText = null;
            }

            try {
                gaydarText = mSocialMedia.getGaydar().trim();
                Log.d(TAG, "GAYDAR: " + gaydarText);
            } catch (Exception e) {
                gaydarText = null;
            }

            try {
                grindrText = mSocialMedia.getGrindr().trim();
                Log.d(TAG, "GRINDR: " + grindrText);
            } catch (Exception e) {
                grindrText = null;
            }

            try {
                growlrText = mSocialMedia.getGrowlr().trim();
                Log.d(TAG, "GROWLR: " + growlrText);
            } catch (Exception e) {
                growlrText = null;
            }

            try {
                hornetText = mSocialMedia.getHornet().trim();
                Log.d(TAG, "HORNET: " + hornetText);
            } catch (Exception e) {
                hornetText = null;
            }

            try {
                jackdText = mSocialMedia.getJackd().trim();
                Log.d(TAG, "JACKD: " + jackdText);
            } catch (Exception e) {
                jackdText = null;
            }

            try {
                manhuntText = mSocialMedia.getManhunt().trim();
                Log.d(TAG, "MANHUNT: " + manhuntText);
            } catch (Exception e) {
                manhuntText = null;
            }

            try {
                planetromeoText = mSocialMedia.getPlanetRomeo().trim();
                Log.d(TAG, "PLANETROMEO: " + planetromeoText);
            } catch (Exception e) {
                planetromeoText = null;
            }

            try {
                reconText = mSocialMedia.getRecon().trim();
                Log.d(TAG, "RECON: " + reconText);
            } catch (Exception e) {
                reconText = null;
            }

            try {
                scruffText = mSocialMedia.getScruff().trim();
                Log.d(TAG, "SCRUFF: " + scruffText);
            } catch (Exception e) {
                scruffText = null;
            }

            try {
                tinderText = mSocialMedia.getTinder().trim();
                Log.d(TAG, "TINDER: " + tinderText);
            } catch (Exception e) {
                tinderText = null;
            }

            // GAMING
            // GAMING
            // GAMING
            // GAMING
            try {
                miiverseText = mSocialMedia.getMiiverse().trim();
                Log.d(TAG, "MIIVERSE: " + miiverseText);
            } catch (Exception e) {
                miiverseText = null;
            }

            try {
                nintendonetworkText = mSocialMedia.getNintendoNetwork().trim();
                Log.d(TAG, "NINTENDONETWORK: " + nintendonetworkText);
            } catch (Exception e) {
                nintendonetworkText = null;
            }


            try {
                playstationnetworkText = mSocialMedia.getPlaystationNetwork().trim();
                Log.d(TAG, "PLAYSTATIONNETWORK: " + playstationnetworkText);
            } catch (Exception e) {
                playstationnetworkText = null;
            }

            try {
                xboxgamertagText = mSocialMedia.getXboxGamertag().trim();
                Log.d(TAG, "XBOXGAMERTAG: " + xboxgamertagText);
            } catch (Exception e) {
                xboxgamertagText = null;
            }

        }

        Needle.onMainThread().execute(() -> updateUI());
//        updateScrollview();
    }

    void updateUI() {
        Needle.onMainThread().execute(() -> {

            grayLayout.setMinimumHeight(activity.getDisplayHeight(false, true));
            innerPadding = activity.getInnerCardPaddingInPixels();

            noInformationCard.setVisibility(View.GONE);
            noInformationLayout.setVisibility(View.GONE);

            // SOCIAL NETWORKS
            // SOCIAL NETWORKS
            // SOCIAL NETWORKS
            boolean deleteSocialNetworksCard = true;
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // FACEBOOK
            if ((mFacebookText != null) && (mFacebookText.length() > 0)) {
                View facebookView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                facebookView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(mFacebookText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, mFacebookText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView facebookIcon = facebookView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.facebook_24dp).into(facebookIcon);

                TextView facebookHint = facebookView.findViewById(R.id.sm_hint);
                facebookHint.setText(R.string.facebook);

                TextView facebookValue = facebookView.findViewById(R.id.sm_value);
                facebookValue.setText(tools.removeHttpPrefix(mFacebookText));
                facebookValue.setOnClickListener(view -> {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    if (URLUtil.isValidUrl(mFacebookText)) {
                        i.setData(Uri.parse(mFacebookText));
                    } else {
                        i.setData(Uri.parse("https://www.facebook.com/" + mFacebookText));
                    }
                    startActivity(i);
                });

                socialmediaInnerLayout.addView(facebookView);
                deleteSocialNetworksCard = false;
            }


            // FLICKR
            if ((mFlickrText != null) && (mFlickrText.length() > 0)) {
                View flickrView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                flickrView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(mFlickrText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, mFlickrText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView flickrIcon = flickrView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.flickr_24dp).into(flickrIcon);

                TextView flickrHint = flickrView.findViewById(R.id.sm_hint);
                flickrHint.setText(R.string.flickr);

                TextView flickrValue = flickrView.findViewById(R.id.sm_value);
                flickrValue.setText(tools.removeHttpPrefix(mFlickrText));
                flickrValue.setOnClickListener(view -> {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    if (URLUtil.isValidUrl(mFlickrText)) {
                        i.setData(Uri.parse(mFlickrText));
                    } else {
                        i.setData(Uri.parse("https://www.flickr.com/photos/" + mFlickrText));
                    }
                    startActivity(i);
                });

                socialmediaInnerLayout.addView(flickrView);
                deleteSocialNetworksCard = false;
            }


            // GOOGLE PLUS
            if ((mGooglePlusText != null) && (mGooglePlusText.length() > 0)) {
                View gpView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                gpView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(mGooglePlusText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, mGooglePlusText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView gpIcon = gpView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.googleplus_24dp).into(gpIcon);

                TextView gpHint = gpView.findViewById(R.id.sm_hint);
                gpHint.setText(R.string.googleplus);

                TextView gpValue = gpView.findViewById(R.id.sm_value);
                gpValue.setText(tools.removeHttpPrefix(mGooglePlusText));
                gpValue.setOnClickListener(view -> {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    if (URLUtil.isValidUrl(mGooglePlusText)) {
                        i.setData(Uri.parse(mGooglePlusText));
                    } else {
                        i.setData(Uri.parse("https://plus.google.com/" + mGooglePlusText));
                    }
                    startActivity(i);
                });

                socialmediaInnerLayout.addView(gpView);
                deleteSocialNetworksCard = false;
            }


            // INSTAGRAM
            if ((mInstagramText != null) && (mInstagramText.length() > 0)) {
                View instagramView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                instagramView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(mInstagramText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, mInstagramText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView instagramIcon = instagramView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.instagram_24dp).into(instagramIcon);

                TextView instagramHint = instagramView.findViewById(R.id.sm_hint);
                instagramHint.setText(R.string.instagram);

                TextView instagramValue = instagramView.findViewById(R.id.sm_value);
                instagramValue.setText(tools.removeHttpPrefix(mInstagramText));
                instagramValue.setOnClickListener(view -> {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    if (URLUtil.isValidUrl(mInstagramText)) {
                        i.setData(Uri.parse(mInstagramText));
                    } else {
                        i.setData(Uri.parse("https://www.instagram.com/" + mInstagramText));
                    }
                    startActivity(i);
                });

                socialmediaInnerLayout.addView(instagramView);
                deleteSocialNetworksCard = false;
            }

            // LINKED IN
            if ((mLinkedinText != null) && (mLinkedinText.length() > 0)) {
                View linkedInView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                linkedInView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(mLinkedinText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, mLinkedinText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView linkedInIcon = linkedInView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.linkedin_24dp).into(linkedInIcon);

                TextView linkedInHint = linkedInView.findViewById(R.id.sm_hint);
                linkedInHint.setText(R.string.linkedin);

                TextView linkedInValue = linkedInView.findViewById(R.id.sm_value);
                linkedInValue.setText(tools.removeHttpPrefix(mLinkedinText));
                linkedInValue.setOnClickListener(view -> {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    if (URLUtil.isValidUrl(mLinkedinText)) {
                        i.setData(Uri.parse(mLinkedinText));
                    } else {
                        i.setData(Uri.parse("https://www.linkedin.com/in/" + mLinkedinText));
                    }
                    startActivity(i);
                });

                socialmediaInnerLayout.addView(linkedInView);
                deleteSocialNetworksCard = false;
            }


            // PINTEREST
            if ((mPinterestText != null) && (mPinterestText.length() > 0)) {
                View pinterestView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                pinterestView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(mPinterestText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, mPinterestText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView pinterestIcon = pinterestView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.pinterest_24dp).into(pinterestIcon);

                TextView pinterestHint = pinterestView.findViewById(R.id.sm_hint);
                pinterestHint.setText(R.string.pinterest);

                TextView pinterestValue = pinterestView.findViewById(R.id.sm_value);
                pinterestValue.setText(tools.removeHttpPrefix(mPinterestText));
                pinterestValue.setOnClickListener(view -> {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    if (URLUtil.isValidUrl(mPinterestText)) {
                        i.setData(Uri.parse(mPinterestText));
                    } else {
                        i.setData(Uri.parse("https://www.pinterest.com/" + mPinterestText));
                    }
                    startActivity(i);
                });

                socialmediaInnerLayout.addView(pinterestView);
                deleteSocialNetworksCard = false;
            }


            // PORNHUB
            if ((mPornhubText != null) && (mPornhubText.length() > 0)) {
                View pornhubView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                pornhubView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(mPornhubText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, mPornhubText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView pornhubIcon = pornhubView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.pornhub_24dp).into(pornhubIcon);

                TextView pornhubHint = pornhubView.findViewById(R.id.sm_hint);
                pornhubHint.setText(R.string.pornhub);

                TextView pornhubValue = pornhubView.findViewById(R.id.sm_value);
                pornhubValue.setText(tools.removeHttpPrefix(mPornhubText));
                pornhubValue.setOnClickListener(view -> {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    if (URLUtil.isValidUrl(mPornhubText)) {
                        i.setData(Uri.parse(mPornhubText));
                    } else {
                        i.setData(Uri.parse("http://www.pornhub.com/users/" + mPornhubText));
                    }
                    startActivity(i);
                });

                socialmediaInnerLayout.addView(pornhubView);
                deleteSocialNetworksCard = false;
            }


            // REDTUBE
            if ((mRedtubeText != null) && (mRedtubeText.length() > 0)) {
                View redtubeView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                redtubeView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(mRedtubeText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, mRedtubeText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView redtubeIcon = redtubeView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.redtube_24dp).into(redtubeIcon);

                TextView redtubeHint = redtubeView.findViewById(R.id.sm_hint);
                redtubeHint.setText(R.string.redtube);

                TextView redtubeValue = redtubeView.findViewById(R.id.sm_value);
                redtubeValue.setText(tools.removeHttpPrefix(mRedtubeText));
                redtubeValue.setOnClickListener(view -> {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    if (URLUtil.isValidUrl(mRedtubeText)) {
                        i.setData(Uri.parse(mRedtubeText));
                    } else {
                        i.setData(Uri.parse("http://www.redtube.com/" + mRedtubeText));
                    }
                    startActivity(i);
                });

                socialmediaInnerLayout.addView(redtubeView);
                deleteSocialNetworksCard = false;
            }


            // SPOTIFY
            if ((mSpotifyText != null) && (mSpotifyText.length() > 0)) {
                View spotifyView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                spotifyView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(mSpotifyText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, mSpotifyText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView spotifyIcon = spotifyView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.spotify_24dp).into(spotifyIcon);

                TextView spotifyHint = spotifyView.findViewById(R.id.sm_hint);
                spotifyHint.setText(R.string.spotify);

                TextView spotifyValue = spotifyView.findViewById(R.id.sm_value);
                spotifyValue.setText(tools.removeHttpPrefix(mSpotifyText));
                spotifyValue.setOnClickListener(view -> {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    if (URLUtil.isValidUrl(mSpotifyText)) {
                        i.setData(Uri.parse(mSpotifyText));
                    } else {
                        i.setData(Uri.parse("https://www.spotify.com/" + mSpotifyText));
                    }
                    startActivity(i);
                });

                socialmediaInnerLayout.addView(spotifyView);
                deleteSocialNetworksCard = false;
            }


            // TUMBLR
            if ((mTumblrText != null) && (mTumblrText.length() > 0)) {
                View tumblrView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                tumblrView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(mTumblrText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, mTumblrText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView tumblrIcon = tumblrView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.tumblr_24dp).into(tumblrIcon);

                TextView tumblrHint = tumblrView.findViewById(R.id.sm_hint);
                tumblrHint.setText(R.string.tumblr);

                TextView tumblrValue = tumblrView.findViewById(R.id.sm_value);
                tumblrValue.setText(tools.removeHttpPrefix(mTumblrText));
                tumblrValue.setOnClickListener(view -> {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    if (URLUtil.isValidUrl(mTumblrText)) {
                        i.setData(Uri.parse(mTumblrText));
                    } else {
                        i.setData(Uri.parse("https://" + mTumblrText + ".tumblr.com/"));
                    }
                    startActivity(i);
                });

                socialmediaInnerLayout.addView(tumblrView);
                deleteSocialNetworksCard = false;
            }


            // TWITTER
            if ((mTwitterText != null) && (mTwitterText.length() > 0)) {
                View twitterView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                twitterView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(mTwitterText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, mTwitterText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView twitterIcon = twitterView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.twitter_24dp).into(twitterIcon);

                TextView twitterHint = twitterView.findViewById(R.id.sm_hint);
                twitterHint.setText(R.string.twitter);

                TextView twitterValue = twitterView.findViewById(R.id.sm_value);
                twitterValue.setText(tools.removeHttpPrefix(mTwitterText));
                twitterValue.setOnClickListener(view -> {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    if (URLUtil.isValidUrl(mTwitterText)) {
                        i.setData(Uri.parse(mTwitterText));
                    } else {
                        i.setData(Uri.parse("https://twitter.com/" + mTwitterText));
                    }
                    startActivity(i);
                });

                socialmediaInnerLayout.addView(twitterView);
                deleteSocialNetworksCard = false;
            }


            // VIMEO
            if ((mVimeoText != null) && (mVimeoText.length() > 0)) {
                View vimeoView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                vimeoView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(mVimeoText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, mVimeoText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView vimeoIcon = vimeoView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.vimeo_24dp).into(vimeoIcon);

                TextView vimeoHint = vimeoView.findViewById(R.id.sm_hint);
                vimeoHint.setText(R.string.vimeo);

                TextView vimeoValue = vimeoView.findViewById(R.id.sm_value);
                vimeoValue.setText(tools.removeHttpPrefix(mVimeoText));

                // TODO rest noch mit dem shortcut ersetzen
                // TODO rest noch mit dem shortcut ersetzen
                // TODO rest noch mit dem shortcut ersetzen
                // TODO rest noch mit dem shortcut ersetzen
                vimeoValue.setOnClickListener(clickToOpenWebsite(vimeoValue, Const.VIMEO_BASE_URL, mVimeoText));

                socialmediaInnerLayout.addView(vimeoView);
                deleteSocialNetworksCard = false;
            }


            // XING
            if ((mXingText != null) && (mXingText.length() > 0)) {
                View xingView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                xingView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(mXingText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, mXingText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView xingIcon = xingView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.xing_24dp).into(xingIcon);

                TextView xingHint = xingView.findViewById(R.id.sm_hint);
                xingHint.setText(R.string.xing);

                TextView xingValue = xingView.findViewById(R.id.sm_value);
                xingValue.setText(tools.removeHttpPrefix(mXingText));
                xingValue.setOnClickListener(view -> {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    if (URLUtil.isValidUrl(mXingText)) {
                        i.setData(Uri.parse(mXingText));
                    } else {
                        i.setData(Uri.parse("https://www.xing.com/profile/" + mXingText));
                    }
                    startActivity(i);
                });
                xingValue.setOnClickListener(clickToOpenWebsite(xingValue, Const.XING_BASE_URL, mXingText));

                socialmediaInnerLayout.addView(xingView);
                deleteSocialNetworksCard = false;
            }


            // XHAMSTER
            if ((mXhamsterText != null) && (mXhamsterText.length() > 0)) {
                View xhamsterView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                xhamsterView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(mXhamsterText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, mXhamsterText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView xhamsterIcon = xhamsterView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.xhamster_24dp).into(xhamsterIcon);

                TextView xhamsterHint = xhamsterView.findViewById(R.id.sm_hint);
                xhamsterHint.setText(R.string.xhamster);

                TextView xhamsterValue = xhamsterView.findViewById(R.id.sm_value);
                xhamsterValue.setText(tools.removeHttpPrefix(mXhamsterText));
                xhamsterValue.setOnClickListener(clickToOpenWebsite(xhamsterValue, Const.XHAMSTER_BASE_URL, mXhamsterText));

                socialmediaInnerLayout.addView(xhamsterView);
                deleteSocialNetworksCard = false;
            }


            // XTUBE
            if ((mXtubeText != null) && (mXtubeText.length() > 0)) {
                View xtubeView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                xtubeView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(mXtubeText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, mXtubeText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView xtubeIcon = xtubeView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.xtube_24dp).into(xtubeIcon);

                TextView xtubeHint = xtubeView.findViewById(R.id.sm_hint);
                xtubeHint.setText(R.string.xtube);

                TextView xtubeValue = xtubeView.findViewById(R.id.sm_value);
                xtubeValue.setText(tools.removeHttpPrefix(mXtubeText));
                xtubeValue.setOnClickListener(clickToOpenWebsite(xtubeValue, Const.XTUBE_BASE_URL, mXtubeText));

                socialmediaInnerLayout.addView(xtubeView);
                deleteSocialNetworksCard = false;
            }


            // YOUTUBE
            if ((mYoutubeText != null) && (mYoutubeText.length() > 0)) {
                View youtubeView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                youtubeView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(mYoutubeText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, mYoutubeText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView youtubeIcon = youtubeView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.youtube_24dp).into(youtubeIcon);

                TextView youtubeHint = youtubeView.findViewById(R.id.sm_hint);
                youtubeHint.setText(R.string.youtube);

                TextView youtubeValue = youtubeView.findViewById(R.id.sm_value);
                youtubeValue.setText(tools.removeHttpPrefix(mYoutubeText));
                youtubeValue.setOnClickListener(clickToOpenWebsite(youtubeValue, Const.YOUTUBE_BASE_URL, mYoutubeText));

                socialmediaInnerLayout.addView(youtubeView);
                deleteSocialNetworksCard = false;
            }


            // MESSENGERS
            // MESSENGERS
            // MESSENGERS
            boolean deleteMessengerCard = true;

            if ((mLineText != null) && (mLineText.length() > 0)) {
                View lineView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                lineView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(mLineText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, mLineText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView lineIcon = lineView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.line_24dp).into(lineIcon);

                TextView lineHint = lineView.findViewById(R.id.sm_hint);
                lineHint.setText(R.string.line_messenger);

                TextView lineValue = lineView.findViewById(R.id.sm_value);
                lineValue.setText(tools.removeHttpPrefix(mLineText));

                messengersInnerLayout.addView(lineView);
                deleteMessengerCard = false;
            }


            if ((skypeText != null) && (skypeText.length() > 0)) {
                View skypeView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                skypeView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(skypeText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, skypeText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView skypeIcon = skypeView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.skype_24dp).into(skypeIcon);

                TextView skypeHint = skypeView.findViewById(R.id.sm_hint);
                skypeHint.setText(R.string.skype);

                TextView skypeValue = skypeView.findViewById(R.id.sm_value);
                skypeValue.setText(tools.removeHttpPrefix(skypeText));

                messengersInnerLayout.addView(skypeView);
                deleteMessengerCard = false;
            }


            if ((snapchatText != null) && (snapchatText.length() > 0)) {
                View snapchatView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                snapchatView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(snapchatText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, snapchatText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView snapchatIcon = snapchatView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.snapchat_24dp).into(snapchatIcon);

                TextView snapchatHint = snapchatView.findViewById(R.id.sm_hint);
                snapchatHint.setText(R.string.snapchat);

                TextView snapchatValue = snapchatView.findViewById(R.id.sm_value);
                snapchatValue.setText(tools.removeHttpPrefix(snapchatText));

                messengersInnerLayout.addView(snapchatView);
                deleteMessengerCard = false;
            }


            if ((viberText != null) && (viberText.length() > 0)) {
                View viberView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                viberView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(viberText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, viberText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView viberIcon = viberView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.viber_24dp).into(viberIcon);

                TextView viberHint = viberView.findViewById(R.id.sm_hint);
                viberHint.setText(R.string.viber);

                TextView viberValue = viberView.findViewById(R.id.sm_value);
                viberValue.setText(tools.removeHttpPrefix(viberText));

                messengersInnerLayout.addView(viberView);
                deleteMessengerCard = false;
            }


            if ((wechatText != null) && (wechatText.length() > 0)) {
                View wechatView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                wechatView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(wechatText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, wechatText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView wechatIcon = wechatView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.wechat_24dp).into(wechatIcon);

                TextView wechatHint = wechatView.findViewById(R.id.sm_hint);
                wechatHint.setText(R.string.wechat);

                TextView wechatValue = wechatView.findViewById(R.id.sm_value);
                wechatValue.setText(tools.removeHttpPrefix(wechatText));

                messengersInnerLayout.addView(wechatView);
                deleteMessengerCard = false;
            }


            if ((whatsappText != null) && (whatsappText.length() > 0)) {
                View whatsappView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                whatsappView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(whatsappText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, whatsappText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView whatsappIcon = whatsappView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.whatsapp_24dp).into(whatsappIcon);

                TextView whatsappHint = whatsappView.findViewById(R.id.sm_hint);
                whatsappHint.setText(R.string.whatsapp);

                TextView whatsappValue = whatsappView.findViewById(R.id.sm_value);
                whatsappValue.setText(tools.removeHttpPrefix(whatsappText));

                messengersInnerLayout.addView(whatsappView);
                deleteMessengerCard = false;
            }


            // DATING APPS
            // DATING APPS
            // DATING APPS
            boolean deleteDatingCard = true;

            if ((adam4adamText != null) && (adam4adamText.length() > 0)) {
                View adam4adamView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                adam4adamView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(adam4adamText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, adam4adamText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView adam4adamIcon = adam4adamView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.adam4adam_24dp).into(adam4adamIcon);

                TextView adam4adamHint = adam4adamView.findViewById(R.id.sm_hint);
                adam4adamHint.setText(R.string.adam4adam);

                TextView adam4adamValue = adam4adamView.findViewById(R.id.sm_value);
                adam4adamValue.setText(tools.removeHttpPrefix(adam4adamText));
                adam4adamValue.setOnClickListener(clickToOpenWebsite(adam4adamValue, Const.ADAM4ADAM_BASE_URL, adam4adamText));

                datingInnerLayout.addView(adam4adamView);
                deleteDatingCard = false;
            }


            if ((bbrtText != null) && (bbrtText.length() > 0)) {
                View bbrtView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                bbrtView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(bbrtText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, bbrtText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView bbrtIcon = bbrtView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.bbrt_24dp).into(bbrtIcon);

                TextView bbrtHint = bbrtView.findViewById(R.id.sm_hint);
                bbrtHint.setText(R.string.bbrt);

                TextView bbrtValue = bbrtView.findViewById(R.id.sm_value);
                bbrtValue.setText(tools.removeHttpPrefix(bbrtText));

                datingInnerLayout.addView(bbrtView);
                deleteDatingCard = false;
            }


            if ((boyahoyText != null) && (boyahoyText.length() > 0)) {
                View boyahoyView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                boyahoyView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(boyahoyText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, boyahoyText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView boyahoyIcon = boyahoyView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.boyahoy_24dp).into(boyahoyIcon);

                TextView boyahoyHint = boyahoyView.findViewById(R.id.sm_hint);
                boyahoyHint.setText(R.string.boyahoy);

                TextView boyahoyValue = boyahoyView.findViewById(R.id.sm_value);
                boyahoyValue.setText(tools.removeHttpPrefix(boyahoyText));

                datingInnerLayout.addView(boyahoyView);
                deleteDatingCard = false;
            }


            if ((dudesnudeText != null) && (dudesnudeText.length() > 0)) {
                View dudesnudeView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                dudesnudeView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(dudesnudeText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, dudesnudeText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView dudesnudeIcon = dudesnudeView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.dudesnude_24dp).into(dudesnudeIcon);

                TextView dudesnudeHint = dudesnudeView.findViewById(R.id.sm_hint);
                dudesnudeHint.setText(R.string.dudesnude);

                TextView dudesnudeValue = dudesnudeView.findViewById(R.id.sm_value);
                dudesnudeValue.setText(tools.removeHttpPrefix(dudesnudeText));
                dudesnudeValue.setOnClickListener(clickToOpenWebsite(dudesnudeValue, Const.DUDESNUDE_BASE_URL, dudesnudeText));

                datingInnerLayout.addView(dudesnudeView);
                deleteDatingCard = false;
            }


            if ((gaycomText != null) && (gaycomText.length() > 0)) {
                View gayComView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                gayComView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(gaycomText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, gaycomText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView gayComIcon = gayComView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.gaycom_24dp).into(gayComIcon);

                TextView gayComHint = gayComView.findViewById(R.id.sm_hint);
                gayComHint.setText(R.string.gaycom);

                TextView gayComValue = gayComView.findViewById(R.id.sm_value);
                gayComValue.setText(tools.removeHttpPrefix(gaycomText));
                gayComValue.setOnClickListener(view -> {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    if (URLUtil.isValidUrl(gaycomText)) {
                        i.setData(Uri.parse(gaycomText));
                    } else {
                        i.setData(Uri.parse(Const.GAYCOM_BASE_URL + gaycomText));
                    }
                    startActivity(i);
                });

                datingInnerLayout.addView(gayComView);
                deleteDatingCard = false;
            }

            if ((gaydarText != null) && (gaydarText.length() > 0)) {
                View gaydarView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                gaydarView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(gaydarText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, gaydarText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView gaydarIcon = gaydarView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.gaydar_24dp).into(gaydarIcon);

                TextView gaydarHint = gaydarView.findViewById(R.id.sm_hint);
                gaydarHint.setText(R.string.gaydar);

                TextView gaydarValue = gaydarView.findViewById(R.id.sm_value);
                gaydarValue.setText(tools.removeHttpPrefix(gaydarText));
                gaydarValue.setOnClickListener(view -> {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    if (URLUtil.isValidUrl(gaydarText)) {
                        i.setData(Uri.parse(gaydarText));
                    } else {
                        i.setData(Uri.parse(Const.GAYDAR_BASE_URL + gaydarText));
                    }
                    startActivity(i);
                });

                datingInnerLayout.addView(gaydarView);
                deleteDatingCard = false;
            }

            if ((grindrText != null) && (grindrText.length() > 0)) {
                View grindrView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                grindrView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(grindrText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, grindrText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView grindrIcon = grindrView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.grindr_24dp).into(grindrIcon);

                TextView grindrHint = grindrView.findViewById(R.id.sm_hint);
                grindrHint.setText(R.string.grindr);

                TextView grindrValue = grindrView.findViewById(R.id.sm_value);
                grindrValue.setText(tools.removeHttpPrefix(grindrText));

                datingInnerLayout.addView(grindrView);
                deleteDatingCard = false;
            }


            if ((growlrText != null) && (growlrText.length() > 0)) {
                View growlrView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                growlrView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(growlrText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, growlrText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView growlrIcon = growlrView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.growlr_24dp).into(growlrIcon);

                TextView growlrHint = growlrView.findViewById(R.id.sm_hint);
                growlrHint.setText(R.string.growlr);

                TextView growlrValue = growlrView.findViewById(R.id.sm_value);
                growlrValue.setText(tools.removeHttpPrefix(growlrText));

                datingInnerLayout.addView(growlrView);
                deleteDatingCard = false;
            }


            if ((hornetText != null) && (hornetText.length() > 0)) {
                View hornetView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                hornetView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(hornetText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, hornetText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView hornetIcon = hornetView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.hornet_24dp).into(hornetIcon);

                TextView hornetHint = hornetView.findViewById(R.id.sm_hint);
                hornetHint.setText(R.string.hornet);

                TextView hornetValue = hornetView.findViewById(R.id.sm_value);
                hornetValue.setText(tools.removeHttpPrefix(hornetText));

                datingInnerLayout.addView(hornetView);
                deleteDatingCard = false;
            }


            if ((jackdText != null) && (jackdText.length() > 0)) {
                View jackdView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                jackdView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(jackdText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, jackdText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView jackdIcon = jackdView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.jackd_24dp).into(jackdIcon);

                TextView jackdHint = jackdView.findViewById(R.id.sm_hint);
                jackdHint.setText(R.string.jackd);

                TextView jackdValue = jackdView.findViewById(R.id.sm_value);
                jackdValue.setText(tools.removeHttpPrefix(jackdText));

                datingInnerLayout.addView(jackdView);
                deleteDatingCard = false;
            }


            if ((manhuntText != null) && (manhuntText.length() > 0)) {
                View manhuntView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                manhuntView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(manhuntText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, manhuntText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView manhuntIcon = manhuntView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.manhunt_24dp).into(manhuntIcon);

                TextView manhuntHint = manhuntView.findViewById(R.id.sm_hint);
                manhuntHint.setText(R.string.manhunt);

                TextView manhuntValue = manhuntView.findViewById(R.id.sm_value);
                manhuntValue.setText(tools.removeHttpPrefix(manhuntText));
                manhuntValue.setOnClickListener(view -> {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    if (URLUtil.isValidUrl(manhuntText)) {
                        i.setData(Uri.parse(manhuntText));
                    } else {
                        i.setData(Uri.parse(Const.MANHUNT_BASE_URL + manhuntText));
                    }
                    startActivity(i);
                });

                datingInnerLayout.addView(manhuntView);
                deleteDatingCard = false;
            }


            if ((planetromeoText != null) && (planetromeoText.length() > 0)) {
                View planetRomeoView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                planetRomeoView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(planetromeoText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, planetromeoText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView planetRomeoIcon = planetRomeoView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.planetromeo_24dp).into(planetRomeoIcon);

                TextView planetRomeoHint = planetRomeoView.findViewById(R.id.sm_hint);
                planetRomeoHint.setText(R.string.planetromeo);

                TextView planetRomeoValue = planetRomeoView.findViewById(R.id.sm_value);
                planetRomeoValue.setText(tools.removeHttpPrefix(planetromeoText));

                datingInnerLayout.addView(planetRomeoView);
                deleteDatingCard = false;
            }


            if ((reconText != null) && (reconText.length() > 0)) {
                View reconView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                reconView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(reconText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, reconText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView reconIcon = reconView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.recon_24dp).into(reconIcon);

                TextView reconHint = reconView.findViewById(R.id.sm_hint);
                reconHint.setText(R.string.recon);

                TextView reconValue = reconView.findViewById(R.id.sm_value);
                reconValue.setText(tools.removeHttpPrefix(reconText));

                datingInnerLayout.addView(reconView);
                deleteDatingCard = false;
            }


            if ((scruffText != null) && (scruffText.length() > 0)) {
                View scruffView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                scruffView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(scruffText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, scruffText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView scruffIcon = scruffView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.scruff_24dp).into(scruffIcon);

                TextView scruffHint = scruffView.findViewById(R.id.sm_hint);
                scruffHint.setText(R.string.scruff);

                TextView scruffValue = scruffView.findViewById(R.id.sm_value);
                scruffValue.setText(tools.removeHttpPrefix(scruffText));

                datingInnerLayout.addView(scruffView);
                deleteDatingCard = false;
            }


            if ((tinderText != null) && (tinderText.length() > 0)) {
                View tinderView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                tinderView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(tinderText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, tinderText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView tinderIcon = tinderView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.tinder_24dp).into(tinderIcon);

                TextView tinderHint = tinderView.findViewById(R.id.sm_hint);
                tinderHint.setText(R.string.tinder);

                TextView tinderValue = tinderView.findViewById(R.id.sm_value);
                tinderValue.setText(tools.removeHttpPrefix(tinderText));

                datingInnerLayout.addView(tinderView);
                deleteDatingCard = false;
            }


            // GAMING
            // GAMING
            // GAMING
            boolean deleteGamingCard = true;

            if ((miiverseText != null) && (miiverseText.length() > 0)) {
                View miiverseView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                miiverseView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(miiverseText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, miiverseText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView miiverseIcon = miiverseView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.miiverse_24dp).into(miiverseIcon);

                TextView miiverseHint = miiverseView.findViewById(R.id.sm_hint);
                miiverseHint.setText(R.string.miiverse);

                TextView miiverseValue = miiverseView.findViewById(R.id.sm_value);
                miiverseValue.setText(tools.removeHttpPrefix(miiverseText));

                gamingInnerLayout.addView(miiverseView);
                deleteGamingCard = false;
            }


            if ((nintendonetworkText != null) && (nintendonetworkText.length() > 0)) {
                View nintendoNetworkIdView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                nintendoNetworkIdView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(nintendonetworkText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, nintendonetworkText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView nintendoNetworkIdIcon = nintendoNetworkIdView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.nintendonetwork_24dp).into(nintendoNetworkIdIcon);

                TextView nintendoNetworkIdHint = nintendoNetworkIdView.findViewById(R.id.sm_hint);
                nintendoNetworkIdHint.setText(R.string.nintendonetwork);

                TextView nintendoNetworkIdValue = nintendoNetworkIdView.findViewById(R.id.sm_value);
                nintendoNetworkIdValue.setText(tools.removeHttpPrefix(nintendonetworkText));

                gamingInnerLayout.addView(nintendoNetworkIdView);
                deleteGamingCard = false;
            }


            if ((playstationnetworkText != null) && (playstationnetworkText.length() > 0)) {
                View playstationNetworkIdView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                playstationNetworkIdView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(playstationnetworkText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, playstationnetworkText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView playstationNetworkIdIcon = playstationNetworkIdView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.playstation_24dp).into(playstationNetworkIdIcon);

                TextView playstationNetworkIdHint = playstationNetworkIdView.findViewById(R.id.sm_hint);
                playstationNetworkIdHint.setText(R.string.playstationnetwork);

                TextView playstationNetworkIdValue = playstationNetworkIdView.findViewById(R.id.sm_value);
                playstationNetworkIdValue.setText(tools.removeHttpPrefix(playstationnetworkText));

                gamingInnerLayout.addView(playstationNetworkIdView);
                deleteGamingCard = false;
            }


            if ((xboxgamertagText != null) && (xboxgamertagText.length() > 0)) {
                View xboxGamertagView = inflater.inflate(R.layout.social_media_item_constraint_layout, null);
                xboxGamertagView.setOnLongClickListener(view -> {
                    tools.copyToClipboard(xboxgamertagText);
                    Toast.makeText(activity, getString(R.string.copied_to_clipboard, xboxgamertagText), Toast.LENGTH_SHORT).show();
                    return false;
                });

                ImageView xboxGamertagIcon = xboxGamertagView.findViewById(R.id.sm_icon);
                Glide.with(getContext()).load(R.drawable.xbox_24dp).into(xboxGamertagIcon);

                TextView xboxGamertagHint = xboxGamertagView.findViewById(R.id.sm_hint);
                xboxGamertagHint.setText(R.string.xboxgamertag);

                TextView xboxGamertagValue = xboxGamertagView.findViewById(R.id.sm_value);
                xboxGamertagValue.setText(tools.removeHttpPrefix(xboxgamertagText));

                gamingInnerLayout.addView(xboxGamertagView);
                deleteGamingCard = false;
            }

            if (deleteSocialNetworksCard) {
                socialMediaCard.setVisibility(View.GONE);
            }
            if (deleteMessengerCard) {
                messengerCard.setVisibility(View.GONE);
            }
            if (deleteDatingCard) {
                datingCard.setVisibility(View.GONE);
            }
            if (deleteGamingCard) {
                gamingCard.setVisibility(View.GONE);
            }

            if (deleteSocialNetworksCard && deleteMessengerCard && deleteDatingCard && deleteGamingCard) {
                showNoInformationCard();
            } else {
                LinearLayout.LayoutParams viewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Math.round(getResources().getDimension(R.dimen.lower_ad_banner_height)));
                View lowerPaddingView = new View(activity);
                lowerPaddingView.setLayoutParams(viewLayoutParams);
                addToGrayLayout(lowerPaddingView);
            }
        });
    }

    private View.OnClickListener clickToOpenWebsite(TextView textView, final String baseUrl, final String username) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                if (URLUtil.isValidUrl(username)) {
                    i.setData(Uri.parse(username));
                } else {
                    i.setData(Uri.parse(baseUrl + username));
                }
                startActivity(i);
            }
        };
    }

    void addToGrayLayout(View v) {
        Needle.onMainThread().execute(() -> grayLayout.addView(v));
    }

    void showNoInformationCard() {
        Needle.onMainThread().execute(() -> {
            int noInformationLayoutCorrectHeight = Math.round(activity.getDisplayHeight(false, false) - tools.convertPxToDp(Const.HEIGHT_TAB_BAR_AND_AD_BANNER));

            socialMediaCard.setVisibility(View.GONE);
            messengerCard.setVisibility(View.GONE);
            datingCard.setVisibility(View.GONE);
            gamingCard.setVisibility(View.GONE);

            noInformationCard.setVisibility(View.VISIBLE);
            noInformationLayout.setVisibility(View.VISIBLE);
            noInformationLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, noInformationLayoutCorrectHeight));
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
//        ObservableScrollView scrollView = (ObservableScrollView) view.findViewById(R.id.scroll);
//
//        // Also pass this event to parent Activity
//        ContactViewWithViewPagerTabActivity parentActivity =
//                (ContactViewWithViewPagerTabActivity) getActivity();
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
