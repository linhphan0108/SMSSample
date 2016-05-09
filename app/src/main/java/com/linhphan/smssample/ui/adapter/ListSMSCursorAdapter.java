package com.linhphan.smssample.ui.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.androidboilerplate.util.ViewUtil;
import com.linhphan.smssample.R;
import com.linhphan.smssample.data.contentprovider.SmsProvider;
import com.linhphan.smssample.data.model.SmsModel;
import com.linhphan.smssample.data.table.TblMessage;

/**
 * Created by linh on 03/04/2016.
 */
public class ListSMSCursorAdapter extends CursorAdapter{
    private ClickCallback mCallback;

    public ListSMSCursorAdapter(Context context, Cursor c, boolean autoRequery, ClickCallback callback) {
        super(context, c, autoRequery);
        mCallback = callback;
    }

    public ListSMSCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Logger.d(getClass().getName(), "new view");
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_sms, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Logger.d(getClass().getName(), "bind view");
        TextView txtContent = (TextView) view.findViewById(R.id.txt_message_content);
        ImageButton btnSend = (ImageButton) view.findViewById(R.id.btn_send);
        ImageButton btnCopy = (ImageButton) view.findViewById(R.id.btn_copy);
        ImageButton btnShare = (ImageButton) view.findViewById(R.id.btn_share);
        ImageButton btnSchedule = (ImageButton) view.findViewById(R.id.btn_schedule);
        ImageView imgStar = (ImageView) view.findViewById(R.id.img_star);

        //== get index
        int idIndex = cursor.getColumnIndex(TblMessage.COLUMN_ID);
        int contentIndex = cursor.getColumnIndex(TblMessage.COLUMN_CONTENT);
        int starIndex = cursor.getColumnIndex(TblMessage.COLUMN_STARED);

        //== get data
        final int id = cursor.getInt(idIndex);
        String content = cursor.getString(contentIndex);
        boolean stared = cursor.getInt(starIndex) > 0;

        //== create the message model
        final SmsModel model = new SmsModel();
        model.setId(id);
        model.setContent(content);

        txtContent.setText(content);
        imgStar.setSelected(stared);

        //== set click event handlers
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtil.lockViewTemporary(v);
                mCallback.onSendButtonClicked(model);
            }
        });
        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtil.lockViewTemporary(v);
                mCallback.onCopyButtonClicked(model);
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtil.lockViewTemporary(v);
                mCallback.onShareButtonClicked(model);
            }
        });
        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtil.lockViewTemporary(v);
                mCallback.onScheduleButtonClicked(model);
            }
        });

        imgStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtil.lockViewTemporary(v);
                boolean isSelected = !v.isSelected();
                v.setSelected(isSelected);

                Uri uri = Uri.withAppendedPath(SmsProvider.CONTENT_URI, String.valueOf(id));
                ContentValues values = new ContentValues();
                values.put(TblMessage.COLUMN_STARED, isSelected);
                int changedRows = mContext.getContentResolver().update(uri, values, null, null);

                Logger.e(getClass().getName(), "selected "+ String.valueOf(isSelected));
                Logger.e(getClass().getName(), "changed rows: "+ String.valueOf(changedRows));
            }
        });
    }

    //============== inner interfaces ==============================================================
    public interface ClickCallback{
        void onSendButtonClicked(SmsModel message);
        void onCopyButtonClicked(SmsModel message);
        void onShareButtonClicked(SmsModel message);
        void onScheduleButtonClicked(SmsModel message);
    }
}
