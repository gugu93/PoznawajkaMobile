package poznawajkamobile.pz2.aplicationandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kml.poznawajkamobile.R;

import java.util.ArrayList;

import poznawajkamobile.pz2.aplicationandroid.models.FriendItemModel;
import poznawajkamobile.pz2.aplicationandroid.utils.Utils;


public class MobileArrayAdapter extends ArrayAdapter<FriendItemModel> {
	private TextView mFriendName;
	private ImageView mFriendPhoto;
	private TextView mFriendSurname;
	private TextView mFriendDistanceTO;
	private TextView mFriendAge;
	private final Context context;
	private final ArrayList<FriendItemModel> values;

	public MobileArrayAdapter(Context context, ArrayList<FriendItemModel> list) {
		super(context, R.layout.list_mobile,list);
		this.context = context;
		this.values = list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.friend_item, parent, false);
		mFriendName = (TextView) rowView.findViewById(R.id.list_friend_imie);
		mFriendAge = (TextView)  rowView.findViewById(R.id.list_friend_wiek);
		mFriendPhoto = (ImageView) rowView.findViewById(R.id.list_friend_image);
		mFriendSurname = (TextView) rowView.findViewById(R.id.list_friend_nazwisko);
		mFriendDistanceTO = (TextView) rowView.findViewById(R.id.list_friend_distance);
		mFriendDistanceTO.setVisibility(View.INVISIBLE);

		FriendItemModel Item = values.get(position);

		mFriendName.setText(Item.getName());
		mFriendAge.setText("Wiek " + Item.getAge().toString());
		switch (Item.getId()){
			case 1:
				Utils.getPicasso(getContext())
						.load(R.drawable.ona)
						.error(R.color.white)
						.resizeDimen(R.dimen.hundred_forty_dips, R.dimen.hundred_forty_dips)
						.into(mFriendPhoto);
				break;
			case 2:
				Utils.getPicasso(getContext())
						.load(R.drawable.sample1)
						.error(R.color.white)
						.resizeDimen(R.dimen.hundred_forty_dips, R.dimen.hundred_forty_dips)
						.into(mFriendPhoto);
				break;
			case 3:
				Utils.getPicasso(getContext())
						.load(R.drawable.sample2)
						.error(R.color.white)
						.resizeDimen(R.dimen.hundred_forty_dips, R.dimen.hundred_forty_dips)
						.into(mFriendPhoto);
				break;
			case 4:
				Utils.getPicasso(getContext())
						.load(R.drawable.sample3)
						.error(R.color.white)
						.resizeDimen(R.dimen.hundred_forty_dips, R.dimen.hundred_forty_dips)
						.into(mFriendPhoto);
				break;
			case 5:
				Utils.getPicasso(getContext())
						.load(R.drawable.sample4)
						.error(R.color.white)
						.resizeDimen(R.dimen.hundred_forty_dips, R.dimen.hundred_forty_dips)
						.into(mFriendPhoto);
				break;
			default:
				Utils.getPicasso(getContext())
						.load(R.drawable.sample4)
						.error(R.color.white)
						.resizeDimen(R.dimen.hundred_forty_dips, R.dimen.hundred_forty_dips)
						.into(mFriendPhoto);
				break;
		}		mFriendSurname.setText(Item.getSurname());
		return rowView;
	}
}
