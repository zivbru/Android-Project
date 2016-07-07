package com.example.zivbru.myfamilycalanderfinal;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.zivbru.myfamilycalanderfinal.Model.Event;
import com.example.zivbru.myfamilycalanderfinal.Model.Model;


public class NewEventFragment extends Fragment {

    EditText name,startDate, endDate, description;

    String ownerById,userId="";
    Event event;
    public NewEventFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view= inflater.inflate(R.layout.fragment_new_event, container, false);
//        ((ComingEventsTasksActivity)getActivity()).getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        Bundle extras = getActivity().getIntent().getExtras();
        userId = extras.getString("UserId");
        getActivity().setTitle("Add new event");
        name = (EditText) view.findViewById(R.id.event_name);
        startDate= (EditText) view.findViewById(R.id.event_strat_date);
        endDate = (EditText) view.findViewById(R.id.event_end_date);
        description = (EditText) view.findViewById(R.id.event_notes);
        ownerById =userId;
        Button addEvent = (Button) view.findViewById(R.id.add_event_button);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event = new Event(String.valueOf(name.getText()),String.valueOf(startDate.getText())
                        ,String.valueOf(endDate.getText()),String.valueOf(description.getText()),userId);
                event.setTypeOfEvent("Public");
                Model.instance().AddEvent(event, userId, new Model.SignupListener() {
                    @Override
                    public void success() {
                        Delegate activity = (Delegate) getActivity();
                        activity.switchFragment("EventListFragment");
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
