package com.linhphan.androidboilerplate.data.table;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by linh on 29/03/2016.
 */
public abstract class BaseTable {
    public static  <T extends BaseTable>BaseTable newInstance(Class<T> t){
        BaseTable mModel = null;
            try {
                mModel = t.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        return mModel;
    }

    /**
     * called during creation of the database
     */
    public void onCreate(Context context, SQLiteDatabase database){
        database.execSQL(getCreationSqlStatement());
        populateData(context, database);
    }

    /**
     * called during an upgrade of the database if increase the version of the database
     * @param context
     * @param oldVersion the old version code
     * @param newVersion the new version code
     */
    public abstract void onUpgrade(Context context, SQLiteDatabase database, int oldVersion, int newVersion);

    /**
     * this method must be overridden in subclasses
     * called during creation of the database
     * @return a Sqlite statement which create a table
     */
    protected abstract String getCreationSqlStatement();

    /**
     * called during creation of the database
     * @return the SQLite statement which insert data to database
     */
    protected abstract void populateData(Context context, SQLiteDatabase database);
}
