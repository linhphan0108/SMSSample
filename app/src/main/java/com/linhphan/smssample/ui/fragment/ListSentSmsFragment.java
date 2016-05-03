package com.linhphan.smssample.ui.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.Toast;

import com.linhphan.androidboilerplate.ui.fragment.BaseFragment;
import com.linhphan.smssample.R;
import com.linhphan.smssample.data.contentprovider.SentSmsProvider;

/**
 * Created by linh on 5/3/2016.
 */
public class ListSentSmsFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    //============ inherited methods ===============================================================
    @Override
    protected int getFragmentLayoutResource() {
        return R.layout.fragment_list_sent_sms;
    }

    @Override
    protected void init() {
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void getWidgets(View root, Bundle savedInstanceState) {

    }

    @Override
    protected void registerEventHandler() {

    }

    //============ implemented methods =============================================================
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), SentSmsProvider.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Toast.makeText(getContext(), "sent messages "+ data.getCount(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
