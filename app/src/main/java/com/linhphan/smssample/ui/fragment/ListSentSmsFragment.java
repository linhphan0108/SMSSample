package com.linhphan.smssample.ui.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.linhphan.androidboilerplate.ui.fragment.BaseFragment;
import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.smssample.R;
import com.linhphan.smssample.data.contentprovider.SentSmsProvider;
import com.linhphan.smssample.data.table.TblMessage;
import com.linhphan.smssample.data.table.TblSentMessage;

/**
 * Created by linh on 5/3/2016.
 */
public class ListSentSmsFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private ListSmsAdapter mAdapter;

    //============ inherited methods ===============================================================
    @Override
    protected int getFragmentLayoutResource() {
        return R.layout.fragment_list_sms;
    }

    @Override
    protected void init() {
        getLoaderManager().initLoader(0, null, this);
        mAdapter = new ListSmsAdapter(getContext(), null, true);
    }

    @Override
    protected void getWidgets(View root, Bundle savedInstanceState) {
        ListView lvListMessage = (ListView) root.findViewById(R.id.lv_list_message);

        lvListMessage.setAdapter(mAdapter);
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
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    //========== inner classes =====================================================================
    class ListSmsAdapter extends CursorAdapter {

        public ListSmsAdapter(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
        }

        public ListSmsAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.item_sent_sms_list, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView txtName = (TextView) view.findViewById(R.id.txt_destination_name);
            TextView txtPhone = (TextView) view.findViewById(R.id.txt_phone_number);
            TextView txtContent = (TextView) view.findViewById(R.id.txt_content);
            TextView txtStatus = (TextView) view.findViewById(R.id.txt_status);

            int smsIdIndex = cursor.getColumnIndex(TblSentMessage.COLUMN_SMS_ID);
            int phoneIndex = cursor.getColumnIndex(TblSentMessage.COLUMN_PHONE);
            int nameIndex = cursor.getColumnIndex(TblSentMessage.COLUMN_NAME);
            int contentIndex = cursor.getColumnIndex(TblMessage.COLUMN_CONTENT);
            int statusIndex = cursor.getColumnIndex(TblSentMessage.COLUMN_STATUS);

            int id = cursor.getInt(smsIdIndex);
            String phone = cursor.getString(phoneIndex);
            String name = cursor.getString(nameIndex);
            String content = cursor.getString(contentIndex);
            int status = cursor.getInt(statusIndex);

            txtContent.setText(content);
            txtName.setText(name);
            txtPhone.setText(phone);
            txtStatus.setText(String.valueOf(status));
        }
    }
}
