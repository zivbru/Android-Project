package com.example.zivbru.myfamilycalanderfinal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.zivbru.myfamilycalanderfinal.Model.Model;
import com.example.zivbru.myfamilycalanderfinal.Model.User;

import java.util.ArrayList;


public class ComingEventsTasksActivity extends ActionBarActivity implements Delegate {

    String userId;
    String eventId;
    String taskId;
    ImageView userPicture;
    User user;
    TextView userNameInSidebar;
    EventListFragment eventListFragment;
    TaskListFragment taskListFragment;
    GroupTaskListFragment groupTaskListFragment;
    GroupEventListFragment groupEventListFragment;
    EventDetailsFragment eventDetailsFragment;
    NewEventFragment newEventFragment;
    TaskDetailsFragment taskDetailsFragment;
    NewTaskFragment newTaskFragment;
    NewGroupEventFragment newGroupEventFragment;
    GroupEventDetailsFragment groupEventDetailsFragment;
    NewGroupTaskFragment newGroupTaskFragment;
    GroupTaskDetailsFragment groupTaskDetailsFragment;
    ArrayList<Fragment> fragmentArrayList;

    private static String TAG = ComingEventsTasksActivity.class.getSimpleName();

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentArrayList= new ArrayList<Fragment>();
        Bundle extras = getIntent().getExtras();
        userId = extras.getString("UserId");
        Bundle bundle = new Bundle();
        bundle.putString("UserId", userId);
        eventListFragment= new EventListFragment();
        taskListFragment= new TaskListFragment();
        groupEventListFragment= new GroupEventListFragment();
        groupTaskListFragment= new GroupTaskListFragment();
        eventListFragment.setArguments(bundle);
        taskListFragment.setArguments(bundle);
        groupEventListFragment.setArguments(bundle);
        groupTaskListFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.mainContent, eventListFragment, "eventListFragment");
        transaction.add(R.id.mainContent, taskListFragment, "taskListFragment");
        transaction.add(R.id.mainContent, groupTaskListFragment, "groupTaskListFragment");
        transaction.add(R.id.mainContent, groupEventListFragment, "groupEventListFragment");
        transaction.show(eventListFragment);
        transaction.hide(taskListFragment);
        transaction.hide(groupTaskListFragment);
        transaction.hide(groupEventListFragment);
        transaction.commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText("Upcoming events").setTabListener(new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                fragmentTransaction.show(eventListFragment);
                for (Fragment fragment : fragmentArrayList) {
                    fragmentTransaction.hide(fragment);
                }
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                fragmentTransaction.hide(eventListFragment);
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }
        }));
        actionBar.addTab(actionBar.newTab().setText("Upcoming tasks").setTabListener(new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                fragmentTransaction.show(taskListFragment);



                for (Fragment fragment : fragmentArrayList) {
                    fragmentTransaction.hide(fragment);
                }
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                fragmentTransaction.hide(taskListFragment);
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }
        }));
        actionBar.addTab(actionBar.newTab().setText("Upcoming groups events").setTabListener(new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                fragmentTransaction.show(groupEventListFragment);
                for (Fragment fragment : fragmentArrayList) {
                    fragmentTransaction.hide(fragment);
                }
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                fragmentTransaction.hide(groupEventListFragment);
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }

        }));
        actionBar.addTab(actionBar.newTab().setText("Upcoming groups tasks").setTabListener(new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                fragmentTransaction.show(groupTaskListFragment);

                for (Fragment fragment : fragmentArrayList) {
                    fragmentTransaction.hide(fragment);
                }

            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                fragmentTransaction.hide(groupTaskListFragment);
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }

        }));

        mNavItems.add(new NavItem("Add group", "", R.drawable.addgroup));
        mNavItems.add(new NavItem("Groups list", "View all your groups", R.drawable.groupslist));
        mNavItems.add(new NavItem("Preferences", "Change your preferences", R.drawable.setting));
        mNavItems.add(new NavItem("Logout", "", R.drawable.logout));
        mNavItems.add(new NavItem("About", "Get to know about us", R.drawable.info));
        userPicture= (ImageView) findViewById(R.id.user_picture);
        userNameInSidebar= (TextView) findViewById(R.id.userNameInSidebar);
