package poznawajkamobile.pz2.aplicationandroid.fragment;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.kml.poznawajkamobile.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.squareup.picasso.Picasso;

import org.mapsforge.map.model.Model;

import java.util.ArrayList;
import java.util.HashMap;

import poznawajkamobile.pz2.aplicationandroid.activity.AbstractActivity;
import poznawajkamobile.pz2.aplicationandroid.utils.GoogleMapUtils;
import poznawajkamobile.pz2.aplicationandroid.utils.Utils;


public class GMapFragment extends SupportMapFragment implements GoogleMap.OnInfoWindowClickListener, GoogleMap.InfoWindowAdapter, GoogleMap.OnMarkerClickListener {

    public static final String FRAGMENT_TAG = GMapFragment.class.getSimpleName();
    public static final double DEFAULT_LATITUDE = 50.0619720;
    public static final double DEFAULT_LONGITUDE = 19.93791;
    public static final LatLng DEFAULT_POSITION = new LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
    private LatLngBounds.Builder mBuilder = new LatLngBounds.Builder();
    private HashMap<Marker, Model> mMarkers = new HashMap<Marker, Model>();
    private LatLng startPosition;
    private Polygon polygon;
    private GoogleMapUtils mMapUtils;
    private int[] objectsIds;
    private ArrayList<PolygonOptions> arrPolygonOptions;
    private Picasso mPicasso;
    private Polyline line;
    private HashMap<Integer, PolygonOptions> mZones = new HashMap<Integer, PolygonOptions>();
    private LatLng destPosition;
    private int lineColor;
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
        lineColor = getResources().getColor(R.color.my_blue);
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
//                    if (line != null) line.remove();
//                    if (mAddress.getLongitude() != null && mAddress.getLatitude() != null) {
//                        destPosition = new LatLng(mAddress.getLatitude(), mAddress.getLongitude());
//                        startPosition = new LatLng(arg0.getLatitude(), arg0.getLongitude());
//                        line = getMap().addPolyline(new PolylineOptions()
//                                .add(startPosition, destPosition)
//                                .width(4)
//                                .color(lineColor));
//                           }
                }
            });
            if (startPosition != null) {
                getMap().setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
//                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
//                            builder.include(startPosition);
//                            builder.include(destPosition);
//                            LatLngBounds bounds = builder.build();
//                            int padding = 100; // offset from edges of the map in pixels
//                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
//                            getMap().moveCamera(cameraUpdate);
//                            getMap().animateCamera(cameraUpdate);
                    }
                });
            }

        }
//            addMarkerAndZoom(mAddress);
        if (getMap() != null) {
            getMap().setInfoWindowAdapter(this);
            getMap().setOnInfoWindowClickListener(this);
            getMap().setOnMarkerClickListener(this);
        }
    }


