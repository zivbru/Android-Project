package com.example.zivbru.myfamilycalanderfinal.Model;

import android.content.Context;

import com.firebase.client.Firebase;

import java.util.ArrayList;


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

    public void getGroups(final Model.GetGroupsListener listener) {
        groupFireBase.getGroups(listener);
    }

    public void getAllEvents(String id,String lastUpdateDate, Model.GetListEventListener listener){
        eventFireBase.getAllEvents(id, lastUpdateDate, listener);
    }

    public void AddEvent(Event event , String id,final Model.SignupListener listener){
        eventFireBase.AddEvent(event, id, listener);
    }

    public void addTask(Task task , String id,final Model.SignupListener listener){
        taskFireBase.addTask(task, id, listener);
    }

    public void logout() {myFirebaseRef.unauth();}

    public void getAllTasks(String id, String lastUpdateDate, Model.GetListTaskListener listener){
        taskFireBase.getAllTasks(id, lastUpdateDate, listener);
    }

    public void getEvent(String userId,String eventId, Model.GetEventListener listener) {
        eventFireBase.getEvent(userId, eventId, listener);
    }

    public void getAllGroupsTasks(String userId,String lastUpdateDate,String groupId, Model.GetListTaskListener getGroupsTasksListener) {
        groupsTaskFireBase.getAllGroupsTasks(userId, lastUpdateDate, groupId, getGroupsTasksListener);
    }

    public void AddGroupTask(Task task, String id,String groupId , Model.SignupListener listener) {
        groupsTaskFireBase.addGroupTask(task, id, groupId, listener);
    }

    public void getAllGroupsEvents(String userId,String lastUpdateDate,  String groupId,Model.GetListEventListener groupsEventsListener) {
        groupsEventFireBase.getAllGroupsEvents(userId, lastUpdateDate, groupId, groupsEventsListener);
    }

    public void AddGroupEvent(Event event, String id,String groupId, Model.SignupListener listener) {
        groupsEventFireBase.AddGroupEvent(event, id, groupId, listener);
    }

    public void getAllEventsName(String id, Model.GetEventsNameListener listener) {
        eventFireBase.getAllEventsName(id, listener);
    }

    public void getAllGroup(String userId,String lastUpdateDate, Model.GetGroupsListListener getGroupslistner) {
        groupFireBase.getAllGroup(userId, lastUpdateDate, getGroupslistner);
    }

    public void getTask(String userId, String taskId, Model.GetTaskListener listener) {
        taskFireBase.getTask(userId, taskId, listener);

    }
    public void getGroupTask(String userId, String taskId,String groupId, Model.GetTaskListener listener) {
        taskFireBase.getGroupTask(userId, taskId, groupId, listener);

    }


    public void getGroupEvent(String groupId, String eventId, Model.GetEventListener getTaskListener) {
        groupsEventFireBase.getGroupEvent(groupId, eventId, getTaskListener);
    }

    public void AddGroup(Group group,  Model.addGroupListener listener) {
        groupFireBase.AddGroup(group, listener);
    }

    public void getUser(String id , Model.UserListener userListener){
       userFireBase.getUser(id, userListener);
    }

    public void deleteEvent(String userId, String eventId, Model.SignupListener listener) {
        eventFireBase.deleteEvent(userId, eventId, listener);
    }

    public void deleteTask(String userId, String taskId, Model.SignupListener listener) {
        taskFireBase.deleteTask(userId, taskId, listener);
    }

    public void deleteGroupEvent(String userId, String eventId,String groupId ,Model.SignupListener listener) {
        groupsEventFireBase.deleteEvent(userId, eventId, groupId, listener);
    }

    public void deleteGroupTask(String userId, String taskId,String groupId, Model.SignupListener listener) {
        groupsTaskFireBase.deleteGroupTask(userId, taskId, groupId, listener);
    }

    public void getAllUsersFromGroup(String selectedGroup,  Model.GetUsersFromGroupListener listener) {
        groupFireBase.getAllUsersFromGroup(selectedGroup, listener);
    }

    public void getAllUsersById(ArrayList<String> usersNames, Model.GetUsersListListener getGroupsListListener) {
        userFireBase.getAllUsersById(usersNames, getGroupsListListener);
    }

    public void getNameForUser(String usersNames, Model.getUserNameListener listener) {
        userFireBase.getNameForUser(usersNames, listener);
    }

    public void getIdForUser(String name, Model.getUserNameListener listener) {
        userFireBase.getIdForUser(name, listener);
    }

    public void getAllUpcomingEvents(String id,Model.GetUsersListener listener) {
        eventFireBase.getAllUpcomingEvents(id,listener);
//        taskFireBase.getAllUpcomingEvents(id,listener);


    }
}
