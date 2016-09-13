package com.posco.erp.wipapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
    //Constants for db name and version
    private static final String DATABASE_NAME = "wip_erp.db";
    private static final int DATABASE_VERSION = 1;
    //Constants for identifying table and columns
    public static final String TABLE_ONHAND = "onhand";
    public static final String ONHAND_ID = "_id";
    public static final String ONHAND_ITEM_CD = "item_cd";
    public static final String ONHAND_QUANTITY = "quantity";
    public static final String ONHAND_ITEM_DESC = "item_desc";

    public static final String ONHAND_CREATED = "onhandCreated";

    public static final String[] ALL_COLUMNS = {ONHAND_ID, ONHAND_ITEM_CD,ONHAND_QUANTITY,ONHAND_ITEM_DESC,ONHAND_CREATED };
    //SQL to create table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_ONHAND + " (" +
                    ONHAND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ONHAND_ITEM_CD + " TEXT, " +
                    ONHAND_ITEM_DESC + " TEXT, " +
                    ONHAND_QUANTITY + " NUMBER, " +
                    ONHAND_CREATED + " TEXT default CURRENT_TIMESTAMP" +
                    ")";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS " + TABLE_ONHAND);
        onCreate(db);
    }

}