//        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.drawerPane);
        Model.instance().getUser(userId, new Model.UserListener() {
            @Override
            public void done(User retUser) {
                user = retUser;
                userNameInSidebar.setText(user.getFirstName()+ " "+user.getLastName());
                //add progress bar
                Model.instance().loadImage(user.getPictureName(), new Model.LoadImageListener() {
                    @Override
                    public void onResult(Bitmap imageBmp) {
                        if (userPicture != null) {
                            userPicture.setImageBitmap(imageBmp);
                        }
                    }
                });

            }
        });


        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
                Log.d("selected", String.valueOf(position));
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if(position==0){//addGroup
                    Intent intent = new Intent(ComingEventsTasksActivity.this,NewGroupActivity.class);
                    intent.putExtra("UserId",userId);
                    startActivity(intent);


                }
                else if(position==1){//groupslist

                    mDrawerLayout.closeDrawer(mDrawerPane);
                    GroupListFragment groupListFragment= new GroupListFragment();
                    transaction.hide(eventListFragment);
                    transaction.add(R.id.mainContent, groupListFragment);
                    transaction.addToBackStack("");
                    transaction.commit();
                    fragmentArrayList.add(groupListFragment);

                }
                else if(position==2){//setting

                }
                else if(position==3){//logout
                    Model.instance().updateLogin("","false");
                    Intent intent= new Intent(ComingEventsTasksActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();

                }
                else if( position==4){//aboutUs

                }
                /// item selected
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.d(TAG, "onDrawerClosed: " + getTitle());

                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle
        // If it returns true, then it has handled
        // the nav drawer indicator touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }


    private void selectItemFromDrawer(int position) {

        mDrawerList.setItemChecked(position, true);
        setTitle(mNavItems.get(position).mTitle);
        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {

        if(mDrawerLayout.isDrawerOpen(mDrawerPane)){
            mDrawerLayout.closeDrawer(mDrawerPane);
        }
        else {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();

            } else {
                super.onBackPressed();
            }
        }
    }




    @Override
    public void switchFragment(final String fragmentName) {

        if (fragmentName.equals("EventDetailsFragment")){
            eventDetailsFragment= new EventDetailsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(eventListFragment);
            transaction.add(R.id.mainContent, eventDetailsFragment);
            transaction.addToBackStack("");
            transaction.commit();
            fragmentArrayList.add(eventDetailsFragment);
        }
        else if(fragmentName.equals("EventListFragment")){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(newEventFragment);
            transaction.hide(eventDetailsFragment);
            transaction.show(eventListFragment);
            transaction.addToBackStack("");
            transaction.commit();
            fragmentArrayList.add(eventListFragment);

        }
        else if(fragmentName.equals("NewEventFragment")){
            newEventFragment= new NewEventFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(eventListFragment);
            transaction.add(R.id.mainContent, newEventFragment);
            transaction.addToBackStack("");
            transaction.commit();
            fragmentArrayList.add(newEventFragment);
        }
        else if(fragmentName.equals("TaskDetailsFragment")){
            taskDetailsFragment= new TaskDetailsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(taskListFragment);
            transaction.add(R.id.mainContent, taskDetailsFragment);
            transaction.addToBackStack("");
            transaction.commit();
            fragmentArrayList.add(taskDetailsFragment);
        }
        else if(fragmentName.equals("TaskListFragment")){
            taskListFragment= new TaskListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(taskDetailsFragment);
            transaction.hide(newTaskFragment);
            transaction.show(taskListFragment);
            transaction.addToBackStack("");
            transaction.commit();
            fragmentArrayList.add(taskListFragment);
        }
        else if(fragmentName.equals("NewTaskFragment")){
            newTaskFragment = new NewTaskFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(taskListFragment);
            transaction.add(R.id.mainContent, newTaskFragment);
            transaction.addToBackStack("");
            transaction.commit();
            fragmentArrayList.add(newTaskFragment);
        }
        else if(fragmentName.equals("GroupEventListFragment")){
            groupEventListFragment= new GroupEventListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(newGroupEventFragment);
            transaction.hide(groupEventDetailsFragment);
            transaction.show(groupEventListFragment);
            transaction.addToBackStack("");
            transaction.commit();
            fragmentArrayList.add(groupEventListFragment);

        }
        else if(fragmentName.equals("NewGroupEventFragment")){
            newGroupEventFragment= new NewGroupEventFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(groupEventListFragment);
            transaction.add(R.id.mainContent, newGroupEventFragment);
            transaction.addToBackStack("");
            transaction.commit();
            fragmentArrayList.add(newGroupEventFragment);
        }
        else if(fragmentName.equals("GroupsEventDetailsFragment")){
            groupEventDetailsFragment= new GroupEventDetailsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(groupEventListFragment);
            transaction.add(R.id.mainContent, groupEventDetailsFragment);
            transaction.addToBackStack("");
            transaction.commit();
            fragmentArrayList.add(groupEventDetailsFragment);
        }
        else if(fragmentName.equals("GroupTaskListFragment")){
            groupTaskListFragment= new GroupTaskListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(newGroupTaskFragment);
            transaction.hide(groupTaskDetailsFragment);
            transaction.show(groupTaskListFragment);
            transaction.addToBackStack("");
            transaction.commit();
            fragmentArrayList.add(groupTaskListFragment);
        }
        else if(fragmentName.equals("NewGroupTaskFragment")){
            newGroupTaskFragment= new NewGroupTaskFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(groupTaskListFragment);
            transaction.add(R.id.mainContent, newGroupTaskFragment);
            transaction.addToBackStack("");
            transaction.commit();
            fragmentArrayList.add(newGroupTaskFragment);
        }
        else if(fragmentName.equals("GroupTaskDetailsFragment")){
            groupTaskDetailsFragment= new GroupTaskDetailsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(groupTaskListFragment);
            transaction.add(R.id.mainContent, groupTaskDetailsFragment);
            transaction.addToBackStack("");
            transaction.commit();
            fragmentArrayList.add(groupTaskDetailsFragment);
        }
    }




    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTasksId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    class NavItem {
        String mTitle;
        String mSubtitle;
        int mIcon;

        public NavItem(String title, String subtitle, int icon) {
            mTitle = title;
            mSubtitle = subtitle;
            mIcon = icon;
        }
    }

    class DrawerListAdapter extends BaseAdapter {

        Context mContext;
        ArrayList<NavItem> mNavItems;

        public DrawerListAdapter(Context context, ArrayList<NavItem> navItems) {
            mContext = context;
            mNavItems = navItems;
        }

        @Override
        public int getCount() {
            return mNavItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mNavItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.drawer_item, null);
            }
            else {
                view = convertView;
            }

            TextView titleView = (TextView) view.findViewById(R.id.title);
            TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
            ImageView iconView = (ImageView) view.findViewById(R.id.icon);

            titleView.setText( mNavItems.get(position).mTitle );
            subtitleView.setText( mNavItems.get(position).mSubtitle );
            iconView.setImageResource(mNavItems.get(position).mIcon);

            return view;
        }
    }


}
