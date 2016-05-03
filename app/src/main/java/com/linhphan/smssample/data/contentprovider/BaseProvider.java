package com.linhphan.smssample.data.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.smssample.data.helper.SMSSQLiteHelper;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by linh on 5/3/2016.
 */
public abstract class BaseProvider extends ContentProvider {
    // SMSDatabaseHelper
    protected SMSSQLiteHelper SMSDatabaseHelper;

    // used for the UriMacher
    protected static final int MATCH_ANY = 11;
    protected static final int MATCH_ID = 21;

    protected final UriMatcher sURIMatcher;

    //============ constructors ====================================================================
    public BaseProvider(String authority, String basePath) {
        sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sURIMatcher.addURI(authority, basePath, MATCH_ANY);
        sURIMatcher.addURI(authority, basePath + "/#", MATCH_ID);
    }

    //============ inherited methods ===============================================================
    @Override
    public boolean onCreate() {
        Logger.i(getClass().getName(), "onCreate");
        SMSDatabaseHelper = new SMSSQLiteHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public abstract Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder);

    @Nullable
    @Override
    public abstract String getType(Uri uri);

    @Nullable
    @Override
    public abstract Uri insert(Uri uri, ContentValues values);

    @Override
    public abstract int delete(Uri uri, String selection, String[] selectionArgs);

    @Override
    public abstract int update(Uri uri, ContentValues values, String selection, String[] selectionArgs);

    //============ inner methods ===================================================================
    protected void checkColumns(String[] projection){
        String[] available = getAvailableFields();

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

    protected abstract String[] getAvailableFields();
}
