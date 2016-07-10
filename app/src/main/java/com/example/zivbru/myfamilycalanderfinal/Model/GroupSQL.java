package com.example.zivbru.myfamilycalanderfinal.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by zivbru on 5/4/2016.
 */
public class GroupSQL {

    public static final String GROUP_TABLE_NAME = "GroupsTable";
    public static final String GROUP_ID = "GroupId";
    public static final String GROUP_TITLE = "GroupTitle";
    public static final String GROUP_PICTURE_NAME = "PictureName";
    public static final String GROUP_LAST_UPDATE = "EventLastUpdate";



    public static void createTableGroup(SQLiteDatabase db){

        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                GROUP_TABLE_NAME + " (" +
                GROUP_ID + " TEXT PRIMARY KEY ," +
                GROUP_TITLE + " TEXT," +
                GROUP_PICTURE_NAME + " TEXT," +
                GROUP_LAST_UPDATE + " TEXT);");
    }
    public static void onUpgrade(SQLiteDatabase db){
        db.execSQL("drop table " + GROUP_TABLE_NAME);
    }

    public static boolean InsertGroup(Group group,SQLiteDatabase db ){

        GroupUsers groupUsers;
        ContentValues contentValues = new ContentValues();
        contentValues.put(GROUP_ID,group.getId());
        contentValues.put(GROUP_TITLE,group.getTitle());
        contentValues.put(GROUP_PICTURE_NAME,group.getPictureName());
        contentValues.put(GROUP_LAST_UPDATE,group.getLastUpdate());
        for (String s:group.getUsersList()) {
            groupUsers= new GroupUsers(group.getId(),s);
            GroupUsersSQL.InsertGroupUsers(groupUsers,db);
        }
        long result=db.insertWithOnConflict(GROUP_TABLE_NAME, GROUP_ID, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        return result != -1;
    }

    public static boolean updateGroup(Group group,SQLiteDatabase db ){

        ContentValues contentValues = new ContentValues();
        contentValues.put(GROUP_ID,group.getId());
        contentValues.put(GROUP_TITLE,group.getTitle());
        contentValues.put(GROUP_PICTURE_NAME,group.getPictureName());

        long result=db.update(GROUP_TABLE_NAME, contentValues, "GroupId = ?", new String[]{group.getId()});
        return result != -1;
    }

    public static Integer deleteGroup(String groupId, SQLiteDatabase db){
        return db.delete(GROUP_TABLE_NAME,"GroupId = ?",new String[]{groupId});
    }

    public static Cursor getGroupById(String groupId, SQLiteDatabase db){

        Cursor res = db.rawQuery("SELECT * FROM " + GROUP_TABLE_NAME + " WHERE " + GROUP_ID + "=" + groupId, null);
        return res;
    }

    public static Group getGroup(Cursor cursor){
        Group group = null;
        while (cursor.moveToNext()){
            group = new Group(cursor.getString(0),cursor.getString(1),cursor.getString(2));
        }
        return  group;
    }

    public static String getLastUpdateDate(SQLiteDatabase db){
        return LastUpdateSql.getLastUpdate(db,GROUP_TABLE_NAME);
    }
    public static void setLastUpdateDate(SQLiteDatabase db, String date){
        LastUpdateSql.setLastUpdate(db, GROUP_TABLE_NAME, date);
    }


    public static ArrayList<Group> getAllGroups(SQLiteDatabase db) {
        Cursor cursor = db.query(GROUP_TABLE_NAME, null, null , null, null, null, null);
        ArrayList<Group> groups = new ArrayList<Group>();

        if (cursor.moveToFirst()) {
            int groupId = cursor.getColumnIndex(GROUP_ID);
            int groupTitle = cursor.getColumnIndex(GROUP_TITLE);
            int pictureName = cursor.getColumnIndex(GROUP_PICTURE_NAME);
            int groupLastUpdate = cursor.getColumnIndex(GROUP_LAST_UPDATE);


            do {
                String id = cursor.getString(groupId);
                String name = cursor.getString(groupTitle);
                String picture = cursor.getString(pictureName);
                String lastUpdate = cursor.getString(groupLastUpdate); //0 false / 1 true

                Group group= new Group(id,name,picture,lastUpdate);
                group.setUsersList(GroupUsersSQL.getAllUsers(group.getId(),db));
                groups.add(group);

            } while (cursor.moveToNext());
        }

        return groups;
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("drop table " + GROUP_TABLE_NAME + ";");
    }

    public static String getGroupIdByName(String groupName, SQLiteDatabase db) {
        Cursor cursor =db.rawQuery("SELECT * FROM " + GROUP_TABLE_NAME + " WHERE " + GROUP_TITLE + " =?" ,  new String[] {groupName});
        String id= "";

        if (cursor.moveToFirst()) {
            int groupId = cursor.getColumnIndex(GROUP_ID);

            do {
                id = cursor.getString(groupId);


            } while (cursor.moveToNext());
        }
        return id;
    }

    public static ArrayList<String> getAllGroupsId(SQLiteDatabase db) {
        Cursor cursor =db.rawQuery("SELECT * FROM " + GROUP_TABLE_NAME , null);
        ArrayList<String> groups = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            int groupId = cursor.getColumnIndex(GROUP_ID);

            do {
                String id = cursor.getString(groupId);
                groups.add(id);

            } while (cursor.moveToNext());
        }
        return groups;
    }

}


//    need to add more functions


