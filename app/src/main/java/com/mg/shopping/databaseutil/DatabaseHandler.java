package com.mg.shopping.databaseutil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.MyApplication;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;


/**
 * Created by hp on 2/27/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    public static final String DATA_TYPE_TEXT="TEXT";
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = Utility.getStringFromRes(MyApplication.getInstance(), R.string.app_name)+".db";


    public static final String CREATE_FAVOURITES_TABLE = "CREATE TABLE " + Constant.DatabaseColumn.FAVOURITES_TABLE_NAME + "(" +
            Constant.DatabaseColumn.FAVOURITES_COLUMN_ID + " INTEGER PRIMARY KEY," +
            Constant.DatabaseColumn.FAVOURITES_COLUMN_USER_ID+ DATA_TYPE_TEXT+" ," +
            Constant.DatabaseColumn.FAVOURITES_COLUMN_OBJECT_ID +DATA_TYPE_TEXT+ "," +
            Constant.DatabaseColumn.FAVOURITES_COLUMN_OBJECT_TYPE + DATA_TYPE_TEXT+" TEXT " +
            ")";

    public static final String CREATE_FOLLOWING_TABLE = "CREATE TABLE " + Constant.DatabaseColumn.BRAND_TABLE_NAME + "(" +
            Constant.DatabaseColumn.FAVOURITES_COLUMN_ID + " INTEGER PRIMARY KEY," +
            Constant.DatabaseColumn.FAVOURITES_COLUMN_USER_ID+ " TEXT," +
            Constant.DatabaseColumn.FAVOURITES_COLUMN_OBJECT_ID + " TEXT," +
            Constant.DatabaseColumn.FAVOURITES_COLUMN_OBJECT_TYPE + " TEXT " +
            ")";
    




    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        
        db.execSQL(CREATE_FAVOURITES_TABLE);
        db.execSQL(CREATE_FOLLOWING_TABLE);
      


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        
        db.execSQL("DROP TABLE IF EXISTS " + Constant.DatabaseColumn.FAVOURITES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constant.DatabaseColumn.BRAND_TABLE_NAME);

        // Create tables again
        onCreate(db);
    }


    @Override
    public void onOpen(SQLiteDatabase database) {
        super.onOpen(database);
        if(Build.VERSION.SDK_INT >= 28)
        {
            database.disableWriteAheadLogging();
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);

        if(Build.VERSION.SDK_INT >= 28)
        {
            db.disableWriteAheadLogging();
        }

    }
}
