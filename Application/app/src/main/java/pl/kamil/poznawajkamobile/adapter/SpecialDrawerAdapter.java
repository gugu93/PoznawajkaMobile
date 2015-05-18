package pl.kamil.poznawajkamobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kml.poznawajkamobile.R;
import com.joanzapata.android.iconify.Iconify;

import pl.kamil.poznawajkamobile.utils.GraphicUtils;

/**
 * Created by Kml on 2015-05-18.
 */
public class SpecialDrawerAdapter extends BaseAdapter {

    protected Context mContext;

    public SpecialDrawerAdapter(Activity activity) {
        mContext = activity.getApplicationContext();
    }

    @Override
    public int getCount() {
        return SpecialDrawerItem.values().length;
    }

    @Override
    public Object getItem(int position) {
        return SpecialDrawerItem.getByOrdinal(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.special_drawer_item, parent, false);
        }
        SpecialDrawerItem item = (SpecialDrawerItem) getItem(position);
        TextView title = (TextView) convertView.findViewById(R.id.special_drawer_item_title);
        ImageView icon = (ImageView) convertView.findViewById(R.id.special_drawer_item_icon);
        title.setText(item.getTitle());
        icon.setImageDrawable(
                GraphicUtils.generateStateList(mContext, item.getIcon(), android.R.color.white, android.R.color.black));
        return convertView;
    }

    public void goTo(Class<?> cls) {
        Intent i = new Intent(mContext, cls);
        mContext.startActivity(i);
    }

    public enum SpecialDrawerItem {
     SETTINGS(R.string.drawer_settings, Iconify.IconValue.fa_sliders);

        public int title;
        public Iconify.IconValue icon;
        public Class<?> cls;
        public static Intent intent = new Intent(Intent.ACTION_SEND);


        SpecialDrawerItem(int title, Iconify.IconValue icon, Class<?> cls) {
            this.title = title;
            this.icon = icon;
            this.cls = cls;
        }

        SpecialDrawerItem(int title, Iconify.IconValue icon){
            this.title = title;
            this.icon = icon;
        }

        public int getTitle() {
            return title;
        }

        public Iconify.IconValue getIcon() {
            return icon;
        }

//        public Class<?> getAction() {
//            if (this.equals(SETTINGS)) {
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
//                    return SettingsCompatActivity.class;
//                }
//            }
//            return cls;
//        }

        public static SpecialDrawerItem getByOrdinal(int position) {
            return values()[position];
        }
    }

}