//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        if (getActivity() != null && data != null) {
//            switch (loader.getId()) {
//                case Constant.LOADER_ROUTE_LIST:
//                    objectsIds = new int[data.getCount()];
//                    LatLng[] arrLatLng = new LatLng[data.getCount()];
//                    int i=0;
//                    while (data.moveToNext()) {
//                        AddressModel model = new AddressModel(data);
//                        if (model.getLongitude() != null && model.getLatitude() != null) {
//                            objectsIds[data.getPosition()] = model.getId();
//                            mBuilder.include(new LatLng(model.getLatitude(), model.getLongitude()));
//                            addMarker(model);
//                            arrLatLng[i++]= new LatLng(model.getLatitude(),model.getLongitude());
//                        }
//                    }
//                    for(int j = 0; j<data.getCount()-1;j++){
//                        destPosition = arrLatLng[j+1];
//                        startPosition = arrLatLng[j];
//                        line = getMap().addPolyline(new PolylineOptions()
//                                .add(startPosition, destPosition)
//                                .width(5)
//                                .color(lineColor));
//                    }
//
//                    if (getMap() != null && mMarkers.size() > 0) {
//                        try {
//                            getMap().moveCamera(CameraUpdateFactory.newLatLngBounds(mBuilder.build(), 32));
//                        } catch (IllegalStateException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    break;
//                case Constant.LOADER_OBJECT_PLANNER:
//                    objectsIds = new int[data.getCount()];
//                    while (data.moveToNext()) {
//                        AddressModel model = new AddressModel(data);
//                        if (model.getLongitude() != null && model.getLatitude() != null) {
//                            objectsIds[data.getPosition()] = model.getId();
//                            mBuilder.include(new LatLng(model.getLatitude(), model.getLongitude()));
//                            addMarker(model);
//                        }
//                    }
//                    getLoaderManager().initLoader(Constant.LOADER_CALENDAR_PLANNER, null, this);
//                    break;
//
//                case Constant.LOADER_CALENDAR_PLANNER:
//                    objectsIds = new int[data.getCount()];
//                    while (data.moveToNext()) {
//                        CalendarEventModel model = new CalendarEventModel(data);
//                        if (model.getLongitude() != null && model.getLatitude() != null) {
//                            objectsIds[data.getPosition()] = model.getId();
//                            mBuilder.include(new LatLng(model.getLatitude(), model.getLongitude()));
//                            addMarker(model);
//                        }
//                    }
//                    break;
//                case Constant.LOADER_OBJECT:
//                    while (data.moveToNext()) {
//                        AddressModel model = new AddressModel(data);
//                        if (model.getLongitude() != null && model.getLatitude() != null) {
//                            mBuilder.include(new LatLng(model.getLatitude(), model.getLongitude()));
//                            addMarker(model);
//                        }
//                    }
//
//                    if(getMap()!=null) {
//
//                        getMap().setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
//                            @Override
//                            public void onMapLoaded() {
//                                LatLngBounds.Builder builder = new LatLngBounds.Builder();
//                                for (HashMap.Entry<Marker, Model> marker : mMarkers.entrySet()) {
//                                    builder.include(marker.getKey().getPosition());
//                                }
//                                LatLngBounds bounds = builder.build();
//                                int padding = 100; // offset from edges of the map in pixels
//                                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
//                                getMap().moveCamera(cameraUpdate);
//                                getMap().animateCamera(cameraUpdate);
//                            }
//                        });
//                    }
//                            break;
//
//                case Constant.LOADER_CALENDAR_EVENT_LIST:
//                    while (data.moveToNext()) {
//                        CalendarEventModel model = new CalendarEventModel(data);
//                        if (model.getLongitude() != null && model.getLatitude() != null) {
//                            mBuilder.include(new LatLng(model.getLatitude(), model.getLongitude()));
//                            addMarker(model);
//                        }
//                    }
//
//                    if(getMap()!=null) {
//                        getMap().setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
//                            @Override
//                            public void onMapLoaded() {
//                                LatLngBounds.Builder builder = new LatLngBounds.Builder();
//                                for (HashMap.Entry<Marker, Model> marker : mMarkers.entrySet()) {
//                                    builder.include(marker.getKey().getPosition());
//                                }
//                                LatLngBounds bounds = builder.build();
//                                int padding = 100; // offset from edges of the map in pixels
//                                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
//                                getMap().moveCamera(cameraUpdate);
//                                getMap().animateCamera(cameraUpdate);
//                            }
//                        });
//                    }
//                    break;
//
//                case Constant.LOADER_PARKING_ZONES:
//                    while (data.moveToNext()) {
//                        ParkingZoneModel zone = new ParkingZoneModel();
//                        zone.fromCursor(data);
//                        PolygonOptions polygonOptions = new PolygonOptions().strokeColor(Utils.getColorFromHex(zone.getColor()))
//                            .fillColor(Utils.getColorFromHex(zone.getColor().replace("#", "#CC")));
//                        mZones.put(zone.getId(), polygonOptions);
//
//                    }
//                    getLoaderManager().initLoader(Constant.LOADER_PARKING_ZONE_POINTS, null, this);
//                    break;
//                case Constant.LOADER_PARKING_ZONE_POINTS:
//                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
//                    while (data.moveToNext()) {
//                        ParkingZonePointModel pointModel = new ParkingZonePointModel(data);
//                        if (mZones.get(pointModel.getParkingZoneId()) != null) {
//                            LatLng position = new LatLng(pointModel.getLatitude(), pointModel.getLongitude());
//                            mZones.get(pointModel.getParkingZoneId()).add(position);
//                            builder.include(position);
//                        }
//                    }
//                    for (HashMap.Entry<Integer, PolygonOptions> zone : mZones.entrySet()) {
//                        getMap().addPolygon(zone.getValue());
//                    }
//                    LatLngBounds bounds = builder.build();
//                    int padding = 10; // offset from edges of the map in pixels
//                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
//                    getMap().moveCamera(cameraUpdate);
//                    getMap().animateCamera(cameraUpdate);
//                    break;
//                case Constant.LOADER_KMKBIKES:
//                    objectsIds = new int[data.getCount()];
//                    while (data.moveToNext()) {
//                        AddressModel model = new AddressModel(data);
//                        if (model.getLongitude() != null && model.getLatitude() != null) {
//                            objectsIds[data.getPosition()] = model.getId();
//                            mBuilder.include(new LatLng(model.getLatitude(), model.getLongitude()));
//                            addMarker(model);
//                        }
//                    }
//                    break;
//                }
//            }
//    }


    @Override
    public void onInfoWindowClick(Marker marker) {
//        if (mMarkers.containsKey(marker)) {
//            if (mMarkers.get(marker) instanceof CalendarEventModel) {
//                    Intent calendarDetail = new Intent(getActivity(), CalendarEventDetailActivity.class);
//                    calendarDetail.putExtra(Constant.EXTRA_INTENT_CALENDAR, ModelUtils.toJson(calendar));
//                    startActivity(calendarDetail);
//            }
//        }
    }


    private Marker addMarker(Model model) {
//        if (model instanceof AddressModel) {
//            AddressModel address = (AddressModel) model;
//            if (address.getLatitude() != null && address.getLongitude() != null) {
//                LatLng position = new LatLng(address.getLatitude(), address.getLongitude());
//                Marker marker = getMap().addMarker(new MarkerOptions()
//                    .position(position)
//                    .icon(mMapUtils.getIcon())
//                    .anchor(0.1f, 0.9f)
//                    .title(address.getName())
//                    .visible(true));
//                if (marker != null) {
//                    mMarkers.put(marker, model);
//                }
//            }
//        }
        return null;
    }

    private void addMarkerAndZoom(Model model) {
//        if (model instanceof AddressModel) {
//            AddressModel address = (AddressModel) model;
//            if (address.getLatitude() != null && address.getLongitude() != null) {
//                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//                getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14), 1, null);
//                Marker marker = addMarker(model);
//                if(marker!=null)marker.showInfoWindow();
//            }
//        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
//        float aLat = 0;
//        float aLon = 0;
//        View view = LayoutInflater.from(getActivity()).inflate(R.layout.map_object_description, null);
//        TextView name = (TextView) view.findViewById(R.id.map_object_name);
//        TextView distance = (TextView) view.findViewById(R.id.map_object_distance);
//        ImageView mImage = (ImageView) view.findViewById(R.id.map_object_image);
//        if (getMap().getMyLocation() != null) {
//            aLat = (float) getMap().getMyLocation().getLatitude();
//            aLon = (float) getMap().getMyLocation().getLongitude();
//        } else {
//            distance.setText(" ");
//        }
//        Model model = mMarkers.get(marker);
//        if (model != null) {
//            if (model instanceof AddressModel) {
//                final AddressModel address = (AddressModel) model;
//                name.setText(Html.fromHtml(StringEscapeUtils.unescapeHtml4(address.getName())));
//                long dist = Math.round(Utils.distanceBetween(aLat, aLon, address.getLatitude(),
//                    address.getLongitude()));
//                if (aLat > 0 && aLon > 0) {
//                    distance.setText(Iconify.IconValue.fa_location_arrow.formattedName() + " "
//                        + Utils.humanReadableDistance(dist));
//                }
//                ArrayList<String> photos = ModelUtils.getPhotosForAddress(getActivity(), address);
//                if (photos != null && photos.size() > 0) {
//                    String photoPath = photos.get(0);
//                    File photo = new  File(photoPath);
//                    if (photo.exists()) {
//                        mPicasso
//                            .load(photo)
//                            .resizeDimen(R.dimen.eighty_dips, R.dimen.eighty_dips)
//                            .centerCrop()
//                            .error(R.color.main_grid_blue)
//                            .into(mImage);
//                    }
//                }
//                view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Class cls = isMasterDetail ? ObjectListActivity.class
//                            : ObjectDetailActivity.class;
//                        Intent intent = new Intent(getActivity(), cls);
//                        intent.putExtra(Constant.EXTRA_PLANNER_OBJECT, ModelUtils.toJson(address));
//                        startActivity(intent);
//                    }
//                });
//            } else if (model instanceof CalendarEventModel) {
//                final CalendarEventModel event = (CalendarEventModel) model;
//                name.setText(event.getName());
//                long dist = Math.round(Utils.distanceBetween(aLat, aLon, event.getLatitude(),
//                    event.getLongitude()));
//                if (aLat > 0 && aLon > 0) {
//                    distance.setText(Iconify.IconValue.fa_location_arrow.formattedName() + " "
//                        + Utils.humanReadableDistance(dist));
//                }
//                mPicasso
//                    .load("file:" + event.getImage())
//                    .resizeDimen(R.dimen.eighty_dips, R.dimen.eighty_dips)
//                    .centerCrop()
//                    .error(R.color.main_grid_blue)
//                    .into(mImage);
//                view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Class cls = isMasterDetail ? CalendarEventListActivity.class
//                            : CalendarEventDetailActivity.class;
//                        Intent intent = new Intent(getActivity(), cls);
//                        intent.putExtra(Constant.EXTRA_PLANNER_OBJECT, ModelUtils.toJson(event));
//                        startActivity(intent);
//                    }
//                });
//            }
//            if (aLat > 0 && aLon > 0) {
//                Iconify.addIcons(distance);
//            }
//        }
//        return view;
        return null;
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
