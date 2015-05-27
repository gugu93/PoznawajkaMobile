package poznawajkamobile.pz2.aplicationandroid.utils.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

import java.io.File;

import poznawajkamobile.pz2.aplicationandroid.utils.Preferences;
import poznawajkamobile.pz2.aplicationandroid.utils.UpdateUtils;
import poznawajkamobile.pz2.aplicationandroid.utils.listners.AvatarListner;
import poznawajkamobile.pz2.aplicationandroid.utils.requests.ImageDownloadTask;

public class AvatarService extends Service implements AvatarListner {

    private final IBinder mBinder = new AvatarBinder();
    private boolean hasExternalStorage = UpdateUtils.hasExternalStorage();
    private Preferences mPreferences;
    private boolean working = false;
    private boolean succes = false;

    @Override
    public void onCreate() {
        super.onCreate();
        mPreferences = new Preferences(this);
    }

    public boolean isSucces() {
        return succes;
    }

    public void start(String login) {
        working = true;
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/photos/");
        dir.mkdirs();
        ImageDownloadTask task = new ImageDownloadTask(this);
        task.execute(login, dir.getPath());
    }

    @Override
    public void onComplete(Boolean result) {
        working = false;
        succes = result;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class AvatarBinder extends Binder {
        public AvatarService getService() {
            return AvatarService.this;
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

