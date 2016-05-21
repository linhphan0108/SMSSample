package com.linhphan.smssample.ui.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.linhphan.smssample.R;
import com.linhphan.smssample.data.contentprovider.SmsProvider;
import com.linhphan.smssample.data.model.SmsWrapper;
import com.linhphan.smssample.data.model.SmsModel;
import com.linhphan.smssample.data.table.TblMessage;
import com.linhphan.smssample.ui.activity.ComposerActivity;
import com.linhphan.smssample.ui.adapter.ListSMSCursorAdapter;
import com.linhphan.smssample.ui.dialog.EditSmsDialog;
import com.linhphan.smssample.util.Constant;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by linh on 02/04/2016.
 */
public class ListSmsFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>, ListSMSCursorAdapter.ClickCallback{
    public static final String ARG_CATEGORY_ID = "ARG_CATEGORY_ID";

    private ListSMSCursorAdapter mAdapter;

    private int mCatId;

    //================= overridden methods =========================================================
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null){
            Bundle bundle = getArguments();
            mCatId = bundle.getInt(ARG_CATEGORY_ID);
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getFragmentLayoutResource() {
        return R.layout.fragment_list_sms;
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
        Logger.d(getClass().getName(), "category id = "+ mCatId);

        String selection = TblMessage.COLUMN_CAT_ID +"=?";
        String selectionArg[] = {
                String.valueOf(mCatId)
        };
        return new CursorLoader(getContext(), SmsProvider.CONTENT_URI, null, selection, selectionArg, null);
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
    public void onSendButtonClicked(final SmsModel message) {
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
    public void onCopyButtonClicked(SmsModel message) {
        Toast.makeText(getContext(), "copied", Toast.LENGTH_SHORT).show();
        ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("Copied", message.getContent());
        manager.setPrimaryClip(clipData);
    }

    @Override
    public void onShareButtonClicked(SmsModel message) {
        Toast.makeText(getContext(), String.valueOf(message.getCatId()), Toast.LENGTH_SHORT).show();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message.getContent());
        startActivity(Intent.createChooser(sharingIntent, "Choose an App"));
    }

    @Override
    public void onEditButtonClicked(SmsModel message) {
        EditSmsDialog dialog = new EditSmsDialog();
        dialog.setContent(message.getContent());
        dialog.setCallback(new EditSmsDialog.DialogCallback() {
            @Override
            public void onOkClicked(String newContent) {

            }
        });
        dialog.show(getChildFragmentManager(), EditSmsDialog.class.getName());
    }

    @Override
    public void onScheduleButtonClicked(SmsModel message) {
//        Toast.makeText(getContext(), String.valueOf(message.getCatId()), Toast.LENGTH_LONG).show();
//        String sms = "160 characters, 160 characters, 160 characters, 160 characters, 160 characters, 160 characters, 160 characters, 160 characters, 160 characters, 160 characters, 160 characters, 160 characters, 160 characters, 160 characters, 160 characters, 160 characters";
//        String tamPHoneNumber = "0978992209";
//        String ninh = "01632131479";
//        SmsWrapper messageWrapper = new SmsWrapper(message);
//        messageWrapper.setDestinationPhoneNumber(tamPHoneNumber);
//        showDateTimePicker(messageWrapper);
//        BaseActivity baseActivity = getOwnerActivity();
//        if (baseActivity != null) {
//            baseActivity.replaceFragment(R.id.fl_main_content, SmsComposerFragment.class, true, null, null);
//        }

        Bundle bundle = new Bundle();
        bundle.putParcelable(ComposerActivity.ARG_MESSAGE, message);
        Intent intent = new Intent(getContext(), ComposerActivity.class);
        intent.putExtra(ComposerActivity.ARG_BUNDLE_MESSAGE, bundle);
        getActivity().startActivity(intent);
    }

    //================= inner methods ==============================================================
    private void openMessenger(String sms){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("sms_body", sms);
        intent.setType("vnd.android-dir/mms-sms");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivity(intent);
    }

    private void scheduleAlarm(long timeInMillis, SmsWrapper messageWrapper){
        int requestCode = (int) (Math.random()*100 + 100);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(Constant.ARG_BUNDLE_MESSAGE, messageWrapper);
        Intent alarmIntent = new Intent(Constant.INTENT_FLAG_ALARM_SENT);
        alarmIntent.putExtra(Constant.ARG_INTENT_MESSAGE, messageWrapper.toJsonString());
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(getContext(), requestCode, alarmIntent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, alarmPendingIntent);

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        long now = calendar.getTimeInMillis();
        long duration = now - timeInMillis;
        calendar.setTimeInMillis(timeInMillis);
        Date date = calendar.getTime();
        Toast.makeText(getContext(), "sms will sent in "+ String.valueOf(duration) +" at "+ date.toString(), Toast.LENGTH_SHORT).show();
    }

    private void showDateTimePicker(final SmsWrapper messageWrapper){
        Calendar calendarNow = Calendar.getInstance(Locale.getDefault());
        long now = calendarNow.getTimeInMillis();
        DateAndTimePickerDialog picker = new DateAndTimePickerDialog();
        picker.setMinDate(now);
        picker.setMinHour(calendarNow.get(Calendar.HOUR_OF_DAY));
        picker.setMinMinute(calendarNow.get(Calendar.MINUTE));
        picker.setCallback(new DateAndTimePickerDialog.OnPickerFinish() {
            @Override
            public void onFinish(long timeInMillis) {
                messageWrapper.setDue(timeInMillis);
                scheduleAlarm(timeInMillis, messageWrapper);
            }
        });
        picker.show(getFragmentManager(), DateAndTimePickerDialog.class.getName());
    }
}
