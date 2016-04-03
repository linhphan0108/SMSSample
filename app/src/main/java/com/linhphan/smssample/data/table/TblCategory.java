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
public class TblCategory extends BaseTable {
    public static final String TBL_NAME = "tbl_category";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    public static final int LOVE_ID = 0;
    public static final int NIGHT_GREETING_GIRL_ID = 1;
    public static final int NIGHT_GREETING_BOY_ID = 2;
    public static final int VALENTINE_ID = 3;

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

        values.put(COLUMN_ID, 0);
        values.put(COLUMN_NAME, context.getString(R.string.love));
        database.insert(TBL_NAME, null, values);

        values.put(COLUMN_ID, 1);
        values.put(COLUMN_NAME, context.getString(R.string.night_greetings_for_girls));
        database.insert(TBL_NAME, null, values);

        values.put(COLUMN_ID, 2);
        values.put(COLUMN_NAME, context.getString(R.string.night_greetings_for_boys));
        database.insert(TBL_NAME, null, values);

        values.put(COLUMN_ID, 3);
        values.put(COLUMN_NAME, context.getString(R.string.valentine));
        database.insert(TBL_NAME, null, values);
    }
}
