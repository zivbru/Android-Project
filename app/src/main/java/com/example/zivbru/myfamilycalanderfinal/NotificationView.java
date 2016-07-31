package com.example.zivbru.myfamilycalanderfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class NotificationView extends ActionBarActivity {
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_view);

        Bundle extras = getIntent().getExtras();
        String events = extras.getString("message");
        userId= extras.getString("UserId");
        TextView event = (TextView) findViewById(R.id.events);
        event.setText(events);
    }


    @Override
    public void onBackPressed() {
        Intent intent= new Intent(NotificationView.this,ComingEventsTasksActivity.class);
        intent.putExtra("UserId", userId);
        startActivity(intent);
        finish();
    }
}
