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
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.zivbru.myfamilycalanderfinal.Model.Model;
import com.example.zivbru.myfamilycalanderfinal.Model.Task;

import java.util.ArrayList;
import java.util.List;


public class TaskListFragment extends Fragment {
    String userId="";
    ListView list;
    ArrayList<Task> tasks;
    TaskAdapter adapter;
    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        tasks= new ArrayList<Task>();
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        userId =  ((ComingEventsTasksActivity) getActivity()).getUserId();
        getActivity().setTitle("Tasks List");
        list= (ListView) view.findViewById(R.id.task_list_view);
        progressBar = (ProgressBar) view.findViewById(R.id.mainProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        Model.instance().getAllTasks(userId, new Model.GetListTaskListener() {
            @Override
            public void onResult(ArrayList<Task> allTasks) {
                tasks = allTasks;
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancel() {

            }
        });
        adapter = new TaskAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Delegate activity = (Delegate) getActivity();
                activity.switchFragment("TaskDetailsFragment");
                ((ComingEventsTasksActivity) getActivity()).setTaskId(tasks.get(position).getId());
                Log.d("SetTaskId",tasks.get(position).getId());

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
        getActivity().setTitle("Tasks List");
        adapter.notifyDataSetChanged();
    }

    public class TaskAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return tasks.size();
        }

        @Override
        public Object getItem(int position) {
            return tasks.get(position);
        }

        @Override
        public long getItemId(int position) {
            return  position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null){
                LayoutInflater layoutInflater = getActivity().getLayoutInflater();
                convertView= layoutInflater.inflate(R.layout.fragment_task_list_row,null);
            }
            else{
                Log.d("TAG", "use convert view:" + position);
            }

            TextView taskTitle= (TextView) convertView.findViewById(R.id.task_list_row_task_title);

            Task task= tasks.get(position);
            taskTitle.setText(task.getTitle());




            return  convertView;
        }
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}



