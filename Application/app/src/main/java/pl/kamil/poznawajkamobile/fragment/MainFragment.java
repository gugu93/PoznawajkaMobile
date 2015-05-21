package pl.kamil.poznawajkamobile.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.kml.poznawajkamobile.R;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;

import pl.kamil.poznawajkamobile.activity.AbstractActivity;
import pl.kamil.poznawajkamobile.activity.MainActivity;
import pl.kamil.poznawajkamobile.adapter.MainGridAdapter;
import pl.kamil.poznawajkamobile.models.MenuModel;
import pl.kamil.poznawajkamobile.utils.GraphicUtils;
import pl.kamil.poznawajkamobile.utils.Utils;
import pl.kamil.poznawajkamobile.views.MainGrid;


public class MainFragment extends AbstractFragment implements  MainGrid.OnMainGridItemClickListener {

    public static final String FRAGMENT_TAG = MainFragment.class.getSimpleName();
    private MainGrid mGrid;
    private FrameLayout proponowane;
    private MainGridAdapter mAdapter;
    private Fragment promoted;


//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what == Constant.LOADER_MAIN_GRID) {
//                getBaseActivity().goToAndClear(UpdateActivity.class);
//            }
//        }
//    };

    public static MainFragment newInstance(Intent intent) {
        MainFragment fragment = new MainFragment();
        fragment.setArguments(AbstractActivity.intentToFragmentArguments(intent));
        fragment.setRetainInstance(false);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mAdapter = new MainGridAdapter(this.getActivity().getApplicationContext(),m);
        mGrid.setAdapter(mAdapter);
    }

    public ArrayList<MenuModel> createData() {
        ArrayList<MenuModel> m = new ArrayList<MenuModel>();
        MenuModel a = new MenuModel(1, "TWOJ PROFIL");
        MenuModel b = new MenuModel(2, "SZUKAJ ZNAJOMYCH", "icon1.jpg");
        MenuModel c = new MenuModel(3, "ZNAJOMI", "icon2.jpg");
        MenuModel d = new MenuModel(4, "GALERIA", "icon3.jpg");
        MenuModel e = new MenuModel(5, "USTAWIENIA", "icon4.jpg");
        MenuModel f = new MenuModel(6, "O PROGRAMIE", "icon5.jpg");
        m.add(a);
        m.add(b);
        m.add(c);
        m.add(d);
        m.add(e);
        m.add(f);
        return m;
    }


//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        switch (loader.getId()) {
//            case Constant.LOADER_MAIN_GRID:
//                if (data != null && data.getCount() > 0) {
//                    mAdapter = new MainGridAdapter(getActivity(), data);
//                    mGrid.setAdapter(mAdapter);
//                } else {
//                    firstTimeLaunched=true;
//                    handler.sendEmptyMessage(Constant.LOADER_MAIN_GRID);
//                }
//                if (!firstTimeLaunched)startLoader(Constant.LOADER_LAST_NEWS);
//                break;
//            case Constant.LOADER_LAST_NEWS:
//                if (data != null && data.getCount() > 0) {
//                    while (data.moveToNext()) {
//                        RssModel rss = new RssModel(data);
//                        if (!Strings.isNullOrEmpty(rss.getImage())) {
//                            mGrid.updateItemBackground(Constant.NEWS_ID, rss.getImage());
//                            break;
//                        }
//                    }
//                }
//                startLoader(Constant.LOADER_LAST_EVENT);
//                break;
//            case Constant.LOADER_LAST_EVENT:
//                if (data != null && data.getCount() > 0) {
//                    while (data.moveToNext()) {
//                        CalendarEventModel event = new CalendarEventModel(data);
//                        if (!Strings.isNullOrEmpty(event.getImage())) {
//                            mGrid.updateItemBackground(Constant.EVENTS_ID, event.getImage());
//                            break;
//                        }
//                    }
//                }
//                startLoader(Constant.LOADER_LAST_PLANNER);
//                break;
//            case Constant.LOADER_LAST_PLANNER:
//                if (data != null && data.getCount() > 0) {
//                    data.moveToFirst();
//                    plannerModel = new PlannerModel(data);
//                    switch (plannerModel.getObject_type())
//                    {
//                        case 1: //object
//                        startLoader(Constant.LOADER_ADDRESS);
//                        break;
//                        case 2: //route
//                        startLoader(Constant.LOADER_ROUTE);
//                        break;
//                        case 3: //event
//                        startLoader(Constant.LOADER_CALENDAR);
//                        break;
//                    }
//                }
//                else {
//                    mGrid.updateItemBackground(Constant.PLANNER_ID,Utils.getResource("icon9", "drawable", getActivity()));
//                }
//                break;
//            case Constant.LOADER_ADDRESS:
//
//                if (data != null && data.getCount() > 0) {
//                    data.moveToFirst();
//                    AddressModel addressModel = new AddressModel(data);
//                    String photoAddress = getPhotoUrl(addressModel);
//
//                    if (!Strings.isNullOrEmpty(photoAddress)) {
//                        mGrid.updateItemBackground(Constant.PLANNER_ID, photoAddress);
//                    }
//                }
//                break;
//            case Constant.LOADER_ROUTE:
//
//                if (data != null && data.getCount() > 0) {
//                    data.moveToFirst();
//                    RouteModel route = new RouteModel(data);
//
//                    if (!Strings.isNullOrEmpty(route.getLogo())) {
//                        Preferences prefs = new Preferences(getActivity());
//                        String filesDir = getActivity().getFilesDir().getAbsolutePath() + "/";
//                        filesDir += (prefs.getUpdateInfo() != null)
//                                ? prefs.getUpdateInfo().getVersion()
//                                : Constant.APP_VERSION;
//                        File image = new File(filesDir + "/" + route.getLogo());
//                        File imageWithSpace = new File(filesDir + "/" + route.getLogo() + " ");
//                        if (image.exists()) {
//                            mGrid.updateItemBackground(Constant.PLANNER_ID,image );
//                        }
//                        if (imageWithSpace.exists()) {
//                            mGrid.updateItemBackground(Constant.PLANNER_ID, imageWithSpace);
//                        }
//                    }
//                }
//                break;
//            case Constant.LOADER_CALENDAR:
//
//                if (data != null && data.getCount() > 0) {
//                    data.moveToFirst();
//                    CalendarEventModel calendarEventModel = new CalendarEventModel(data);
//                    if (!Strings.isNullOrEmpty(calendarEventModel.getImage())) {
//                        mGrid.updateItemBackground(Constant.PLANNER_ID, calendarEventModel.getImage());
//                    }
//                }
//                break;
//        }
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> cursorLoader) { }

    @Override
    public void onItemClick(View view, int position) {
        if (mAdapter != null) {
//            Cursor data = (Cursor) mAdapter.getItem(position);
//            MenuItemModel menuItem = new MenuItemModel(data);
//            new ModelIntentBuilder(getActivity(), menuItem)
//                .performAction();
        }
    }

//
//    public String getPhotoUrl(AddressModel address) {
//        String key = "mod_" + address.getModId() + "_id_" + address.getObjectId();
//        if (photos.containsKey(key)) {
//            ArrayList<String> list = photos.get(key);
//            if (list.size() > 0) {
//                return list.get(0);
//            }
//        }
//        return null;
//    }

}
