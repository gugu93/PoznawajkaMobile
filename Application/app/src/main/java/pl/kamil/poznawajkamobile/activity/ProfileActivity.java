package pl.kamil.poznawajkamobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kml.poznawajkamobile.R;


public class ProfileActivity extends ActionBarActivity {
    private Button myButton;
    private TextView profileEmail;
    private TextView profileName;
    private TextView profileLastName;
    private TextView profilePreferences;
    private TextView profileAppearance;
    private ImageView profile_gallery;
    private ImageView profile_friends;
    private TextView zmienzdjecie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        myButton = (Button)findViewById(R.id.buttonMeetNewPpl);
        profile_gallery = (ImageView)findViewById(R.id.profile_gallery);
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
