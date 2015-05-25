package poznawajkamobile.pz2.aplicationandroid.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.view.View;

import com.example.kml.poznawajkamobile.R;
import com.squareup.picasso.Picasso;

import java.io.File;

import poznawajkamobile.pz2.aplicationandroid.fragment.MainFragment;
import poznawajkamobile.pz2.aplicationandroid.fragment.ObjectPromotedFragment;
import poznawajkamobile.pz2.aplicationandroid.utils.Utils;
import poznawajkamobile.pz2.aplicationandroid.utils.services.AvatarService;
import poznawajkamobile.pz2.aplicationandroid.utils.services.AvatarService.AvatarBinder;
import poznawajkamobile.pz2.aplicationandroid.utils.services.ProposedFriendsService;

public class MainActivity extends AbstractMenuActivity {
    private MainFragment f;
    private ObjectPromotedFragment p;
    private AvatarService avatarService;
    private Boolean mBoundAvatar = false;
    private ServiceConnection mConnectionAvatar = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            AvatarBinder binder = (AvatarBinder) service;
            avatarService = binder.getService();
            mBoundAvatar = true;
            avatarService.start(getPreferences().getString("login"));
            f.mAdapter.avatar.setVisibility(View.INVISIBLE);
            File sdCard = Environment.getExternalStorageDirectory();
            File avatarFile = new File(sdCard.getAbsolutePath() + "/photos/"+getPreferences().getString("login"));
            Picasso p = Utils.getPicasso(getApplicationContext());
            p.load(avatarFile).error(R.drawable.tavatar).into(f.mAdapter.avatarView);
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
            p.setData(mProposedFriendsService.list);
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

        Intent intentAvatarService = new Intent(this, AvatarService.class);
        bindService(intentAvatarService, mConnectionAvatar, Context.BIND_AUTO_CREATE);

        Intent intentProposedFriends = new Intent(this, ProposedFriendsService.class);
        bindService(intentProposedFriends, mConnectionFriends, Context.BIND_AUTO_CREATE);
    }
    @Override
    public void onBackPressed() {}

    public void onIconClick() {
        mMenuDrawer.toggleMenu();
        mMenuList.setSelection(mMenuList.getCount() - 1);
    }
}
