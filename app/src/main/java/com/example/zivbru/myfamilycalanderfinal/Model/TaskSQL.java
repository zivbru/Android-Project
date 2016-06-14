package com.example.zivbru.myfamilycalanderfinal.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by zivbru on 5/4/2016.
 */
public class TaskSQL {

    public static final String TASKS_TABLE_NAME = "TasksTable";
    public static final String TASK_ID = "TaskId";
    public static final String TASK_TITLE = "TaskTitle";
    public static final String FROM_DATE = "FromDate";
    public static final String TO_DATE = "ToDate";
    public static final String EVENT_ID = "EventId";
    public static final String TASK_NOTES = "TaskNotes";

    public static void createTableTasks(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TASKS_TABLE_NAME + " (" + TASK_ID + " TEXT,"+ TASK_TITLE + " TEXT,"
                + FROM_DATE + " TEXT," + TO_DATE + " TEXT,"  + EVENT_ID + " TEXT,"+ TASK_NOTES + " TEXT )");
    }
    public static void onUpgrade(SQLiteDatabase db){
        db.execSQL("drop table " + TASKS_TABLE_NAME);
    }

    public static boolean InsertTask(Task task,SQLiteDatabase db ){

        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_ID,task.getId());
        contentValues.put(TASK_TITLE,task.getTitle());
        contentValues.put(FROM_DATE,task.getTargetDate());
        contentValues.put(TASK_NOTES,task.getDescription());
        contentValues.put(EVENT_ID,task.getRelatedEvent());

        long result=db.insert(TASKS_TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public static boolean updateTask(Task task,SQLiteDatabase db ){

        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_ID,task.getId());
        contentValues.put(TASK_TITLE,task.getTitle());
        contentValues.put(FROM_DATE,task.getTargetDate());
        contentValues.put(TASK_NOTES,task.getDescription());
        contentValues.put(EVENT_ID,task.getRelatedEvent());

        long result=db.update(TASKS_TABLE_NAME, contentValues, "TaskId = ?", new String[]{task.getId()});
        return result != -1;
    }

    public static Integer deleteTask(String taskId, SQLiteDatabase db){
        return db.delete(TASKS_TABLE_NAME,"EventId = ?",new String[]{taskId});
    }

    public static Cursor getTaskById(String taskId, SQLiteDatabase db){

        Cursor res = db.rawQuery("SELECT * FROM " + TASKS_TABLE_NAME + " WHERE " + TASK_ID + "=" + taskId, null);
        return res;
    }

    public static Task getTask(Cursor cursor){
        Task task = null;
        while (cursor.moveToNext()){
            task = new Task(cursor.getString(0),cursor.getString(1),cursor.getString(2)
                    ,cursor.getString(3),cursor.getString(4),cursor.getString(5));
        }
        return  task;
    }

//    need to add more functions

}





