package com.example.zivbru.myfamilycalanderfinal.Model;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by zivbru on 5/18/2016.
 */
public class GroupsEventFireBase {

    Firebase myFirebaseRef;
    Firebase addEventStRef;
    private User tempUser;

    public GroupsEventFireBase(Firebase myFirebaseRef) {
        this.myFirebaseRef=myFirebaseRef;
    }

    public void getAllGroupsEvents(final String id,String lastUpdateDate,  String groupId,final Model. GetListEventListener groupsEventsListener) {
            Firebase stRef = myFirebaseRef.child("groupsEvents").child(groupId);
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
                    groupsEventsListener.onResult(events);
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    groupsEventsListener.onCancel();
                }
            });


    }

    public void AddGroupEvent(final Event event,final String id,String groupId,final Model.SignupListener listener) {
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = new GregorianCalendar();
        TimeZone timeZone = calendar.getTimeZone();
        calendar.setTimeZone(timeZone);
        Date updateTime = calendar.getInstance(timeZone).getTime();
        String date = null;
        date =  dateFormatGmt.format(updateTime);
        event.setLastUpdate(date);

        addEventStRef = myFirebaseRef.child("groupsEvents").child(groupId);
        tempUser = new User();
        UserFireBase userFireBase= new UserFireBase(myFirebaseRef);
        userFireBase.getNameForUser(id, new Model.getUserNameListener() {
            @Override
            public void success(String name) {
                event.setOwnerById(name);
                event.setTypeOfEvent("Public");
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
                event.setId(eventId);
                eventRef.setValue(event);
                tempUser.insertEventById(eventId);
                stRef1.setValue(tempUser);
                listener.success();
            }
        });
    }

    public void getGroupEvent(String groupId,String eventId, final Model.GetEventListener listener) {

        Firebase stRef = myFirebaseRef.child("groupsEvents").child(groupId).child(eventId);
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

    public void deleteEvent(String userId, String eventId,String groupId, Model.SignupListener listener) {
        Firebase stRef = myFirebaseRef.child("groupsEvents").child(groupId).child(eventId);
        stRef.removeValue();
        stRef = myFirebaseRef.child("users").child(userId).child("eventsById").child(eventId);
        stRef.removeValue();

        listener.success();
    }
}
