package com.example.zivbru.myfamilycalanderfinal;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zivbru.myfamilycalanderfinal.Model.Event;
import com.example.zivbru.myfamilycalanderfinal.Model.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class EventListFragment extends Fragment {
    String userId;
    ListView list;
    ArrayList<Event> events;
    EventAdapter adapter;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        events= new ArrayList<Event>();
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        getActivity().setTitle("Events List");
        userId =  ((ComingEventsTasksActivity) getActivity()).getUserId();
        Bundle bundle = new Bundle();
        bundle.putString("UserId", userId);
        list= (ListView) view.findViewById(R.id.event_list_view);
        progressBar = (ProgressBar) view.findViewById(R.id.mainProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        Model.instance().getAllEvents(userId, new Model.GetListEventListener() {
            @Override
            public void onResult(ArrayList<Event> allEvents) {
                events = allEvents;
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancel() {

            }
        });

        adapter = new EventAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Delegate activity = (Delegate) getActivity();
                activity.switchFragment("EventDetailsFragment");
                ((ComingEventsTasksActivity) getActivity()).setEventId(events.get(position).getId());

            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_login, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Events List");
        adapter.notifyDataSetChanged();
    }

    public class EventAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return events.size();
        }

        @Override
        public Object getItem(int position) {
            return events.get(position);
        }

        @Override
        public long getItemId(int position) {
            return  position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null){
                LayoutInflater layoutInflater = getActivity().getLayoutInflater();
                convertView= layoutInflater.inflate(R.layout.fragment_event_list_row,null);

            }
            else{
                Log.d("TAG", "use convert view:" + position);
            }

            TextView eventName= (TextView) convertView.findViewById(R.id.event_list_row_event_name);

            Event event = events.get(position);
            eventName.setText(event.getName());



            //get the owner from sql by id

            return  convertView;
        }
    }


}
