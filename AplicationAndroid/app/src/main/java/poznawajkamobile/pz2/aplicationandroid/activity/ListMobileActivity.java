package poznawajkamobile.pz2.aplicationandroid.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;

import poznawajkamobile.pz2.aplicationandroid.adapter.MobileArrayAdapter;

public class ListMobileActivity extends ListActivity {

	static final String[] NAME = new String[] { "Adrian Nowak", "Jan Wiaderko",
			"Pawe? Mocho?", "Mamen Kalidow"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//setListAdapter(new ArrayAdapter<String>(this, R.layout.list_mobile,
		//		R.id.label, NAME));
		
		setListAdapter(new MobileArrayAdapter(this, NAME));
		

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		//get selected items
		String selectedValue = (String) getListAdapter().getItem(position);
		Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();

	}

}