package com.example.zivbru.myfamilycalanderfinal.Model;

/**
 * Created by zivbru on 5/4/2016.
 */
public class GroupsUsers {

    String id;
    String groupId;
    String userName;
    String eventId;

    public GroupsUsers() {
    }

    public GroupsUsers(String id, String groupId, String userName, String eventId) {
        this.id = id;
        this.groupId = groupId;
        this.userName = userName;
        this.eventId= eventId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
