package pl.kamil.poznawajkamobile.activity;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.commonsware.cwac.merge.MergeAdapter;
import com.example.kml.poznawajkamobile.R;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

import pl.kamil.poznawajkamobile.adapter.SpecialDrawerAdapter;
import pl.kamil.poznawajkamobile.utils.Constant;

public class AbstractMenuActivity extends AbstractActivity {

    protected MenuDrawer mMenuDrawer;
    protected MergeAdapter mMenuAdapter;
    protected ListView mMenuList;
    private int mActivePosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mActivePosition = savedInstanceState.getInt(Constant.STATE_ACTIVE_POSITION);
        }
        if (!(this instanceof MainActivity)) {
            getSupportActionBar();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.Type.OVERLAY, Position.LEFT);
        mMenuDrawer.setMenuView(R.layout.drawer);
        mMenuDrawer.setDropShadowEnabled(true);
        mMenuDrawer.setDropShadowColor(getResources().getColor(R.color.shadow));
        mMenuDrawer.setDropShadowSize(mMenuDrawer.getContentContainer().getWidth());
        mMenuList = (ListView) mMenuDrawer.getMenuView().findViewById(R.id.drawer_navigation_list);
        mMenuAdapter = new MergeAdapter();
        mMenuAdapter.addAdapter(new SpecialDrawerAdapter(this));
        mMenuList.setAdapter(mMenuAdapter);
        mMenuList.setOnItemClickListener(mItemClickListener);
        mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_NONE);
    }

    @Override
    public void setContentView(int layoutResID) {
        mMenuDrawer.setContentView(layoutResID);
    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mActivePosition = position;
            mMenuDrawer.setActiveView(view, position);
            mMenuDrawer.closeMenu();
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constant.STATE_ACTIVE_POSITION, mActivePosition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem item = menu.add(R.id.menu_group, R.id.menu_drawer, 1000, R.string.navigation_drawer_open);
        item.setIcon(R.drawable.ic_menu_drawer);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_drawer:
                mMenuDrawer.toggleMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        final int drawerState = mMenuDrawer.getDrawerState();
        if (drawerState == MenuDrawer.STATE_OPEN || drawerState == MenuDrawer.STATE_OPENING) {
            mMenuDrawer.closeMenu();
            return;
        }

        super.onBackPressed();
    }
}

