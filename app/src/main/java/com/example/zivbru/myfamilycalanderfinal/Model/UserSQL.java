package com.example.zivbru.myfamilycalanderfinal.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by zivbru on 5/4/2016.
 */
public class UserSQL {

    public static final String USERS_TABLE_NAME = "UsersTable";
    public static final String USERS_TABLE_LOGIN = "LoginTable";
    public static final String USER_ID = "UserId";
    public static final String USER_NAME = "UserName";
    public static final String PASSWORD = "Password";
    public static final String USER_FIRST_NAME = "FirstName";
    public static final String USER_LAST_NAME = "LastName";
    public static final String USER_ADRESS = "Adress";
    public static final String USER_PHONE = "Phone";
    public static final String USER_PICTURE_NAME = "PictureName";
    public static final String USER_BIRTH_DATE = "BirthDate";
    public static final String BOOLEAN_LOGIN = "Login";
    public static final String LOGIN_PARAM = "LoginParam";


    public static void createTableUser(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + USERS_TABLE_NAME + " (" + USER_ID + " TEXT," + USER_NAME + " TEXT," + PASSWORD + " TEXT,"
                + USER_FIRST_NAME + " TEXT," + USER_LAST_NAME + " TEXT," + USER_ADRESS + " TEXT," + USER_PHONE + " TEXT,"
                + USER_BIRTH_DATE + " TEXT," + USER_PICTURE_NAME + " TEXT )");
    }

    public static void createTableUserLogin(SQLiteDatabase db){
        boolean temp=isTableExists(USERS_TABLE_LOGIN, db);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + USERS_TABLE_LOGIN + " (" + USER_ID + " TEXT," + BOOLEAN_LOGIN + " TEXT ," + LOGIN_PARAM + " TEXT )");
        if(!temp)
            InsertLogin("0", "false", db);
    }

    public static void onUpgrade(SQLiteDatabase db){
        db.execSQL("drop table " + USERS_TABLE_NAME);
        db.execSQL("drop table " + USERS_TABLE_LOGIN);
    }
    public static boolean InsertLogin(String userId,String loggedIn,SQLiteDatabase db ){
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID, userId);
        contentValues.put(BOOLEAN_LOGIN, loggedIn);
        contentValues.put(LOGIN_PARAM, 1);
        long result=db.insert(USERS_TABLE_LOGIN, null, contentValues);
        return result != -1;
    }

    public static boolean InsertUser(User user,SQLiteDatabase db ){

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID,user.getUserId());
        contentValues.put(USER_NAME,user.getUserName());
        contentValues.put(PASSWORD,user.getPassword());
        contentValues.put(USER_FIRST_NAME,user.getFirstName());
        contentValues.put(USER_LAST_NAME,user.getLastName());
        contentValues.put(USER_ADRESS,user.getAdress());
        contentValues.put(USER_PHONE,user.getPhone());
        contentValues.put(USER_BIRTH_DATE,user.getBirthDate());
        contentValues.put(USER_PICTURE_NAME, user.getPictureName());

        long result=db.insert(USERS_TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public static boolean updateLogin(String userId,String loggedIn,SQLiteDatabase db ){
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID,userId);
        contentValues.put(BOOLEAN_LOGIN, loggedIn);
        contentValues.put(LOGIN_PARAM, 1);
        long result=db.update(USERS_TABLE_LOGIN, contentValues, "LoginParam = ?", new String[]{"1"});
        return result != -1;
    }

    public static boolean updateUser(User user,SQLiteDatabase db ){

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID,user.getUserId());
        contentValues.put(USER_NAME,user.getUserName());
        contentValues.put(PASSWORD,user.getPassword());
        contentValues.put(USER_FIRST_NAME,user.getFirstName());
        contentValues.put(USER_LAST_NAME,user.getLastName());
        contentValues.put(USER_ADRESS,user.getAdress());
        contentValues.put(USER_PHONE,user.getPhone());
        contentValues.put(USER_BIRTH_DATE,user.getBirthDate());
        contentValues.put(USER_PICTURE_NAME,user.getPictureName());

        long result=db.update(USERS_TABLE_NAME, contentValues, "UserName = ?", new String[]{user.getUserName()});
        return result != -1;
    }

    public static Integer deleteUser(String userId, SQLiteDatabase db){
        return db.delete(USERS_TABLE_NAME,"Id = ?",new String[]{userId});
    }

    public static Cursor getUserByUserName(String userId, SQLiteDatabase db){

        Cursor res = db.rawQuery("SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + USER_ID + "=" + userId, null);
        return res;
    }

    public static User getUser(Cursor cursor){
        User user = null;
        while (cursor.moveToNext()){
            user = new User(cursor.getString(0),cursor.getString(1),cursor.getString(2)
                    ,cursor.getString(3),cursor.getString(4),cursor.getString(5), cursor.getString(6),cursor.getString(7),cursor.getString(8));
        }
        return  user;
    }
    public static String getLoggedinUser(String index, SQLiteDatabase db){
        String userId="null";
        Cursor res = db.rawQuery("SELECT * FROM " + USERS_TABLE_LOGIN + " WHERE " + LOGIN_PARAM + "=" + index, null);
        while(res.moveToNext()) {
            if (res.getString(1).equals("true"))
                userId = res.getString(0);
        }
        return userId;
    }

    private static boolean isTableExists(String tableName, SQLiteDatabase db) {

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public static String getLastUpdateDate(SQLiteDatabase db){
        return LastUpdateSql.getLastUpdate(db,USERS_TABLE_NAME,"");
    }
    public static void setLastUpdateDate(SQLiteDatabase db, String date){
        LastUpdateSql.setLastUpdate(db,USERS_TABLE_NAME, date,"");
    }

    public static void drop(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("drop table " + USERS_TABLE_NAME + ";");

    }

//    Need to add more functions

}
