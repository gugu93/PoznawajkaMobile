package poznawajkamobile.pz2.aplicationandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;

import com.example.kml.poznawajkamobile.R;

import poznawajkamobile.pz2.aplicationandroid.utils.Constant;
import poznawajkamobile.pz2.aplicationandroid.utils.GoogleMapUtils;
import poznawajkamobile.pz2.aplicationandroid.utils.Preferences;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

abstract public class AbstractActivity extends ActionBarActivity {

    private Preferences mPreferences;
    private boolean isTablet;
    private boolean isLandscape;
    private boolean hasGoogleServices = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hasGoogleServices = GoogleMapUtils.checkGooglePlayServices(this, true);
        isTablet = getResources().getBoolean(R.bool.isTablet);
        isLandscape = getResources().getBoolean(R.bool.isLandscape);
    }

    public static Bundle intentToFragmentArguments(Intent intent) {
        Bundle arguments = new Bundle();
        if (intent == null) {
            return arguments;
        }

        final Uri data = intent.getData();
        if (data != null) {
            arguments.putParcelable(Constant.EXTRA_INTENT_URI, data);
        }

        final String action = intent.getAction();
        if (!TextUtils.isEmpty(action)) {
            arguments.putString(Constant.EXTRA_INTENT_ACTION, action);
        }

        final Bundle extras = intent.getExtras();
        if (extras != null) {
            arguments.putAll(intent.getExtras());
        }

        return arguments;
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }


    public Preferences getPreferences() {
        if (mPreferences == null) {
            mPreferences = new Preferences(this);
        }
        return mPreferences;
    }

}
