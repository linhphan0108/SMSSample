package com.linhphan.smssample.ui.fragment;

import android.app.AlarmManager;
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
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.linhphan.androidboilerplate.callback.ConfirmDialogCallback;
import com.linhphan.androidboilerplate.ui.dialog.ConfirmDialog;
import com.linhphan.androidboilerplate.ui.dialog.DateAndTimePickerDialog;
import com.linhphan.androidboilerplate.ui.fragment.BaseFragment;
import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.androidboilerplate.util.TextUtil;
import com.linhphan.androidboilerplate.util.ViewUtil;
import com.linhphan.smssample.R;
import com.linhphan.smssample.data.contentprovider.SMSProvider;
import com.linhphan.smssample.data.model.MessageModel;
import com.linhphan.smssample.data.table.TblMessage;
import com.linhphan.smssample.ui.adapter.ListSMSCursorAdapter;
import com.linhphan.smssample.util.Constant;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by linh on 02/04/2016.
 */
public class ListMessage extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>, ListSMSCursorAdapter.ClickCallback{

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
        ListView lvListMessage = (ListView) root.findViewById(R.id.lv_list_message);

        lvListMessage.setAdapter(mAdapter);
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
        Toast.makeText(getContext(), String.valueOf(message.getCatId()), Toast.LENGTH_LONG).show();
        showDateTimePicker(message.getContent());
    }

    //================= inner methods ==============================================================
    private void openMessenger(String sms){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("sms_body", sms);
        intent.setType("vnd.android-dir/mms-sms");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivity(intent);
    }

    private void scheduleAlarm(long timeInMillis, String message){
        int requestCode = (int) (Math.random()*100 + 100);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(Constant.INTENT_FLAG_ALARM_SENT);
        alarmIntent.putExtra(Constant.ARG_MESSAGE, message);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(getContext(), requestCode, alarmIntent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, alarmPendingIntent);

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        long now = calendar.getTimeInMillis();
        long duration = now - timeInMillis;
        calendar.setTimeInMillis(timeInMillis);
        Date date = calendar.getTime();
        Toast.makeText(getContext(), "sms will sent in "+ String.valueOf(duration) +" at "+ date.toString(), Toast.LENGTH_SHORT).show();
    }

    private void showDateTimePicker(final String message){
        Calendar calendarNow = Calendar.getInstance(Locale.getDefault());
        long now = calendarNow.getTimeInMillis();
        DateAndTimePickerDialog picker = new DateAndTimePickerDialog();
        picker.setMinDate(now);
        picker.setMinHour(calendarNow.get(Calendar.HOUR_OF_DAY));
        picker.setMinMinute(calendarNow.get(Calendar.MINUTE));
        picker.setCallback(new DateAndTimePickerDialog.OnPickerFinish() {
            @Override
            public void onFinish(long timeInMillis) {
                scheduleAlarm(timeInMillis, message);
            }
        });
        picker.show(getFragmentManager(), DateAndTimePickerDialog.class.getName());
    }
}
