package com.example.zivbru.myfamilycalanderfinal.Model;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

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

    public void getAllEvents(final String id,final Model.GetEventsListener listener) {
        Firebase stRef = myFirebaseRef.child("events").child(id);
        stRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Event> events = new ArrayList<Event>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Event event = snapshot.getValue(Event.class);
                    event.setId(snapshot.getKey());
                    events.add(event);
                }
                listener.done(events);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.done(null);
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
        addEventStRef = myFirebaseRef.child("events").child(id);
        tempUser = new User();
        UserFireBase userFireBase= new UserFireBase(myFirebaseRef);
        userFireBase.getNameForUser(id, new Model.getUserNameListener() {
            @Override
            public void success(String name) {

                event.setOwnerById(name);
                event.setTypeOfEvent("Private");
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


}
