package poznawajkamobile.pz2.aplicationandroid.utils.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;

import poznawajkamobile.pz2.aplicationandroid.utils.Preferences;
import poznawajkamobile.pz2.aplicationandroid.utils.UpdateUtils;
import poznawajkamobile.pz2.aplicationandroid.utils.listners.ProposedFriendsListner;
import poznawajkamobile.pz2.aplicationandroid.utils.requests.ProposedFriendsRequest;

public class ProposedFriendsService extends Service implements ProposedFriendsListner {

    private final IBinder mBinder = new ProposedFriendsBinder();
    private boolean hasExternalStorage = UpdateUtils.hasExternalStorage();
    private Preferences mPreferences;
    private boolean working = false;
    private boolean succes = false;

    public boolean isSucces() {
        return succes;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPreferences = new Preferences(this);
    }

    public void start(String login) {
        working = true;
        Log.d("@@@@@@@", "START");
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/photos/");
        dir.mkdirs();
        //TODO napisac adres bezwgledny do avatara na slawka serwerze(login parametr)
        ProposedFriendsRequest task = new ProposedFriendsRequest(this);
        task.execute(login, dir.getPath());
    }

    @Override
    public void onCompleteRequest(String result) {
        working = false;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class ProposedFriendsBinder extends Binder {
        public ProposedFriendsService getService() {
            return ProposedFriendsService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY_COMPATIBILITY;
    }

    public boolean isWorking() {
        return working;
    }
}

