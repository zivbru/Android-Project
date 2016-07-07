package com.example.zivbru.myfamilycalanderfinal.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by zivbru on 5/4/2016.
 */
public class GroupSQL {

    public static final String GROUP_TABLE_NAME = "GroupsTable";
    public static final String GROUP_ID = "GroupId";
    public static final String GROUP_TITLE = "GroupTitle";
    public static final String GROUP_PICTURE_NAME = "PictureName";


    public static void createTableGroup(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS " + GROUP_TABLE_NAME + " (" + GROUP_ID + " TEXT,"+ GROUP_TITLE + " TEXT,"
                + GROUP_PICTURE_NAME + " TEXT )");
    }
    public static void onUpgrade(SQLiteDatabase db){
        db.execSQL("drop table " + GROUP_TABLE_NAME);
    }

    public static boolean InsertGroup(Group group,SQLiteDatabase db ){

        ContentValues contentValues = new ContentValues();
        contentValues.put(GROUP_ID,group.getId());
        contentValues.put(GROUP_TITLE,group.getTitle());
        contentValues.put(GROUP_PICTURE_NAME,group.getPictureName());

        long result=db.insert(GROUP_TABLE_NAME, null, contentValues);
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
        LastUpdateSql.setLastUpdate(db,GROUP_TABLE_NAME, date);
    }

    public static void drop(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("drop table " + GROUP_TABLE_NAME + ";");
    }

//    need to add more functions

}
