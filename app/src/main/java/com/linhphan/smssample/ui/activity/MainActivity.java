package com.linhphan.smssample.ui.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.linhphan.androidboilerplate.ui.activity.BaseActivity;
import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.smssample.R;
import com.linhphan.smssample.data.contentprovider.CategoriesProvider;
import com.linhphan.smssample.data.table.TblCategory;
import com.linhphan.smssample.ui.fragment.ListSentSmsFragment;
import com.linhphan.smssample.ui.fragment.ListSmsFragment;

/**
 * Created by linh on 02/04/2016.
 */
public class MainActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor>, NavigationView.OnNavigationItemSelectedListener {

    private final int SENT_MESSAGE_CAT_ID = 9898;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    //=============== inherited methods ============================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupToolbar();//setup toolbar
        setupNavigationView();
        openListMessage(0);
    }

    @Override
    protected int getActivityLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        getSupportLoaderManager().initLoader(1, null, this);
    }

    @Override
    protected void getWidgets(Bundle savedInstanceState) {
    }

    @Override
    protected void registerEventHandler() {

    }

    //================ implemented methods =========================================================
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Logger.d(getClass().getName(), "onCreateLoader");
        String projection[] = {
                TblCategory.COLUMN_ID,
                TblCategory.COLUMN_NAME
        };
        return new CursorLoader(this, CategoriesProvider.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Logger.d(getClass().getName(), "onLoadFinished");
        if (data != null) {
            Logger.e(getClass().getName(), "total number of rows in category table: " + data.getCount());
            data.moveToFirst();
            int idIndex = data.getColumnIndex(TblCategory.COLUMN_ID);
            int nameIndex = data.getColumnIndex(TblCategory.COLUMN_NAME);

            int id = data.getInt(idIndex);
            String name = data.getString(nameIndex);
            Logger.i(getClass().getName(), "{id: "+ id +", name: "+ name +"}");

            Menu menu = navigationView.getMenu();
            // add menu items
//            menu.add(0, id, Menu.NONE, name);
//            for (int i=0; i< 4; i++){
//                // add submenu item
//                SubMenu subMenu = menu.addSubMenu(i, i, i, "Menu "+ i);
//                for (int j=0; j<3 ; j++){
//                    subMenu.add(i, j, j, "submenu "+ j);
//                }
//            }

            while (data.moveToNext()){
                id = data.getInt(idIndex);
                name = data.getString(nameIndex);
                Logger.i(getClass().getName(), "{id: "+ id +", name: "+ name +"}");

                menu.add(0, id, Menu.NONE, name);
            }

            //sent message category
            menu.add(0, SENT_MESSAGE_CAT_ID, Menu.NONE, getString(R.string.sent_message));

//            for (int i = 0, count = navigationView.getChildCount(); i < count; i++) {
//                final View child = navigationView.getChildAt(i);
//                if (child != null && child instanceof ListView) {
//                    final ListView menuView = (ListView) child;
//                    final HeaderViewListAdapter adapter = (HeaderViewListAdapter) menuView.getAdapter();
//                    final BaseAdapter wrapped = (BaseAdapter) adapter.getWrappedAdapter();
//                    wrapped.notifyDataSetChanged();
//                }
//            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        drawerLayout.closeDrawers();
        switch (menuItem.getGroupId()){
            case 0:
                if (menuItem.getItemId() == SENT_MESSAGE_CAT_ID){
                    openListSentMessage();
                }else {
                    openListMessage(menuItem.getItemId());
                }
                break;
            default:
                break;
        }
        return false;
    }

    //================ inner methods ===============================================================
    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //== set the item is selected
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void openListMessage(int catId){
        Bundle bundle = new Bundle();
        bundle.putInt(ListSmsFragment.ARG_CATEGORY_ID, catId);
        replaceFragment(R.id.fl_main_content, ListSmsFragment.class, false, bundle, null);
    }

    private void openListSentMessage(){
        replaceFragment(R.id.fl_main_content, ListSentSmsFragment.class, false, null, null);
    }
}
