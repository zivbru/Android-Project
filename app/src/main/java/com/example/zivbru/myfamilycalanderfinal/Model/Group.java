package com.example.zivbru.myfamilycalanderfinal.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zivbru on 5/4/2016.
 */
public class Group {

    String id;
    String title;
    String pictureName;
    ArrayList<String> usersList;
    ArrayList<String> relatedEvents;
    ArrayList<String> relatedTasks;
    String lastUpdate;


    public Group() {
        this.usersList = new ArrayList<String>();
        this.relatedEvents = new ArrayList<String>();
        this.relatedTasks = new ArrayList<String>();

    }

    public Group(String id, String title, String pictureName, String lastUpdate) {
        this.id = id;
        this.title = title;
        this.pictureName = pictureName;
        this.lastUpdate = lastUpdate;
    }

    public Group(String id, String title, String pictureName) {
        this.id = id;
        this.title = title;
        this.pictureName = pictureName;
        this.usersList = new ArrayList<String>();
        this.relatedEvents = new ArrayList<String>();
        this.relatedTasks = new ArrayList<String>();

    }

    public Group(String id, String title, String pictureName, ArrayList<String> usersList) {
        this.id = id;
        this.title = title;
        this.pictureName = pictureName;
        this.usersList = usersList;
        this.relatedEvents = new ArrayList<String>();
        this.relatedTasks = new ArrayList<String>();

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public ArrayList<String> getUsersList() {
        return usersList;
    }

    public void setUsersList(ArrayList<String> usersList) {
        this.usersList = usersList;
    }

    public ArrayList<String> getRelatedEvents() {
        return relatedEvents;
    }

    public void setRelatedEvents(ArrayList<String> relatedEvents) {
        this.relatedEvents = relatedEvents;
    }

    public ArrayList<String> getRelatedTasks() {
        return relatedTasks;
    }

    public void setRelatedTasks(ArrayList<String> relatedTasks) {
        this.relatedTasks = relatedTasks;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
