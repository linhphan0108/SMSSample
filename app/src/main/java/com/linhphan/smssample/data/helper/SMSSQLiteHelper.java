package com.linhphan.smssample.data.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.linhphan.androidboilerplate.data.table.BaseTable;
import com.linhphan.smssample.data.table.TblCategory;
import com.linhphan.smssample.data.table.TblLanguage;
import com.linhphan.smssample.data.table.TblMessage;
import com.linhphan.smssample.data.table.TblSentMessage;

/**
 * Created by linh on 29/03/2016.
 */
public class SMSSQLiteHelper extends SQLiteOpenHelper{

    private final static String DATABASE_NAME = "sms_sample.db";
    private static final int DATABASE_VERSION = 1;

    private Context mContext;

    public SMSSQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        BaseTable table = new TblLanguage();
        table.onCreate(mContext, db);

        table = new TblCategory();
        table.onCreate(mContext, db);

        table = new TblMessage();
        table.onCreate(mContext, db);

        table = new TblSentMessage();
        table.onCreate(mContext, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        BaseTable table = new TblLanguage();
        table.onUpgrade(mContext, db, oldVersion, newVersion);

        table = new TblCategory();
        table.onUpgrade(mContext, db, oldVersion, newVersion);

        table = new TblMessage();
        table.onUpgrade(mContext, db, oldVersion, newVersion);

        table = new TblSentMessage();
        table.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
