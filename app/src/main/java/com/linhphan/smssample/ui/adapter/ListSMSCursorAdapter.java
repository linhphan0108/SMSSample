package com.linhphan.smssample.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.smssample.R;
import com.linhphan.smssample.data.model.MessageModel;
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
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_message, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Logger.d(getClass().getName(), "bind view");
        TextView txtContent = (TextView) view.findViewById(R.id.txt_message_content);
        Button btnSend = (Button) view.findViewById(R.id.btn_send);
        Button btnCopy = (Button) view.findViewById(R.id.btn_copy);
        Button btnShare = (Button) view.findViewById(R.id.btn_share);
        Button btnSchedule = (Button) view.findViewById(R.id.btn_schedule);

        int idIndex = cursor.getColumnIndex(TblMessage.COLUMN_ID);
        int contentIndex = cursor.getColumnIndex(TblMessage.COLUMN_CONTENT);
        int id = cursor.getInt(idIndex);
        String content = cursor.getString(contentIndex);
        final MessageModel model = new MessageModel();
        model.setCatId(id);
        model.setContent(content);

        txtContent.setText(content);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onSendButtonClicked(model);
            }
        });
        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onCopyButtonClicked(model);
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onShareButtonClicked(model);
            }
        });
        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onScheduleButtonClicked(model);
            }
        });
    }

    //============== inner interfaces ==============================================================
    public interface ClickCallback{
        void onSendButtonClicked(MessageModel message);
        void onCopyButtonClicked(MessageModel message);
        void onShareButtonClicked(MessageModel message);
        void onScheduleButtonClicked(MessageModel message);
    }
}
