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

import java.util.ArrayList;


public class NewGroupEventFragment extends Fragment {

    EditText name, startDate,endDate ,decription;
    String userId,selectedGroup="";
    Event event;
    ArrayList<Group> groupsList;
    View view;
    ArrayList<String> groupsName;
    public NewGroupEventFragment() {
        // Required empty public constructor
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
        Model.instance().getAllGroups(userId, new Model.GetGroupslistner() {

            @Override
            public void done(ArrayList<Group> groups) {
                for (Group group:groups) {
                    groupsName.add(group.getTitle());
                }
                Spinner dropdown = (Spinner) view.findViewById(R.id.group__spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,groupsName);
                dropdown.setAdapter(adapter);
                dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        selectedGroup = (String) parent.getItemAtPosition(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            }


        });
        Button addGroupEvent = (Button) view.findViewById(R.id.add_group_event_button);
        addGroupEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event = new Event(String.valueOf(name.getText()),String.valueOf(startDate.getText())
                        ,String.valueOf(endDate.getText()),String.valueOf(decription.getText()),userId,selectedGroup);
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
