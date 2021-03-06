package com.linhphan.smssample.data.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.smssample.data.table.TblCategory;
import com.linhphan.smssample.data.table.TblMessage;

/**
 * Created by linh on 03/04/2016.
 */
public class CategoriesProvider extends BaseProvider {

    private static final String AUTHORITY = "com.linhphan.smssample.catprovider";
    private static final String BASE_PATH = "cat";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    public CategoriesProvider() {
        super(AUTHORITY, BASE_PATH);
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

    @Override
    protected String[] getAvailableFields() {
        return new String[]{
                TblCategory.COLUMN_ID,
                TblCategory.COLUMN_NAME,
        };
    }
}
