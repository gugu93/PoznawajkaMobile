package poznawajkamobile.pz2.aplicationandroid.utils.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import poznawajkamobile.pz2.aplicationandroid.models.FriendItemModel;
import poznawajkamobile.pz2.aplicationandroid.utils.Preferences;
import poznawajkamobile.pz2.aplicationandroid.utils.UpdateUtils;
import poznawajkamobile.pz2.aplicationandroid.utils.listners.FriendListListner;
import poznawajkamobile.pz2.aplicationandroid.utils.requests.ListFriendsRequest;

public class ListFriendsService extends Service implements FriendListListner {

    private final IBinder mBinder = new ListFriendsBinder();
    private boolean hasExternalStorage = UpdateUtils.hasExternalStorage();
    private Preferences mPreferences;
    private boolean working = false;
    private boolean succes = false;
    public ArrayList<FriendItemModel> list;

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
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/photos/");
        dir.mkdirs();
        ListFriendsRequest task = new ListFriendsRequest(this);
        task.execute(login, dir.getPath());
    }

    @Override
    public void onFinish(String result) {
        working = false;
        JSONArray contacts = null;
        try {
                JSONObject jsonObj = new JSONObject(result);
                contacts = jsonObj.getJSONArray("list_friend");
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    String name = c.getString("imie");
                    String lastname = c.getString("nazwisko");
                    String age = c.getString("wiek");
                    String icon = c.getString("icon");
                    Double gps_x = c.getDouble("gps_x");
                    Double gps_y = c.getDouble("gpx_y");
                    list.add(new FriendItemModel(1,Integer.parseInt(age),name,lastname,icon,gps_x.floatValue(),gps_y.floatValue()));
                }
        }
        catch (Exception e){
            list = null;
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

    public class ListFriendsBinder extends Binder {
        public ListFriendsService getService() {
            return ListFriendsService.this;
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

