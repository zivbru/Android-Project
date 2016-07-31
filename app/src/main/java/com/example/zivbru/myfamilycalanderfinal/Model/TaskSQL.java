package com.example.zivbru.myfamilycalanderfinal.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by zivbru on 5/4/2016.
 */
public class TaskSQL {

    public static final String TASKS_TABLE_NAME = "TasksTable";
    public static final String TASK_ID = "TaskId";
    public static final String TASK_TITLE = "TaskTitle";
    public static final String TARGET_DATE = "TargetDate";
    public static final String OWNER = "Owner";
    public static final String EVENT_ID = "EventId";
    public static final String TASK_DESCRIPTION = "TaskDescription";
    public static final String GROUP_ID = "GroupId";
    public static final String TYPE_OF_TASK = "TypeOfTask";
    public static final String Task_LAST_UPDATE = "TaskLastUpdate";
    public static final String USER = "UserId";


    public static void createTableTasks(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                TASKS_TABLE_NAME + " (" +
                TASK_ID + " TEXT PRIMARY KEY," +
                TASK_TITLE + " TEXT," +
                TARGET_DATE + " TEXT," +
                OWNER + " TEXT," +
                EVENT_ID + " TEXT," +
                TASK_DESCRIPTION + " TEXT," +
                GROUP_ID + " TEXT," +
                TYPE_OF_TASK + " TEXT," +
                USER + " TEXT," +
                Task_LAST_UPDATE + " TEXT);");
    }
    public static void onUpgrade(SQLiteDatabase db){
        db.execSQL("drop table " + TASKS_TABLE_NAME);
    }

    public static boolean InsertTask(Task task,String userId,SQLiteDatabase db ){

        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_ID,task.getId());
        contentValues.put(TASK_TITLE,task.getTitle());
        contentValues.put(TARGET_DATE,task.getTargetDate());
        contentValues.put(OWNER,task.getOwnerId());
        contentValues.put(EVENT_ID,task.getRelatedEvent());
        contentValues.put(TASK_DESCRIPTION,task.getDescription());
        contentValues.put(GROUP_ID,task.getGroupId());
        contentValues.put(TYPE_OF_TASK,task.getTypeOfTask());
        contentValues.put(Task_LAST_UPDATE,task.getLastUpdate());
        contentValues.put(USER,userId);

        long result=db.insertWithOnConflict(TASKS_TABLE_NAME, TASK_ID, contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        return result != -1;
    }

    public static boolean updateTask(Task task,SQLiteDatabase db ){

        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_ID,task.getId());
        contentValues.put(TASK_TITLE,task.getTitle());
        contentValues.put(TARGET_DATE,task.getTargetDate());
        contentValues.put(OWNER,task.getOwnerId());
        contentValues.put(EVENT_ID,task.getRelatedEvent());
        contentValues.put(TASK_DESCRIPTION,task.getDescription());
        contentValues.put(GROUP_ID,task.getGroupId());
        contentValues.put(TYPE_OF_TASK,task.getTypeOfTask());
        contentValues.put(Task_LAST_UPDATE,task.getLastUpdate());

        long result=db.update(TASKS_TABLE_NAME, contentValues, "TaskId = ?", new String[]{task.getId()});
        return result != -1;
    }

    public static Integer deleteTask(String taskId, SQLiteDatabase db){
        return db.delete(TASKS_TABLE_NAME,"TaskId = ?",new String[]{taskId});
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

    public static String getLastUpdateDate(SQLiteDatabase db,String userId){
        return LastUpdateSql.getLastUpdate(db,TASKS_TABLE_NAME,userId);
    }
    public static void setLastUpdateDate(SQLiteDatabase db, String date,String userId){
        LastUpdateSql.setLastUpdate(db,TASKS_TABLE_NAME, date,userId);
    }

    public static ArrayList<Task> getAllTasks(SQLiteDatabase db,String usersId) {
        Cursor cursor = db.query(TASKS_TABLE_NAME, null, null , null, null, null, null);
        ArrayList<Task> tasks = new ArrayList<Task>();

        if (cursor.moveToFirst()) {
            int taskId = cursor.getColumnIndex(TASK_ID);
            int taskTitle = cursor.getColumnIndex(TASK_TITLE);
            int targetDate = cursor.getColumnIndex(TARGET_DATE);
            int owner = cursor.getColumnIndex(OWNER);
            int eventId = cursor.getColumnIndex(EVENT_ID);
            int taskDescription = cursor.getColumnIndex(TASK_DESCRIPTION);
            int groupId = cursor.getColumnIndex(GROUP_ID);
            int typeOfTask = cursor.getColumnIndex(TYPE_OF_TASK);
            int taskLastUpdate = cursor.getColumnIndex(Task_LAST_UPDATE);
            int userId=cursor.getColumnIndex(USER);

            do {
                String id = cursor.getString(taskId);
                String title = cursor.getString(taskTitle);
                String date = cursor.getString(targetDate);
                String ownerId = cursor.getString(owner);
                String event = cursor.getString(eventId);
                String description= cursor.getString(taskDescription);
                String groupName = cursor.getString(groupId);
                String type = cursor.getString(typeOfTask);
                String user = cursor.getString(userId);
                String lastUpdate = cursor.getString(taskLastUpdate);

                Task task= new Task(id,title,date,ownerId,event,description,groupName,type,lastUpdate,user);
                if(user.equals(usersId))
                    tasks.add(task);

            } while (cursor.moveToNext());
        }
        return tasks;
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("drop table " + TASKS_TABLE_NAME + ";");
    }

//    need to add more functions

}





