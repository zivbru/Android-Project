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
        Bundle extra = getIntent().getBundleExtra("extra");
        ArrayList<String> objects = (ArrayList<String>) extra.getSerializable("objects");

        userId= extras.getString("UserId");
        TextView event = (TextView) findViewById(R.id.events);
        String message="Your have upcoming events on:\n";
        int i=1;
        for (String s:objects) {
            message+= i+". "+s + "\n";
            i++;
        }
        event.setText(message);
    }


    @Override
    public void onBackPressed() {
        Intent intent= new Intent(NotificationView.this,ComingEventsTasksActivity.class);
        intent.putExtra("UserId", userId);
        startActivity(intent);
        finish();
    }
}
