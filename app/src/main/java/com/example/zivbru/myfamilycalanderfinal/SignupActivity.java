package com.example.zivbru.myfamilycalanderfinal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.zivbru.myfamilycalanderfinal.Model.Model;
import com.example.zivbru.myfamilycalanderfinal.Model.User;
import com.example.zivbru.myfamilycalanderfinal.Model.UserFireBase;
import com.firebase.client.AuthData;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SignupActivity extends ActionBarActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    EditText email,password,firstName,lastName,adress,phone,birthDate;
    Button signUp;
    ImageView addPicture;
    User user;
    String imageFileName = null;
    Bitmap imageBitmap = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        this.setTitle("Signup");
        user= new User();
        final EditText idEt = (EditText) findViewById(R.id.nsIdEditText);
        email= (EditText) findViewById(R.id.input_email);
        password= (EditText) findViewById(R.id.input_password);
        firstName= (EditText) findViewById(R.id.firstName);
        lastName= (EditText) findViewById(R.id.lastName);
        adress= (EditText) findViewById(R.id.adress);
        phone= (EditText) findViewById(R.id.phone);
        birthDate= (EditText) findViewById(R.id.birthDate);
        addPicture= (ImageView) findViewById(R.id.add_picture);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.SignUpProgressBar);
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takingPicture();
            }
        });
        signUp= (Button) findViewById(R.id.btn_login);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Model.instance().signup(String.valueOf(email.getText()), String.valueOf(password.getText()), new Model.LoginListener() {
                    @Override
                    public void success(AuthData authData) {
                        UserFireBase userFireBase = new UserFireBase(Model.instance().getFirebaseModel().getMyFirebaseRef());
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        imageFileName = idEt.getText().toString() + timeStamp + ".jpg";
                        user.setUserName(String.valueOf(email.getText()));
                        user.setPassword(String.valueOf(password.getText()));
                        user.setUserId(authData.getUid());
                        user.setFirstName(String.valueOf(firstName.getText()));
                        user.setLastName(String.valueOf(lastName.getText()));
                        user.setAdress(String.valueOf(adress.getText()));
                        user.setPhone(String.valueOf(phone.getText()));
                        user.setBirthDate(String.valueOf(birthDate.getText()));
                        user.setPictureName(imageFileName);
                        userFireBase.insertUser(user);
                        Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                        intent.putExtra("UserId", authData.getUid());
                        startActivity(intent);
                        finish();
                        Model.instance().saveImage(imageBitmap, imageFileName);

                        Intent resultIntent = new Intent();
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void fail(String msg) {

                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(getApplicationContext(), msg, duration);
                        toast.show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takingPicture();
            }
        });
    }


    private void takingPicture(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            addPicture.setImageBitmap(imageBitmap);
        }
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
