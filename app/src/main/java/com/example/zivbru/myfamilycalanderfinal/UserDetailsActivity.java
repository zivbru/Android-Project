package com.example.zivbru.myfamilycalanderfinal;


import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.zivbru.myfamilycalanderfinal.Model.Model;
import com.example.zivbru.myfamilycalanderfinal.Model.User;


public class UserDetailsActivity extends ActionBarActivity {

    String userId;
    ImageView  picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        Bundle extras = getIntent().getExtras();
        userId = extras.getString("UserId");
        final TextView userFirstName = (TextView) findViewById(R.id.user_name);
        final TextView userLastName = (TextView) findViewById(R.id.user_last_name);
        final TextView userBirthDate = (TextView) findViewById(R.id.birthDate);
        final TextView mail = (TextView) findViewById(R.id.email);
        final TextView password = (TextView) findViewById(R.id.password);
        final TextView adress= (TextView) findViewById(R.id.address);
        final TextView phone= (TextView) findViewById(R.id.phone);
        final ProgressBar progressBar= (ProgressBar) findViewById(R.id.progress);
        picture= (ImageView) findViewById(R.id.add_picture);
        Model.instance().getUser(userId, new Model.UserListener() {
            @Override
            public void done(User user) {
                userFirstName.setText(user.getFirstName());
                userLastName.setText(user.getLastName());
                userBirthDate.setText(user.getBirthDate());
                mail.setText(user.getUserName());
                password.setText(user.getPassword());
                adress.setText(user.getAdress());
                phone.setText(user.getPhone());
                Model.instance().loadImage(user.getPictureName(), new Model.LoadImageListener() {
                    @Override
                    public void onResult(Bitmap imageBmp) {
                        if (picture != null) {
                            picture.setImageBitmap(imageBmp);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });




    }






}
