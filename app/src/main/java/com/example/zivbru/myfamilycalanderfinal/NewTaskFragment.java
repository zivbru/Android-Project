package com.example.zivbru.myfamilycalanderfinal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.zivbru.myfamilycalanderfinal.Model.Model;
import com.example.zivbru.myfamilycalanderfinal.Model.Task;

import java.util.ArrayList;


public class NewTaskFragment extends Fragment {

    EditText title, targetDate,description;
    String userId ,selectedEvent= "";
    Task task;
    ArrayList<String> events;
    View view;
    public NewTaskFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_new_task, container, false);
        getActivity().setTitle("Add new task");
        Bundle extras = getActivity().getIntent().getExtras();
        userId = extras.getString("UserId");
        title = (EditText) view.findViewById(R.id.task_name);
        targetDate = (EditText) view.findViewById(R.id.begin_date);
        description = (EditText) view.findViewById(R.id.task_notes);
        Model.instance().getAllEventsName(userId, new Model.GetEventsNameListener() {

            @Override
            public void done(ArrayList<String> eventList) {
                events = eventList;
                Spinner dropdown = (Spinner) view.findViewById(R.id.events);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, events);
                dropdown.setAdapter(adapter);
                dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        selectedEvent = (String) parent.getItemAtPosition(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            }


        });



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