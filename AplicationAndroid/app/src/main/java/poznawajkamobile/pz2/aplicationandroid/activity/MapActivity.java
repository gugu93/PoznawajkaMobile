package poznawajkamobile.pz2.aplicationandroid.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.example.kml.poznawajkamobile.R;

import poznawajkamobile.pz2.aplicationandroid.fragment.GMapFragment;
import poznawajkamobile.pz2.aplicationandroid.utils.services.MapFriendsService;

public class MapActivity extends AbstractActivity {
    private MapFriendsService mapFriendsService;
    private Boolean mBoundMap = false;
    private GMapFragment gMapFragment;
    private ServiceConnection mConnectionMap = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MapFriendsService.MapFriendsBinder binder = (MapFriendsService.MapFriendsBinder) service;
            mapFriendsService = binder.getService();
            mBoundMap = true;
            mapFriendsService.start(getPreferences().getString("login"));
            gMapFragment.loadData(mapFriendsService.list);
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBoundMap = false;
        }
    };


    @Override
    public void onStop() {
        super.onStop();
        if (mBoundMap) {
            unbindService(mConnectionMap);
            mBoundMap = false;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment);
        gMapFragment = GMapFragment.newInstance((getIntent()));
        if (savedInstanceState == null && findViewById(R.id.fragment_container) != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, gMapFragment, GMapFragment.FRAGMENT_TAG)
                    .commit();
        }
        Intent intent = new Intent(this, MapFriendsService.class);
        bindService(intent, mConnectionMap, Context.BIND_AUTO_CREATE);
    }


}
