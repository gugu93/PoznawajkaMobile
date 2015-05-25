package poznawajkamobile.pz2.aplicationandroid.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import poznawajkamobile.pz2.aplicationandroid.activity.AbstractActivity;
import poznawajkamobile.pz2.aplicationandroid.utils.Preferences;

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
