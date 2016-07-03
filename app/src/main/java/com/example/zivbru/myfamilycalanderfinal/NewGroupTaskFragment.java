package com.example.zivbru.myfamilycalanderfinal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import com.example.zivbru.myfamilycalanderfinal.Model.Event;
import com.example.zivbru.myfamilycalanderfinal.Model.Model;
import com.example.zivbru.myfamilycalanderfinal.Model.Task;
import java.util.ArrayList;


public class NewGroupTaskFragment extends Fragment {

    EditText taskName, targetDate, description;
    String userId = "";
    String selectedUser = "";
    String selectedGroup="";
    Task task;
    ArrayList<String> groupsName;

    View view;
    EditText showSelectedGroup;
    SingleDialog singleDialog ;
    public NewGroupTaskFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view=  inflater.inflate(R.layout.fragment_new_group_task, container, false);
        getActivity().setTitle("Add new task");
        groupsName= new ArrayList<String>();
        singleDialog = new SingleDialog();
        userId=  ((ComingEventsTasksActivity) getActivity()).getUserId();
        taskName = (EditText) view.findViewById(R.id.task_name);
        targetDate = (EditText) view.findViewById(R.id.target_date);
        description = (EditText) view.findViewById(R.id.task_description);
        showSelectedGroup= (EditText) view.findViewById(R.id.selected_group);
        TextView chooseGroup= (TextView) view.findViewById(R.id.choose_group);
        chooseGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance().getGroups(new Model.GetUsersListener() {
                    @Override
                    public void done(ArrayList<String> groupsList) {
                        if(groupsList.size()>0) {
                            groupsName = groupsList;
                            singleDialog.setData(groupsList);
                            singleDialog.show(getFragmentManager(), "TAG");
                            selectedGroup = singleDialog.getSelected();
                            showSelectedGroup.setText(groupsName.get(Integer.parseInt(selectedGroup)));
                        }
                        else{
                            Toast toast = Toast.makeText(getActivity(), "Yoe have no groups", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
            }
        });

        if(selectedGroup!=""&&groupsName.size()>0) {

            Button pickUsers = (Button) view.findViewById(R.id.pick_users);
            View b = view.findViewById(R.id.pick_users);
            b.setVisibility(View.VISIBLE);
            pickUsers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MulitpleDialog mulitpleDialog = new MulitpleDialog();
                    mulitpleDialog.show(getFragmentManager(), "TAG");
                }
            });
        }
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
