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

    public void signup(final String email, final String pwd, final LoginListener listener) {
        firebaseModel.signup(email, pwd, listener);
    }

    public void login(final String email, final String pwd, final LoginListener listener) {
        firebaseModel.login(email, pwd, listener);
    }

    public void getUsers(final Model.GetUsersListener listener) {
        firebaseModel.getUsers(listener);
    }

    public void getGroups(final Model.GetUsersListener listener) {
        firebaseModel.getGroups(listener);
    }

    public void getAllEvents(final String id, final GetEventsListener listener) {
        firebaseModel.getAllEvents(id, listener);
    }

    public void getAllEventsName(final String id, final GetEventsNameListener listener) {
        firebaseModel.getAllEventsName(id, listener);
    }

    public void AddEvent(Event event , String id,final SignupListener listener) {
        firebaseModel.AddEvent(event, id, listener);
    }

    public void deleteEvent(String userId, String eventId, Model.SignupListener listener) {
        firebaseModel.deleteEvent(userId, eventId, listener);
    }

    public void deleteGroupEvent(String userId, String eventId, SignupListener listener) {
        firebaseModel.deleteGroupEvent(userId, eventId, listener);
    }

    public void deleteTask(String userId, String taskId, Model.SignupListener listener) {
        firebaseModel.deleteTask(userId, taskId, listener);
    }

    public void deleteGroupTask(String userId, String taskId, SignupListener listener) {
        firebaseModel.deleteGroupTask(userId, taskId, listener);
    }


    public void getAllGroupsEvents(String userId, GetEventsListener getGroupsEventsListener) {
        firebaseModel.getAllGroupsEvents(userId, getGroupsEventsListener);
    }

    public void AddGroup(Group group ,final addGroupListener listener) {
        firebaseModel.AddGroup(group, listener);
    }

    public void getAllTasks(String userId, GetTasksListener listener) {
        firebaseModel.getAllTasks(userId, listener);
    }

    public void AddTask(Task task, String id, final SignupListener listener) {
        firebaseModel.addTask(task, id, listener);
    }

    public void getAllGroupsTasks(String userId, GetTasksListener getGroupsTasksListener) {
        firebaseModel.getAllGroupsTasks(userId, getGroupsTasksListener);
    }

    public void getAllGroups(String userId, GetGroupslistner getGroupslistner) {
        firebaseModel.getAllGroup(userId, getGroupslistner);
    }

    public void AddGroupTask(Task task, String id, final SignupListener listener) {
        firebaseModel.AddGroupTask(task, id, listener);
    }

    public void AddGroupEvent(Event event, String id, final SignupListener listener) {
        firebaseModel.AddGroupEvent(event, id, listener);
    }

    public void getEvent(String userId,String eventId, GetEventListener listener) {
        firebaseModel.getEvent(userId, eventId, listener);
    }

    public void getTask(String userId,String taskId, GetTaskListener listener) {
        firebaseModel.getTask(userId, taskId, listener);
    }

    public void getGroupTask(String userId, String taskId, GetTaskListener getTaskListener) {
        firebaseModel.getGroupTask(userId, taskId, getTaskListener);
    }

    public void getGroupEvent(String userId, String taskId, GetEventListener getTaskListener) {
        firebaseModel.getGroupEvent(userId, taskId, getTaskListener);
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
       firebaseModel.getUser(id,userListener);
    }


}
