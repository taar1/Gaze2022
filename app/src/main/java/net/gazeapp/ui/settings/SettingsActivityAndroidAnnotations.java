package net.gazeapp.ui.settings;

import net.gazeapp.AppCompatPreferenceActivity;

/**
 * Created by taar1 on 29.10.2016.
 */
//@PreferenceScreen(R.xml.pref_general)
//@EActivity
public class SettingsActivityAndroidAnnotations extends AppCompatPreferenceActivity {

    private final String TAG = getClass().getSimpleName();

//    @App
//    GazeApplication application;
//
//    @Bean
//    RewardedVideoAd rewardedVideoAd;
//    @Bean
//    Fingerprint fingerprint;
//    @Bean
//    FingerprintAuthenticationMemorizer fingerprintAuthenticationMemorizer;
//
//    @Pref
//    MyPrefs_ myPrefs;
//
//    @BindView(android.R.id.content)
//    View outerView;
//
//
//    // DISABLE ADS
//    @PreferenceByKey(R.string.pref_enable_ads_key)
//    SwitchPreference enableAds;
//
//    // Called after changing the switch value
//    @PreferenceClick(R.string.pref_enable_ads_key)
//    void prefClick(SwitchPreference preference) {
//        boolean isAdsEnabled = preference.isChecked();
//        Log.d(TAG, "setAdsEnabled to: " + isAdsEnabled);
//        if (isAdsEnabled) {
//            enableAds.setSummary(R.string.pref_description_ads_enabled);
//        } else {
//            enableAds.setSummary(R.string.pref_description_ads_disabled);
//        }
//    }
//
//    // FINGERPRINT AUTHENTICATION
//    @PreferenceByKey(R.string.finger_key)
//    SwitchPreference fingerprintAuthKey;
//
//    @PreferenceClick(R.string.finger_key)
//    void fingerprintAuthClick(SwitchPreference preference) {
//        // Set this to TRUE to avoid the finger print auth screen to show up suddenly.
//        fingerprintAuthenticationMemorizer.setFingerprintAuthenticated(true);
//        boolean isFingerprintAuthEnabled = preference.isChecked();
//        Log.d(TAG, "setFingerprintAuthenticationEnabled to: " + isFingerprintAuthEnabled);
//        String enabledOrDisabled;
//        if (isFingerprintAuthEnabled) {
//            enabledOrDisabled = getString(R.string.enabled).toUpperCase();
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle(R.string.important_to_know);
//            builder.setMessage(R.string.fingerprint_auth_activation_notice);
//            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    // DO nothing, just close the dialog
//                }
//            });
//            AlertDialog dialog = builder.create();
//            dialog.show();
//        } else {
//            enabledOrDisabled = getString(R.string.disabled).toUpperCase();
//        }
//        fingerprintAuthKey.setSummary(getString(R.string.pref_fingerprint_text_disabled_enabled, enabledOrDisabled));
//    }
//
//    // PRO USER
//    @PreferenceByKey(R.string.pref_pro_user_key)
//    Preference proUser;
//
//    @PreferenceClick(R.string.pref_pro_user_key)
//    void myPrefPreferenceClicked(Preference preference) {
//        // Show Upgrade Activity
//        Intent intent = new Intent(this, UpgradingActivity.class);
//        startActivity(intent);
//    }
//
//    @PreferenceByKey(R.string.pref_eggplants_key)
//    Preference eggplants;
//
//    @PreferenceClick(R.string.pref_eggplants_key)
//    void eggplantsClicked(Preference preference) {
//        rewardedVideoAd.showExplanationDialog(false);
//    }
//
//    // UNIT SYSTEM
//    @PreferenceByKey(R.string.pref_unit_system_key)
//    ListPreference unitSystem;
//
//    @PreferenceChange(R.string.pref_unit_system_key)
//    void preferenceChangeIntParameter(Preference preference, int newValue) {
//        // Unit System has been changed. So whatever...
//        String unitSystemStr = getString(R.string.metric_system);
//        switch (newValue) {
//            case 0:
//                unitSystemStr = getString(R.string.metric_system);
//                break;
//            case 1:
//                unitSystemStr = getString(R.string.imperial_system);
//                break;
//        }
//        Log.d(TAG, "Using Unit System: " + unitSystemStr);
//
//        unitSystem.setSummary(getString(R.string.pref_description_metric_system, unitSystemStr));
//    }
//
//    @AfterPreferences
//    void initPrefs() {
//        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
//
//        unitSystem.setSummary(getString(R.string.pref_description_metric_system, unitSystem.getEntry()));
//
//        // Is PRO User?
//        if (GazeTools.isProUser()) {
//            proUser.setSummary(R.string.pref_pro_user);
//            enableAds.setEnabled(true);
//
//            if (GazeTools.isAdsEnabled()) {
//                enableAds.setChecked(true);
//                enableAds.setSummary(R.string.pref_description_ads_enabled);
//            } else {
//                enableAds.setChecked(false);
//                enableAds.setSummary(R.string.pref_description_ads_disabled);
//            }
//        } else {
//            proUser.setSummary(R.string.pref_pro_free_user);
//
//            enableAds.setSummary(R.string.pref_description_ads_enabled_free_user);
//            enableAds.setChecked(false);
//            enableAds.setEnabled(false);
//        }
//
//        // FINGERPRINT AUTHENTICATION
//        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
//            fingerprintAuthKey.setSummary(getString(R.string.pref_fingerprint_text_incompatible, Build.VERSION.RELEASE));
//            fingerprintAuthKey.setChecked(false);
//            fingerprintAuthKey.setEnabled(false);
//        } else {
//            if (fingerprint.checkFinger()) {
//                fingerprintAuthKey.setEnabled(true);
//
//                boolean fingerPrintAuthEnabled = false;
//                String enabledOrDisabled;
//                if (GazeTools.isFingerprintAuthenticationEnabled()) {
//                    fingerPrintAuthEnabled = true;
//                    enabledOrDisabled = getString(R.string.enabled).toUpperCase();
//                } else {
//                    enabledOrDisabled = getString(R.string.disabled).toUpperCase();
//                }
//                fingerprintAuthKey.setChecked(fingerPrintAuthEnabled);
//                fingerprintAuthKey.setSummary(getString(R.string.pref_fingerprint_text_disabled_enabled, enabledOrDisabled));
//            } else {
//                fingerprintAuthKey.setEnabled(false);
//                fingerprintAuthKey.setSummary(getString(R.string.pref_fingerprint_not_active));
//            }
//        }
//
//        // Eggplants
//        int amountOfEggplants = myPrefs.eggplantsEarned().getOr(0);
//        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
//            if (amountOfEggplants == 0) {
//                eggplants.setSummary(getString(R.string.no_eggplants, getString(R.string.eggplants)));
//            } else {
//                eggplants.setSummary(getString(R.string.eggplants_earned, String.valueOf(amountOfEggplants), String.valueOf(Const.MAX_AMOUNT_EGGPLANTS), getString(R.string.eggplants)));
//            }
//        } else {
//            if (amountOfEggplants == 0) {
//                eggplants.setSummary(getString(R.string.no_eggplants, "\uD83C\uDF46"));
//            } else {
//                eggplants.setSummary(getString(R.string.eggplants_earned, String.valueOf(amountOfEggplants), String.valueOf(Const.MAX_AMOUNT_EGGPLANTS), "\uD83C\uDF46"));
//            }
//        }
//
//        setupActionBar();
//    }
//
//    @PreferenceClick(R.string.pref_clear_search_history_key)
//    void clearSearchHistory() {
//        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
//        suggestions.clearHistory();
//
//        GazeTools.showMaterialSnackBar(getParent(), outerView, getString(R.string.pref_clear_search_history_success), SnackBarType.SUCCESS);
//    }
//
//    @PreferenceClick(R.string.pref_send_feedback_key)
//    void sendFeedback() {
//        GazeTools.sendEmail(this, Const.EMAIL_FEEDBACK, "Gaze: " + getString(R.string.suggestion_for_improvement));
//    }
//
//    private void setupActionBar() {
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            // Show the Up button in the action bar.
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
//    }
//
//    @OptionsItem(android.R.id.home)
//    void homeSelected() {
//        NavUtils.navigateUpFromSameTask(this);
//    }
}
