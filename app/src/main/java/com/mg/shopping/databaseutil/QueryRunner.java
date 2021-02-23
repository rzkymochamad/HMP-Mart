package com.mg.shopping.databaseutil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.objectutil.CursorObject;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class QueryRunner {
    Context context;

    /**
     * <p>It contain methods for executing the SQL Database Queries & get Required Result</p>
     */
    public QueryRunner(Context context) {
        this.context = context;
        Utility.Logger(QueryRunner.class.getName(), "Setting : Working");
    }


    /**
     * <p>It execute Query and return data in the form of list by adding into arraylist</p>
     *
     * @param query            SQL Query which we want to execute
     * @param sqLiteOpenHelper SQLiteOpenHelper instance
     * @return arraylist which contain all of required data from Database
     */
    public List<Object> getAll(String query, SQLiteOpenHelper sqLiteOpenHelper, DatabaseObject databaseObject) {

        CursorObject cursorObject = getCursor(query, sqLiteOpenHelper);
        Cursor cursor = cursorObject.getCursor();
        ArrayList<Object> objectArrayList = new ArrayList<>();
        Utility.Logger(QueryRunner.class.getName(), "Size of Cursor : " + cursor.getCount());

        if (cursor.moveToFirst()) {

            do {

                if (databaseObject.getTypeOperation() == TypeConstraint.FAVOURITES
                || databaseObject.getTypeOperation() == TypeConstraint.BRAND) {


                    if (databaseObject.getDbOperation() == DbConstraint.RETRIEVE_SPECIFIC_USER_FAVOURITES_PRODUCT
                        || databaseObject.getDbOperation() == DbConstraint.RETRIEVE_SPECIFIC_USER_FAVOURITES_STORE
                        || databaseObject.getDbOperation() == DbConstraint.RETRIEVE_SPECIFIC_BRAND_FOLLOWING){

                        objectArrayList.add(new DataObject()
                                .setId(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.FAVOURITES_COLUMN_ID)))
                                .setUserId(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.FAVOURITES_COLUMN_USER_ID)))
                                .setType(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.FAVOURITES_COLUMN_OBJECT_TYPE)))
                                .setProductId(cursor.getString(cursor.getColumnIndex(Constant.DatabaseColumn.FAVOURITES_COLUMN_OBJECT_ID)))
                        );

                    }
                    else {
                        Utility.Logger(getClass().getSimpleName(),"Other condition");
                    }

                }

            }
            while (cursor.moveToNext());

        }

        cursor.close();
        cursorObject.getDatabase().close();

        return objectArrayList;
    }


    /**
     * <p>Execute query and give true/false on Success or Failing</p>
     *
     * @param query            SQL Query which we want to execute
     * @param sqLiteOpenHelper SQLiteOpenHelper instance
     * @return true if perform successfully
     */
    public CursorObject getStatus(String query, SQLiteOpenHelper sqLiteOpenHelper) {
        Utility.Logger(QueryRunner.class.getName(), "Setting : " + query);
        CursorObject cursorObject = getCursor(query, sqLiteOpenHelper);
        Cursor cursor = cursorObject.getCursor();
        try {

            cursor.moveToFirst();

            cursor.close();

        } catch (SQLiteConstraintException e) {

            cursorObject.setCursor(null).setDatabase(null);
            e.fillInStackTrace();

        } catch (UnsupportedOperationException uoe) {

            cursorObject.setCursor(null).setDatabase(null);
            uoe.fillInStackTrace();
        }

        return cursorObject;
    }


    /**
     * <p>Execute query and give last inserted Row Id</p>
     *
     * @param query            SQL Query which we want to execute
     * @param sqLiteOpenHelper SQLiteOpenHelper instance
     * @return long      last inserted id
     */
    public long getLastInsertID(String query, SQLiteOpenHelper sqLiteOpenHelper) {
        Utility.Logger("Last Inserted", query);
        long lastInsertedId;
        CursorObject cursorObject = getCursor(query, sqLiteOpenHelper);
        Cursor cursor = cursorObject.getCursor();
        cursor.moveToFirst();
        lastInsertedId = cursor.getLong(0);
        Utility.Logger("Before", String.valueOf(lastInsertedId));
        cursor.close();
        cursorObject.getDatabase().close();
        Utility.Logger("After", String.valueOf(lastInsertedId));
        return lastInsertedId;
    }


    /**
     * <p>It run the SQL Query and return data in Cursor</p>
     *
     * @param query            SQL Query which we want to run
     * @param sqLiteOpenHelper Instance of SQLiteOpenHelper
     * @return cursor which contain data fetch from Database
     */
    private CursorObject getCursor(String query, SQLiteOpenHelper sqLiteOpenHelper) {
        CursorObject cursorObject = new CursorObject();

        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursorObject.setCursor(cursor).setDatabase(db);


        return cursorObject;
    }


}
