package com.linhphan.smssample.ui.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.linhphan.androidboilerplate.ui.fragment.BaseFragment;
import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.smssample.R;
import com.linhphan.smssample.data.contentprovider.SentSmsProvider;
import com.linhphan.smssample.data.table.TblSentMessage;
import com.linhphan.smssample.ui.adapter.SmsLogsAdapter;

/**
 * Created by linh on 5/20/2016.
 */
public class SuccessfullySentSmsFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private SmsLogsAdapter mAdapter;

    //============ inherited methods ===============================================================
    @Override
    protected int getFragmentLayoutResource() {
        return R.layout.recycler_view;
    }

    @Override
    protected void init() {
        getLoaderManager().initLoader(1, null, this);
        mAdapter = new SmsLogsAdapter(getContext(), null);
    }

    @Override
    protected void getWidgets(View root, Bundle savedInstanceState) {
        RecyclerView rlListMessage = (RecyclerView) root.findViewById(R.id.recyclerView);

        rlListMessage.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlListMessage.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void registerEventHandler() {

    }

    //============ implemented methods =============================================================
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = TblSentMessage.COLUMN_STATUS + "=? OR " + TblSentMessage.COLUMN_STATUS + "=?";
        String[] selectionArgs = {
                String.valueOf(TblSentMessage.Status.Delivered.ordinal()),
                String.valueOf(TblSentMessage.Status.Sent.ordinal())
        };
        Logger.d("**status**", String.valueOf(TblSentMessage.Status.Pending.ordinal()));
        String sortOrder = TblSentMessage.COLUMN_DUE + " ASC";
        return new CursorLoader(getContext(), SentSmsProvider.CONTENT_URI, null, selection, selectionArgs, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Toast.makeText(getContext(), "sent messages "+ data.getCount(), Toast.LENGTH_SHORT).show();
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    //========== inner classes =====================================================================
}
