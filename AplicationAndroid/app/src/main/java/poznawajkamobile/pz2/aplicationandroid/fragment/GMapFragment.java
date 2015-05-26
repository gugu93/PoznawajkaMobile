package poznawajkamobile.pz2.aplicationandroid.fragment;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kml.poznawajkamobile.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.joanzapata.android.iconify.Iconify;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringEscapeUtils;
import org.mapsforge.map.model.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import poznawajkamobile.pz2.aplicationandroid.activity.AbstractActivity;
import poznawajkamobile.pz2.aplicationandroid.activity.OtherProfilActivity;
import poznawajkamobile.pz2.aplicationandroid.models.FriendItemModel;
import poznawajkamobile.pz2.aplicationandroid.utils.GoogleMapUtils;
import poznawajkamobile.pz2.aplicationandroid.utils.Utils;


public class GMapFragment extends SupportMapFragment implements GoogleMap.OnInfoWindowClickListener, GoogleMap.InfoWindowAdapter, GoogleMap.OnMarkerClickListener {

    public static final String FRAGMENT_TAG = GMapFragment.class.getSimpleName();
    public static final double DEFAULT_LATITUDE = 50.03029499;
    public static final double DEFAULT_LONGITUDE = 19.90633607;
    public static final LatLng DEFAULT_POSITION = new LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
    private HashMap<Marker, FriendItemModel> mMarkers = new HashMap<Marker, FriendItemModel>();
    private LatLng startPosition;
    private GoogleMapUtils mMapUtils;
    private Picasso mPicasso;
    private MenuItem Terrain;
    private MenuItem Satellite;

