package poznawajkamobile.pz2.aplicationandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;

import poznawajkamobile.pz2.aplicationandroid.activity.AbstractActivity;

public class AbstractListFragment extends ListFragment {

    protected AbstractActivity getBaseActivity() {
        return (AbstractActivity) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

}
