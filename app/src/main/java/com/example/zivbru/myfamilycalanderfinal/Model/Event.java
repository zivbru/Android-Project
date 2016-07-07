package com.example.zivbru.myfamilycalanderfinal.Model;

import java.util.ArrayList;

/**
 * Created by zivbru on 5/4/2016.
 */
public class Event {
    String id;
    String name;
    String startDate;
    String endDate;
    String description;
    String ownerById;
    ArrayList<String> users;
    ArrayList<String> tasks;
    String groupName;
    String typeOfEvent;
    String lastUpdate;

    public Event() {
    }

    public Event(String name, String startDate, String endDate, String description, String ownerById) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.ownerById = ownerById;

    }

    public Event(String name, String startDate, String endDate, String description, String ownerById,String groupName) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.ownerById = ownerById;
        this.groupName=groupName;

    }

    public Event(String id, String name, String startDate, String endDate, String description, String ownerById, String groupName, String typeOfEvent, String lastUpdate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.ownerById = ownerById;
        this.groupName = groupName;
        this.typeOfEvent = typeOfEvent;
        this.lastUpdate = lastUpdate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerById() {
        return ownerById;
    }

    public void setOwnerById(String ownerById) {
        this.ownerById = ownerById;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public ArrayList<String> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<String> tasks) {
        this.tasks = tasks;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTypeOfEvent() {
        return typeOfEvent;
    }

    public void setTypeOfEvent(String typeOfEvent) {
        this.typeOfEvent = typeOfEvent;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
