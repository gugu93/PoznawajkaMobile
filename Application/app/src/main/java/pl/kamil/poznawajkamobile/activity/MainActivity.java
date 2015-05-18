package pl.kamil.poznawajkamobile.activity;

import android.os.Bundle;

import com.example.kml.poznawajkamobile.R;

import pl.kamil.poznawajkamobile.fragment.MainFragment;

public class MainActivity extends AbstractMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment);
        if (savedInstanceState == null && findViewById(R.id.fragment_container) != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, MainFragment.newInstance(getIntent()), MainFragment.FRAGMENT_TAG)
                    .commit();
        }
    }

    public void onIconClick(){
        mMenuDrawer.toggleMenu();
        mMenuList.setSelection(mMenuList.getCount() -1);
    }
}
