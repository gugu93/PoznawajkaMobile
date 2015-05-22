package pl.kamil.poznawajkamobile.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kml.poznawajkamobile.R;


public class OtherProfilActivity extends ActionBarActivity {


    private TextView nameOtherProfile;
    private TextView lastNameOtherProfile;
    private TextView preferenceOtherProfile;
    private TextView appearenceOtherProfile;
    private Button buttonRequest;
    private Button buttonRate;
    private Button buttonAddToFriends;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        nameOtherProfile = (TextView)findViewById(R.id.nameOtherProfile);
        lastNameOtherProfile = (TextView)findViewById(R.id.lastNameOtherProfile);
        preferenceOtherProfile = (TextView)findViewById(R.id.preferenceOtherProfile);
        appearenceOtherProfile = (TextView)findViewById(R.id.appearenceOtherProfile);

        buttonRequest = (Button)findViewById(R.id.buttonRequest);
        buttonRate = (Button)findViewById(R.id.buttonRate);
        buttonAddToFriends = (Button)findViewById(R.id.buttonAddToFriends);

        buttonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        buttonRate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });

        buttonAddToFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inna_osoba);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.other_profil, menu);
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
