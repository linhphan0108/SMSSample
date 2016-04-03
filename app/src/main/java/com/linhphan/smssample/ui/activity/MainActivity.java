package com.linhphan.smssample.ui.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;

import com.linhphan.androidboilerplate.ui.activity.BaseActivity;
import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.smssample.R;
import com.linhphan.smssample.data.contentprovider.CategoriesProvider;
import com.linhphan.smssample.data.table.TblCategory;
import com.linhphan.smssample.ui.fragment.ListMessage;

/**
 * Created by linh on 02/04/2016.
 */
public class MainActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        openListMessage();
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
            while (data.moveToNext()){
                id = data.getInt(idIndex);
                name = data.getString(nameIndex);

                Logger.i(getClass().getName(), "{id: "+ id +", name: "+ name +"}");
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    //================ inner methods ===============================================================
    private void openListMessage(){
        replaceFragment(R.id.fl_main_content, ListMessage.class, false, null, null);
    }
}
