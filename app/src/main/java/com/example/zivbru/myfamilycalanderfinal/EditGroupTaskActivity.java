package com.example.zivbru.myfamilycalanderfinal;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zivbru.myfamilycalanderfinal.Model.Model;
import com.example.zivbru.myfamilycalanderfinal.Model.Task;

public class EditGroupTaskActivity extends ActionBarActivity {

    String userId,taskId,groupId;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group_task);
        Bundle extras = getIntent().getExtras();
        userId = extras.getString("UserId");
        taskId = extras.getString("TaskId");
        groupId= extras.getString("GroupId");
        task= new Task();
        final EditText taskName = (EditText) findViewById(R.id.taskName);
        final EditText targetDate = (EditText) findViewById(R.id.targetDate);
        final EditText taskDescription = (EditText) findViewById(R.id.taskDescription);
        final EditText relatedEvent = (EditText) findViewById(R.id.relatedEvent);
        final EditText group = (EditText) findViewById(R.id.group);

        final TextView owner = (TextView) findViewById(R.id.owner);
        Model.instance().getGroupTask(userId, taskId,groupId, new Model.GetTaskListener() {
            @Override
            public void done(Task retTask) {
                task=retTask;
                taskName.setText(task.getTitle());
                targetDate.setText(task.getTargetDate());
                taskDescription.setText(task.getDescription());
                relatedEvent.setText(task.getRelatedEvent());
                owner.setText(task.getOwnerId());
                group.setText(task.getGroupId());
            }
        });

        Button cancel = (Button) findViewById(R.id.task_cancel);
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

        Button delete = (Button) findViewById(R.id.task_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String groupId= Model.instance().getGroupIdByName(group.getText().toString());
                Model.instance().deleteGroupTask(userId, taskId,groupId, new Model.SignupListener() {
                    @Override
                    public void success() {
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {

                                finish();
                            }
                        });
                        t.start();
                        Intent intent = new Intent(EditGroupTaskActivity.this, ComingEventsTasksActivity.class);
                        intent.putExtra("UserId", userId);
                        startActivity(intent);
                        Toast.makeText(EditGroupTaskActivity.this, "Task deleted", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void fail(String msg) {

                    }
                });
            }
        });
        Button save = (Button) findViewById(R.id.task_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = new Task(taskName.getText().toString(), targetDate.getText().toString(), task.getOwnerId(),
                        relatedEvent.getText().toString(), taskDescription.getText().toString());
                final String groupId= Model.instance().getGroupIdByName(group.getText().toString());
                Model.instance().deleteGroupTask(userId, taskId,groupId, new Model.SignupListener() {
                    @Override
                    public void success() {
                        Model.instance().AddGroupTask(task, userId,groupId, new Model.SignupListener() {
                            @Override
                            public void success() {
                                Thread t = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(EditGroupTaskActivity.this, ComingEventsTasksActivity.class);
                                        intent.putExtra("UserId", userId);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                t.start();
                                Toast.makeText(EditGroupTaskActivity.this, "Task updated", Toast.LENGTH_LONG).show();

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

