package pl.kamil.poznawajkamobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kml.poznawajkamobile.R;


public class MeetNewPeopleActivity extends ActionBarActivity {
    private CheckBox checkLocation;
    private CheckBox checkPreferences;
    private CheckBox checkRandom;
    private Button buttonReturn;
    private Button buttonSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nowy_znajomi);
        setTitle("Poznaj nowych znajomych");

        checkLocation= (CheckBox)findViewById(R.id.checkLocation);
        checkPreferences= (CheckBox)findViewById(R.id.checkPreferences);
        checkRandom= (CheckBox)findViewById(R.id.checkRandom);
        buttonSearch = (Button)findViewById(R.id.buttonSearch);
        buttonReturn = (Button)findViewById(R.id.buttonReturn);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),OtherProfilActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.meet_new_ppl, menu);
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
