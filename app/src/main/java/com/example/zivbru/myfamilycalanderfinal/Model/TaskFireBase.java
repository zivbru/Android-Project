package com.example.zivbru.myfamilycalanderfinal.Model;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by zivbru on 5/16/2016.
 */
public class TaskFireBase {

    Firebase myFirebaseRef;
    Firebase addTaskstRef;
    User tempUser;


    public TaskFireBase(Firebase myFirebaseRef) {
        this.myFirebaseRef=myFirebaseRef;
    }

    public void getAllTasks(final String userId, final Model.GetTasksListener listener) {
        Firebase stRef = myFirebaseRef.child("tasks").child(userId);
        stRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Task> tasks = new ArrayList<Task>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Task task = snapshot.getValue(Task.class);
                    task.setId(snapshot.getKey());
                    tasks.add(task);
                }
                listener.done(tasks);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.done(null);
            }
        });
    }

    public void addTask(final Task task,final String id,final Model.SignupListener listener) {
        addTaskstRef = myFirebaseRef.child("tasks").child(id);
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
        userFireBase.getUser(id, new Model.UserListener() {
            @Override
            public void done(User user) {
                tempUser = user;
                Firebase taskRef = addTaskstRef.push();
                String taskId = taskRef.getKey();
                Firebase stRef1 = myFirebaseRef.child("users").child(id);
                taskRef.setValue(task);
                tempUser.insertEventById(taskId);
                stRef1.setValue(tempUser);
                listener.success();
            }
        });

    }

    public void getTask(String userId, String taskId, final Model.GetTaskListener listener) {
        Firebase stRef = myFirebaseRef.child("tasks").child(userId).child(taskId);
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

    public void getGroupTask(String userId, String taskId, final Model.GetTaskListener listener) {
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
}

