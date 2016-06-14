package com.example.zivbru.myfamilycalanderfinal.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by zivbru on 5/4/2016.
 */
public class EventSQL {
    public static final String EVENT_TABLE_NAME = "EventsTable";
    public static final String EVENT_ID = "EventId";
    public static final String EVENT_NAME = "EventName";
    public static final String EVENT_DATE = "EventDate";
    public static final String EVENT_NOTES = "EventNotes";

    public static void createTableEvents(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS " + EVENT_TABLE_NAME + " (" + EVENT_ID + " TEXT,"+ EVENT_NAME + " TEXT,"
                + EVENT_DATE + " TEXT," + EVENT_NOTES + " TEXT )");
    }
    public static void onUpgrade(SQLiteDatabase db){
        db.execSQL("drop table " + EVENT_TABLE_NAME);
    }

    public static boolean InsertEvent(Event event,SQLiteDatabase db ){

        ContentValues contentValues = new ContentValues();
        contentValues.put(EVENT_ID,event.getId());
        contentValues.put(EVENT_NAME,event.getName());
        contentValues.put(EVENT_DATE,event.getStartDate());
        contentValues.put(EVENT_NOTES,event.getDescription());

        long result=db.insert(EVENT_TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public static boolean updateEvent(Event event,SQLiteDatabase db ){

        ContentValues contentValues = new ContentValues();
        contentValues.put(EVENT_ID,event.getId());
        contentValues.put(EVENT_NAME,event.getName());
        contentValues.put(EVENT_DATE,event.getStartDate());
        contentValues.put(EVENT_NOTES,event.getDescription());

        long result=db.update(EVENT_TABLE_NAME, contentValues, "EventId = ?", new String[]{event.getId()});
        return result != -1;
    }

    public static Integer deleteEvent(String eventId, SQLiteDatabase db){
        return db.delete(EVENT_TABLE_NAME,"EventId = ?",new String[]{eventId});
    }


    public static Cursor getEventById(String eventId, SQLiteDatabase db){

        Cursor res = db.rawQuery("SELECT * FROM " + EVENT_TABLE_NAME + " WHERE " + EVENT_ID + "=" + eventId, null);
        return res;
    }

    public static Event getEvent(Cursor cursor){
        Event event= null;
        while (cursor.moveToNext()){
            event = new Event(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
        }
        return  event;
    }
//    need to add more functions

}


