package com.example.zivbru.myfamilycalanderfinal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zivbru.myfamilycalanderfinal.Model.Model;
import com.example.zivbru.myfamilycalanderfinal.Model.Task;

import java.util.ArrayList;


public class NewTaskFragment extends Fragment {

    EditText title, targetDate,description;
    TextView chosenEvent;
    String userId ,selectedEvent= "";
    Task task;
    ArrayList<String> events;
    View view;
    SingleDialog singleDialog ;
    Button chooseEvent;
    public NewTaskFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_new_task, container, false);
        getActivity().setTitle("Add new task");
//        ((ComingEventsTasksActivity)getActivity()).getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        Bundle extras = getActivity().getIntent().getExtras();
        userId = extras.getString("UserId");
        title = (EditText) view.findViewById(R.id.task_name);
        targetDate = (EditText) view.findViewById(R.id.begin_date);
        description = (EditText) view.findViewById(R.id.task_notes);
        singleDialog = new SingleDialog();
        chosenEvent= (TextView) view.findViewById(R.id.chosen_event);
        chooseEvent= (Button) view.findViewById(R.id.choose_event_button);
        chooseEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance().getAllEventsName(userId, new Model.GetEventsNameListener() {

                    @Override
                    public void done(ArrayList<String> eventList) {

                        if(eventList.size()>0) {
                            events = eventList;
                            singleDialog.setData(events);
                            singleDialog.show(getFragmentManager(), "TAG");
                            selectedEvent = singleDialog.getSelected();
//                            chosenEvent.setText(events.get(Integer.parseInt(selectedEvent)));
                        }
                        else{
                            Toast toast = Toast.makeText(getActivity(), "You have no events", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });

            }


        });


//        chosenEvent.setText(events.get(Integer.parseInt(selectedEvent)));
        Button addTask= (Button) view.findViewById(R.id.add_task_button);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = new Task(String.valueOf(title.getText()), String.valueOf(targetDate.getText()),userId,selectedEvent, String.valueOf(description.getText()));
                Model.instance().AddTask(task, userId, new Model.SignupListener() {
                    @Override
                    public void success() {
                        Delegate activity = (Delegate) getActivity();
                        activity.switchFragment("TaskListFragment");
                    }

                    @Override
                    public void fail(String msg) {

                    }
                });

            }
        });

        return view;
    }
}