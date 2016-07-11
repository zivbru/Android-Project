package com.example.zivbru.myfamilycalanderfinal;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zivbru.myfamilycalanderfinal.Model.Event;
import com.example.zivbru.myfamilycalanderfinal.Model.Model;

public class EditGroupEventActivity extends ActionBarActivity {

    String userId,eventId,groupId;
    Event event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group_event);
        Bundle extras = getIntent().getExtras();
        userId = extras.getString("UserId");
        eventId = extras.getString("EventId");
        groupId= extras.getString("GroupId");
        event= new Event();
        final EditText eventName = (EditText) findViewById(R.id.eventName);
        final EditText eventStartDate = (EditText) findViewById(R.id.eventStartDate);
        final EditText eventEndDate = (EditText) findViewById(R.id.eventEndDate);
        final EditText eventDescription = (EditText) findViewById(R.id.eventDescription);
        final EditText eventGroup = (EditText) findViewById(R.id.eventGroup);
        final TextView owner = (TextView) findViewById(R.id.owner);

        Model.instance().getGroupEvent(groupId, eventId, new Model.GetEventListener() {
            @Override
            public void done(Event retEvent) {
                event=retEvent;

                eventName.setText(event.getName());
                eventStartDate.setText(event.getStartDate());
                eventEndDate.setText(event.getEndDate());
                eventDescription.setText(event.getDescription());
                eventGroup.setText(event.getGroupName());
                owner.setText(event.getOwnerById());
            }
        });


        Button cancel = (Button) findViewById(R.id.event_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });
                t.start();

            }
        });
        Button delete = (Button) findViewById(R.id.event_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String groupId= Model.instance().getGroupIdByName(eventGroup.getText().toString());
                Model.instance().deleteGroupEvent(userId, eventId,groupId, new Model.SignupListener() {
                    @Override
                    public void success() {
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {

                                finish();
                            }
                        });
                        t.start();
                        Intent intent= new Intent(EditGroupEventActivity.this,ComingEventsTasksActivity.class);
                        intent.putExtra("UserId", userId);
                        startActivity(intent);
                        Toast.makeText(EditGroupEventActivity.this, "Event deleted", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void fail(String msg) {

                    }
                });
            }
        });
        Button save = (Button) findViewById(R.id.event_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event= new Event(eventName.getText().toString(),eventStartDate.getText().toString(),
                        eventEndDate.getText().toString(),eventDescription.getText().toString(),event.getOwnerById(),eventGroup.getText().toString());
               final String groupId= Model.instance().getGroupIdByName(eventGroup.getText().toString());
                Model.instance().deleteGroupEvent(userId, eventId,groupId, new Model.SignupListener() {
                    @Override
                    public void success() {
                        Model.instance().AddGroupEvent(event, userId,groupId, new Model.SignupListener() {
                            @Override
                            public void success() {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent= new Intent(EditGroupEventActivity.this,ComingEventsTasksActivity.class);
                                        intent.putExtra("UserId", userId);
                                        startActivity(intent);

                                    }
                                }).start();
                                finish();
                                Toast.makeText(EditGroupEventActivity.this, "Event updated", Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void fail(String msg) {

                            }
                        });
                    }

                    @Override
                    public void fail(String msg) {

                    }
                });
            }
        });

    }
}

