package com.example.zivbru.myfamilycalanderfinal.Model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zivbru.myfamilycalanderfinal.DB.DataBaseHelper;

import java.util.ArrayList;


/**
 * Created by zivbru on 5/4/2016.
 */
public class ModelSQL {

    DataBaseHelper myDataBaseHelper;

    public ModelSQL() {
        myDataBaseHelper = new DataBaseHelper(MyApplication.getAppContext());
    }

    public void createTables(){
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        LastUpdateSql.create(db);
        UserSQL.createTableUserLogin(db);
        EventSQL.createTableEvents(db);
        GroupSQL.createTableGroup(db);
        TaskSQL.createTableTasks(db);
        GroupEventSQL.createTableEvents(db);
        GroupTaskSQL.createTableTasks(db);
        GroupUsersSQL.createTableGroup(db);
        /// add all the tables

    }

    public void dropTables(SQLiteDatabase sqLiteDatabase){
        LastUpdateSql.drop(sqLiteDatabase);
        EventSQL.drop(sqLiteDatabase);
        UserSQL.drop(sqLiteDatabase);
        GroupSQL.drop(sqLiteDatabase);
        TaskSQL.drop(sqLiteDatabase);
        GroupEventSQL.drop(sqLiteDatabase);
        GroupTaskSQL.drop(sqLiteDatabase);

    }

    public void insertUser(User user) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        UserSQL.InsertUser(user, db);
    }

    public String  getLoggedinUser() {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        return UserSQL.getLoggedinUser("1", db);
    }

    public void insertLogin(String userId,String loogedIn) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        UserSQL.InsertLogin(userId, loogedIn, db);
    }

    public void updateLogin(String userId,String loogedIn) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        UserSQL.updateLogin(userId, loogedIn, db);
    }

    public void insertGroup(Group group) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        GroupSQL.InsertGroup(group, db);
    }
    public void insertTask(Task task) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        TaskSQL.InsertTask(task, db);
    }

    public void insertGroupUsers(GroupsUsers groupsUsers) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        GroupsUsersSQL.InsertGroupUsers(groupsUsers, db);
    }

    public void insertEvents(Event event) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        EventSQL.InsertEvent(event, db);
    }

    public Integer deleteUser(String userName) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        return  UserSQL.deleteUser(userName, db);
    }

    public Integer deleteGroup(String groupId) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        return  GroupSQL.deleteGroup(groupId, db);
    }

    public Integer deleteTask(String taskId) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        return  TaskSQL.deleteTask(taskId, db);
    }

    public Integer deleteGroupsUser(String groupUserId) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        return  GroupsUsersSQL.deleteGroupUser(groupUserId, db);
    }

    public Integer deleteEvent(String eventId) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        return  EventSQL.deleteEvent(eventId, db);
    }

    public  boolean updateUser(User user) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        return UserSQL.updateUser(user, db);
    }

    public  boolean updateGroup(Group group) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        return GroupSQL.updateGroup(group, db);
    }

    public  boolean updateTask(Task task) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        return TaskSQL.updateTask(task, db);
    }

    public  boolean updateGroupUsers(GroupsUsers groupsUsers) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        return GroupsUsersSQL.updateGroupUsers(groupsUsers, db);
    }

    public  boolean updateEvents(Event event) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        return EventSQL.updateEvent(event, db);
    }

    public Cursor getUserByUserName(String userName) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        return UserSQL.getUserByUserName(userName, db);
    }

    public User getUser (Cursor cursor){
        return  UserSQL.getUser(cursor);
    }

    public Cursor getTaskById(String taskId) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        return TaskSQL.getTaskById(taskId, db);
    }

    public Task getTask(Cursor cursor) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        return TaskSQL.getTask(cursor);
    }
    public Cursor getGroupUserById(String groupUserId) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        return GroupsUsersSQL.getGroupUserById(groupUserId, db);
    }

    public GroupsUsers getGroupUser(Cursor cursor) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        return GroupsUsersSQL.getGroupUser(cursor);
    }

    public Cursor getGroupById(String groupId) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        return GroupSQL.getGroupById(groupId, db);
    }

    public Group getGroup(Cursor cursor) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        return GroupSQL.getGroup(cursor);
    }

    public Cursor getEventById(String eventId) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        return EventSQL.getEventById(eventId, db);
    }

    public Event getEvent(Cursor cursor) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        return EventSQL.getEvent(cursor);
    }

    public ArrayList<String> getEventsName(String userId) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        return EventSQL.getEventsName(userId, db);
    }

    public SQLiteDatabase getWritableDB(){
        return myDataBaseHelper.getWritableDatabase();
    }

    public  SQLiteDatabase getReadbleDB(){
        return myDataBaseHelper.getReadableDatabase();
    }


    public String getGroupIdByName(String groupName) {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        return GroupSQL.getGroupIdByName(groupName, db);
    }

    public  ArrayList<String> getAllGroupsId() {
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        return GroupSQL.getAllGroupsId(db);
    }
}





