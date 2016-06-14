package com.example.zivbru.myfamilycalanderfinal.Model;

import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by zivbru on 5/8/2016.
 */
public class UserFireBase {

    Firebase myFirebaseRef;
    String name;

    public UserFireBase(Firebase myFirebaseRef) {
        this.myFirebaseRef=myFirebaseRef;
    }

    public void insertUser(User user) {

        Firebase stRef = myFirebaseRef.child("users").child(user.getUserId());
        stRef.setValue(user);

    }

    public void login(String email, String pwd, final Model.LoginListener listener) {
        myFirebaseRef.authWithPassword(email, pwd, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                listener.success(authData);

            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                listener.fail(firebaseError.getMessage());
            }
        });

    }

    public void signup(final String email, final String pwd, final Model.LoginListener listener) {
        myFirebaseRef.createUser(email, pwd, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                login(email, pwd, new Model.LoginListener() {
                    @Override
                    public void success(AuthData authData) {
                        listener.success(authData);
                    }

                    @Override
                    public void fail(String msg) {
                        listener.fail(msg);
                    }
                });

            }

            @Override
            public void onError(FirebaseError firebaseError) {
                listener.fail(firebaseError.getMessage());
            }
        });

    }

    public void getNameForUser(final String id,  final Model.getUserNameListener listener){

        Firebase stRef = myFirebaseRef.child("users");
        stRef.addValueEventListener(new ValueEventListener() {
            User user = new User();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    user = snapshot.getValue(User.class);
                    if (user.getUserId().equals(id)) {
                        name = user.getFirstName() + " " + user.getLastName();
                        listener.success(name);
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError error) {
                name = "Unknowen";
                listener.fail("failed");
            }
        });

    }

    public void getUser(final String id, final Model.UserListener listener) {
        Firebase stRef = myFirebaseRef.child("users");
        stRef.addListenerForSingleValueEvent(new ValueEventListener() {
            User user = new User();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    user = snapshot.getValue(User.class);
                    if(user.getUserId().equals(id))
                      listener.done(user);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.done(null);
            }
        });
    }

    public void getUsers(final Model.GetUsersListener listener) {
        Firebase stRef = myFirebaseRef.child("users");
        stRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> usersList = new ArrayList<String>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    usersList.add(user.getFirstName()+" "+user.lastName);
                }
                listener.done(usersList);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.done(null);
            }
        });
    }

    public void deleteUser(User user, Model.UserListener listener) {
        Firebase stRef = myFirebaseRef.child("user").child(user.getUserName());
        stRef.removeUser(user.getUserName(), user.getPassword(), new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                Log.d("user removed", "");
            }

            @Override
            public void onError(FirebaseError firebaseError) {

            }
        });
    }

    public String getUserId(){
        AuthData authData = myFirebaseRef.getAuth();
        Log.d("AuthData", String.valueOf(authData != null));
        if (authData != null) {
            return authData.getUid();
        }
        return null;
    }


}
