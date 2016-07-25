package com.example.zivbru.myfamilycalanderfinal;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zivbru.myfamilycalanderfinal.Model.Event;
import com.example.zivbru.myfamilycalanderfinal.Model.Model;

public class EditEventActivity extends ActionBarActivity {

    String userId,eventId;
    Event event;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        Bundle extras = getIntent().getExtras();
        userId = extras.getString("UserId");
        eventId = extras.getString("EventId");
        event= new Event();
        final EditText eventName = (EditText) findViewById(R.id.eventName);
        final EditText eventStartDate = (EditText) findViewById(R.id.eventStartDate);
        final EditText eventEndDate = (EditText) findViewById(R.id.eventEndDate);
        final EditText eventDescription = (EditText) findViewById(R.id.eventDescription);
        final EditText eventGroup = (EditText) findViewById(R.id.eventGroup);
        final TextView owner = (TextView) findViewById(R.id.owner);
        progressBar= (ProgressBar) findViewById(R.id.ProgressBarEdit_event);
        Model.instance().getEvent(userId, eventId, new Model.GetEventListener() {
            @Override
            public void done(Event retEvent) {
                event = retEvent;
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
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }).start();

            }
        });
        Button delete = (Button) findViewById(R.id.event_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditEventActivity.this, ComingEventsTasksActivity.class);
                intent.putExtra("UserId", userId);
                startActivity(intent);
                progressBar.setVisibility(View.VISIBLE);
                Model.instance().deleteEvent(userId, eventId, new Model.SignupListener() {
                    @Override
                    public void success() {


                        finish();
                    }

                    @Override
                    public void fail(String msg) {

                    }
                });
                progressBar.setVisibility(View.GONE);
                Toast.makeText(EditEventActivity.this, "Event deleted", Toast.LENGTH_LONG).show();
            }
        });
        Button save = (Button) findViewById(R.id.event_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event= new Event(eventName.getText().toString(),eventStartDate.getText().toString(),
                        eventEndDate.getText().toString(),eventDescription.getText().toString(),event.getOwnerById(),eventGroup.getText().toString());
                Model.instance().deleteEvent(userId, eventId, new Model.SignupListener() {
                    @Override
                    public void success() {
                        Model.instance().AddEvent(event, userId, new Model.SignupListener() {
                            @Override
                            public void success() {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(EditEventActivity.this, ComingEventsTasksActivity.class);
                                        intent.putExtra("UserId", userId);
                                        startActivity(intent);
                                    }
                                }).start();
                                Toast.makeText(EditEventActivity.this, "Event updated", Toast.LENGTH_LONG).show();

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

