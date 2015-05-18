package pl.kamil.poznawajkamobile.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.MenuItem;

import com.example.kml.poznawajkamobile.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.joanzapata.android.iconify.Iconify;

import pl.kamil.poznawajkamobile.utils.Constant;
import pl.kamil.poznawajkamobile.utils.GoogleMapUtils;
import pl.kamil.poznawajkamobile.utils.GraphicUtils;
import pl.kamil.poznawajkamobile.utils.Preferences;
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
    protected void onStart() {
        super.onStart();
        if (hasGoogleServices) {
            GoogleAnalytics.getInstance(this).reportActivityStart(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (hasGoogleServices) {
            GoogleAnalytics.getInstance(this).reportActivityStop(this);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    public static Intent fragmentArgumentsToIntent(Bundle arguments) {
        Intent intent = new Intent();
        if (arguments == null) {
            return intent;
        }

        final Uri data = arguments.getParcelable(Constant.EXTRA_INTENT_URI);
        if (data != null) {
            intent.setData(data);
        }

        final String action = arguments.getString(Constant.EXTRA_INTENT_ACTION);
        if (!TextUtils.isEmpty(action)) {
            intent.setAction(action);
        }

        intent.putExtras(arguments);
        intent.removeExtra(Constant.EXTRA_INTENT_URI);
        intent.removeExtra(Constant.EXTRA_INTENT_ACTION);
        return intent;
    }

    public void goTo(Class<?> cls) {
        Intent i = new Intent(this, cls);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void goToAndClear(Class<?> cls) {
        Intent i = new Intent(this, cls);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    public Preferences getPreferences() {
        if (mPreferences == null) {
            mPreferences = new Preferences(this);
        }
        return mPreferences;
    }

    public void setActionBarItem(MenuItem item, Iconify.IconValue icon) {
        int show;
        boolean isHorizontal = getResources().getBoolean(R.bool.isLandscape);
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isHorizontal || isTablet) {
            show = MenuItemCompat.SHOW_AS_ACTION_IF_ROOM | MenuItemCompat.SHOW_AS_ACTION_WITH_TEXT;
        } else {
            show = MenuItemCompat.SHOW_AS_ACTION_IF_ROOM;
        }
        item.setIcon(getMenuIcon(icon));
        MenuItemCompat.setShowAsAction(item, show);
    }

    public void setActionBarItem(MenuItem item, Drawable icon) {
        int show;
        boolean isHorizontal = getResources().getBoolean(R.bool.isLandscape);
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isHorizontal || isTablet) {
            show = MenuItemCompat.SHOW_AS_ACTION_IF_ROOM | MenuItemCompat.SHOW_AS_ACTION_WITH_TEXT;
        } else {
            show = MenuItemCompat.SHOW_AS_ACTION_IF_ROOM;
        }
        item.setIcon(icon);
        MenuItemCompat.setShowAsAction(item, show);
    }

    public Drawable getMenuIcon(Iconify.IconValue icon) {
        return GraphicUtils.getMenuIcon(this, icon);
    }


    public void setActionBarItem(MenuItem item, Iconify.IconValue icon, int show) {
        item.setIcon(getMenuIcon(icon));
        MenuItemCompat.setShowAsAction(item, show);

    }

    public boolean isTablet() {
        return isTablet;
    }

    public boolean isLandscape() {
        return isLandscape;
    }

    public boolean isMasterDetail() {
        return isLandscape() && isTablet();
    }
}
