package com.example.zivbru.myfamilycalanderfinal.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by zivbru on 7/7/2016.
 */
public class GroupUsersSQL {

    public static final String GROUP_USERS_TABLE_NAME = "GroupsUsersTable";
    public static final String GROUP_ID = "GroupId";
    public static final String USER = "User";


    public static void createTableGroup(SQLiteDatabase db){

        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                GROUP_USERS_TABLE_NAME + " (" +
                GROUP_ID + " TEXT PRIMARY KEY," +
                USER + " TEXT);");
    }

    public static boolean InsertGroupUsers(GroupUsers group,SQLiteDatabase db ){

        ContentValues contentValues = new ContentValues();
        contentValues.put(GROUP_ID,group.getGroupId());
        contentValues.put(USER,group.getUser());


        long result=db.insertWithOnConflict(GROUP_USERS_TABLE_NAME, GROUP_ID, contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        return result != -1;

    }

    public static ArrayList<String> getAllUsers(String groupId,SQLiteDatabase db) {
        Cursor cursor =db.rawQuery("SELECT * FROM " + GROUP_USERS_TABLE_NAME + " WHERE " + GROUP_ID + " =?" ,  new String[] {groupId});
        ArrayList<String> users = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            int userName = cursor.getColumnIndex(USER);

            do {
                String user = cursor.getString(userName);
                users.add(user);

            } while (cursor.moveToNext());
        }
        return users;
    }

}
