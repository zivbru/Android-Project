package com.example.zivbru.myfamilycalanderfinal.Model;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.example.zivbru.myfamilycalanderfinal.ComingEventsTasksActivity;
import com.example.zivbru.myfamilycalanderfinal.NotificationView;
import com.example.zivbru.myfamilycalanderfinal.R;

import java.util.ArrayList;

/**
 * Created by zivbru on 7/11/2016.
 */
public class MyService extends Service {

    ArrayList<String> notifications;



    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Bundle extras = intent.getExtras();
        String userId = extras.getString("UserId");
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        notifications= new ArrayList<String>();
        displayNotification(userId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    public void displayNotification(String userId) {
        Log.i("Start", "notification");

   /* Invoking the default notification service */
        final NotificationCompat.Builder  mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setContentTitle("New Message");
        mBuilder.setContentText("You've received new message.");
        mBuilder.setTicker("New Message Alert!");
        mBuilder.setSmallIcon(R.drawable.groupslist);
        int numMessages=0;
   /* Increase notification number every time a new notification arrives */
        mBuilder.setNumber(++numMessages);

   /* Add Big View Specific Configuration */
        final NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();


        notifications= new ArrayList<String>();
        Model.instance().getAllUpcomingEvents(userId, new Model.GetUsersListener() {
            @Override
            public void done(ArrayList<String> usersList) {
                notifications = usersList;
                inboxStyle.setBigContentTitle("Big Title Details:");

                // Moves events into the big view
                for (int i=0; i < usersList.size(); i++) {

                    inboxStyle.addLine(usersList.toArray()[i].toString());
                    Log.d("notifivation",usersList.toArray()[i].toString());
                }

                mBuilder.setStyle(inboxStyle);
                Intent resultIntent = new Intent(MyService.this, NotificationView.class);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(MyService.this);
                stackBuilder.addParentStack(NotificationView.class);

   /* Adds the Intent that starts the Activity to the top of the stack */
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

   /* notificationID allows you to update the notification later on. */
                mNotificationManager.notify(1, mBuilder.build());
            }
        });

//        String[] events = new String[6];
//        events[0] = new String("This is first line....");
//        events[1] = new String("This is second line...");
//        events[2] = new String("This is third line...");
//        events[3] = new String("This is 4th line...");
//        events[4] = new String("This is 5th line...");
//        events[5] = new String("This is 6th line...");

        // Sets a title for the Inbox style big view


   /* Creates an explicit intent for an Activity in your app */

    }

    public ArrayList<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<String> notifications) {
        this.notifications = notifications;
    }
}