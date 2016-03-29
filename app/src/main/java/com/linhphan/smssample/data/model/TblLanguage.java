package com.linhphan.smssample.data.model;

import android.database.sqlite.SQLiteDatabase;

import com.linhphan.androidboilerplate.data.model.BaseModel;

/**
 * Created by linh on 29/03/2016.
 */
public class TblLanguage extends BaseModel{
    public static final String TBL_NAME = "tbl_language";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }

    @Override
    protected String getCreationSqlStatement() {
        return "CREATE TABLE "+ TBL_NAME +"(" +
                COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, " +
                COLUMN_NAME + " TEXT NOT NULL" +
                ")";
    }
}
