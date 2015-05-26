package poznawajkamobile.pz2.aplicationandroid.fragment;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kml.poznawajkamobile.R;

import org.mapsforge.map.util.MapPositionUtil;

import poznawajkamobile.pz2.aplicationandroid.activity.AbstractActivity;
import poznawajkamobile.pz2.aplicationandroid.utils.Constant;
import poznawajkamobile.pz2.aplicationandroid.utils.GoogleMapUtils;
import poznawajkamobile.pz2.aplicationandroid.utils.Utils;


public class PromotedItemFragment extends AbstractLocationFragment {
    private Location myLocation = new Location("");
    private TextView mFriendName;
    private ImageView mFriendPhoto;
    private TextView mFriendSurname;
    private TextView mFriendDistanceTO;
    private TextView mFriendAge;
    private int id;
    private int age;
    private String name;
    private String surname;
    private String icon;
    private Float gps_x;
    private Float gps_y;

    public static PromotedItemFragment newInstance(Intent intent) {
        PromotedItemFragment fragment = new PromotedItemFragment();
        fragment.setArguments(AbstractActivity.intentToFragmentArguments(intent));
        fragment.setHasOptionsMenu(true);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.id = getActivity().getIntent().getIntExtra(Constant.EXTRA_ID, 0);
            this.age = getActivity().getIntent().getIntExtra(Constant.EXTRA_AGE, 1);
            this.name = getActivity().getIntent().getStringExtra(Constant.EXTRA_NAME);
            this.surname = getActivity().getIntent().getStringExtra(Constant.EXTRA_SURNAME);
            this.icon = getActivity().getIntent().getStringExtra(Constant.EXTRA_PHOTO);
            this.gps_x = getActivity().getIntent().getFloatExtra(Constant.EXTRA_GPSX,50.0f);
            this.gps_y = getActivity().getIntent().getFloatExtra(Constant.EXTRA_GPSY,19.0f);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_friend_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFriendName = (TextView) view.findViewById(R.id.new_promoted_friend_name);
        mFriendAge = (TextView) view.findViewById(R.id.new_promoted_friend_age);
        mFriendPhoto = (ImageView) view.findViewById(R.id.new_promoted_friend_photo);
        mFriendSurname = (TextView) view.findViewById(R.id.new_promoted_friend_lastname);
        mFriendDistanceTO = (TextView) view.findViewById(R.id.new_promoted_friend_distance);
        mFriendName.setText(this.name);
        mFriendSurname.setText(this.surname);
        mFriendAge.setText("Wiek " + this.age);
        myLocation.setLatitude(50f);
        myLocation.setLongitude(19f);
        mFriendDistanceTO.setText(calculateDistance(myLocation.getLatitude(),myLocation.getLongitude(),
                this.gps_x,this.gps_y).toString() + "km");
    }

    @Override
    protected void onNewLocation(Location location) {
        if(location!=null){
            this.myLocation = location;
        }
    }

    private static final int earthRadius = 6371;
    public static Integer calculateDistance(double lat1, double lon1, float lat2, float lon2)
    {
        float dLat = (float) Math.toRadians(lat2 - lat1);
        float dLon = (float) Math.toRadians(lon2 - lon1);
        float a =  (float) (Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
                        * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2));
        float c = (float) (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
        Float d = earthRadius * c;
        return d.intValue()/1000;
    }


    @Override
    protected boolean isMyLocationEnabled() {
        return true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        switch (this.id){
            case 1:
                Utils.getPicasso(getActivity())
                        .load(R.drawable.ona)
                        .error(R.color.white)
                        .resizeDimen(R.dimen.hundred_forty_dips, R.dimen.hundred_forty_dips)
                        .into(mFriendPhoto);
                break;
            case 2:
                Utils.getPicasso(getActivity())
                        .load(R.drawable.sample1)
                        .error(R.color.white)
                        .resizeDimen(R.dimen.hundred_forty_dips, R.dimen.hundred_forty_dips)
                        .into(mFriendPhoto);
                break;
            case 3:
                Utils.getPicasso(getActivity())
                        .load(R.drawable.sample2)
                        .error(R.color.white)
                        .resizeDimen(R.dimen.hundred_forty_dips, R.dimen.hundred_forty_dips)
                        .into(mFriendPhoto);
                break;
            case 4:
                Utils.getPicasso(getActivity())
                        .load(R.drawable.sample3)
                        .error(R.color.white)
                        .resizeDimen(R.dimen.hundred_forty_dips, R.dimen.hundred_forty_dips)
                        .into(mFriendPhoto);
                break;
            case 5:
                Utils.getPicasso(getActivity())
                        .load(R.drawable.sample4)
                        .error(R.color.white)
                        .resizeDimen(R.dimen.hundred_forty_dips, R.dimen.hundred_forty_dips)
                        .into(mFriendPhoto);
                break;
            default:
                Utils.getPicasso(getActivity())
                        .load(R.drawable.sample4)
                        .error(R.color.white)
                        .resizeDimen(R.dimen.hundred_forty_dips, R.dimen.hundred_forty_dips)
                        .into(mFriendPhoto);
                break;
        }
    }

}





