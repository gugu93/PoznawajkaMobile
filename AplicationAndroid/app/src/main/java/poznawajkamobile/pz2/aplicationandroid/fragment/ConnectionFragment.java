package poznawajkamobile.pz2.aplicationandroid.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;

import com.example.kml.poznawajkamobile.R;
import com.joanzapata.android.iconify.Iconify;


public class ConnectionFragment extends AbstractPreferenceFragment
        implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(getActivity(), R.xml.prefs_connection, false);
        addPreferencesFromResource(R.xml.prefs_connection);
        for (ConnectionPreference pref : ConnectionPreference.values()) {
            Preference preference = findPreference(pref.getKey());
            if (preference != null) {
                preference.setOnPreferenceClickListener(this);
                preference.setOnPreferenceChangeListener(this);
                setPreferenceIcon(preference, pref.getIcon());
            }
        }
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return true;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return true;
    }


    public enum ConnectionPreference {
        ALWAYS("connection_always", Iconify.IconValue.fa_cloud_upload),
        NEWS("connection_news", Iconify.IconValue.fa_bullhorn),
        EVENTS("connection_events", Iconify.IconValue.fa_calendar);

        String key;
        Iconify.IconValue icon;

        ConnectionPreference(String key, Iconify.IconValue icon) {
            this.key = key;
            this.icon = icon;
        }

        public String getKey() {
            return key;
        }

        public Iconify.IconValue getIcon() {
            return icon;
        }
    }
}
