package pl.kamil.poznawajkamobile.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import pl.kamil.poznawajkamobile.activity.AbstractActivity;
import pl.kamil.poznawajkamobile.utils.Preferences;

/**
 * A simple {@link Fragment} subclass.
 */
abstract public class AbstractFragment extends Fragment {


    protected AbstractActivity getBaseActivity() {
        return (AbstractActivity) getActivity();
    }

    protected Preferences getPreferences() {
        return getBaseActivity().getPreferences();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
