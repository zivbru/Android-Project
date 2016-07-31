package com.example.zivbru.myfamilycalanderfinal.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by zivbru on 7/4/2016.
 */
public class LastUpdateSql {

    final static String LAST_UPDATE_TABLE = "last_update";
    final static String LAST_UPDATE_TABLE_TABLE_NAME = "table_name";
    final static String LAST_UPDATE_TABLE_DATE = "date";
    final static String User = "User";


    static public void create(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + LAST_UPDATE_TABLE + " (" +
                LAST_UPDATE_TABLE_TABLE_NAME + " TEXT PRIMARY KEY," +
                User + " TEXT ," +
                LAST_UPDATE_TABLE_DATE + " TEXT);" );
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("drop table " + LAST_UPDATE_TABLE + ";");
    }

    public static String getLastUpdate(SQLiteDatabase db, String tableName, String id) {
        String[] args = {tableName,id};
//        Cursor cursor = db.query(LAST_UPDATE_TABLE, null, LAST_UPDATE_TABLE + " = ?",args , null, null, null);
        Cursor cursor=db.rawQuery("Select * from " + LAST_UPDATE_TABLE + " Where " + LAST_UPDATE_TABLE_TABLE_NAME + "=?" + " and " + User + "=?", args);
        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex(LAST_UPDATE_TABLE_DATE));
        }
        return null;
    }

    public static void setLastUpdate(SQLiteDatabase db, String table, String date,String id) {
        ContentValues values = new ContentValues();
        values.put(LAST_UPDATE_TABLE_TABLE_NAME, table);
        values.put(LAST_UPDATE_TABLE_DATE, date);
        values.put(User, id);


        db.insertWithOnConflict(LAST_UPDATE_TABLE,LAST_UPDATE_TABLE_TABLE_NAME,values, SQLiteDatabase.CONFLICT_REPLACE);
    }
}
