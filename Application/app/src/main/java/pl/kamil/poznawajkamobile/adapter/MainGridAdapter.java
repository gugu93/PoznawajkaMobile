package pl.kamil.poznawajkamobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kml.poznawajkamobile.R;

import java.util.ArrayList;

import pl.kamil.poznawajkamobile.models.MenuModel;

public class MainGridAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<MenuModel> mList;

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
            textView.setText(menuItem.getName());
            ImageView icon = (ImageView) view.findViewById(R.id.main_grid_background);
            switch (i){
                case 0:
                    //icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon1));
                    //TODO ZDJECIE PROFILOWE
                    break;
                case 1:
                    icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon02));
                    break;
                case 2:
                    icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon03));
                    break;
                case 3:
                    icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon04));
                    break;
                case 4:
                    icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon05));
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
