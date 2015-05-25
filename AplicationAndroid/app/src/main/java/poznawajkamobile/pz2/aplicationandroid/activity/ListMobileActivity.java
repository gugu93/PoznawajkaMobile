package poznawajkamobile.pz2.aplicationandroid.activity;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.ImageView;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;

import com.example.kml.poznawajkamobile.R;

import java.util.ArrayList;

import poznawajkamobile.pz2.aplicationandroid.adapter.MobileArrayAdapter;
import poznawajkamobile.pz2.aplicationandroid.models.FriendItemModel;
import poznawajkamobile.pz2.aplicationandroid.models.ProposedPersonModel;
import poznawajkamobile.pz2.aplicationandroid.utils.Preferences;
import poznawajkamobile.pz2.aplicationandroid.utils.services.GalerryService;
import poznawajkamobile.pz2.aplicationandroid.utils.services.ListFriendsService;

public class ListMobileActivity extends ListActivity {
	private ListFriendsService friendsService;
	private Boolean mBoundFriends = false;
	private ServiceConnection mConnectionList = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			ListFriendsService.ListFriendsBinder binder = (ListFriendsService.ListFriendsBinder) service;
			friendsService = binder.getService();
			mBoundFriends = true;
			Preferences p = new Preferences(getApplicationContext());
			friendsService.start(p.getString("login"));
			initListFriends(friendsService.list);

		}
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			mBoundFriends = false;
		}
	};


	public void initListFriends(ArrayList<FriendItemModel> list){
		if (list !=null){

		}else{
			setListAdapter(new MobileArrayAdapter(this,createFakeData()));
		}
	}

	public ArrayList<FriendItemModel> createFakeData() {
		ArrayList<FriendItemModel> list = new ArrayList<FriendItemModel>();
		list.add(new FriendItemModel(1, 20, "Kamila", "Gonet", "image1",50.9f,19f));
		list.add(new FriendItemModel(2, 24, "Anna", "Wlazlo", "image2",19f,50f));
		list.add(new FriendItemModel(3, 18, "Aga", "Sura", "image3",40f,40f));
		list.add(new FriendItemModel(4, 19, "Iza", "Gonet", "image4",30f,40f));
		list.add(new FriendItemModel(5, 22, "Ola", "Jemiolo", "image5",44f,30f));
		list.add(new FriendItemModel(1, 20, "Kamila", "Gonet", "image1",50.9f,19f));
		list.add(new FriendItemModel(2, 24, "Anna", "Wlazlo", "image2",19f,50f));
		list.add(new FriendItemModel(3, 18, "Aga", "Sura", "image3",40f,40f));
		list.add(new FriendItemModel(4, 19, "Iza", "Gonet", "image4",30f,40f));
		list.add(new FriendItemModel(5, 22, "Ola", "Jemiolo", "image5",44f,30f));
		list.add(new FriendItemModel(1, 20, "Kamila", "Gonet", "image1",50.9f,19f));
		list.add(new FriendItemModel(2, 24, "Anna", "Wlazlo", "image2",19f,50f));
		list.add(new FriendItemModel(3, 18, "Aga", "Sura", "image3",40f,40f));
		list.add(new FriendItemModel(4, 19, "Iza", "Gonet", "image4",30f,40f));
		list.add(new FriendItemModel(5, 22, "Ola", "Jemiolo", "image5",44f,30f));
		return list;
	}

	@Override
	public void onStop() {
		super.onStop();
		if (mBoundFriends) {
			unbindService(mConnectionList);
			mBoundFriends = false;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intentFriendList = new Intent(this, ListFriendsService.class);
		bindService(intentFriendList, mConnectionList, Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String selectedValue = (String) getListAdapter().getItem(position);

	}

}