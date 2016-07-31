package com.example.zivbru.myfamilycalanderfinal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.zivbru.myfamilycalanderfinal.Model.Group;
import com.example.zivbru.myfamilycalanderfinal.Model.Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewGroupActivity extends ActionBarActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int RESULT_OK = -1;
    EditText name;
    ImageView addPicture;
    Group group;
    ArrayList<String> usersId;
    String imageFileName = null;
    Bitmap imageBitmap = null;
    Button addGroup;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        final EditText idEt = (EditText) findViewById(R.id.IdEditText);
        this.setTitle("Add new group");
        Bundle extras = getIntent().getExtras();
        userId = extras.getString("UserId");
        name= (EditText) findViewById(R.id.group_name);
        group= new Group();
        final ArrayList<String> users= new ArrayList<String>();
        Model.instance().getUsers(new Model.GetUsersListener() {
            @Override
            public void done(final ArrayList<String> usersList) {
                usersId= new ArrayList<String>();
                for (String s:usersList) {
                    usersId.add(s.split(",")[1]);
                }
                Button pickUsers = (Button) findViewById(R.id.pick_users);
                pickUsers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final MulitpleDialog mulitpleDialog = new MulitpleDialog();
                        mulitpleDialog.setData(usersId);
                        mulitpleDialog.setDelegate(new MulitpleDialog.Delegate() {
                            @Override
                            public void ok() {
                                boolean[] b= mulitpleDialog.getSelected();
                                for (int i=0;i<b.length;i++){
                                    if(b[i])
                                        if(!users.contains(usersList.get(i).split(",")[0]))
                                            users.add(usersList.get(i).split(",")[0]);

                                }
                            }

                            @Override
                            public void cancel() {
                                Log.d("cancel", "cancel;");
                            }
                        });
                        mulitpleDialog.show(getSupportFragmentManager(), "TAG");
                    }
                });


            }
        });


        addPicture= (ImageView) findViewById(R.id.add_picture);
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takingPicture();
            }
        });
        addGroup= (Button) findViewById(R.id.add_group_button);
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                imageFileName = idEt.getText().toString() + timeStamp + ".jpg";
                group.setTitle(String.valueOf(name.getText()));
                //take the users list
                if(imageBitmap!=null)
                    Model.instance().saveImage(imageBitmap, imageFileName);
                group = new Group(null, String.valueOf(name.getText()), imageFileName,users);
//                final ArrayList<String>usersId= new ArrayList<String>();
//
//                for (String name:users) {
//                    Model.instance().getIdForUser(name, new Model.getUserNameListener() {
//                        @Override
//                        public void success(String name) {
//                            group.getUsersList().add(name);
//                        }
//
//                        @Override
//                        public void fail(String msg) {
//
//                        }
//                    });
//
//                }
//                group.setUsersList(usersId);

                Model.instance().AddGroup(group, new Model.addGroupListener() {
                    @Override
                    public void success(Group group) {
                        Intent intent= new Intent(NewGroupActivity.this,ComingEventsTasksActivity.class);
                        intent.putExtra("UserId",userId);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void fail(String msg) {

                    }
                });

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
