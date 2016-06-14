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
import com.example.zivbru.myfamilycalanderfinal.Model.Event;
import com.example.zivbru.myfamilycalanderfinal.Model.Group;
import com.example.zivbru.myfamilycalanderfinal.Model.Model;
import com.example.zivbru.myfamilycalanderfinal.Model.Task;

import java.util.ArrayList;


public class NewGroupTaskFragment extends Fragment {

    EditText taskName, targetDate, description;
    String userId = "";
    String selectedUser = "";
    String selectedGroup="";
    Task task;
    ArrayList<String> users;
    ArrayList<Event>events;
    ArrayList<String> groupsName;
    View view;
    public NewGroupTaskFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view=  inflater.inflate(R.layout.fragment_new_group_task, container, false);
        getActivity().setTitle("Add new task");
        groupsName= new ArrayList<String>();
        userId=  ((ComingEventsTasksActivity) getActivity()).getUserId();
        taskName = (EditText) view.findViewById(R.id.task_name);
        targetDate = (EditText) view.findViewById(R.id.target_date);
        description = (EditText) view.findViewById(R.id.task_description);
        Model.instance().getUsers(new Model.GetUsersListener() {
            @Override
            public void done(ArrayList<String> usersList) {

                users = usersList;
                Spinner dropdown = (Spinner) view.findViewById(R.id.users);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, users);
                dropdown.setAdapter(adapter);
                dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        selectedUser = (String) parent.getItemAtPosition(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                ///model.getGroups

            }
        });

        Button addGroupTask= (Button) view.findViewById(R.id.add_group_task_button);
        addGroupTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = new Task(String.valueOf(taskName.getText()), String.valueOf(targetDate.getText()),selectedUser,"", String.valueOf(description.getText()));
                Model.instance().AddGroupTask(task, userId, new Model.SignupListener() {
                    @Override
                    public void success() {
                        Delegate activity = (Delegate) getActivity();
                        activity.switchFragment("GroupTaskListFragment");
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
