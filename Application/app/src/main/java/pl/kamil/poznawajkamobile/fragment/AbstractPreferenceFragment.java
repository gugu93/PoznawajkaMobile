package pl.kamil.poznawajkamobile.fragment;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.example.kml.poznawajkamobile.R;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import pl.kamil.poznawajkamobile.activity.SettingsActivity;
import pl.kamil.poznawajkamobile.utils.Preferences;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AbstractPreferenceFragment extends PreferenceFragment {

    protected Preferences mPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = new Preferences(getActivity());
    }

    public SettingsActivity getSettingsActivity() {
        return (SettingsActivity) getActivity();
    }

    public Drawable getMenuIcon(Iconify.IconValue icon) {
        return new IconDrawable(getSettingsActivity(), icon)
                .colorRes(R.color.my_blue)
                .actionBarSize();
    }

    public void setPreferenceIcon(Preference preference, Iconify.IconValue icon) {
        if (preference != null) {
            preference.setIcon(getMenuIcon(icon));
        }
    }
}

