package com.example.zivbru.myfamilycalanderfinal.Model;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.firebase.client.AuthData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zivbru on 5/4/2016.
 */
public class Model {

    ModelFireBase firebaseModel;
    ModelSQL modelSQL;
    ModelCloudinary modelCloudinary;


    private final static Model instance = new Model();

    private Model(){
        firebaseModel = new ModelFireBase(MyApplication.getAppContext());
        modelSQL = new ModelSQL();
        modelCloudinary= new ModelCloudinary();
//        modelSQL.dropTables(modelSQL.getWritableDB());
    }

    public static Model instance(){
        return instance;
    }

    public void createTables(){
        modelSQL.createTables();
    }

    public ModelFireBase getFirebaseModel() {
        return firebaseModel;
    }



    public interface GetTasksListener {
        void done(ArrayList<Task> taskList);
    }

    public interface  GetGroupslistner{
        void done(ArrayList<Group> groups);
    }

    public interface UserListener {
        void done(User user);
    }

    public interface GroupListener {
        void done(Group group);
    }

    public interface GetUsersListener {
        void done(ArrayList<String> usersList);
    }

    public interface GetGroupsListener {
        void done(ArrayList<Group> groupsList);
    }

    public interface LoginListener {
        void success(AuthData authData);

        void fail(String msg);
    }

    public interface SignupListener {
        void success();

        void fail(String msg);
    }

    public interface addGroupListener {
        void success(Group group);

        void fail(String msg);
    }

    public interface getUserNameListener {
        void success(String name);

        void fail(String msg);
    }

    public interface GetEventsListener {
        void done(ArrayList<Event> eventList);
    }

    public interface GetEventsNameListener {
        void done(ArrayList<String> eventList);
    }

    public interface GetEventListener {
        void done(Event event);
    }

    public interface GetTaskListener {
        void done(Task task);
    }

    public interface GetUsersFromGroupListener {
        void done(ArrayList<String> users);
    }

    public interface GetListEventListener{
        void onResult(ArrayList<Event> events);
        void onCancel();
    }

    public interface GetListTaskListener{
        void onResult(ArrayList<Task> tasks);
        void onCancel();
    }

    public interface GetGroupsListListener{
        void onResult(ArrayList<Group> groups);
        void onCancel();
    }

    public interface GetUsersListListener{
        void onResult(ArrayList<String> groups);
        void onCancel();
    }


    public void signup(final String email, final String pwd, final LoginListener listener) {
        firebaseModel.signup(email, pwd, listener);
    }

    public void login(final String email, final String pwd, final LoginListener listener) {
        firebaseModel.login(email, pwd, listener);
    }

    public void getUsers(final Model.GetUsersListener listener) {
        firebaseModel.getUsers(listener);
    }

    public void getGroups(final Model.GetGroupsListener listener) {
        firebaseModel.getGroups(listener);
    }

    public void getAllEventsName(final String id, final GetEventsNameListener listener) {
        listener.done(modelSQL.getEventsName(id));
    }

    public void AddEvent(Event event , String id,final SignupListener listener) {
        firebaseModel.AddEvent(event, id, listener);
    }

