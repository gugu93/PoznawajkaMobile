package pl.kamil.poznawajkamobile.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kml.poznawajkamobile.R;

import pl.kamil.poznawajkamobile.activity.AbstractActivity;
import pl.kamil.poznawajkamobile.utils.Constant;
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
        setHasOptionsMenu(true);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

}
