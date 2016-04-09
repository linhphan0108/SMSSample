package com.linhphan.androidboilerplate.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.linhphan.androidboilerplate.util.AppUtil;
import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.smssample.R;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by linh on 08/04/2016.
 */
public class DateAndTimePickerDialog extends DialogFragment implements TimePicker.OnTimeChangedListener, DatePicker.OnDateChangedListener, View.OnClickListener {

    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private Button mBtnOk;

    private long mMaxDate;
    private long mMinDate;
    private int mMaxHour;
    private int mMaxMinute;
    private int mMinHour;
    private int mMinMinute;

    private int mHour;
    private int mMinute;
    private int mYear;
    private int mMonth;
    private int mDayOfMonth;

    //== callbacks
    private OnPickerFinish mCallback;

    //=========== setters and getters ==============================================================
    public long getMaxDate() {
        return mMaxDate;
    }

    public void setMaxDate(long maxDate) {
        this.mMaxDate = maxDate;
    }

    public long getMinDate() {
        return mMinDate;
    }

    public void setMinDate(long minDate) {
        this.mMinDate = minDate;
    }

    public int getMaxHour() {
        return mMaxHour;
    }

    public void setMaxHour(int maxHour) {
        this.mMaxHour = maxHour;
    }

    public int getMaxMinute() {
        return mMaxMinute;
    }

    public void setMaxMinute(int maxMinute) {
        this.mMaxMinute = maxMinute;
    }

    public int getMinHour() {
        return mMinHour;
    }

    public void setMinHour(int minHour) {
        this.mMinHour = minHour;
    }

    public int getMinMinute() {
        return mMinMinute;
    }

    public void setMinMinute(int minMinute) {
        this.mMinMinute = minMinute;
    }

    public OnPickerFinish getCallback() {
        return mCallback;
    }

    public void setCallback(OnPickerFinish callback) {
        this.mCallback = callback;
    }

    //============ overridden methods ==============================================================
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_date_time_picker, container, false);

        getWidgets(root, savedInstanceState);
        setupPicker();
        registerEventHandlers();
        return root;
    }

    //============ implemented methods =============================================================
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ok:
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, mYear);
                calendar.set(Calendar.MONTH, mMonth);
                calendar.set(Calendar.DAY_OF_MONTH, mDayOfMonth);
                calendar.set(Calendar.HOUR_OF_DAY, mHour);
                calendar.set(Calendar.MINUTE, mMinute);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                mCallback.onFinish(calendar.getTimeInMillis());
                this.dismiss();
                break;

            default:
                break;
        }
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mYear= year;
        mMonth = monthOfYear;
        mDayOfMonth = dayOfMonth;
    }

    //============ inner methods ===================================================================
    private void getWidgets(View root, Bundle savedInstanceState){
        mDatePicker = (DatePicker) root.findViewById(R.id.datePicker);
        mTimePicker = (TimePicker) root.findViewById(R.id.timePicker);
        mBtnOk = (Button) root.findViewById(R.id.btn_ok);
    }

    private void setupPicker(){
        if (mMinDate <= 0 && mMaxDate > 0){
            mDatePicker.setMaxDate(mMaxDate);

        }else if (mMinDate > 0 && mMaxDate <= 0){
            mDatePicker.setMinDate(mMinDate);

        }else if(mMinDate > 0 && mMaxDate > 0 && mMinDate < mMaxDate){
            mDatePicker.setMaxDate(mMaxDate);
            mDatePicker.setMinDate(mMinDate);

        }else{
            Logger.d(getClass().getName(), "max date and min date is invalided");
        }
    }

    private void registerEventHandlers(){
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        long now = calendar.getTimeInMillis();
        //== check date
        if (now < mMinDate || now > mMaxDate){
            calendar.setTimeInMillis(mMinDate);
        }

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
        //== check time
        if (mHour < mMinHour || mHour > mMaxHour){
            mHour = mMinHour;
        }
        if (mHour == mMinHour || mHour == mMaxHour){
            mMinute = mMinMinute;
        }

        mDatePicker.init(mYear, mMonth, mDayOfMonth, this);
        mTimePicker.setOnTimeChangedListener(this);
        mTimePicker.setCurrentHour(mHour);
        mTimePicker.setCurrentMinute(mMinute);
        mBtnOk.setOnClickListener(this);
    }

    //============= inner classes ==================================================================
    public interface OnPickerFinish{
        void onFinish(long timeInMillis);
    }
}
