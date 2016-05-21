package com.linhphan.smssample.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.telephony.PhoneNumberUtils;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ex.chips.BaseRecipientAdapter;
import com.android.ex.chips.RecipientEditTextView;
import com.android.ex.chips.recipientchip.DrawableRecipientChip;
import com.linhphan.androidboilerplate.ui.activity.BaseActivity;
import com.linhphan.androidboilerplate.ui.dialog.DateAndTimePickerDialog;
import com.linhphan.androidboilerplate.util.DateUtil;
import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.smssample.R;
import com.linhphan.smssample.data.contentprovider.SentSmsProvider;
import com.linhphan.smssample.data.contentprovider.SmsProvider;
import com.linhphan.smssample.data.model.SmsModel;
import com.linhphan.smssample.data.model.SmsWrapper;
import com.linhphan.smssample.data.table.TblMessage;
import com.linhphan.smssample.data.table.TblSentMessage;
import com.linhphan.smssample.util.Common;
import com.linhphan.smssample.util.Constant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by linh on 4/30/2016.
 */
public class ComposerActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener, View.OnClickListener {

    public static final String ARG_BUNDLE_MESSAGE = "ARG_BUNDLE_MESSAGE";
    public static final String ARG_MESSAGE = "ARG_MESSAGE";

    private EditText mEdtSmsContent;
    private TextView mTxtScheduledTime;
    private ImageButton mBtnTime;
    private ImageButton mBtnSendNow;
    private ImageButton mBtnBack;
    private Button mBtnSet;
    private RecipientEditTextView mPhoneRetv;
    private ContactAdapter mAdapter;

    private SmsModel mSms;
    private long mScheduledTimeInMillis;
    private boolean mShouldEditSmsContent;
    private KeyListener mKeyListener;

    //== data columns
    @SuppressLint("InlinedApi")
    private final String COLUMN_NAME = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY : ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
    private final String COLUMN_PHONE_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER ;

    //== query params
    private final String[] PROJECTION = {
            ContactsContract.CommonDataKinds.Phone._ID,
            COLUMN_NAME,
            COLUMN_PHONE_NUMBER};
    // Defines the text expression
    private final String SELECTION = COLUMN_NAME + " LIKE ? OR " + COLUMN_PHONE_NUMBER + " LIKE ?";
    // Defines a mKeyListener for the search string
    private String mSearchString = "";
    private String[] mSelectionArgs = { mSearchString, mSearchString};
    private final String SORT_ORDER = PROJECTION[2] + " ASC ";


