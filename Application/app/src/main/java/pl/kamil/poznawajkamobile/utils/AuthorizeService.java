package pl.kamil.poznawajkamobile.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class AuthorizeService extends Service implements AuthorizeListner {

    private final IBinder mBinder = new AuthorizeBinder();
    private boolean hasExternalStorage = UpdateUtils.hasExternalStorage();
    private Preferences mPreferences;
    private boolean working = false;
    private boolean authorized = false;

    public boolean isAuthorized() {
        return authorized;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPreferences = new Preferences(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void start(String login, String passwd) {
        working = true;
        AuthorizeRequest task = new AuthorizeRequest(this);
        task.execute(login, passwd);
    }

    @Override
    public void onAuthorize(String result) {
        working = false;
        if (result.equals("OK")) {
            authorized = true;
        } else {
            authorized = true;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class AuthorizeBinder extends Binder {
        public AuthorizeService getService() {
            return AuthorizeService.this;
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

