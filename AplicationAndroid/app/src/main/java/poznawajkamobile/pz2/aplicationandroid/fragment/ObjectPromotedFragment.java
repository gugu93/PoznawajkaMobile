package poznawajkamobile.pz2.aplicationandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kml.poznawajkamobile.R;

import java.util.ArrayList;

import poznawajkamobile.pz2.aplicationandroid.activity.AbstractActivity;
import poznawajkamobile.pz2.aplicationandroid.models.ProposedPersonModel;
import poznawajkamobile.pz2.aplicationandroid.utils.Constant;
import poznawajkamobile.pz2.aplicationandroid.views.DotPagerIndicator;


public class ObjectPromotedFragment extends AbstractFragment implements ViewPager.OnPageChangeListener {

    public static final String FRAGMENT_TAG = ObjectPromotedFragment.class.getSimpleName();
    private ViewPager mPager;
    private DotPagerIndicator mIndicator;

    public static ObjectPromotedFragment newInstance(Intent intent) {
        ObjectPromotedFragment fragment = new ObjectPromotedFragment();
        fragment.setArguments(AbstractActivity.intentToFragmentArguments(intent));
        fragment.setHasOptionsMenu(true);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.promoted, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPager = (ViewPager) view.findViewById(R.id.promoted_pager);
        mPager.setOnPageChangeListener(this);
        mIndicator = (DotPagerIndicator) view.findViewById(R.id.promoted_indicator);
    }

    public ArrayList<ProposedPersonModel> createFakeData() {
        ArrayList<ProposedPersonModel> list = new ArrayList<ProposedPersonModel>();
        list.add(new ProposedPersonModel(1, 20, "Kamila", "Gonet", "image1",50.9f,19f));
        list.add(new ProposedPersonModel(2, 24, "Anna", "Wlazlo", "image2",19f,50f));
        list.add(new ProposedPersonModel(3, 18, "Aga", "Sura", "image3",40f,40f));
        list.add(new ProposedPersonModel(4, 19, "Iza", "Gonet", "image4",30f,40f));
        list.add(new ProposedPersonModel(5, 22, "Ola", "Jemiolo", "image5",44f,30f));
        return list;
    }

    public void setData(ArrayList<ProposedPersonModel> list){
        if(list!=null) {
            mIndicator.setDotSpacing(10);
            mIndicator.setDotsAmount(list.size());
            mPager.setAdapter(new PromotedPagerAdapter(getFragmentManager(), list));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mIndicator.refresh();
                }
            }, 100);
        }else {
            mIndicator.setDotSpacing(10);
            mIndicator.setActiveDot(0);
            ArrayList<ProposedPersonModel> mList = createFakeData();
            mIndicator.setDotsAmount(mList.size());
            mPager.setAdapter(new PromotedPagerAdapter(getFragmentManager(), mList));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mIndicator.refresh();
                }
            }, 100);
        }
    }


    @Override
    public void onPageScrolled(int i, float v, int i2) {
    }

    @Override
    public void onPageSelected(int i) {
        mIndicator.setActiveDot(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }


    private class PromotedPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<ProposedPersonModel> mList;
        public PromotedPagerAdapter(FragmentManager fm, ArrayList<ProposedPersonModel> list) {
            super(fm);
            mList = list;
        }

        @Override
        public Fragment getItem(int position) {
            ProposedPersonModel model = mList.get(position);
            Intent intent = getActivity().getIntent();
            intent.putExtra(Constant.EXTRA_ID, model.getId());
            intent.putExtra(Constant.EXTRA_AGE, model.getAge());
            intent.putExtra(Constant.EXTRA_NAME, model.getName());
            intent.putExtra(Constant.EXTRA_SURNAME, model.getSurname());
            intent.putExtra(Constant.EXTRA_PHOTO, model.getIcon());
            intent.putExtra(Constant.EXTRA_GPSX, model.getGps_x());
            intent.putExtra(Constant.EXTRA_GPSY, model.getGps_y());
            return PromotedItemFragment.newInstance(intent);
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}
