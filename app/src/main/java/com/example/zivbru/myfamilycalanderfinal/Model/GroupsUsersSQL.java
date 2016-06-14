package com.example.zivbru.myfamilycalanderfinal.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by zivbru on 5/4/2016.
 */
public class GroupsUsersSQL {

    public static final String GROUPS_USERS_TABLE_NAME = "GroupUsersTable";
    public static final String GROUPS_USERS_ID = "GroupUsersId";
    public static final String GROUP_ID = "GroupId";
    public static final String USER_NAME = "UserName";
    public static final String EVENT_ID = "EventId";


    public static void createTableGroupsUsers(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS " + GROUPS_USERS_TABLE_NAME + " (" + GROUPS_USERS_ID + " TEXT,"+ GROUP_ID + " TEXT,"
                + EVENT_ID + " TEXT,"+ USER_NAME + " TEXT )");
    }
    public static void onUpgrade(SQLiteDatabase db){
        db.execSQL("drop table " + GROUPS_USERS_TABLE_NAME);
    }

    public static boolean InsertGroupUsers(GroupsUsers groupUser,SQLiteDatabase db ){

        ContentValues contentValues = new ContentValues();
        contentValues.put(GROUPS_USERS_ID,groupUser.getId());
        contentValues.put(GROUP_ID,groupUser.getGroupId());
        contentValues.put(EVENT_ID,groupUser.getEventId());
        contentValues.put(USER_NAME,groupUser.getUserName());

        long result=db.insert(GROUPS_USERS_TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public static boolean updateGroupUsers(GroupsUsers groupUser,SQLiteDatabase db ){


        ContentValues contentValues = new ContentValues();
        contentValues.put(GROUPS_USERS_ID,groupUser.getId());
        contentValues.put(GROUP_ID,groupUser.getGroupId());
        contentValues.put(USER_NAME,groupUser.getUserName());
        contentValues.put(EVENT_ID,groupUser.getEventId());

        long result=db.update(GROUPS_USERS_TABLE_NAME, contentValues, "GroupUsers = ?", new String[]{groupUser.getId()});
        return result != -1;
    }

    public static Integer deleteGroupUser(String groupUserId, SQLiteDatabase db){
        return db.delete(GROUPS_USERS_TABLE_NAME,"GroupUsers = ?",new String[]{groupUserId});
    }


    public static Cursor getGroupUserById(String groupUserId, SQLiteDatabase db){

        Cursor res = db.rawQuery("SELECT * FROM " + GROUPS_USERS_TABLE_NAME + " WHERE " + GROUPS_USERS_ID + "=" + groupUserId, null);
        return res;
    }

    public static GroupsUsers getGroupUser(Cursor cursor){
        GroupsUsers groupsUsers = null;
        while (cursor.moveToNext()){
            groupsUsers = new GroupsUsers(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
        }
        return  groupsUsers;
    }
//    need to add more functions

}
