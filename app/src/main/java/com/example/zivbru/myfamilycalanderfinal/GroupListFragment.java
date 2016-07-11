
package com.example.zivbru.myfamilycalanderfinal;

import android.graphics.Bitmap;
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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zivbru.myfamilycalanderfinal.Model.Group;
import com.example.zivbru.myfamilycalanderfinal.Model.Model;
import java.util.ArrayList;


public class GroupListFragment extends Fragment {

    String userId="";
    ListView list;
    ArrayList<Group> groups;
    GroupAdapter adapter;
    ImageView groupPicture;

    public GroupListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragmen_group_list, container, false);
        userId =  ((ComingEventsTasksActivity) getActivity()).getUserId();
        getActivity().setTitle("Groups List");
        groups= new ArrayList<Group>();
        list= (ListView) view.findViewById(R.id.group_list_view);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.GroupListProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        Model.instance().getAllGroups(userId, new Model.GetGroupsListListener() {
            @Override
            public void onResult(ArrayList<Group> allGroups) {
                groups = allGroups;
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancel() {

            }
        });
        adapter = new GroupAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Delegate activity = (Delegate) getActivity();
//                activity.switchFragment("TaskDetailsFragment");
//                ((ComingEventsTasksActivity) getActivity()).setTaskId(tasks.get(position).getId());
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

    public class GroupAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return groups.size();
        }

        @Override
        public Object getItem(int position) {
            return groups.get(position);
        }

        @Override
        public long getItemId(int position) {
            return  position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null){
                LayoutInflater layoutInflater = getActivity().getLayoutInflater();
                convertView= layoutInflater.inflate(R.layout.fragment_group_list_row,null);
            }
            else{
                Log.d("TAG", "use convert view:" + position);
            }
            groupPicture= (ImageView) convertView.findViewById(R.id.group_list_row_image);
            ListView allUsers= (ListView) convertView.findViewById(R.id.group_list_row_users);
            ListView allEvents= (ListView) convertView.findViewById(R.id.group_list_row_events);
            ListView allTasks= (ListView) convertView.findViewById(R.id.group_list_row_tasks);
            TextView title= (TextView) convertView.findViewById(R.id.group_title);
            final ProgressBar progressBar= (ProgressBar) convertView.findViewById(R.id.ProgressBarGetNames);
            Group group=groups.get(position);
            Model.instance().loadImage(group.getPictureName(), new Model.LoadImageListener() {
                @Override
                public void onResult(Bitmap imageBmp) {
                    groupPicture.setImageBitmap(imageBmp);
                }
            });
            title.setText(group.getTitle());
//            UserFireBase userFireBase = new UserFireBase(Model.instance().getFirebaseModel().getMyFirebaseRef());
            ArrayList<String> usersNames= group.getUsersList();

            //add progress bar

//            for (String id:group.getUsersList()) {
//                userFireBase.getNameForUser(id, new Model.getUserNameListener() {
//                    @Override
//                    public void success(String name) {
//                        usersNames.add(name);
//
//                    }
//
//                    @Override
//                    public void fail(String msg) {
//
//                    }
//                });
//            }

            progressBar.setVisibility(View.VISIBLE);

            progressBar.setVisibility(View.GONE);
            ArrayAdapter usersAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, usersNames);
            allUsers.setAdapter(usersAdapter);
//            ArrayAdapter eventAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, group.getRelatedEvents());
//            allEvents.setAdapter(eventAdapter);
//            ArrayAdapter TaskAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, group.getRelatedTasks());
//            allTasks.setAdapter(TaskAdapter);

            return  convertView;
        }
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}


