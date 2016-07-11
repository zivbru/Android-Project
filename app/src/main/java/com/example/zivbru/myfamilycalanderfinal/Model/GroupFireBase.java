package com.example.zivbru.myfamilycalanderfinal.Model;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by zivbru on 5/23/2016.
 */
public class GroupFireBase {

    Firebase myFirebaseRef;
    Firebase groupRef;
    Group group;
    String name;

    public GroupFireBase(Firebase myFirebaseRef) {
        this.myFirebaseRef=myFirebaseRef;
    }

    public void getGroup(String Id, final Model.GroupListener listener) {
        Firebase stRef = myFirebaseRef.child("groups").child(Id);
        stRef.addListenerForSingleValueEvent(new ValueEventListener() {
            Group group = new Group();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                group = dataSnapshot.getValue(Group.class);
                listener.done(group);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.done(null);
            }
        });
    }

    public void getGroups(final Model.GetGroupsListener listener) {
        Firebase stRef = myFirebaseRef.child("groups");
        stRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Group> groupslist = new ArrayList<Group>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Group group = snapshot.getValue(Group.class);
                    groupslist.add(group);
                }
                listener.done(groupslist);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.done(null);
            }
        });
    }

    public void getAllGroup(final String userId, final String lastUpdateDate,  final Model.GetGroupsListListener getGroupslistner) {

        Firebase stRef = myFirebaseRef.child("groups");
        Query queryRef = stRef.orderByChild("lastUpdate").startAt(lastUpdateDate);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Group> groupslist = new ArrayList<Group>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Group group = snapshot.getValue(Group.class);
                    if (group.getUsersList().contains(userId))
                        groupslist.add(group);
                }
                getGroupslistner.onResult(groupslist);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                getGroupslistner.onCancel();
            }
        });
    }




    public void AddGroup(final Group group,final Model.addGroupListener listener){
        Firebase stRef = myFirebaseRef.child("groups");
        UserFireBase userFireBase= new UserFireBase(myFirebaseRef);
        groupRef = stRef.push();
        String groupId = groupRef.getKey();
        for (final String id :group.getUsersList()) {
            userFireBase.getUser(id, new Model.UserListener() {
                @Override
                public void done(User user) {
                    String groupId = groupRef.getKey();
                    Firebase stRef1 = myFirebaseRef.child("users").child(id);
                    user.insertGroupById(groupId);
                    stRef1.setValue(user);
                }
            });
        }
        group.setId(groupId);
        stRef.child(groupId).setValue(group);
        listener.success(group);

    }

    public  void getAllUsersFromGroup(String selectedGroup, final Model.GetUsersFromGroupListener listener) {
        Firebase stRef = myFirebaseRef.child("groups").child(selectedGroup);
        stRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> usersName = new ArrayList<String>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Group group = snapshot.getValue(Group.class);
                    for (String s:group.getUsersList()) {
                        usersName.add(s);
                    }
                }
                listener.done(usersName);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.done(null);
            }
        });
    }

}

