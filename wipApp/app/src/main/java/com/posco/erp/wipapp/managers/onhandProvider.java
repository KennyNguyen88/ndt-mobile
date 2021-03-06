package com.posco.erp.wipapp.managers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.posco.erp.wipapp.utils.DBOpenHelper;

public class onhandProvider extends ContentProvider {
    private static final String AUTHORITY = "com.posco.erp.wipapp.onhandprovider";
    private static final String BASE_PATH = "onhand"; //table name
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    // Constant to identify the requested operation
//    private static final int NOTES = 1;
//    private static final int NOTES_ID = 2;

//    private static final UriMatcher urimatcher = new UriMatcher(UriMatcher.NO_MATCH); // Parse URI and tell which operation has been requested
//    public static final String CONTENT_ITEM_TYPE = "Note";

//    static {
//        urimatcher.addURI(AUTHORITY,BASE_PATH,NOTES);
//        urimatcher.addURI(AUTHORITY,BASE_PATH + "/#",NOTES_ID);
//    }

    private SQLiteDatabase database;
    @Override
    public boolean onCreate() {
        DBOpenHelper helper = new DBOpenHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
//        if (urimatcher.match(uri) == NOTES_ID) {
//            selection = DBOpenHelper.NOTE_ID + "=" + uri.getLastPathSegment();
//        }
        return database.query(DBOpenHelper.TABLE_ONHAND, DBOpenHelper.ALL_COLUMNS, selection, null,null,null,DBOpenHelper.ONHAND_CREATED + " DESC");
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = database.insert(DBOpenHelper.TABLE_ONHAND, null,values);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
