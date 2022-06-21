package net.gazeapp.ui.settings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import net.gazeapp.R;

public class SettingsActivity extends AppCompatActivity {

    private final static String TAG = SettingsActivity.class.getSimpleName();

//    @BindView(R.id.toolbar)
//    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_settings);
//        ButterKnife.bind(this);

//        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.action_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // TODO FIXME hier anhand SettingsActivityAndroidAnnotations die dialoge und alerts etc. noch einbauen
        // TODO FIXME hier anhand SettingsActivityAndroidAnnotations die dialoge und alerts etc. noch einbauen
        // TODO FIXME hier anhand SettingsActivityAndroidAnnotations die dialoge und alerts etc. noch einbauen
        // TODO FIXME hier anhand SettingsActivityAndroidAnnotations die dialoge und alerts etc. noch einbauen
        // TODO FIXME hier anhand SettingsActivityAndroidAnnotations die dialoge und alerts etc. noch einbauen
        // TODO FIXME hier anhand SettingsActivityAndroidAnnotations die dialoge und alerts etc. noch einbauen
        // TODO FIXME hier anhand SettingsActivityAndroidAnnotations die dialoge und alerts etc. noch einbauen

//        editor = settings.edit();

//        if (sharedPreferences.getBoolean(getString(R.string.pref_enable_achievements_key), true)) {
//            enableAchievements.setSummary(R.string.enabled);
//            enableAchievements.setChecked(true);
//        } else {
//            enableAchievements.setSummary(R.string.disabled);
//            enableAchievements.setChecked(false);
//        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
    }

}
