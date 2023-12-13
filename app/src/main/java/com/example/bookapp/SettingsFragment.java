package com.example.bookapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String PREF_SORT_KEY = "sort_key";
    public static final String PREF_SORT_ALPHABETICALLY = "pref_sort_alphabetically";

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        addPreferencesFromResource(R.xml.pref_general);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String key) {

        if (key.equals(PREF_SORT_KEY)) {
            Preference sortPreference = findPreference(key);
            sortPreference.setSummary(sharedPreferences.getString(key, ""));
        } else if (key.equals(PREF_SORT_ALPHABETICALLY)) {
            Preference sortAlphabetically = findPreference(key);
            sortAlphabetically.setSummary(String.valueOf(sharedPreferences.getBoolean(key, false)));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
