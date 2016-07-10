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

import com.example.zivbru.myfamilycalanderfinal.Model.Event;
import com.example.zivbru.myfamilycalanderfinal.Model.Group;
import com.example.zivbru.myfamilycalanderfinal.Model.Model;

import java.util.ArrayList;


public class NewGroupEventFragment extends Fragment {

    EditText name, startDate,endDate ,decription;
    String userId,selectedGroup,selectedGroupId="";
    Event event;
    ArrayList<Group> groupsList;
    ArrayList<Group> groups;
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
        groups= new ArrayList<Group>();
        getActivity().setTitle("Add new group event");
        name = (EditText) view.findViewById(R.id.group_event_name);
        startDate = (EditText) view.findViewById(R.id.group_event_start_date);
        endDate = (EditText) view.findViewById(R.id.group_event_end_date);
        decription = (EditText) view.findViewById(R.id.group_event_description);

        Button chooseGroup= (Button) view.findViewById(R.id.choose_group);
        chooseGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance().getGroups(new Model.GetGroupsListener() {
                    @Override
                    public void done(final ArrayList<Group> groupsList) {
                        if (groupsList.size() > 0) {
                            for (Group  group:groupsList) {
                                if(!groupsName.contains(group.getTitle()))
                                    groupsName.add(group.getTitle());
                            }
                            singleDialog = new SingleDialog();
                            singleDialog.setDelegate(new SingleDialog.Delegate() {
                                @Override
                                public void ok() {
                                    selectedGroup = groupsName.get(Integer.parseInt(singleDialog.getSelected()));
                                    Log.d("selected group",selectedGroup +" lllll");
                                    for (Group g:groupsList) {
                                        if(g.getTitle().equals(selectedGroup)) {
                                            selectedGroupId = g.getId();
                                            Log.d("selected group", selectedGroup + " lllll");
                                        }
                                    }
                                }

                                @Override
                                public void cancel() {
                                }
                            });
                            singleDialog.setData(groupsName);
                            singleDialog.show(getFragmentManager(), "TAG");

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
                if(selectedGroup!=null) {
                    event = new Event(String.valueOf(name.getText()), String.valueOf(startDate.getText())
                            , String.valueOf(endDate.getText()), String.valueOf(decription.getText()), userId, selectedGroup);
                    event.setTypeOfEvent("Public");
                    String groupId = Model.instance().getGroupIdByName(selectedGroup);
                    Model.instance().AddGroupEvent(event, userId, groupId, new Model.SignupListener() {
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
                else {
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
