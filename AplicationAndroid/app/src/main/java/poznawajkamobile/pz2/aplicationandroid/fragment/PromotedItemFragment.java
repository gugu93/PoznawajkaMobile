package poznawajkamobile.pz2.aplicationandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kml.poznawajkamobile.R;

import poznawajkamobile.pz2.aplicationandroid.activity.AbstractActivity;
import poznawajkamobile.pz2.aplicationandroid.utils.Constant;
import poznawajkamobile.pz2.aplicationandroid.utils.Utils;


public class PromotedItemFragment extends AbstractFragment {

    private TextView mFriendName;
    private ImageView mFriendPhoto;
    private TextView mFriendSurname;
    private TextView mFriendDistanceTO;
    private TextView mFriendAge;
    private boolean isParkingMachine = false;
    private int id;
    private int age;
    private String name;
    private String surname;
    private String icon;

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
        mFriendDistanceTO.setText("2km");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Utils.getPicasso(getActivity())
                .load(R.drawable.ona)
                .error(R.color.white)
                .resizeDimen(R.dimen.hundred_forty_dips, R.dimen.hundred_forty_dips)
                .into(mFriendPhoto);
    }

}





