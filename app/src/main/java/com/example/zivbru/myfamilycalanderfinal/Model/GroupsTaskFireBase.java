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
public class GroupsTaskFireBase {

    Firebase myFirebaseRef;
    private User tempUser;

    public GroupsTaskFireBase(Firebase myFirebaseRef) {
        this.myFirebaseRef=myFirebaseRef;
    }

    public void getAllGroupsTasks(String userId,String lastUpdateDate,String groupId, final Model.GetListTaskListener listener) {

        Firebase stRef = myFirebaseRef.child("groupsTasks").child(groupId);
        Query queryRef = stRef.orderByChild("lastUpdate").startAt(lastUpdateDate);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Task> groupsTasks = new ArrayList<Task>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Task task = snapshot.getValue(Task.class);
                    task.setId(snapshot.getKey());
                    groupsTasks.add(task);
                }
                listener.onResult(groupsTasks);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.onCancel();
            }
        });
    }

    public void addGroupTask(final Task task,String id,String groupId ,final Model.SignupListener listener) {
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = new GregorianCalendar();
        TimeZone timeZone = calendar.getTimeZone();
        calendar.setTimeZone(timeZone);
        Date updateTime = calendar.getInstance(timeZone).getTime();
        String date = null;
        date =  dateFormatGmt.format(updateTime);
        task.setLastUpdate(date);

        Firebase stRef = myFirebaseRef.child("groupsTasks").child(groupId);
        tempUser = new User();
        UserFireBase userFireBase = new UserFireBase(myFirebaseRef);
        userFireBase.getNameForUser(id, new Model.getUserNameListener() {
            @Override
            public void success(String name) {

                task.setOwnerId(name);
            }

            @Override
            public void fail(String msg) {

            }
        });

        stRef.push().setValue(task);
        listener.success();
    }

    public void getGroupsTask(String userId, String taskId, final Model.GetTaskListener listener) {
        Firebase stRef = myFirebaseRef.child("groupsTasks").child(userId).child(taskId);
        stRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Task task = dataSnapshot.getValue(Task.class);
                listener.done(task);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.done(null);
            }
        });
    }

    public void deleteGroupTask(String userId, String taskId,String groupId, Model.SignupListener listener) {
        Firebase stRef = myFirebaseRef.child("groupsTasks").child(groupId).child(taskId);
        stRef.removeValue();
        listener.success();

    }
}
