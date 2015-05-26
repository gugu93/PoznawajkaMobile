package poznawajkamobile.pz2.aplicationandroid.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kml.poznawajkamobile.R;
import com.squareup.picasso.Picasso;

import java.io.File;

import poznawajkamobile.pz2.aplicationandroid.models.FriendItemModel;
import poznawajkamobile.pz2.aplicationandroid.utils.Utils;
import poznawajkamobile.pz2.aplicationandroid.utils.services.ProfilInfoService;


public class ProfileActivity extends AbstractActivity {
    private Button myButton;
    private TextView profileEmail;
    private TextView profileName;
    private TextView profileLastName;
    private TextView profilePreferences;
    private TextView profileAppearance;
    private ImageView profile_gallery;
    private ImageView profile_friends;
    private ImageView avatar;
    private TextView zmienzdjecie;
    private ProfilInfoService profilInfoService;
    private Boolean mBounInfo = false;
    private ServiceConnection mConnectionInfo = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ProfilInfoService.ProfilInfoBinder binder = (ProfilInfoService.ProfilInfoBinder) service;
            profilInfoService = binder.getService();
            mBounInfo = true;
            profilInfoService.start(getPreferences().getString("login"));
            initData(profilInfoService.item);
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBounInfo = false;
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        if (mBounInfo) {
            unbindService(mConnectionInfo);
            mBounInfo = false;
        }

    }

    public void initData(FriendItemModel model){
        if(model !=null){

        }else{
            Picasso p = Utils.getPicasso(getApplicationContext());
            p.load(new File("")).resize(100,100).error(R.drawable.tavatar).into(avatar);
           // p.load(new File("")).resize(100,100).error(R.drawable.zaslepka).into(profile_gallery);
           // p.load(new File("")).resize(100,100).error(R.drawable.tavatar).into(profile_friends);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        myButton = (Button)findViewById(R.id.buttonMeetNewPpl);
        profile_gallery = (ImageView)findViewById(R.id.profile_gallery);
        avatar = (ImageView) findViewById(R.id.avatarImg);
        profile_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
                startActivity(intent);
            }
        });
        profile_friends = (ImageView)findViewById(R.id.profile_friends);
        profile_friends.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), FriendsActivity.class);
                //startActivity(intent);
            }
        });
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MeetNewPeopleActivity.class);
                startActivity(intent);
            }
        });
        zmienzdjecie = (TextView)findViewById(R.id.zmienzdjecie);
        zmienzdjecie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),GalleryActivity.class);
            startActivity(intent);
            }
        });
        Intent intent = new Intent(this, ProfilInfoService.class);
        bindService(intent, mConnectionInfo, Context.BIND_AUTO_CREATE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logged, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
