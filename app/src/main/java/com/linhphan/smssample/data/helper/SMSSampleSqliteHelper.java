package com.linhphan.smssample.data.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.linhphan.androidboilerplate.data.table.BaseTable;
import com.linhphan.smssample.data.table.TblCategory;
import com.linhphan.smssample.data.table.TblLanguage;
import com.linhphan.smssample.data.table.TblMessage;

/**
 * Created by linh on 29/03/2016.
 */
public class SMSSampleSqliteHelper extends SQLiteOpenHelper{

    private final static String DATABASE_NAME = "sms_sample.db";
    private static final int DATABASE_VERSION = 1;

    public SMSSampleSqliteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        BaseTable.newInstance(TblLanguage.class).onCreate(db);
        BaseTable.newInstance(TblCategory.class).onCreate(db);
        BaseTable.newInstance(TblMessage.class).onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        BaseTable.newInstance(TblLanguage.class).onUpgrade(db, oldVersion, newVersion);
        BaseTable.newInstance(TblCategory.class).onUpgrade(db, oldVersion, newVersion);
        BaseTable.newInstance(TblMessage.class).onUpgrade(db, oldVersion, newVersion);
    }
}
