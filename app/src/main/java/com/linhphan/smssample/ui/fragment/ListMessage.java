package com.linhphan.smssample.ui.fragment;

import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.linhphan.androidboilerplate.callback.ConfirmDialogCallback;
import com.linhphan.androidboilerplate.ui.dialog.ConfirmDialog;
import com.linhphan.androidboilerplate.ui.fragment.BaseFragment;
import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.androidboilerplate.util.TextUtil;
import com.linhphan.smssample.R;
import com.linhphan.smssample.data.contentprovider.SMSProvider;
import com.linhphan.smssample.data.model.MessageModel;
import com.linhphan.smssample.data.table.TblCategory;
import com.linhphan.smssample.data.table.TblMessage;
import com.linhphan.smssample.ui.adapter.ListSMSCursorAdapter;
import com.linhphan.smssample.util.Constant;

/**
 * Created by linh on 02/04/2016.
 */
public class ListMessage extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>, ListSMSCursorAdapter.ClickCallback{

    private ListView mLvListMessage;

    private ListSMSCursorAdapter mAdapter;

    //================= overridden methods =========================================================
    @Override
    protected int getFragmentLayoutResource() {
        return R.layout.fragment_list_message;
    }

    @Override
    protected void init() {
        getLoaderManager().initLoader(0, null, this);
        mAdapter = new ListSMSCursorAdapter(getContext(), null, true, this);
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
//        String selection = TblMessage.COLUMN_CAT_ID +"=?";
//        String selectionArg[] = {
//                String.valueOf(TblCategory.NIGHT_GREETING_GIRL_ID)
//        };
        return new CursorLoader(getContext(), SMSProvider.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        Logger.d(getClass().getName(), "onLoadFinished");
        if (data != null){
            Logger.e(getClass().getName(), "number of rows in table message: "+ data.getCount());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // data is not available anymore, delete reference
        mAdapter.swapCursor(null);
        Logger.d(getClass().getName(), "onLoaderReset");
    }

    //====== adapter callbacks
    @Override
    public void onSendButtonClicked(final MessageModel message) {
        Toast.makeText(getContext(), String.valueOf(message.getCatId()), Toast.LENGTH_SHORT).show();
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setMessage("do you want a message without vietnamese accent?");
        dialog.setNegativeButtonName("nope");
        dialog.setPositiveButtonName("ok");
        dialog.setCallback(new ConfirmDialogCallback() {
            @Override
            public void onPositiveButtonClicked() {
                String sms = TextUtil.removeAccent(message.getContent());
                openMessenger(sms);
            }

            @Override
            public void onNegativeButtonClicked() {
                openMessenger(message.getContent());
            }
        });
        dialog.show(getFragmentManager(), ConfirmDialog.class.getName());
    }

    @Override
    public void onCopyButtonClicked(MessageModel message) {
        Toast.makeText(getContext(), "copied", Toast.LENGTH_SHORT).show();
        ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Copied", message.getContent());
        manager.setPrimaryClip(clipData);
    }

    @Override
    public void onShareButtonClicked(MessageModel message) {
        Toast.makeText(getContext(), String.valueOf(message.getCatId()), Toast.LENGTH_SHORT).show();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message.getContent());
        startActivity(Intent.createChooser(sharingIntent, "Choose an App"));
    }

    @Override
    public void onScheduleButtonClicked(MessageModel message) {
        Toast.makeText(getContext(), String.valueOf(message.getCatId()), Toast.LENGTH_SHORT).show();
        sendMessageDirectly();
    }

    //================= inner methods ==============================================================
    private void openMessenger(String sms){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("sms_body", sms);
        intent.setType("vnd.android-dir/mms-sms");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivity(intent);
    }

    private void sendMessageDirectly(){
        String ninhPHoneNumber = "01632131479";
        String message = "sent from SMSSample app developed by linh";
        String myPhoneNumber = "01685918345";
        Intent sentIntent = new Intent(Constant.INTENT_FLAG_SMS_SENT);
        Intent deliveryIntent = new Intent(Constant.INTENT_FLAG_SMS_DELIVERY);
        PendingIntent sentPendingIntent = PendingIntent.getBroadcast(getContext(), Constant.REQUSET_CODE_MESSAGE_SENT, sentIntent, 0);
        PendingIntent deliveryPendingIntent =PendingIntent.getBroadcast(getContext(), Constant.REQUSET_CODE_MESSAGE_DELIVERED, deliveryIntent, 0);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(ninhPHoneNumber, null, message, sentPendingIntent, deliveryPendingIntent);
        Logger.d(getClass().getName(), "message will be sent to "+ ninhPHoneNumber);
    }
}
