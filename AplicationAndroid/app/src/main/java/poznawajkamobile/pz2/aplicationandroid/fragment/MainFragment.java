package poznawajkamobile.pz2.aplicationandroid.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.kml.poznawajkamobile.R;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;

import poznawajkamobile.pz2.aplicationandroid.activity.AbstractActivity;
import poznawajkamobile.pz2.aplicationandroid.activity.MainActivity;
import poznawajkamobile.pz2.aplicationandroid.adapter.MainGridAdapter;
import poznawajkamobile.pz2.aplicationandroid.models.MenuModel;
import poznawajkamobile.pz2.aplicationandroid.utils.GraphicUtils;
import poznawajkamobile.pz2.aplicationandroid.utils.Utils;
import poznawajkamobile.pz2.aplicationandroid.views.MainGrid;


public class MainFragment extends AbstractFragment implements  MainGrid.OnMainGridItemClickListener {
    public static final String FRAGMENT_TAG = MainFragment.class.getSimpleName();
    private MainGrid mGrid;
    private FrameLayout proponowane;
    public MainGridAdapter mAdapter;


    public static MainFragment newInstance(Intent intent) {
        MainFragment fragment = new MainFragment();
        fragment.setArguments(AbstractActivity.intentToFragmentArguments(intent));
        fragment.setRetainInstance(false);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGrid = (MainGrid) view.findViewById(R.id.main_grid);
        proponowane = (FrameLayout) view.findViewById(R.id.proponowane);
        mGrid.setOnMainGridItemClickListener(this);
        ImageButton menu = (ImageButton) view.findViewById(R.id.main_menu);
        menu.setImageDrawable(GraphicUtils.generateStateList(getActivity(),
                Iconify.IconValue.fa_sliders, R.color.text_black_white, R.color.my_blue, 24));
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).onIconClick();
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int height = Math.round(Utils.getScreenHeight(getActivity()) * 0.75f);
        mGrid.getLayoutParams().width = Math.round(height * 7 / 10);
        proponowane.getLayoutParams().width = mGrid.getLayoutParams().width;
        ArrayList<MenuModel> m = createData();
        mAdapter = new MainGridAdapter(getActivity(), m);
        mGrid.setAdapter(mAdapter);
    }

    public ArrayList<MenuModel> createData() {
        ArrayList<MenuModel> m = new ArrayList<MenuModel>();
        MenuModel a = new MenuModel(1, "TWOJ PROFIL");
        MenuModel b = new MenuModel(2, "SZUKAJ ZNAJOMYCH", "icon1.jpg");
        MenuModel c = new MenuModel(3, "ZNAJOMI", "icon2.jpg");
        MenuModel d = new MenuModel(4, "GALERIA", "icon3.jpg");
        MenuModel e = new MenuModel(5, "MAPA ZNAJOMOSCI", "icon4.jpg");
        MenuModel f = new MenuModel(6, "O PROGRAMIE", "icon5.jpg");
        m.add(a);
        m.add(b);
        m.add(c);
        m.add(d);
        m.add(e);
        m.add(f);
        return m;
    }




    @Override
    public void onItemClick(View view, int position) {
        if (mAdapter != null) {

        }
    }


}
