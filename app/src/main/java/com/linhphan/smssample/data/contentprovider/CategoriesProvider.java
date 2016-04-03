package com.linhphan.smssample.data.contentprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.FeatureInfo;
import android.content.pm.PathPermission;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.smssample.data.helper.SMSSQLiteHelper;
import com.linhphan.smssample.data.table.TblCategory;
import com.linhphan.smssample.data.table.TblMessage;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by linh on 03/04/2016.
 */
public class CategoriesProvider extends ContentProvider {

    // SMSDatabaseHelper
    private SMSSQLiteHelper SMSDatabaseHelper;

    // used for the UriMacher
    private static final int CAT = 11;
    private static final int CAT_ID = 21;

    private static final String AUTHORITY = "com.linhphan.smssample.catprovider";
    private static final String BASE_PATH = "cat";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/cat";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/cat";

    private final UriMatcher sURIMatcher;

    public CategoriesProvider() {
        super();
        sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, CAT);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", CAT_ID);
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
        queryBuilder.setTables(TblCategory.TBL_NAME);

        int uriType = sURIMatcher.match(uri);
        switch (uriType){

            case CAT:

                break;

            case CAT_ID:
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
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private void checkColumns(String[] projection){
        String[] available = {
                TblCategory.COLUMN_ID,
                TblCategory.COLUMN_NAME,
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
