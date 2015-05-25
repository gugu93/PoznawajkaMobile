package poznawajkamobile.pz2.aplicationandroid.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.example.kml.poznawajkamobile.R;

import poznawajkamobile.pz2.aplicationandroid.fragment.MainFragment;
import poznawajkamobile.pz2.aplicationandroid.fragment.ObjectPromotedFragment;
import poznawajkamobile.pz2.aplicationandroid.utils.services.AvatarService;
import poznawajkamobile.pz2.aplicationandroid.utils.services.AvatarService.AvatarBinder;
import poznawajkamobile.pz2.aplicationandroid.utils.services.ProposedFriendsService;

public class MainActivity extends AbstractMenuActivity {
    private AvatarService avatarService;
    private Boolean mBoundAvatar = false;
    private ServiceConnection mConnectionAvatar = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            AvatarBinder binder = (AvatarBinder) service;
            avatarService = binder.getService();
            mBoundAvatar = true;
            avatarService.start(getPreferences().getString("login"));
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBoundAvatar = false;
        }
    };
    private ProposedFriendsService mProposedFriendsService;
    private Boolean mBoundFriends = false;
    private ServiceConnection mConnectionFriends = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ProposedFriendsService.ProposedFriendsBinder binder = (ProposedFriendsService.ProposedFriendsBinder) service;
            mProposedFriendsService = binder.getService();
            mBoundFriends = true;
            mProposedFriendsService.start(getPreferences().getString("login"));
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBoundFriends = false;
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        if (mBoundAvatar) {
            unbindService(mConnectionAvatar);
            mBoundAvatar = false;
        }
        if (mBoundFriends) {
            unbindService(mConnectionFriends);
            mBoundFriends = false;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment);
        MainFragment f = MainFragment.newInstance(getIntent());
        ObjectPromotedFragment p = ObjectPromotedFragment.newInstance(getIntent());
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

        Intent intentAvatarService = new Intent(this, AvatarService.class);
        bindService(intentAvatarService, mConnectionAvatar, Context.BIND_AUTO_CREATE);

        Intent intentProposedFriends = new Intent(this, ProposedFriendsService.class);
        bindService(intentProposedFriends, mConnectionFriends, Context.BIND_AUTO_CREATE);
    }
    @Override
    public void onBackPressed() {
    }

    public void onIconClick() {
        mMenuDrawer.toggleMenu();
        mMenuList.setSelection(mMenuList.getCount() - 1);
    }
}
