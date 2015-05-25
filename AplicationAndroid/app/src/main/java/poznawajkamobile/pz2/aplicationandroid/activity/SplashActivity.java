package poznawajkamobile.pz2.aplicationandroid.activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

import com.example.kml.poznawajkamobile.R;

import poznawajkamobile.pz2.aplicationandroid.utils.Constant;
import poznawajkamobile.pz2.aplicationandroid.utils.GoogleMapUtils;

public class SplashActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB && getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if (GoogleMapUtils.checkGooglePlayServices(this, false)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            }, 1500);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.RESULT_GOOGLE_PLAY_SERVICES:
                if (resultCode == RESULT_OK) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                } else {
                    GoogleMapUtils.checkGooglePlayServices(this, false);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}
