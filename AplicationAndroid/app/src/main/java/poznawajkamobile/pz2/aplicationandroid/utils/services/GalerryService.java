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

import poznawajkamobile.pz2.aplicationandroid.models.GaleryModel;
import poznawajkamobile.pz2.aplicationandroid.models.ProposedPersonModel;
import poznawajkamobile.pz2.aplicationandroid.utils.Preferences;
import poznawajkamobile.pz2.aplicationandroid.utils.UpdateUtils;
import poznawajkamobile.pz2.aplicationandroid.utils.listners.GaleryListner;
import poznawajkamobile.pz2.aplicationandroid.utils.requests.DownloadGalleryTask;
import poznawajkamobile.pz2.aplicationandroid.utils.requests.ImageDownloadTask;

public class GalerryService extends Service implements GaleryListner {

    private final IBinder mBinder = new GalleryBinder();
    private boolean hasExternalStorage = UpdateUtils.hasExternalStorage();
    private Preferences mPreferences;
    private boolean working = false;
    private boolean succes = false;
    public ArrayList<GaleryModel> list;

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
        DownloadGalleryTask task = new DownloadGalleryTask(this);
        task.execute(login, dir.getPath());
    }

    @Override
    public void onFinish(String result) {
        working = false;
        working = false;
        JSONArray contacts = null;
        try {
            JSONObject jsonObj = new JSONObject(result);
            contacts = jsonObj.getJSONArray("photos");
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);
                int id = c.getInt("id");
                int userID = c.getInt("userId");
                String name = c.getString("name");
                list.add(new GaleryModel(id,name,userID));
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

    public class GalleryBinder extends Binder {
        public GalerryService getService() {
            return GalerryService.this;
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

