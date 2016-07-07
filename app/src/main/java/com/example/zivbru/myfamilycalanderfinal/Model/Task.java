package com.example.zivbru.myfamilycalanderfinal.Model;

/**
 * Created by zivbru on 5/4/2016.
 */
public class Task {

    String id;
    String title;
    String targetDate;
    String assigenedTo;
    String ownerId;
    String relatedEvent;
    String description;
    String groupId;
    String TypeOfTask;
    String lastUpdate;

    public Task() {
    }

    public Task(String title, String targetDate, String assigenedTo, String ownerId, String relatedEvent,String description) {
        this.title = title;
        this.targetDate = targetDate;
        this.assigenedTo = assigenedTo;
        this.ownerId = ownerId;
        this.relatedEvent = relatedEvent;

        this.description = description;
    }

    public Task(String title, String targetDate, String ownerId, String relatedEvent,String description) {
        this.title = title;
        this.targetDate = targetDate;
        this.ownerId = ownerId;
        this.relatedEvent = relatedEvent;

        this.description = description;
    }

    public Task(String id, String title, String targetDate, String ownerId, String relatedEvent, String description, String groupId, String typeOfTask, String lastUpdate) {
        this.id = id;
        this.title = title;
        this.targetDate = targetDate;
        this.ownerId = ownerId;
        this.relatedEvent = relatedEvent;
        this.description = description;
        this.groupId = groupId;
        TypeOfTask = typeOfTask;
        this.lastUpdate = lastUpdate;
    }

    public Task(String title, String targetDate, String description) {
        this.title=title;
        this.targetDate=targetDate;
        this.description=description;
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

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public String getAssigenedTo() {
        return assigenedTo;
    }

    public void setAssigenedTo(String assigenedTo) {
        this.assigenedTo = assigenedTo;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getRelatedEvent() {
        return relatedEvent;
    }

    public void setRelatedEvent(String relatedEvent) {
        this.relatedEvent = relatedEvent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTypeOfTask() {
        return TypeOfTask;
    }

    public void setTypeOfTask(String typeOfTask) {
        TypeOfTask = TypeOfTask;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
