package pl.kamil.poznawajkamobile.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import de.greenrobot.event.EventBus;
import pl.kamil.poznawajkamobile.fragment.DownloadListener;

public class UpdateService extends Service implements DownloadListener {

    private final IBinder mBinder = new UpdateBinder();
    private boolean hasExternalStorage = UpdateUtils.hasExternalStorage();
    private Preferences mPreferences;
    private boolean working = false;

    @Override
    public void onCreate() {
        super.onCreate();
        mPreferences = new Preferences(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void startOnline(boolean noMedia) {
        MakeHttpRequest task = new MakeHttpRequest(this);
        task.execute(noMedia);
    }

    @Override
    public void onCheckFinished(String result) {
        working = false;
//        EventBus.getDefault().post(new CheckFinishEvent(result));
// jak to sie skonczy to sie wywoluje kolejne itp itd dodac do listnera i calosc sie bedzie updejtowala synchronicznie
    }

    @Override
    public Context getContext() {
        return this;
    }

    public class UpdateBinder extends Binder {
        public UpdateService getService() {
            return UpdateService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY_COMPATIBILITY;
    }

    public boolean isWorking() {
        return working;
    }
}
