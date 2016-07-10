package com.example.zivbru.myfamilycalanderfinal.Model;

/**
 * Created by zivbru on 7/7/2016.
 */
public class GroupUsers {

    String id;
    String groupId;
    String user;

    public GroupUsers() {
    }

    public GroupUsers( String groupId, String user) {

        this.groupId = groupId;
        this.user = user;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
