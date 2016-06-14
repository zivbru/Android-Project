package com.example.zivbru.myfamilycalanderfinal.Model;

import android.content.Context;

import com.firebase.client.Firebase;


/**
 * Created by zivbru on 5/4/2016.
 */
public class ModelFireBase {

    public static final String FIREBASE_URL = "https://my-family-calander.firebaseio.com/";

    Firebase myFirebaseRef;
    UserFireBase userFireBase;
    EventFireBase eventFireBase;
    TaskFireBase taskFireBase;
    GroupsTaskFireBase groupsTaskFireBase;
    GroupsEventFireBase groupsEventFireBase;
    GroupFireBase groupFireBase;
    public Firebase getMyFirebaseRef() {
        return myFirebaseRef;
    }

    public ModelFireBase(Context context) {
        Firebase.setAndroidContext(context);
        myFirebaseRef = new Firebase(FIREBASE_URL);
        userFireBase= new UserFireBase(myFirebaseRef);
        eventFireBase= new EventFireBase(myFirebaseRef);
        taskFireBase= new TaskFireBase(myFirebaseRef);
        groupsTaskFireBase = new GroupsTaskFireBase(myFirebaseRef);
        groupsEventFireBase= new GroupsEventFireBase(myFirebaseRef);
        groupFireBase= new GroupFireBase(myFirebaseRef);
    }

    public UserFireBase getUserFireBase() {
        return userFireBase;
    }

    public void signup(final String email, final String pwd, final Model.LoginListener listener) {
        userFireBase.signup(email, pwd, listener);
    }

    public void login(String email, String pwd, final Model.LoginListener listener){
        userFireBase.login(email, pwd, listener);
    }

    public void getUsers(final Model.GetUsersListener listener) {
        userFireBase.getUsers(listener);
    }

    public void getGroups(final Model.GetUsersListener listener) {
        groupFireBase.getGroups(listener);
    }

    public void getAllEvents(String id, Model.GetEventsListener listener){
        eventFireBase.getAllEvents(id, listener);
    }

    public void AddEvent(Event event , String id,final Model.SignupListener listener){
        eventFireBase.AddEvent(event, id, listener);
    }

    public void addTask(Task task , String id,final Model.SignupListener listener){
        taskFireBase.addTask(task, id, listener);
    }

    public void logout() {myFirebaseRef.unauth();}

    public void getAllTasks(String id, Model.GetTasksListener listener){
        taskFireBase.getAllTasks(id, listener);
    }

    public void getEvent(String userId,String eventId, Model.GetEventListener listener) {
        eventFireBase.getEvent(userId, eventId, listener);
    }

    public void getAllGroupsTasks(String userId, Model.GetTasksListener getGroupsTasksListener) {
        groupsTaskFireBase.getAllGroupsTasks(userId, getGroupsTasksListener);
    }

    public void AddGroupTask(Task task, String id, Model.SignupListener listener) {
        groupsTaskFireBase.addGroupTask(task, id, listener);
    }

    public void getAllGroupsEvents(String userId, Model.GetEventsListener getGroupsEventsListener) {
        groupsEventFireBase.getAllGroupsEvents(userId, getGroupsEventsListener);
    }

    public void AddGroupEvent(Event event, String id, Model.SignupListener listener) {
        groupsEventFireBase.AddGroupEvent(event, id, listener);
    }

    public void getAllEventsName(String id, Model.GetEventsNameListener listener) {
        eventFireBase.getAllEventsName(id, listener);
    }

    public void getAllGroup(String userId, Model.GetGroupslistner getGroupslistner) {
        groupFireBase.getAllGroup(userId, getGroupslistner);
    }

    public void getTask(String userId, String taskId, Model.GetTaskListener listener) {
        taskFireBase.getTask(userId, taskId, listener);

    }
    public void getGroupTask(String userId, String taskId, Model.GetTaskListener listener) {
        taskFireBase.getGroupTask(userId, taskId, listener);

    }


    public void getGroupEvent(String userId, String taskId, Model.GetEventListener getTaskListener) {
        groupsEventFireBase.getGroupEvent(userId, taskId, getTaskListener);
    }

    public void AddGroup(Group group,  Model.addGroupListener listener) {
        groupFireBase.AddGroup(group, listener);
    }

    public void getUser(String id , Model.UserListener userListener){
       userFireBase.getUser(id,userListener);
    }
}
