package com.linhphan.smssample.ui.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ListView;

import com.linhphan.androidboilerplate.ui.fragment.BaseFragment;
import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.smssample.R;
import com.linhphan.smssample.data.contentprovider.SMSProvider;
import com.linhphan.smssample.data.table.TblMessage;

/**
 * Created by linh on 02/04/2016.
 */
public class ListMessage extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private ListView mLvListMessage;

    private SimpleCursorAdapter mAdapter;

    //================= overridden methods =========================================================
    @Override
    protected int getFragmentLayoutResource() {
        return R.layout.fragment_list_message;
    }

    @Override
    protected void init() {
        //fields from database
        String[] from = {
                TblMessage.COLUMN_CONTENT
        };
        //fields from ui to which we map
        int[] to = {
            R.id.txt_message_content
        };
        getLoaderManager().initLoader(0, null, this);
        mAdapter = new SimpleCursorAdapter(getContext(), R.layout.item_row_message, null, from, to, 0);
    }

    @Override
    protected void getWidgets(View root, Bundle savedInstanceState) {
        mLvListMessage = (ListView) root.findViewById(R.id.lv_list_message);

        mLvListMessage.setAdapter(mAdapter);
    }

    @Override
    protected void registerEventHandler() {

    }

    //================= implemented methods ========================================================
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Logger.d(getClass().getName(), "onCreateLoader");
        String projection[] = {
                TblMessage.COLUMN_ID,
                TblMessage.COLUMN_CONTENT
        };
        return new CursorLoader(getContext(), SMSProvider.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        Logger.d(getClass().getName(), "onLoadFinished");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // data is not available anymore, delete reference
        mAdapter.swapCursor(null);
        Logger.d(getClass().getName(), "onLoaderReset");
    }

    //================= inner methods ==============================================================
}
