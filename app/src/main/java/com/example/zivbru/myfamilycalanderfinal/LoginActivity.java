package com.example.zivbru.myfamilycalanderfinal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zivbru.myfamilycalanderfinal.Model.Model;
import com.example.zivbru.myfamilycalanderfinal.Model.MyApplication;
import com.firebase.client.AuthData;

public class LoginActivity extends ActionBarActivity {
    EditText email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication m = new MyApplication(this);
        Model.instance().createTables();
        String logeduserId=Model.instance().getLoggedinUser();
        if(!logeduserId.equals("null")){
            Intent intent= new Intent(this,ComingEventsTasksActivity.class);
            intent.putExtra("UserId", logeduserId);
            startActivity(intent);
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.setTitle("Login");
        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);
        Button login = (Button) findViewById(R.id.btn_login);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.ProgressBarLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Model.instance().login(String.valueOf(email.getText()), String.valueOf(password.getText()), new Model.LoginListener() {
                    public void success(AuthData authData) {
                        Model.instance().updateLogin(authData.getUid(),"true");
                        Intent intent = new Intent(LoginActivity.this, ComingEventsTasksActivity.class);
                        intent.putExtra("UserId", authData.getUid());
                        startActivity(intent);
                        finish();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void fail(String msg) {
                        progressBar.setVisibility(View.GONE);
                        Context context = getApplicationContext();
                        CharSequence text = msg;
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });
            }
        });
        TextView signup = (TextView) findViewById(R.id.link_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
