package poznawajkamobile.pz2.aplicationandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kml.poznawajkamobile.R;
import com.joanzapata.android.iconify.Iconify;

import poznawajkamobile.pz2.aplicationandroid.utils.GraphicUtils;

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


    public enum SpecialDrawerItem {
     SETTINGS("ZACZEPKI", Iconify.IconValue.fa_bullseye),
     BUG("ZGLOS BUGA", Iconify.IconValue.fa_bug),
     FB("INTEGRACJA Z FACEBOOKIEM", Iconify.IconValue.fa_facebook_square);

        public String title;
        public Iconify.IconValue icon;
        public static Intent intent = new Intent(Intent.ACTION_SEND);


        SpecialDrawerItem(String title, Iconify.IconValue icon) {
            this.title = title;
            this.icon = icon;
        }


        public String getTitle() {
            return title;
        }

        public Iconify.IconValue getIcon() {
            return icon;
        }


        public static SpecialDrawerItem getByOrdinal(int position) {
            return values()[position];
        }
    }

}