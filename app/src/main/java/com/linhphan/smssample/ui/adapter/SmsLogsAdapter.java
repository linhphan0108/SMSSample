package com.linhphan.smssample.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linhphan.androidboilerplate.util.DateUtil;
import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.smssample.R;
import com.linhphan.smssample.data.table.TblMessage;
import com.linhphan.smssample.data.table.TblSentMessage;
import com.squareup.picasso.Picasso;

/**
 * Created by linh on 5/21/2016.
 */
public class SmsLogsAdapter extends RecyclerView.Adapter<SmsLogsAdapter.RecyclerItemViewHolder>{

    Cursor mDataCursor;
    Context mContext;

    public Cursor swapCursor(Cursor cursor) {
        if (mDataCursor == cursor) {
            return null;
        }
        Cursor oldCursor = mDataCursor;
        this.mDataCursor = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }

    public SmsLogsAdapter(Context context, Cursor dataCursor) {
        this.mDataCursor = dataCursor;
        this.mContext = context;
    }

    @Override
    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sent_sms_list, parent, false);
        return new RecyclerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerItemViewHolder holder, int position) {

        mDataCursor.moveToPosition(position);

        int smsIdIndex = mDataCursor.getColumnIndex(TblSentMessage.COLUMN_SMS_ID);
        int phoneIndex = mDataCursor.getColumnIndex(TblSentMessage.COLUMN_PHONE);
        int nameIndex = mDataCursor.getColumnIndex(TblSentMessage.COLUMN_NAME);
        int contentIndex = mDataCursor.getColumnIndex(TblMessage.COLUMN_CONTENT);
        int statusIndex = mDataCursor.getColumnIndex(TblSentMessage.COLUMN_STATUS);
        int avatarIndex = mDataCursor.getColumnIndex(TblSentMessage.COLUMN_COVER);
        int dueIndex = mDataCursor.getColumnIndex(TblSentMessage.COLUMN_DUE);


        int id = mDataCursor.getInt(smsIdIndex);
        String phone = mDataCursor.getString(phoneIndex);
        String name = mDataCursor.getString(nameIndex);
        String content = mDataCursor.getString(contentIndex);
        int status = mDataCursor.getInt(statusIndex);
        String avatar = mDataCursor.getString(avatarIndex);
        if (dueIndex > -1) {
            int due = mDataCursor.getInt(dueIndex);
            holder.txtDue.setText(DateUtil.parseDateTime(due));
        }

        if (!TextUtils.isEmpty(avatar)) {
            Logger.d("**avatar**", avatar);
        }else{
            Picasso.with(mContext)
                    .load(R.drawable.ic_contact_picture)
                    .into(holder.imgContactAvatar);
        }

        holder.txtContent.setText(content);
        holder.txtName.setText(name);
        holder.txtPhone.setText(phone);
        holder.txtStatus.setText(TblSentMessage.getStatus(mContext, status));
    }

    @Override
    public int getItemCount() {
        return (mDataCursor == null) ? 0 : mDataCursor.getCount();
    }
    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtPhone;
        TextView txtContent;
        TextView txtStatus;
        TextView txtDue;
        ImageView imgContactAvatar;

        public RecyclerItemViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txt_destination_name);
            txtPhone = (TextView) itemView.findViewById(R.id.txt_phone_number);
            txtContent = (TextView) itemView.findViewById(R.id.txt_content);
            txtStatus = (TextView) itemView.findViewById(R.id.txt_status);
            txtDue = (TextView) itemView.findViewById(R.id.txt_due);
            imgContactAvatar = (ImageView) itemView.findViewById(R.id.img_contact_avatar);
        }
    }
}
