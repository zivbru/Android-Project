package com.example.zivbru.myfamilycalanderfinal;


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
import android.widget.TextView;

import com.example.zivbru.myfamilycalanderfinal.Model.Event;
import com.example.zivbru.myfamilycalanderfinal.Model.Model;

import java.util.ArrayList;


public class GroupEventListFragment extends Fragment {
    String userId;
    ListView list;
    ArrayList<Event> groupsEvents;
    GroupsEventAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        groupsEvents= new ArrayList<Event>();
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_group_event_list, container, false);
        getActivity().setTitle("Groups Events List");
        userId = ((ComingEventsTasksActivity) getActivity()).getUserId();
        Bundle bundle = new Bundle();
        bundle.putString("UserId", userId);
        list= (ListView) view.findViewById(R.id.group_event_list_view);
        Model.instance().getAllGroupsEvents(userId,  new Model.GetListEventListener() {
            @Override
            public void onResult(ArrayList<Event> allEvents) {
                groupsEvents = allEvents;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancel() {

            }
        });
        adapter = new GroupsEventAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Delegate activity = (Delegate) getActivity();
                activity.switchFragment("GroupsEventDetailsFragment");
                ((ComingEventsTasksActivity) getActivity()).setEventId(groupsEvents.get(position).getId());
                ((ComingEventsTasksActivity) getActivity()).setGroupName(groupsEvents.get(position).getGroupName());
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

    public class GroupsEventAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return groupsEvents.size();
        }

        @Override
        public Object getItem(int position) {
            return groupsEvents.get(position);
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
            TextView startDate = (TextView) convertView.findViewById(R.id.event_list_row_start_date);
            TextView endDate = (TextView) convertView.findViewById(R.id.event_list_row_end_date);

            TextView ownerById = (TextView) convertView.findViewById(R.id.event_owner_by_id);
            TextView group = (TextView) convertView.findViewById(R.id.group_by_id);

            Event event = groupsEvents.get(position);
            eventName.setText(event.getName());
            startDate.setText(event.getStartDate());
            endDate.setText(event.getEndDate());

            group.setText(event.getGroupName());

            //get the owner from sql by id
            ownerById.setText(event.getOwnerById());
            return  convertView;
        }
    }


}
