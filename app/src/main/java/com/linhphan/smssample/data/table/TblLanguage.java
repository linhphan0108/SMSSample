package com.linhphan.smssample.data.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.linhphan.androidboilerplate.data.table.BaseTable;
import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.smssample.R;

/**
 * Created by linh on 29/03/2016.
 */
public class TblLanguage extends BaseTable {
    public static final String TBL_NAME = "tbl_language";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    public static final int VI_ID = 0;
    public static final int EN_ID = 1;
    public static final int JP_ID = 2;
    public static final int K_ID = 3;
    public static final int CN_ID = 4;
    public static final int T_ID = 5;

    @Override
    public void onUpgrade(Context context, SQLiteDatabase database, int oldVersion, int newVersion) {
        Logger.i(getClass().getName(), "on upgrade (old version: "+ oldVersion +", new version: "+ newVersion +")");
    }

    @Override
    protected String getCreationSqlStatement() {
        Logger.i(getClass().getName(), "on create");
        return "CREATE TABLE IF NOT EXISTS "+ TBL_NAME +"(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT NOT NULL" +
                ")";
    }

    @Override
    protected void populateData(Context context, SQLiteDatabase database) {
        ContentValues values = new ContentValues(1);

        values.put(COLUMN_ID, VI_ID);
        values.put(COLUMN_NAME, context.getString(R.string.vietnamese));
        database.insert(TBL_NAME, null, values);

        values.put(COLUMN_ID, EN_ID);
        values.put(COLUMN_NAME, context.getString(R.string.english));
        database.insert(TBL_NAME, null, values);

        values.put(COLUMN_ID, JP_ID);
        values.put(COLUMN_NAME, context.getString(R.string.japanese));
        database.insert(TBL_NAME, null, values);

        values.put(COLUMN_ID, K_ID);
        values.put(COLUMN_NAME, context.getString(R.string.korean));
        database.insert(TBL_NAME, null, values);

        values.put(COLUMN_ID, CN_ID);
        values.put(COLUMN_NAME, context.getString(R.string.chinese));
        database.insert(TBL_NAME, null, values);

        values.put(COLUMN_ID, T_ID);
        values.put(COLUMN_NAME, context.getString(R.string.thai));
        database.insert(TBL_NAME, null, values);
    }
}