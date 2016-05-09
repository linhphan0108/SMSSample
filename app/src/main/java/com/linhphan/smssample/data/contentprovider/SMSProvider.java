package com.linhphan.smssample.data.contentprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.smssample.data.helper.SMSSQLiteHelper;
import com.linhphan.smssample.data.table.TblMessage;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by linh on 02/04/2016.
 */
public class SmsProvider extends ContentProvider {

    // SMSDatabaseHelper
    private SMSSQLiteHelper SMSDatabaseHelper;

    // used for the UriMacher
    private static final int MATCH_ANY = 10;
    private static final int MATCH_ID = 20;

    private static final String AUTHORITY = "com.linhphan.smssample.smsprovider";
    private static final String BASE_PATH = "sms";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/sms";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/sms";

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, MATCH_ANY);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", MATCH_ID);
    }

    @Override
    public boolean onCreate() {
        Logger.i(getClass().getName(), "onCreate");
        SMSDatabaseHelper = new SMSSQLiteHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Logger.i(getClass().getName(), "query");

        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
        checkColumns(projection);

        //set the table name
        queryBuilder.setTables(TblMessage.TBL_NAME);

        int uriType = sURIMatcher.match(uri);
        switch (uriType){
            case MATCH_ANY:

                break;

            case MATCH_ID:
                // adding the ID to the original query
                queryBuilder.appendWhere(TblMessage.COLUMN_ID +"="+ uri.getLastPathSegment());
                break;

            default:
                throw new IllegalArgumentException("unknown uri"+ uri);
        }

        SQLiteDatabase sqLiteDatabase = SMSDatabaseHelper.getReadableDatabase();
        Cursor cursor = queryBuilder.query(sqLiteDatabase, projection, selection, selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }else{
            throw new NullPointerException("context is null");
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        Logger.i(getClass().getName(), "insert");

        SQLiteDatabase db = SMSDatabaseHelper.getReadableDatabase();
        long id;
        int uriType = sURIMatcher.match(uri);
        switch (uriType){
            case MATCH_ANY:
                id = db.insert(TblMessage.TBL_NAME, null, values);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }else{
            throw new NullPointerException("context is null");
        }

        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        Logger.i(getClass().getName(), "delete");

        SQLiteDatabase db = SMSDatabaseHelper.getReadableDatabase();
        int rowsDeleted = 0;
        int uriType = sURIMatcher.match(uri);
        switch (uriType){

            case MATCH_ANY:
                rowsDeleted = db.delete(TblMessage.TBL_NAME, selection, selectionArgs);
                break;

            case MATCH_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)){
                    rowsDeleted = db.delete(TblMessage.TBL_NAME, TblMessage.COLUMN_ID +"="+ id, null);
                }else{
                    rowsDeleted = db.delete(TblMessage.TBL_NAME, TblMessage.COLUMN_ID +"="+ id +" AND " +selection,
                            selectionArgs);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (getContext() != null){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Logger.i(getClass().getName(), "update");

        SQLiteDatabase db = SMSDatabaseHelper.getWritableDatabase();
        int rowsUpdated = 0;
        int uriType = sURIMatcher.match(uri);
        switch (uriType){
            case MATCH_ANY:
                rowsUpdated = db.update(TblMessage.TBL_NAME, values, selection, selectionArgs);
                break;

            case MATCH_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(TblMessage.TBL_NAME, values,
                            TblMessage.COLUMN_ID + "=" + id, selectionArgs);
                }else{
                    rowsUpdated = db.update(TblMessage.TBL_NAME, values,
                            TblMessage.COLUMN_ID + "=" + id + " AND " + selection, selectionArgs);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (getContext() != null){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    private void checkColumns(String[] projection){
        String[] available = {
                TblMessage.COLUMN_ID,
                TblMessage.COLUMN_CAT_ID,
                TblMessage.COLUMN_LANG_ID,
                TblMessage.COLUMN_CONTENT,
                TblMessage.COLUMN_TRANSLATION,
                TblMessage.COLUMN_STARED
        };
        if (projection != null){
            HashSet<String> requestedColumn = new HashSet<>(Arrays.asList(projection));
            HashSet<String> availableColumn = new HashSet<>(Arrays.asList(available));
            //check if all columns which are requested are available
            if (!availableColumn.containsAll(requestedColumn)){
                Logger.d(getClass().getName(), "requested columns: "+ requestedColumn.toString());
                Logger.d(getClass().getName(), "available columns: "+ availableColumn.toString());
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }
}