    //=========== inherited methods ================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(ARG_BUNDLE_MESSAGE);
        if (bundle != null){
            mSms = bundle.getParcelable(ARG_MESSAGE);
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getActivityLayoutResource() {
        return R.layout.activity_composer;
    }

    @Override
    protected void init() {
        getSupportLoaderManager().initLoader(0, null, this);
        mAdapter = new ContactAdapter(this, null, true);
    }

    @Override
    protected void getWidgets(Bundle savedInstanceState) {
        mEdtSmsContent = (EditText) findViewById(R.id.edt_sms_content);
        mTxtScheduledTime = (TextView) findViewById(R.id.txt_scheduled_time);
        mBtnTime = (ImageButton) findViewById(R.id.btn_alarm);
        mBtnSendNow = (ImageButton) findViewById(R.id.btn_send);
        mBtnBack = (ImageButton) findViewById(R.id.btn_back);
        mBtnSet = (Button) findViewById(R.id.btn_set);
        mPhoneRetv = (RecipientEditTextView) findViewById(R.id.phone_retv);

        if (mSms != null){
            mEdtSmsContent.setText(mSms.getContent());
        }
        mKeyListener = mEdtSmsContent.getKeyListener();
        mEdtSmsContent.setKeyListener(null);

        setupReceiptPhoneTextView();
    }

    @Override
    protected void registerEventHandler() {
        mBtnTime.setOnClickListener(this);
        mBtnSendNow.setOnClickListener(this);
        mBtnBack.setOnClickListener(this);
        mBtnSet.setOnClickListener(this);
        mEdtSmsContent.setOnClickListener(this);
    }

    //================ implemented methods =========================================================
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_alarm:
                showDateTimePicker();
                break;

            case R.id.btn_send:
                onSendNowClicked();
                break;

            case R.id.btn_back:
                onBackPressed();
                break;

            case R.id.btn_set:
                onSetButtonClicked();
                break;

            case R.id.edt_sms_content:
                onEditorClicked();
                break;

            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show();
        Object object = mAdapter.getItem(position);
        if (object != null && object instanceof Cursor){
            Cursor cursor = (Cursor) object;
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            String name = cursor.getString(nameIndex);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        mSelectionArgs[0] = mSelectionArgs[1] = "%" + mSearchString + "%";
        Logger.d(getClassTagName(),"selection " +SELECTION);
        // Starts the query
        return new CursorLoader(this, ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, SELECTION, mSelectionArgs, SORT_ORDER);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        Logger.e("contacts ", data.getCount() + "");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
    //=== end loader callbacks

    //============== inner methods =================================================================
    private void setupReceiptPhoneTextView(){
        mPhoneRetv.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        BaseRecipientAdapter adapter = new BaseRecipientAdapter(BaseRecipientAdapter.QUERY_TYPE_PHONE, this);
        adapter.setShowMobileOnly(true);
        mPhoneRetv.setAdapter(adapter);
        mPhoneRetv.dismissDropDownOnItemSelected(true);
    }

    private void showDateTimePicker(){
        Calendar calendarNow = Calendar.getInstance(Locale.getDefault());
        long now = calendarNow.getTimeInMillis();
        DateAndTimePickerDialog picker = new DateAndTimePickerDialog();
        picker.setMinDate(now);
        picker.setMinHour(calendarNow.get(Calendar.HOUR_OF_DAY));
        picker.setMinMinute(calendarNow.get(Calendar.MINUTE));
        picker.setCallback(new DateAndTimePickerDialog.OnPickerFinish() {
            @Override
            public void onFinish(long timeInMillis) {
                mScheduledTimeInMillis = timeInMillis;
                mTxtScheduledTime.setText(DateUtil.parseDateTime(timeInMillis));
            }
        });
        picker.show(getSupportFragmentManager(), DateAndTimePickerDialog.class.getName());
    }

    private void scheduleAlarm(SmsWrapper messageWrapper){
        int requestCode = (int) (Math.random()*100 + 100);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(Constant.INTENT_FLAG_ALARM_SENT);
        alarmIntent.putExtra(Constant.ARG_INTENT_MESSAGE, messageWrapper.toJsonString());
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(this, requestCode, alarmIntent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, messageWrapper.getDue(), alarmPendingIntent);

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        long now = calendar.getTimeInMillis();
        long duration = now - messageWrapper.getDue();
        Toast.makeText(this, "sms will sent in "+ DateUtil.parseIntervalTime(duration), Toast.LENGTH_SHORT).show();
    }

    private void onSendNowClicked(){
        if (mSms == null){
            return;
        }

        DrawableRecipientChip[] chips = mPhoneRetv.getSortedRecipients();
        if (chips.length <= 0){
            Toast.makeText(this, getString(R.string.no_selected_phone), Toast.LENGTH_SHORT).show();
            return;
        }

        checkAndStoreSms();

        List<SmsWrapper> list = new ArrayList<>();
        for (DrawableRecipientChip chip : chips) {
            String name = chip.getEntry().getDisplayName();
            String phoneNumber = chip.getEntry().getDestination();
            Uri cover = chip.getEntry().getPhotoThumbnailUri();
            if (!Common.isPhoneNumberExisted(list, phoneNumber)){
                SmsWrapper messageWrapper = new SmsWrapper(mSms);
                messageWrapper.setDestinationPhoneNumber(phoneNumber);
                messageWrapper.setContactName(name);
                messageWrapper.setCoverUri(cover);
                messageWrapper.setDue(DateUtil.getCurrentTimeInMillis());
                messageWrapper.setWrapperId(SmsWrapper.generateWrapperId(messageWrapper.getId(), messageWrapper.getDestinationPhoneNumber(), messageWrapper.getDue()));
                list.add(messageWrapper);
            }
        }

//        Common.sendMessageDirectly(this, list);// TODO: 5/19/2016 this should be un-commented out
        storeToSentTable(list);
        onBackPressed();
    }

    private void onEditorClicked(){
        if (mShouldEditSmsContent){
            mShouldEditSmsContent = true;
            Toast.makeText(this, getString(R.string.click_more), Toast.LENGTH_SHORT).show();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mShouldEditSmsContent = false;
                }
            }, 1000);
        }else{
            mEdtSmsContent.setKeyListener(mKeyListener);
        }
    }

    private void onSetButtonClicked(){
        if (mSms == null){
            return;
        }

        DrawableRecipientChip[] chips = mPhoneRetv.getSortedRecipients();
        if (chips.length <= 0){
            Toast.makeText(this, getString(R.string.no_selected_phone), Toast.LENGTH_SHORT).show();
            return;
        }

        if (mScheduledTimeInMillis <= 0){
            Toast.makeText(this, getString(R.string.no_time_set), Toast.LENGTH_SHORT).show();
            return;
        }else if (mScheduledTimeInMillis <= DateUtil.getCurrentTimeInMillis()){
            Toast.makeText(this, getString(R.string.time_invalid), Toast.LENGTH_SHORT).show();
            return;
        }

        checkAndStoreSms();

        List<SmsWrapper> list = new ArrayList<>();
        for (DrawableRecipientChip chip : chips) {
            String name = chip.getEntry().getDisplayName();
            String phoneNumber = chip.getEntry().getDestination();
            Uri cover = chip.getEntry().getPhotoThumbnailUri();
            if (!Common.isPhoneNumberExisted(list, phoneNumber)){
                SmsWrapper messageWrapper = new SmsWrapper(mSms);
                messageWrapper.setDestinationPhoneNumber(phoneNumber);
                messageWrapper.setContactName(name);
                messageWrapper.setCoverUri(cover);
                messageWrapper.setDue(mScheduledTimeInMillis);
                messageWrapper.setWrapperId(SmsWrapper.generateWrapperId(messageWrapper.getId(), messageWrapper.getDestinationPhoneNumber(), messageWrapper.getDue()));
//                scheduleAlarm(messageWrapper); // TODO: 5/19/2016 this should be un-commented out
            }
        }

        storeToSentTable(list);
        onBackPressed();
    }

    /**
     * determine whether sms' content has changed or not. if the content has changed
     * then store it into a new sms into database
     */
    private void checkAndStoreSms(){
        if (isContentChanged()){
            Uri uri = storeCustomSms();
            if (uri != null) {
                String sNewSmsId = uri.getLastPathSegment();
                try {
                    int iNewSmsId = Integer.parseInt(sNewSmsId);
                    mSms.setId(iNewSmsId);
                    mSms.setContent(mEdtSmsContent.getText().toString());

                    Logger.d(getClassTagName(), "new sms id: "+ sNewSmsId);
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * determine whether the content of message has been changed by user.
     * @return true if the content has been changed otherwise return false
     */
    private boolean isContentChanged(){
        String original = mSms.getContent().trim();
        String content = mEdtSmsContent.getText().toString().trim();
        return !original.equals(content);
    }

    /**
     * store a new message to database
     * @return the uri locates to new message in database
     */
    private Uri storeCustomSms(){
        Toast.makeText(this, getString(R.string.store_custom_sms), Toast.LENGTH_SHORT).show();
        ContentValues values = new ContentValues();
        values.put(TblMessage.COLUMN_CAT_ID, mSms.getCatId());
        values.put(TblMessage.COLUMN_LANG_ID, mSms.getLangId());
        values.put(TblMessage.COLUMN_STARED, false);
        values.put(TblMessage.COLUMN_CONTENT, mEdtSmsContent.getText().toString());
        Uri uri = getContentResolver().insert(SmsProvider.CONTENT_URI, values);
        Logger.d(getClassTagName(), "store custom sms at "+ uri);

        return uri;
    }

    /**
     * store the information of session to sent table in database
     * @param list a list of sms wrapper to be saved
     */
    private void storeToSentTable(List<SmsWrapper> list){
        for (SmsWrapper smsWrapper : list){
            storeToSentTable(smsWrapper);
        }
    }

    /**
     * store the information of session to sent table in database
     * @param sms a sms wrapper to be saved
     */
    private void storeToSentTable(SmsWrapper sms){
        ContentValues values = new ContentValues();
        values.put(TblSentMessage.COLUMN_ID, sms.getWrapperId());
        values.put(TblSentMessage.COLUMN_SMS_ID, sms.getId());
        values.put(TblSentMessage.COLUMN_DUE, sms.getDue());
        values.put(TblSentMessage.COLUMN_ERROR, sms.getError());
        values.put(TblSentMessage.COLUMN_PHONE, sms.getDestinationPhoneNumber());
        values.put(TblSentMessage.COLUMN_NAME, sms.getContactName());
        values.put(TblSentMessage.COLUMN_STATUS, TblSentMessage.Status.Pending.ordinal());
        Uri cover = sms.getCoverUri();
        if (cover != null) {
            values.put(TblSentMessage.COLUMN_COVER, cover.getPath());
        }
        getContentResolver().insert(SentSmsProvider.CONTENT_URI, values);
    }

    //============== inner classes =================================================================
    class ContactAdapter extends CursorAdapter {

        public ContactAdapter(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
        }

        public ContactAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_list, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            //== get views
            TextView txtName = (TextView) view.findViewById(R.id.txt_name);
            TextView txtPhoneNumber = (TextView) view.findViewById(R.id.txt_phone_number);

            //== show data
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int phoneNumberIndex = cursor.getColumnIndex(COLUMN_PHONE_NUMBER);
            String name = cursor.getString(nameIndex);
            String phoneNumber = cursor.getString(phoneNumberIndex);
            phoneNumber = PhoneNumberUtils.stripSeparators(phoneNumber);

            txtName.setText(name);
            txtPhoneNumber.setText(PhoneNumberUtils.formatNumber(phoneNumber));
        }
    }
}
