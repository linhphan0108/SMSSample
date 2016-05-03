package com.linhphan.smssample.data.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.smssample.data.table.TblCategory;
import com.linhphan.smssample.data.table.TblMessage;
import com.linhphan.smssample.data.table.TblSentMessage;
/**
 * Created by linh on 5/3/2016.
 */
public class SentSmsProvider extends BaseProvider{

    private static final String AUTHORITY = "com.linhphan.smssample.sentsmsprovider";
    private static final String BASE_PATH = "sent";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    public SentSmsProvider() {
        super(AUTHORITY, BASE_PATH);
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
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
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase sqlDB = SMSDatabaseHelper.getWritableDatabase();
        int uriType = sURIMatcher.match(uri);
        long id;
        switch (uriType) {
            case MATCH_ANY:
                id = sqlDB.insert(TblSentMessage.TBL_NAME, null, values);
                return Uri.withAppendedPath(CONTENT_URI, String.valueOf(id));

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    protected String[] getAvailableFields() {
        return new String[] {
                TblSentMessage.COLUMN_ID,
                TblSentMessage.COLUMN_SMS_ID,
                TblSentMessage.COLUMN_DUE,
                TblSentMessage.COLUMN_ERROR,
                TblSentMessage.COLUMN_PHONE,
                TblSentMessage.COLUMN_NAME,
                TblSentMessage.COLUMN_COVER,
                TblSentMessage.COLUMN_STATUS

        };
    }
}
