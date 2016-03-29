package com.linhphan.smssample.data.model;

import android.database.sqlite.SQLiteDatabase;

import com.linhphan.androidboilerplate.data.model.BaseModel;

/**
 * Created by linh on 29/03/2016.
 */
public class TblMessage extends BaseModel {

    public static final String TABLE_NAME = "tbl_message";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CAT_ID = "cat_id";
    public static final String COLUMN_LANG_ID = "lang_id";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_TRANSLATION = "translation";

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }

    @Override
    protected String getCreationSqlStatement() {
        return "CREATE TABLE " + TABLE_NAME +
                "(" +
                COLUMN_ID +" INTEGER PRIMARY KEY autoincrement, "+
                COLUMN_CAT_ID +" INTEGER, "+
                COLUMN_LANG_ID +" INTEGER, "+
                COLUMN_CONTENT +" TEXT NOT NULL, "+
                COLUMN_TRANSLATION +"TEXT, "+
                "FOREIGN KEY ("+COLUMN_CAT_ID+") REFERENCES "+ TblCategory.TBL_NAME +"("+ TblCategory.COLUMN_ID+"), "+
                "FOREIGN KEY ("+COLUMN_LANG_ID+") REFERENCES "+ TblLanguage.TBL_NAME +"("+ TblLanguage.COLUMN_ID+")"+
                ")";
    }
}
