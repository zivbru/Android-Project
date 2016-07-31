package com.example.zivbru.myfamilycalanderfinal;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zivbru.myfamilycalanderfinal.Model.Group;
import com.example.zivbru.myfamilycalanderfinal.Model.Model;
import com.example.zivbru.myfamilycalanderfinal.Model.Task;


import java.util.ArrayList;


public class NewGroupTaskFragment extends Fragment {

    EditText taskName, targetDate, description,showSelectedGroup;
    String userId = "";
    String [] selectedUsers ;
    String selectedGroup;
    Task task;
    ArrayList<String> groupsName;
    ArrayList<Group> groups;

    View view;
    SingleDialog singleDialog ;
    public NewGroupTaskFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view=  inflater.inflate(R.layout.fragment_new_group_task, container, false);
        getActivity().setTitle("Add new task");
        groupsName= new ArrayList<String>();
        groups= new ArrayList<Group>();
        userId=  ((ComingEventsTasksActivity) getActivity()).getUserId();
        taskName = (EditText) view.findViewById(R.id.task_name);
        targetDate = (EditText) view.findViewById(R.id.target_date);
        description = (EditText) view.findViewById(R.id.task_description);
        showSelectedGroup= (EditText) view.findViewById(R.id.selected_group);
        Button chooseGroup= (Button) view.findViewById(R.id.choose_group);

        ///need to change this
        chooseGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance().getGroups(new Model.GetGroupsListener() {
                    @Override
                    public void done(ArrayList<Group> groupsList) {
                        if (groupsList.size() > 0) {
                            for (Group  group:groupsList) {
                                groupsName.add(group.getTitle());
                            }
                            groups = groupsList;
                            singleDialog = new SingleDialog();
                            singleDialog.setData(groupsName);
                            singleDialog.show(getFragmentManager(), "TAG");
                            singleDialog.setDelegate(new SingleDialog.Delegate() {

                                @Override
                                public void ok() {
                                    selectedGroup = groupsName.get(Integer.parseInt(singleDialog.getSelected()));
                                    showSelectedGroup.setText(selectedGroup);
                                }
                                @Override
                                public void cancel() {

                                }
                            });
                        } else {
                            Toast toast = Toast.makeText(getActivity(), "You have no groups", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
            }
        });

        Button addGroupTask= (Button) view.findViewById(R.id.add_group_task_button);
        addGroupTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedGroup!=null) {
                    task= new Task(String.valueOf(taskName.getText()), String.valueOf(targetDate.getText()),userId,selectedGroup,String.valueOf(description.getText()));
//                    task = new Task(String.valueOf(taskName.getText()), String.valueOf(targetDate.getText()), selectedGroup, "", String.valueOf(description.getText()));
                    String groupId = Model.instance().getGroupIdByName(selectedGroup);
                    Model.instance().AddGroupTask(task, userId,groupId, new Model.SignupListener() {
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
                else{
                    Context context = getActivity();
                    CharSequence text = "You have to choose a group!!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });

        return view;
    }


}
