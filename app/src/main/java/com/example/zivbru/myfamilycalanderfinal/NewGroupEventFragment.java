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
import android.widget.Toast;

import com.example.zivbru.myfamilycalanderfinal.Model.Event;
import com.example.zivbru.myfamilycalanderfinal.Model.Group;
import com.example.zivbru.myfamilycalanderfinal.Model.Model;

import java.util.ArrayList;


public class NewGroupEventFragment extends Fragment {

    EditText name, startDate,endDate ,decription;
    String userId,selectedGroup="";
    Event event;
    ArrayList<Group> groupsList;
    View view;
    ArrayList<String> groupsName;
    SingleDialog singleDialog ;
    public NewGroupEventFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view= inflater.inflate(R.layout.fragment_new_group_event, container, false);
        userId=  ((ComingEventsTasksActivity) getActivity()).getUserId();
        groupsName= new ArrayList<String>();
        getActivity().setTitle("Add new group event");
        name = (EditText) view.findViewById(R.id.group_event_name);
        startDate = (EditText) view.findViewById(R.id.group_event_start_date);
        endDate = (EditText) view.findViewById(R.id.group_event_end_date);
        decription = (EditText) view.findViewById(R.id.group_event_description);
        singleDialog = new SingleDialog();
        Button chooseGroup= (Button) view.findViewById(R.id.choose_group);
        chooseGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance().getGroups(new Model.GetUsersListener() {
                    @Override
                    public void done(ArrayList<String> groupsList) {
                        if (groupsList.size() > 0) {
                            groupsName = groupsList;
                            singleDialog.setData(groupsList);
                            singleDialog.show(getFragmentManager(), "TAG");
                            selectedGroup = singleDialog.getSelected();

//                    showSelectedGroup.setText(groupsName.get(Integer.parseInt(selectedGroup)));
                        } else {
                            Toast toast = Toast.makeText(getActivity(), "You have no groups", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
            }
        });






        Button addGroupEvent = (Button) view.findViewById(R.id.add_group_event_button);
        addGroupEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event = new Event(String.valueOf(name.getText()), String.valueOf(startDate.getText())
                        , String.valueOf(endDate.getText()), String.valueOf(decription.getText()), userId, selectedGroup);
                event.setTypeOfEvent("Public");
                Model.instance().AddGroupEvent(event, userId, new Model.SignupListener() {
                    @Override
                    public void success() {
                        Delegate activity = (Delegate) getActivity();
                        activity.switchFragment("GroupEventListFragment");
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
