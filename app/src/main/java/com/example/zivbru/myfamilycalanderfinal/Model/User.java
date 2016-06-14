package com.example.zivbru.myfamilycalanderfinal.Model;

import java.util.ArrayList;

/**
 * Created by zivbru on 5/4/2016.
 */
public class User {

    String userId;
    String userName;
    String password;
    String firstName;
    String lastName;
    String adress;
    String phone;
    String pictureName;
    String birthDate;
    ArrayList<String> eventsById;
    ArrayList<String> taskById;
    ArrayList<String> groupsById;



    public User() {
        eventsById=new ArrayList<String>();
        taskById= new ArrayList<String>();
        groupsById= new ArrayList<String>();
    }

    public User(String userId,String userName, String password, String firstName, String lastName, String adress,
                String phone, String pictureName, String birthDate ) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.adress = adress;
        this.phone = phone;
        this.pictureName = pictureName;
        this.birthDate = birthDate;
        this.userId=userId;
        eventsById=new ArrayList<String>();
        taskById= new ArrayList<String>();
        groupsById= new ArrayList<String>();

    }



    public User(String userId,String userName, String password, String firstName, String lastName, String adress,
                String phone, String pictureName, String birthDate, ArrayList<String> eventsById, ArrayList<String> groupsById) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.adress = adress;
        this.phone = phone;
        this.pictureName = pictureName;
        this.birthDate = birthDate;
        this.userId=userId;
        this.eventsById= eventsById;
        this.taskById= new ArrayList<String>();
        this.groupsById= groupsById;
    }

    public User(String userId,String userName, String password, String firstName, String lastName, String adress,
                String phone, String pictureName, String birthDate, ArrayList<String> eventsById) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.adress = adress;
        this.phone = phone;
        this.pictureName = pictureName;
        this.birthDate = birthDate;
        this.userId=userId;
        this.eventsById= eventsById;
        this.taskById= new ArrayList<String>();
        this.groupsById= new ArrayList<String>();
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<String> getEventsById() {
        return eventsById;
    }

    public void setEventsById(ArrayList<String> eventsById) {
        this.eventsById = eventsById;
    }

    public ArrayList<String> getTaskById() {
        return taskById;
    }

    public void setTaskById(ArrayList<String> taskById) {
        this.taskById = taskById;
    }

    public ArrayList<String> getGroupsById() {
        return groupsById;
    }

    public void setGroupsById(ArrayList<String> groupsById) {
        this.groupsById = groupsById;
    }

    public void insertTaskById(String taskById) {
        this.taskById.add(taskById);
    }

    public void insertEventById(String eventById)  {
        this.eventsById.add(eventById);
    }

    public void insertGroupById(String groupById) {
        this.groupsById.add(groupById);
    }
}
