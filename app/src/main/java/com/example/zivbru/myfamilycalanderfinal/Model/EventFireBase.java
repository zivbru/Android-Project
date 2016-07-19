package com.example.zivbru.myfamilycalanderfinal.Model;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by zivbru on 5/13/2016.
 */
public class EventFireBase {

    Firebase myFirebaseRef;
    Firebase addEventStRef;
    User tempUser;
    public EventFireBase(Firebase myFirebaseRef) {
        this.myFirebaseRef=myFirebaseRef;
    }

    public void getAllEvents(final String id,String lastUpdateDate,final Model.GetListEventListener listener) {

        Firebase stRef = myFirebaseRef.child("events").child(id);
        Query queryRef = stRef.orderByChild("lastUpdate").startAt(lastUpdateDate);
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Event> events = new ArrayList<Event>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event event = snapshot.getValue(Event.class);
                    event.setId(snapshot.getKey());
                    events.add(event);
                }
                listener.onResult(events);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.onCancel();
            }
        });
    }
    public void getAllEventsName(final String id,final Model.GetEventsNameListener listener) {
        Firebase stRef = myFirebaseRef.child("events").child(id);
        stRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> events = new ArrayList<String>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event event = snapshot.getValue(Event.class);
                    event.setId(snapshot.getKey());
                    events.add(event.getName());
                }
                listener.done(events);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.done(null);
            }
        });
    }

    public void AddEvent(final Event event,final String id,final Model.SignupListener listener) {

        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = new GregorianCalendar();
        TimeZone timeZone = calendar.getTimeZone();
        calendar.setTimeZone(timeZone);
        Date updateTime = calendar.getInstance(timeZone).getTime();
        String date = null;
        date =  dateFormatGmt.format(updateTime);
        event.setLastUpdate(date);

        addEventStRef = myFirebaseRef.child("events").child(id);
        tempUser = new User();
        UserFireBase userFireBase= new UserFireBase(myFirebaseRef);
        userFireBase.getNameForUser(id, new Model.getUserNameListener() {
            @Override
            public void success(String name) {

                event.setOwnerById(name);
                event.setTypeOfEvent("Private");
                event.setGroupName("None");
            }

            @Override
            public void fail(String msg) {

            }
        });

        userFireBase.getUser(id, new Model.UserListener() {
            @Override
            public void done(User user) {
                tempUser = user;
                Firebase eventRef = addEventStRef.push();
                String eventId = eventRef.getKey();
                Firebase stRef1 = myFirebaseRef.child("users").child(id);
                eventRef.setValue(event);
                tempUser.insertEventById(eventId);
                stRef1.setValue(tempUser);
                listener.success();
            }
        });
    }



    public void getEvent(String userId,String eventId, final Model.GetEventListener listener) {
        Firebase stRef = myFirebaseRef.child("events").child(userId).child(eventId);
        stRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue(Event.class);

                listener.done(event);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.done(null);
            }
        });
    }

    public void deleteEvent(String userId, String eventId, Model.SignupListener listener) {
        Firebase stRef = myFirebaseRef.child("events").child(userId).child(eventId);
        stRef.removeValue();
        stRef = myFirebaseRef.child("users").child(userId).child("eventsById").child(eventId);
        stRef.removeValue();

        listener.success();

    }

    public void getAllUpcomingEvents(String id, final Model.GetUsersListener listener) {
        Firebase stRef = myFirebaseRef.child("events").child(id);
        final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        final Date currentTime = new Date();
        final Date[] convertedDate = new Date[1];
        stRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> events = new ArrayList<String>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event event = snapshot.getValue(Event.class);
                    try {
                        convertedDate[0] = format.parse(event.getStartDate());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(currentTime.before(convertedDate[0])) {
                        String message = "You have private event on: "+event.getStartDate();
                        events.add(message);
                    }
                }
                listener.done(events);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