    public static GMapFragment newInstance(Intent intent) {
        GMapFragment fragment = new GMapFragment();
        fragment.setArguments(AbstractActivity.intentToFragmentArguments(intent));
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPicasso = Utils.getPicasso(getActivity());
        mMapUtils = new GoogleMapUtils((AbstractActivity) getActivity());
        mMapUtils.checkMapsReady();
        if (getMap() != null) {
            getMap().setMyLocationEnabled(true);
            getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_POSITION, 14), 1, null);
        }
        if (getMap() != null) {
            getMap().setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location arg0) {
                        startPosition = new LatLng(arg0.getLatitude(), arg0.getLongitude());
                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            builder.include(startPosition);
                            LatLngBounds bounds = builder.build();
                            int padding = 100; // offset from edges of the map in pixels
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                            getMap().moveCamera(cameraUpdate);
                            getMap().animateCamera(cameraUpdate);
                }
            });
            if (startPosition != null) {
                getMap().setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            builder.include(startPosition);
                            LatLngBounds bounds = builder.build();
                            int padding = 100; // offset from edges of the map in pixels
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                            getMap().moveCamera(cameraUpdate);
                            getMap().animateCamera(cameraUpdate);
                    }
                });
            }
        }
        if (getMap() != null) {
            getMap().setInfoWindowAdapter(this);
            getMap().setOnInfoWindowClickListener(this);
            getMap().setOnMarkerClickListener(this);
        }

    }

    public void loadData(ArrayList<FriendItemModel> list){
        if(list!=null){
            for(FriendItemModel f :list)
                addMarker(f);
        }else {
            FriendItemModel a = new FriendItemModel(1, 20, "Kamila", "Gonet", "image1",50.03102553f,19.90675986f);
            FriendItemModel b =  new FriendItemModel(2, 24, "Anna", "Wlazlo", "image2",50.02972984f,19.90584254f);
            FriendItemModel c = new FriendItemModel(3, 18, "Aga", "Sura", "image3",50.03068094f,19.90628779f);
            FriendItemModel d =  new FriendItemModel(4, 19, "Iza", "Gonet", "image4",50.02811115f,19.90411718f);
            addMarker(a);
            addMarker(b);
            addMarker(c);
            addMarker(d);
        }

    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        if (mMarkers.containsKey(marker)) {
                    Intent intent = new Intent(getActivity(), OtherProfilActivity.class);
                    startActivity(intent);
        }
    }

    private Marker addMarker(FriendItemModel model) {
            FriendItemModel friendModel = model;
            if (friendModel.getGps_x() != null && friendModel.getGps_y() != null) {
                LatLng position = new LatLng(friendModel.getGps_x(), friendModel.getGps_y());
                Marker marker = getMap().addMarker(new MarkerOptions()
                    .position(position)
                    .icon(mMapUtils.getIcon())
                    .anchor(0.1f, 0.9f)
                    .title(friendModel.getName())
                    .visible(true));
                if (marker != null) {
                    mMarkers.put(marker, friendModel);
                }
            }
        return null;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        float aLat = 0;
        float aLon = 0;
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.new_friend_item, null);
        TextView name = (TextView) view.findViewById(R.id.new_promoted_friend_name);
        TextView surname = (TextView) view.findViewById(R.id.new_promoted_friend_lastname);
        ImageView mImage = (ImageView) view.findViewById(R.id.new_promoted_friend_photo);
        if (getMap().getMyLocation() != null) {
            aLat = (float) getMap().getMyLocation().getLatitude();
            aLon = (float) getMap().getMyLocation().getLongitude();
        } else {
            surname.setText(" ");
        }
        FriendItemModel model = mMarkers.get(marker);
        if (model != null) {
            name.setText(Html.fromHtml(StringEscapeUtils.unescapeHtml4(model.getName())));
            long dist = Math.round(Utils.distanceBetween(aLat, aLon, model.getGps_x(),
                    model.getGps_y()));
            if (aLat > 0 && aLon > 0) {
                surname.setText(Iconify.IconValue.fa_location_arrow.formattedName() + " "
                        + Utils.humanReadableDistance(dist));
            }
            switch (model.getId()) {
                case 1:
                    Utils.getPicasso(getActivity().getApplicationContext())
                            .load(R.drawable.ona)
                            .error(R.color.white)
                            .resizeDimen(R.dimen.hundred_forty_dips, R.dimen.hundred_forty_dips)
                            .into(mImage);
                    break;
                case 2:
                    Utils.getPicasso(getActivity().getApplicationContext())
                            .load(R.drawable.sample1)
                            .error(R.color.white)
                            .resizeDimen(R.dimen.hundred_forty_dips, R.dimen.hundred_forty_dips)
                            .into(mImage);
                    break;
                case 3:
                    Utils.getPicasso(getActivity().getApplicationContext())
                            .load(R.drawable.sample2)
                            .error(R.color.white)
                            .resizeDimen(R.dimen.hundred_forty_dips, R.dimen.hundred_forty_dips)
                            .into(mImage);
                    break;
                case 4:
                    Utils.getPicasso(getActivity().getApplicationContext())
                            .load(R.drawable.sample3)
                            .error(R.color.white)
                            .resizeDimen(R.dimen.hundred_forty_dips, R.dimen.hundred_forty_dips)
                            .into(mImage);
                    break;
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), OtherProfilActivity.class);
                    startActivity(intent);
                }
            });
        }
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Terrain = menu.add(R.id.menu_group, R.id.mapStreet, 11, "Terrain");
        Satellite = menu.add(R.id.menu_group, R.id.mapSat, 11, "Satelita");
        Terrain = Terrain.setVisible(false);
        MenuItemCompat.setShowAsAction(Terrain, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        MenuItemCompat.setShowAsAction(Satellite, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_open_info:
                getActivity().finish();
                return true;
            case R.id.mapSat:
                getMap().setMapType(GoogleMap.MAP_TYPE_HYBRID);
                Terrain = Terrain.setVisible(true);
                Satellite = Satellite.setVisible(false);
                return true;
            case R.id.mapStreet:
                getMap().setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                Terrain = Terrain.setVisible(false);
                Satellite = Satellite.setVisible(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }
}
