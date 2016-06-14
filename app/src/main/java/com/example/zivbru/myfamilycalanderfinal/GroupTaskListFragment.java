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
import com.example.zivbru.myfamilycalanderfinal.Model.Model;
import com.example.zivbru.myfamilycalanderfinal.Model.Task;

import java.util.ArrayList;


public class GroupTaskListFragment extends Fragment {
    String userId="";
    ListView list;
    ArrayList<Task> groupsTasks;
    GroupTaskAdapter adapter;
    ImageButton addGroupTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        groupsTasks= new ArrayList<Task>();
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_group_task_list, container, false);
        userId = ((ComingEventsTasksActivity) getActivity()).getUserId();
        getActivity().setTitle("Groups Tasks List");
        list= (ListView) view.findViewById(R.id.group_task_list_view);
        Model.instance().getAllGroupsTasks(userId, new Model.GetTasksListener() {
            @Override
            public void done(ArrayList<Task> allTasks) {
                groupsTasks = allTasks;
                adapter.notifyDataSetChanged();
            }
        });
        adapter = new GroupTaskAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Delegate activity = (Delegate) getActivity();
                activity.switchFragment("GroupTaskDetailsFragment");
                ((ComingEventsTasksActivity) getActivity()).setTaskId(groupsTasks.get(position).getId());
            }
        });
        addGroupTask = (ImageButton) view.findViewById(R.id.group_task_list_add_task);
        addGroupTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delegate activity = (Delegate) getActivity();
                activity.switchFragment("NewGroupTaskFragment");
                adapter.notifyDataSetChanged();
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
        getActivity().setTitle("Groups Tasks List");
        adapter.notifyDataSetChanged();
    }

    public class GroupTaskAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return groupsTasks.size();
        }

        @Override
        public Object getItem(int position) {
            return groupsTasks.get(position);
        }

        @Override
        public long getItemId(int position) {
            return  position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null){
                LayoutInflater layoutInflater = getActivity().getLayoutInflater();
                convertView= layoutInflater.inflate(R.layout.fragment_group_task_list_row,null);
            }
            else{
                Log.d("TAG", "use convert view:" + position);
            }

            TextView taskTitle= (TextView) convertView.findViewById(R.id.group_task_list_row_task_title);
            TextView targetDate = (TextView) convertView.findViewById(R.id.group_task_list_row_target_date);
            TextView assigenedTo = (TextView) convertView.findViewById(R.id.group_task_list_row_assigenedTo);
            TextView description = (TextView) convertView.findViewById(R.id.group_task_list_row_description);
            TextView ownerId = (TextView) convertView.findViewById(R.id.group_task_list_row_ownerId);
            TextView relatedEvent = (TextView) convertView.findViewById(R.id.group_task_list_row_relatedEvent);
            TextView group=  (TextView) convertView.findViewById(R.id.group_task_list_row_group);
            Task task = groupsTasks.get(position);
            taskTitle.setText(task.getTitle());
            targetDate.setText(task.getTargetDate());
            assigenedTo.setText(task.getAssigenedTo());
            description.setText(task.getDescription());
            ownerId.setText(task.getOwnerId());
            relatedEvent.setText(task.getRelatedEvent());
            group.setText(task.getGroupId());

            return  convertView;
        }
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
