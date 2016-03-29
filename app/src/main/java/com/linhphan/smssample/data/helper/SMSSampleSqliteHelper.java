package com.linhphan.smssample.data.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.linhphan.androidboilerplate.data.model.BaseModel;
import com.linhphan.smssample.data.model.TblCategory;
import com.linhphan.smssample.data.model.TblLanguage;
import com.linhphan.smssample.data.model.TblMessage;

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
        BaseModel.getInstance(TblLanguage.class).onCreate(db);
        BaseModel.getInstance(TblCategory.class).onCreate(db);
        BaseModel.getInstance(TblMessage.class).onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        BaseModel.getInstance(TblLanguage.class).onUpgrade(db, oldVersion, newVersion);
        BaseModel.getInstance(TblCategory.class).onUpgrade(db, oldVersion, newVersion);
        BaseModel.getInstance(TblMessage.class).onUpgrade(db, oldVersion, newVersion);
    }
}
