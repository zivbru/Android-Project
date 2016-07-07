package com.example.zivbru.myfamilycalanderfinal;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zivbru.myfamilycalanderfinal.Model.Event;
import com.example.zivbru.myfamilycalanderfinal.Model.Model;

public class GroupEventDetailsFragment extends Fragment {
    String userId,eventId;
    Event event;
    public GroupEventDetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_event_details, container, false);
        userId=   ((ComingEventsTasksActivity) getActivity()).getUserId();
        eventId=   ((ComingEventsTasksActivity) getActivity()).getEventId();
        final TextView eventName = (TextView) view.findViewById(R.id.details_event_name);
        final TextView eventStartDate = (TextView) view.findViewById(R.id.details_event_start_date);
        final TextView eventEbdDate = (TextView) view.findViewById(R.id.details_event_end_date);
        final TextView eventDescription = (TextView) view.findViewById(R.id.description);
        final TextView eventOwner = (TextView) view.findViewById(R.id.details_event_owner);
        final TextView eventGroup= (TextView) view.findViewById(R.id.details_event_group);
        Model.instance().getGroupEvent(userId, eventId, new Model.GetEventListener() {
            @Override
            public void done(Event doneEvent) {
                event = doneEvent;
                eventName.setText(event.getName());
                eventStartDate.setText(event.getStartDate());
                eventEbdDate.setText(event.getEndDate());
                eventDescription.setText(event.getDescription());
                eventOwner.setText(event.getOwnerById());
                eventGroup.setText(event.getGroupName());
            }
        });

//        EventImage.setBackground(Drawable.createFromPath(event.get));

        Button editEvent= (Button) view.findViewById(R.id.edit_event);
        editEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent= new Intent(getActivity(),EditGroupEventActivity.class);
                        intent.putExtra("UserId", userId);
                        intent.putExtra("EventId", eventId);
                        startActivity(intent);
                    }
                });
                t.start();


            }
        });


        return view;
    }


}
