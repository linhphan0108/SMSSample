package com.linhphan.androidboilerplate.data.model;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by linh on 29/03/2016.
 */
public abstract class BaseModel {

    private static BaseModel mModel;

    public static  <T extends BaseModel>BaseModel getInstance(Class<T> t){
        if (mModel == null){
            try {
                mModel = t.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return mModel;
    }

    public void onCreate(SQLiteDatabase database){
        database.execSQL(getCreationSqlStatement());
    }
    public abstract void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion);

    protected abstract String getCreationSqlStatement();
}