    public void deleteEvent(final String userId, final String eventId, final Model.SignupListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                firebaseModel.deleteEvent(userId, eventId, listener);
                modelSQL.deleteEvent(eventId);
            }
        }).start();

    }

    public void deleteGroupEvent(String userId, String eventId,String groupId ,SignupListener listener) {
        firebaseModel.deleteGroupEvent(userId, eventId, groupId, listener);
    }

    public void deleteTask(String userId, String taskId, Model.SignupListener listener) {
        firebaseModel.deleteTask(userId, taskId, listener);
        modelSQL.deleteTask(taskId);
    }

    public void deleteGroupTask(String userId, String taskId,String groupId, SignupListener listener) {
        firebaseModel.deleteGroupTask(userId, taskId, groupId, listener);
    }

    public void AddGroup(Group group ,final addGroupListener listener) {
        firebaseModel.AddGroup(group, listener);
    }

    public void AddTask(Task task, String id, final SignupListener listener) {
        firebaseModel.addTask(task, id, listener);
    }

    public void AddGroupTask(Task task, String id,String groupId ,final SignupListener listener) {
        firebaseModel.AddGroupTask(task, id, groupId, listener);
    }

    public void AddGroupEvent(Event event, String id,String groupId, final SignupListener listener) {
        firebaseModel.AddGroupEvent(event, id, groupId, listener);
    }

    public void getEvent(String userId,String eventId, GetEventListener listener) {
        firebaseModel.getEvent(userId, eventId, listener);
    }

    public void getTask(String userId,String taskId, GetTaskListener listener) {
        firebaseModel.getTask(userId, taskId, listener);
    }

    public void getGroupTask(String userId, String taskId,String groupId, GetTaskListener getTaskListener) {
        firebaseModel.getGroupTask(userId, taskId,groupId, getTaskListener);
    }

    public void getGroupEvent(String groupId, String eventId, GetEventListener getTaskListener) {
        firebaseModel.getGroupEvent(groupId, eventId, getTaskListener);
    }

    public void saveImage(final Bitmap imageBitmap, final String imageName) {
        saveImageToFile(imageBitmap,imageName); // synchronously save image locally
        Thread d = new Thread(new Runnable() {  // asynchronously save image to parse
            @Override
            public void run() {
                modelCloudinary.saveImage(imageBitmap,imageName);
            }
        });
        d.start();
    }

    public interface LoadImageListener{
        public void onResult(Bitmap imageBmp);
    }

    private void saveImageToFile(Bitmap imageBitmap, String imageFileName){
        FileOutputStream fos;
        OutputStream out = null;
        try {
            //File dir = context.getExternalFilesDir(null);
            File dir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File imageFile = new File(dir,imageFileName);
            imageFile.createNewFile();

            out = new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

            //add the picture to the gallery so we dont need to manage the cache size
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(imageFile);
            mediaScanIntent.setData(contentUri);
            MyApplication.getAppContext().sendBroadcast(mediaScanIntent);
            Log.d("tag", "add image to cache: " + imageFileName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadImage(final String imageName, final LoadImageListener listener) {
        AsyncTask<String,String,Bitmap> task = new AsyncTask<String, String, Bitmap >() {
            @Override
            protected Bitmap doInBackground(String... params) {
//                Bitmap bmp = loadImageFromFile(imageName);              //first try to fin the image on the device
//                if (bmp == null) {                                      //if image not found - try downloading it from parse
                Bitmap bmp = modelCloudinary.loadImage(imageName);
                if (bmp != null) saveImageToFile(bmp,imageName);    //save the image locally for next time
//                }
                return bmp;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                listener.onResult(result);
            }
        };
        task.execute();
    }

    private Bitmap loadImageFromFile(String imageFileName){
        String str = null;
        Bitmap bitmap = null;
        try {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File imageFile = new File(dir,imageFileName);

            //File dir = context.getExternalFilesDir(null);
            InputStream inputStream = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(inputStream);
            Log.d("tag","got image from cache: " + imageFileName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public void updateLogin(String userId,String loggedin){
        modelSQL.updateLogin(userId, loggedin);
    }

    public String getLoggedinUser() {
        return modelSQL.getLoggedinUser();
    }

    public void getUser(String id, UserListener userListener){
        firebaseModel.getUser(id, userListener);
    }

    public void getAllGroupsTasks(final String userId,final GetListTaskListener listener) {
        final String lastUpdateDate = GroupTaskSQL.getLastUpdateDate(modelSQL.getReadbleDB(),userId);
        ArrayList<String> groupsId = Model.instance().getAllGroupsId(userId);
        for (String groupId : groupsId) {
            firebaseModel.getAllGroupsTasks(userId, lastUpdateDate,groupId, new GetListTaskListener() {
                @Override
                public void onResult(ArrayList<Task> tasks) {
                    if (tasks != null && tasks.size() > 0) {
                        String recent = lastUpdateDate;
                        for (Task task : tasks) {
                            GroupTaskSQL.InsertTask(task,userId, modelSQL.getWritableDB());
                            if (recent == null || task.getLastUpdate().compareTo(recent) > 0) {
                                recent = task.getLastUpdate();
                            }
                            GroupTaskSQL.setLastUpdateDate(modelSQL.getWritableDB(), recent,userId);
                        }
                    }
                    ArrayList<Task> taskList = GroupTaskSQL.getAllTasks(modelSQL.getReadbleDB(),userId);
                    listener.onResult(taskList);
                }

                @Override
                public void onCancel() {

                }
            });
        }
    }

    public void getAllTasks(final String userId, final GetListTaskListener listener){
        final String lastUpdateDate = TaskSQL.getLastUpdateDate(modelSQL.getReadbleDB(),userId);
        firebaseModel.getAllTasks(userId, lastUpdateDate, new GetListTaskListener() {
            @Override
            public void onResult(ArrayList<Task> tasks) {
                if (tasks != null && tasks.size() > 0) {
                    String recent = lastUpdateDate;
                    for (Task task : tasks) {
                        TaskSQL.InsertTask(task,userId, modelSQL.getWritableDB());
                        if (recent == null || task.getLastUpdate().compareTo(recent) > 0) {
                            recent = task.getLastUpdate();
                        }
                        TaskSQL.setLastUpdateDate(modelSQL.getWritableDB(), recent,userId);
                    }
                }
                ArrayList<Task> taskList = TaskSQL.getAllTasks(modelSQL.getReadbleDB(),userId);
                listener.onResult(taskList);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    public void getAllGroupsEvents(final String userId,  final GetListEventListener groupsEventsListener) {
        final String lastUpdateDate = GroupEventSQL.getLastUpdateDate(modelSQL.getReadbleDB(),userId);
        ArrayList<String> groupsId=Model.instance().getAllGroupsId(userId);
        for (String groupId:groupsId) {
            firebaseModel.getAllGroupsEvents(userId, lastUpdateDate, groupId, new GetListEventListener() {
                @Override
                public void onResult(ArrayList<Event> events) {

                    if (events != null && events.size() > 0) {
                        String recent = lastUpdateDate;
                        for (Event event : events) {
                            GroupEventSQL.InsertEvent(event,userId, modelSQL.getWritableDB());
                            if (recent == null || event.getLastUpdate().compareTo(recent) > 0) {
                                recent = event.getLastUpdate();
                            }
                            GroupEventSQL.setLastUpdateDate(modelSQL.getWritableDB(), recent,userId);
                        }
                    }
                    ArrayList<Event> groupEventsList = GroupEventSQL.getAllEvents(modelSQL.getReadbleDB(),userId);
                    groupsEventsListener.onResult(groupEventsList);
                }

                @Override
                public void onCancel() {

                }
            });
        }
    }

    private ArrayList<String> getAllGroupsId(String userId) {
        return modelSQL.getAllGroupsId(userId);
    }

    public void getAllEvents(final String id, final GetListEventListener listener) {
        final String lastUpdateDate = EventSQL.getLastUpdateDate(modelSQL.getReadbleDB(),id);
        firebaseModel.getAllEvents(id, lastUpdateDate, new GetListEventListener() {
            @Override
            public void onResult(ArrayList<Event> events) {

                if (events != null && events.size() > 0) {
                    String recent = lastUpdateDate;
                    for (Event event : events) {
                        EventSQL.InsertEvent(event,id, modelSQL.getWritableDB());
                        if (recent == null || event.getLastUpdate().compareTo(recent) > 0) {
                            recent = event.getLastUpdate();
                        }
                        EventSQL.setLastUpdateDate(modelSQL.getWritableDB(), recent,id);
                    }
                }
                ArrayList<Event> eventsList = EventSQL.getAllEvents(modelSQL.getReadbleDB(),id);
                listener.onResult(eventsList);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    public void getAllGroups(final String userId, final GetGroupsListListener getGroupslistner) {
        final String lastUpdateDate = GroupSQL.getLastUpdateDate(modelSQL.getReadbleDB(),userId);
        firebaseModel.getAllGroup(userId, lastUpdateDate, new GetGroupsListListener() {
            @Override
            public void onResult(ArrayList<Group> groups) {
                if (groups != null && groups.size() > 0) {
                    String recent = lastUpdateDate;
                    for (Group group : groups) {
                        GroupSQL.InsertGroup(group,userId, modelSQL.getWritableDB());
                        if (recent == null || group.getLastUpdate().compareTo(recent) > 0) {
                            recent = group.getLastUpdate();
                        }
                        GroupSQL.setLastUpdateDate(modelSQL.getWritableDB(), recent,userId);
                    }
                }
                ArrayList<Group> groupsList = GroupSQL.getAllGroups(modelSQL.getReadbleDB(),userId);
                getGroupslistner.onResult(groupsList);
            }

            @Override
            public void onCancel() {

            }
        });
    }


    public void getAllUsersFromGroup(String selectedGroup, final GetUsersFromGroupListener listener) {
        firebaseModel.getAllUsersFromGroup(selectedGroup, listener);
    }

    public String getGroupIdByName(String groupName){
        return modelSQL.getGroupIdByName(groupName);
    }

    public void getAllUsersById(ArrayList<String> usersNames, GetUsersListListener getGroupsListListener) {
        firebaseModel.getAllUsersById(usersNames,getGroupsListListener);
    }



    public void getNameForUser(String usersNames, getUserNameListener listener) {
        firebaseModel.getNameForUser(usersNames, listener);
    }


    public void getIdForUser(String name, getUserNameListener listener) {
        firebaseModel.getIdForUser(name, listener);
    }

    public void getAllUpcomingEvents(String id,GetUsersListener listener){
            firebaseModel.getAllUpcomingEvents(id,listener);
    }

    public void insertUser(User user) {
        firebaseModel.insertUser(user);
    }
}
