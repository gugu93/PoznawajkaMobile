package pl.kamil.poznawajkamobile.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.example.kml.poznawajkamobile.R;

import pl.kamil.poznawajkamobile.fragment.MainFragment;
import pl.kamil.poznawajkamobile.fragment.ObjectPromotedFragment;
import pl.kamil.poznawajkamobile.utils.services.AvatarService;

public class MainActivity extends AbstractMenuActivity {
    private MainFragment f;
    private ObjectPromotedFragment p;
    private AvatarService avatarService;
    private Boolean mBound = false;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            AvatarService.AvatarBinder binder = (AvatarService.AvatarBinder) service;
            avatarService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(this, AvatarService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment);
        f = MainFragment.newInstance(getIntent());
        p = ObjectPromotedFragment.newInstance(getIntent());
        if (savedInstanceState == null && findViewById(R.id.fragment_container) != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, f, MainFragment.FRAGMENT_TAG)
                    .commit();
        }
        if (savedInstanceState == null && findViewById(R.id.fragment_container) != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.proponowane, p, ObjectPromotedFragment.FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBound) avatarService.start(getPreferences().getString("login"));
        if (mBound) {
            if (avatarService.isSucces()) {
                f.mAdapter.avatar.setVisibility(View.INVISIBLE);
            } else {
                f.mAdapter.avatar.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onBackPressed() {
    }

    public void onIconClick() {
        mMenuDrawer.toggleMenu();
        mMenuList.setSelection(mMenuList.getCount() - 1);
    }
}
