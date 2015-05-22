package pl.kamil.poznawajkamobile.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {


    private Context mContext;
    private SharedPreferences preferences;

    public Preferences(Context context) {
        mContext = context;
        reloadSharedPreferences();
    }

    public void reloadSharedPreferences() {
        preferences = getDefaultSharedPreferences();
    }

    private SharedPreferences getDefaultSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
    }

    private SharedPreferences.Editor getSharedPreferencesEditor() {
        return preferences.edit();
    }

    public String getString(String key) {
        return preferences.getString(key, null);
    }

    public String getString(String key, String defaultString) {
        return preferences.getString(key, defaultString);
    }

    public void setString(String key, String value) {
        getSharedPreferencesEditor().putString(key, value).apply();
    }

    public int getInt(String key, int defaultValue) {
        try {
            return preferences.getInt(key, defaultValue);
        } catch (ClassCastException e) {
            return Integer.parseInt(preferences.getString(key, Integer.toString(defaultValue)));
        }
    }

    public long getLong(String key, long defaultValue) {
        try {
            return preferences.getLong(key, defaultValue);
        } catch (ClassCastException e) {
            return Integer.parseInt(preferences.getString(key, Long.toString(defaultValue)));
        }
    }

    public boolean getBool(String key, boolean defaultValue) {
        try {
            return preferences.getBoolean(key, defaultValue);
        } catch (ClassCastException e) {
            return defaultValue;
        }
    }

    public void setBool(String key, boolean value) {
        getSharedPreferencesEditor().putBoolean(key, value).apply();
    }

    public void setLong(String key, long value) {
        getSharedPreferencesEditor().putLong(key, value).apply();
    }

    public void remove(String key) {
        getSharedPreferencesEditor().remove(key).apply();
    }

    public boolean keyExists(String key) {
        return getDefaultSharedPreferences().contains(key);
    }



}
