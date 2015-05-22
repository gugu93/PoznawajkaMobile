package pl.kamil.poznawajkamobile.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

abstract public class AbstractLocationFragment extends AbstractFragment implements LocationListener,
        GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
    private static final String TAG = AbstractLocationFragment.class.getSimpleName();
    // Milliseconds per second
    private static final int MILLISECONDS_PER_SECOND = 1000;
    // Update frequency in seconds
    public static final int UPDATE_INTERVAL_IN_SECONDS = 3;
    // Update frequency in milliseconds
    private static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    // The fastest update frequency, in seconds
    private static final int FASTEST_INTERVAL_IN_SECONDS = 10;
    // A fast frequency ceiling in milliseconds
    private static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
    private LocationClient locationClient;

    abstract protected void onNewLocation(Location location);

    abstract protected boolean isMyLocationEnabled();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        locationClient = new LocationClient(getActivity(), this, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isMyLocationEnabled()) {
            locationClient.connect();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (locationClient.isConnected()) {
            locationClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location currentLocation = locationClient.getLastLocation();
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationClient.requestLocationUpdates(locationRequest, this);
        if (currentLocation != null) {
            onLocationChanged(currentLocation);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            onNewLocation(location);
        }
    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public LocationClient getLocationClient() {
        return locationClient;
    }
}
