package net.gazeapp.ui.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import net.gazeapp.R;


public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_general, rootKey);


//        Toast.makeText(getActivity(), "enable_achievements: " + getPreferenceScreen().getSharedPreferences().getBoolean("enable_achievements", false), Toast.LENGTH_SHORT).show();
    }

}
