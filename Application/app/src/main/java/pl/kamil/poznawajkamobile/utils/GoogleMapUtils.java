package pl.kamil.poznawajkamobile.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.kml.poznawajkamobile.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import pl.kamil.poznawajkamobile.activity.AbstractActivity;
import pl.kamil.poznawajkamobile.fragment.GMapFragment;

public class GoogleMapUtils {

    private AbstractActivity mActivity;
    private BitmapDescriptor mMarker = null;

    public GoogleMapUtils(AbstractActivity activity) {
        mActivity = activity;
    }

    public void checkMapsReady() {
        int status =
                GooglePlayServicesUtil.isGooglePlayServicesAvailable(mActivity);

        if (status == ConnectionResult.SUCCESS) {
            if (getVersionFromPackageManager(mActivity) < 2) {
                Toast.makeText(mActivity, R.string.no_maps, Toast.LENGTH_LONG).show();
                finish();
            }
        } else if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
            GooglePlayServicesUtil.getErrorDialog(status, mActivity, 0);
        } else {
            Toast.makeText(mActivity, R.string.no_maps, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public static boolean checkGooglePlayServices(Activity activity, boolean justCheck) {
        if (Constant.IS_ANALYTICS_ENABLED) {
            int status =
                    GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);

            if (status == ConnectionResult.SUCCESS) {
                return true;
            } else {
                if (!justCheck) {
                    GooglePlayServicesUtil.getErrorDialog(status, activity, Constant.RESULT_GOOGLE_PLAY_SERVICES).show();
                }
            }
            return false;
        }
        return true;
    }

    public static int getVersionFromPackageManager(Activity activity) {
        PackageManager packageManager = activity.getPackageManager();
        if (packageManager != null) {
            FeatureInfo[] featureInfos =
                    packageManager.getSystemAvailableFeatures();
            if (featureInfos != null && featureInfos.length > 0) {
                for (FeatureInfo featureInfo : featureInfos) {
                    // Null feature name means this feature is the open
                    // gl es version feature.
                    if (featureInfo.name == null) {
                        if (featureInfo.reqGlEsVersion != FeatureInfo.GL_ES_VERSION_UNDEFINED) {
                            return getMajorVersion(featureInfo.reqGlEsVersion);
                        } else {
                            return 1;
                        }
                    }
                }
            }
        }
        return 1;
    }

    /**
     * @see FeatureInfo#getGlEsVersion()
     */
    public static int getMajorVersion(int glEsVersion) {
        return ((glEsVersion & 0xffff0000) >> 16);
    }

    public BitmapDescriptor getIcon() {
        if (mMarker == null) {
            mMarker = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker);
        }
        return mMarker;
    }

    public enum MapInstance {
        GOOGLE_MAP(GMapFragment.FRAGMENT_TAG);

        private String tag;

        MapInstance(String tag) {
            this.tag = tag;
        }

        public Fragment getInstance(Intent intent) {
            if (this.equals(GOOGLE_MAP)) {
                return GMapFragment.newInstance(intent);
            } else return null;
        }
    }


    private void finish() {
            mActivity.finish();
    }


}

