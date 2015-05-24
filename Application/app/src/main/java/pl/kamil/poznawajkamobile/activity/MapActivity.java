package pl.kamil.poznawajkamobile.activity;

import android.os.Bundle;

import com.example.kml.poznawajkamobile.R;

import pl.kamil.poznawajkamobile.fragment.GMapFragment;

public class MapActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment);
        GMapFragment gMapFragment = GMapFragment.newInstance((getIntent()));
        if (savedInstanceState == null && findViewById(R.id.fragment_container) != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, gMapFragment, GMapFragment.FRAGMENT_TAG)
                    .commit();
        }
    }


}