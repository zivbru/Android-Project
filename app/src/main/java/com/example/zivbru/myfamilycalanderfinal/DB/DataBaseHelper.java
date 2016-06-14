package com.example.zivbru.myfamilycalanderfinal.DB;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.zivbru.myfamilycalanderfinal.Model.UserSQL;


/**
 * Created by zivbru on 5/4/2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    public static final String DATA_BASE_NAME = "Users.db";


    public DataBaseHelper(Context context) {
        super(context, DATA_BASE_NAME, null, VERSION);

    }

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        UserSQL.createTableUser(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        UserSQL.onUpgrade(db);
        onCreate(db);
    }


}
