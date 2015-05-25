package poznawajkamobile.pz2.aplicationandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kml.poznawajkamobile.R;

import java.util.ArrayList;

import poznawajkamobile.pz2.aplicationandroid.activity.GalleryActivity;
import poznawajkamobile.pz2.aplicationandroid.activity.ListMobileActivity;
import poznawajkamobile.pz2.aplicationandroid.activity.MapActivity;
import poznawajkamobile.pz2.aplicationandroid.activity.MeetNewPeopleActivity;
import poznawajkamobile.pz2.aplicationandroid.activity.ProfileActivity;
import poznawajkamobile.pz2.aplicationandroid.models.MenuModel;

public class MainGridAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<MenuModel> mList;
    public ProgressBar avatar;

    public MainGridAdapter(Context context, ArrayList list) {
        mContext = context;
        mList    = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public MenuModel getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)  mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        MenuModel menuItem = mList.get(i);
        if (view == null) {
            view = inflater.inflate( R.layout.main_grid_item , null);
            TextView textView = (TextView) view.findViewById(R.id.main_grid_title);
            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.main_grid_progress);
            textView.setText(menuItem.getName());
            ImageView icon = (ImageView) view.findViewById(R.id.main_grid_background);
            switch (i){
                case 0:
                    //icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon1));
                    avatar = progressBar;
                    progressBar.setVisibility(View.VISIBLE);
                    //TODO ZDJECIE PROFILOWE
                    icon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent0 = new Intent(mContext.getApplicationContext(), ProfileActivity.class);
                            intent0.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent0);
                        }
                    });
                    break;
                case 1:
                    icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon02));
                    icon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent1 = new Intent(mContext.getApplicationContext(), MeetNewPeopleActivity.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent1);
                        }
                    });
                    break;
                case 2:
                    icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon03));
                    icon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent2 = new Intent(mContext.getApplicationContext(), ListMobileActivity.class);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent2);
                        }
                    });
                    break;
                case 3:
                    icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon04));
                    icon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent3 = new Intent(mContext.getApplicationContext(), GalleryActivity.class);
                            intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent3);
                        }
                    });
                    break;
                case 4:
                    icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon05));
                    icon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent4 = new Intent(mContext.getApplicationContext(), MapActivity.class);
                            intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent4);
                        }
                    });
                    break;
                case 5:
                    icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon06));
                    break;
                default:
                    icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon02));
                    break;
            }
        }
        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return  1;
    }

}
