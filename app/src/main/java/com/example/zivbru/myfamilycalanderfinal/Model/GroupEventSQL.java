package com.example.zivbru.myfamilycalanderfinal.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by zivbru on 7/7/2016.
 */
public class GroupEventSQL {

    public static final String GROUP_EVENT_TABLE_NAME = "GroupEventsTable";
    public static final String EVENT_ID = "EventId";
    public static final String EVENT_NAME = "EventName";
    public static final String OWNER = "Owner";
    public static final String EVENT__START_DATE = "EventStartDate";
    public static final String EVENT__END_DATE = "EventEndDate";
    public static final String EVENT_DESCRIPTION = "EventDescription";
    public static final String EVENT_GROUP_NAME = "EventGroupName";
    public static final String EVENT_TYPE = "EventType";
    public static final String EVENT_LAST_UPDATE = "EventLastUpdate";

    public static void createTableEvents(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                GROUP_EVENT_TABLE_NAME + " (" +
                EVENT_ID + " TEXT PRIMARY KEY," +
                EVENT_NAME + " TEXT," +
                EVENT__START_DATE + " TEXT," +
                EVENT__END_DATE + " TEXT," +
                OWNER + " TEXT," +
                EVENT_DESCRIPTION + " TEXT," +
                EVENT_GROUP_NAME + " TEXT," +
                EVENT_TYPE + " TEXT," +
                EVENT_LAST_UPDATE + " TEXT);");
    }

    public static void onUpgrade(SQLiteDatabase db){
        db.execSQL("drop table " + GROUP_EVENT_TABLE_NAME);
    }

    public static boolean InsertEvent(Event event,SQLiteDatabase db ){

        ContentValues contentValues = new ContentValues();
        contentValues.put(EVENT_ID,event.getId());
        contentValues.put(EVENT_NAME,event.getName());
        contentValues.put(EVENT__START_DATE,event.getStartDate());
        contentValues.put(EVENT__END_DATE,event.getEndDate());
        contentValues.put(OWNER,event.getOwnerById());
        contentValues.put(EVENT_DESCRIPTION,event.getDescription());
        contentValues.put(EVENT_TYPE,event.getTypeOfEvent());
        contentValues.put(EVENT_LAST_UPDATE,event.getLastUpdate());

        long result=db.insertWithOnConflict(GROUP_EVENT_TABLE_NAME, EVENT_ID, contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        return result != -1;
    }

    public static boolean updateEvent(Event event,SQLiteDatabase db ){

        ContentValues contentValues = new ContentValues();
        contentValues.put(EVENT_ID,event.getId());
        contentValues.put(EVENT_NAME,event.getName());
        contentValues.put(EVENT__START_DATE,event.getStartDate());
        contentValues.put(EVENT__END_DATE,event.getEndDate());
        contentValues.put(OWNER,event.getOwnerById());
        contentValues.put(EVENT_DESCRIPTION,event.getDescription());
        contentValues.put(EVENT_TYPE,event.getTypeOfEvent());
        contentValues.put(EVENT_LAST_UPDATE,event.getLastUpdate());

        long result=db.update(GROUP_EVENT_TABLE_NAME, contentValues, "EventId = ?", new String[]{event.getId()});
        return result != -1;
    }

    public static Integer deleteEvent(String eventId, SQLiteDatabase db){
        return db.delete(GROUP_EVENT_TABLE_NAME, "EventId = ?", new String[] { eventId });
    }


    public static Cursor getEventById(String eventId, SQLiteDatabase db){

        Cursor res = db.rawQuery("SELECT * FROM " + GROUP_EVENT_TABLE_NAME + " WHERE " + EVENT_ID + "=" + eventId, null);
        return res;
    }

    public static Event getEvent(Cursor cursor){
        Event event= null;
        while (cursor.moveToNext()){
            event = new Event(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
        }
        return  event;
    }

    public static String getLastUpdateDate(SQLiteDatabase db){
        return LastUpdateSql.getLastUpdate(db,GROUP_EVENT_TABLE_NAME);
    }
    public static void setLastUpdateDate(SQLiteDatabase db, String date){
        LastUpdateSql.setLastUpdate(db,GROUP_EVENT_TABLE_NAME, date);
    }


    public static ArrayList<Event> getAllEvents(SQLiteDatabase db) {
        Cursor cursor = db.query(GROUP_EVENT_TABLE_NAME, null, null , null, null, null, null);
        ArrayList<Event> events = new ArrayList<Event>();

        if (cursor.moveToFirst()) {
            int eventId = cursor.getColumnIndex(EVENT_ID);
            int eventName = cursor.getColumnIndex(EVENT_NAME);
            int eventStartDate = cursor.getColumnIndex(EVENT__START_DATE);
            int eventEndDate = cursor.getColumnIndex(EVENT__END_DATE);
            int owner = cursor.getColumnIndex(OWNER);
            int eventGroupName = cursor.getColumnIndex(EVENT_GROUP_NAME);
            int eventType = cursor.getColumnIndex(EVENT_TYPE);
            int eventLastUpdate = cursor.getColumnIndex(EVENT_LAST_UPDATE);
            int eventDescription = cursor.getColumnIndex(EVENT_DESCRIPTION);

            do {
                String id = cursor.getString(eventId);
                String name = cursor.getString(eventName);
                String startDate = cursor.getString(eventStartDate);
                String endDate = cursor.getString(eventEndDate);
                String description= cursor.getString(eventDescription);
                String groupName = cursor.getString(eventGroupName);
                String ownerId = cursor.getString(owner);
                String type = cursor.getString(eventType);
                String lastUpdate = cursor.getString(eventLastUpdate); //0 false / 1 true

                Event event= new Event(id,name,startDate,endDate,description,ownerId,groupName,type,lastUpdate);
                events.add(event);

            } while (cursor.moveToNext());
        }
        return events;
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("drop table " + GROUP_EVENT_TABLE_NAME + ";");
    }

    public  static ArrayList<String>  getEventsName(String userId,SQLiteDatabase db) {
        ArrayList<String> events = new ArrayList<String>();
        Cursor res = db.rawQuery("Select "+ EVENT_NAME +" from "+ GROUP_EVENT_TABLE_NAME,null);
        if (res.moveToFirst()){
            do{
                String data = res.getString(res.getColumnIndex(EVENT_NAME));
                events.add(data);
            }while(res.moveToNext());
        }
        return events;
    }

//    need to add more functions

}


