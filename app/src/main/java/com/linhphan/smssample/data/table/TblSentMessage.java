package com.linhphan.smssample.data.table;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.linhphan.androidboilerplate.data.table.BaseTable;
import com.linhphan.androidboilerplate.util.Logger;

/**
 * Created by linh on 5/3/2016.
 */
public class TblSentMessage extends BaseTable {

    public static final String TBL_NAME = "tbl_sent";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SMS_ID = "sms_id";
    public static final String COLUMN_DUE = "due";
    public static final String COLUMN_ERROR = "error";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COVER = "cover";
    public static final String COLUMN_STATUS = "status";

    @Override
    public void onUpgrade(Context context, SQLiteDatabase database, int oldVersion, int newVersion) {
        Logger.i(getClass().getName(), "on upgrade (old version: "+ oldVersion +", new version: "+ newVersion +")");
    }

    @Override
    protected String getCreationSqlStatement() {
        return "CREATE TABLE IF NOT EXISTS " + TBL_NAME +
                "(" +
                COLUMN_ID +" TEXT PRIMARY KEY, "+
                COLUMN_SMS_ID +" INTEGER NOT NULL, "+
                COLUMN_DUE +" INTEGER NOT NULL, "+
                COLUMN_ERROR +" TEXT, "+
                COLUMN_PHONE +" TEXT NOT NULL, "+
                COLUMN_NAME +" TEXT, "+
                COLUMN_COVER +" TEXT, "+
                COLUMN_STATUS +" INTEGER, "+
                "FOREIGN KEY ("+COLUMN_SMS_ID+") REFERENCES "+ TblMessage.TBL_NAME +"("+ TblMessage.COLUMN_ID+") "+
                ")";
    }

    @Override
    protected void populateData(Context context, SQLiteDatabase database) {
    }

    //============ inner classes ===================================================================
    public enum Status{
        Pending, Sent, Delivered, ErrorSend, ErrorDeliver,
    }
}
