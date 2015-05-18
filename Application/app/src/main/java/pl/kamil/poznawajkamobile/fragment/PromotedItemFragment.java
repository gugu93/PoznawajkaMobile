package pl.kamil.poznawajkamobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kml.poznawajkamobile.R;

import pl.kamil.poznawajkamobile.activity.AbstractActivity;
import pl.kamil.poznawajkamobile.utils.Constant;
import pl.kamil.poznawajkamobile.utils.Utils;


public class PromotedItemFragment extends AbstractFragment {

    private TextView mObjectName;
    private ImageView mObjectPhoto;
    private TextView mObjectAddress;
    private TextView mDistane;
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
        this.id = getActivity().getIntent().getIntExtra(Constant.EXTRA_ID, 0);
        this.age = getActivity().getIntent().getIntExtra(Constant.EXTRA_AGE, 1);
        this.name = getActivity().getIntent().getStringExtra(Constant.EXTRA_NAME);
        this.surname = getActivity().getIntent().getStringExtra(Constant.EXTRA_SURNAME);
        this.icon = getActivity().getIntent().getStringExtra(Constant.EXTRA_PHOTO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.promoted_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mObjectName = (TextView) view.findViewById(R.id.promoted_title);
        mObjectPhoto = (ImageView) view.findViewById(R.id.promoted_photo);
        mObjectAddress = (TextView) view.findViewById(R.id.promoted_address);
        mDistane = (TextView) view.findViewById(R.id.promoted_distance);
        mObjectName.setText(name + " " + surname + " Wiek " + age);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Utils.getPicasso(getActivity())
                .load(R.drawable.zaslepka)
                .error(R.color.main_grid_yellow)
                .resizeDimen(R.dimen.hundred_forty_dips, R.dimen.hundred_forty_dips)
                .into(mObjectPhoto);
    }

}





