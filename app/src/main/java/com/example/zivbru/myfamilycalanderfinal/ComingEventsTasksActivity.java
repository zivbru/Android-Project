package com.example.zivbru.myfamilycalanderfinal;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.example.zivbru.myfamilycalanderfinal.Model.MyApplication;
import com.example.zivbru.myfamilycalanderfinal.Model.MyService;
import com.example.zivbru.myfamilycalanderfinal.Model.User;

import java.util.ArrayList;


public class ComingEventsTasksActivity extends ActionBarActivity implements Delegate {

    String userId;
    String eventId;
    String taskId;
    String groupName;
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
    GroupListFragment groupListFragment;
    ArrayList<Fragment> fragmentArrayList;
    ActionBar.Tab upcomingEventsTab;
    ActionBar.Tab upcomingTasksTab;
    ActionBar.Tab upcomingGroupsEventsTab;
    ActionBar.Tab upcomingGroupsTasksTab;
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
        MyApplication m= new MyApplication(this);
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
        fragmentArrayList.add(eventListFragment);
        fragmentArrayList.add(taskListFragment);
        fragmentArrayList.add(groupTaskListFragment);
        fragmentArrayList.add(groupEventListFragment);
        transaction.commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ActionBar actionBar = getSupportActionBar();
        upcomingEventsTab = actionBar.newTab().setText("Upcoming events");
        upcomingTasksTab = actionBar.newTab().setText("Upcoming tasks");
        upcomingGroupsEventsTab = actionBar.newTab().setText("Upcoming groups events");
        upcomingGroupsTasksTab = actionBar.newTab().setText("Upcoming groups tasks");
        actionBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(upcomingEventsTab.setTabListener(new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                hideFragments();
                fragmentTransaction.show(eventListFragment);

            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                fragmentTransaction.hide(eventListFragment);
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                fragmentTransaction.show(eventListFragment);
            }
        }));
        actionBar.addTab(upcomingTasksTab.setTabListener(new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                hideFragments();
                fragmentTransaction.show(taskListFragment);
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                fragmentTransaction.hide(taskListFragment);
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                fragmentTransaction.show(taskListFragment);
            }
        }));
        actionBar.addTab(upcomingGroupsEventsTab.setTabListener(new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                hideFragments();
                fragmentTransaction.show(groupEventListFragment);

            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                fragmentTransaction.hide(groupEventListFragment);
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                fragmentTransaction.show(groupEventListFragment);
            }

        }));
        actionBar.addTab(upcomingGroupsTasksTab.setTabListener(new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                hideFragments();
                fragmentTransaction.show(groupTaskListFragment);
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                fragmentTransaction.hide(groupTaskListFragment);
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                fragmentTransaction.show(groupTaskListFragment);
            }

        }));

        mNavItems.add(new NavItem("Add group", "", R.drawable.addgroup));
        mNavItems.add(new NavItem("Groups list", "View all your groups", R.drawable.groupslist));
        mNavItems.add(new NavItem("Start service", "Get notifications", R.drawable.start));
        mNavItems.add(new NavItem("Strop service", "", R.drawable.stop));
        mNavItems.add(new NavItem("Logout", "", R.drawable.logout));
        mNavItems.add(new NavItem("About", "Get to know about us", R.drawable.info));
        userPicture= (ImageView) findViewById(R.id.user_picture);
        userNameInSidebar= (TextView) findViewById(R.id.userNameInSidebar);
//        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.drawerPane);
        Model.instance().getUser(userId, new Model.UserListener() {
            @Override
            public void done(User retUser) {
                user = retUser;
                userNameInSidebar.setText(user.getFirstName() + " " + user.getLastName());
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
        RelativeLayout profileBox = (RelativeLayout) findViewById(R.id.profileBox);
        profileBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ComingEventsTasksActivity.this,UserDetailsActivity.class);
                intent.putExtra("UserId",userId);
                startActivity(intent);
            }
        });


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

                if(position==0){//addGroup
                    Intent intent = new Intent(ComingEventsTasksActivity.this,NewGroupActivity.class);
                    intent.putExtra("UserId",userId);
                    startActivity(intent);
                }
                else if(position==1){//groupslist

                    mDrawerLayout.closeDrawer(mDrawerPane);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    if(!fragmentArrayList.contains(groupListFragment)) {
                        groupListFragment = new GroupListFragment();

                    }
                    hideFragments();
                    transaction.remove(groupListFragment);
                    transaction.add(R.id.mainContent, groupListFragment);
                    transaction.show(groupListFragment);
                    transaction.addToBackStack("");
                    transaction.commit();
                    fragmentArrayList.add(groupListFragment);
                    getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);


                }
                else if(position==2){//start service
                    startService(view);
                }
                else if(position==3){//stopService
                   stopService(view);
                }
                else if( position==4){//logout
                    Model.instance().updateLogin("", "false");
                    Intent intent= new Intent(ComingEventsTasksActivity.this,LoginActivity.class);
                    startActivity(intent);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }).start();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle
        // If it returns true, then it has handled
        // the nav drawer indicator touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if(item.getItemId()== R.id.action_favorite){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Title");
            builder.setItems(new CharSequence[]
                            {"Add private event", "Add private task", "Add group event", "Add group task"},
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            // of the selected item

                            switch (which) {
                                case 0:
                                    switchFragment("NewEventFragment");
                                    break;
                                case 1:
                                    switchFragment("NewTaskFragment");
                                    break;
                                case 2:
                                    switchFragment("NewGroupEventFragment");
                                    break;
                                case 3:
                                    switchFragment("NewGroupTaskFragment");
                                    break;
                            }
                        }
                    });
            builder.create().show();
        }


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

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        if(mDrawerLayout.isDrawerOpen(mDrawerPane)){
            mDrawerLayout.closeDrawer(mDrawerPane);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
                getSupportActionBar().selectTab(upcomingEventsTab);
//                hideFragments();
                getSupportFragmentManager().beginTransaction().commit();

            } else {
                super.onBackPressed();
            }
        }
    }


    @Override
    public void switchFragment(final String fragmentName) {

        if (fragmentName.equals("EventDetailsFragment")){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(!fragmentArrayList.contains(eventDetailsFragment)) {
                eventDetailsFragment = new EventDetailsFragment();
            }
            if(getSupportActionBar().getNavigationMode()!=ActionBar.NAVIGATION_MODE_STANDARD)
                getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            hideFragments();
            transaction.add(R.id.mainContent, eventDetailsFragment);
            transaction.show(eventDetailsFragment);
            transaction.addToBackStack("");
            transaction.commit();
            fragmentArrayList.add(eventDetailsFragment);
        }
        else if(fragmentName.equals("EventListFragment")){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            hideFragments();
            transaction.show(eventListFragment);
            transaction.addToBackStack("");
            transaction.commit();
            getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        }
        else if(fragmentName.equals("NewEventFragment")){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(!fragmentArrayList.contains(newEventFragment)) {
                newEventFragment = new NewEventFragment();
            }
            if(getSupportActionBar().getNavigationMode()!=ActionBar.NAVIGATION_MODE_STANDARD)
                getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            hideFragments();
            transaction.add(R.id.mainContent, newEventFragment);
            transaction.show(newEventFragment);
            transaction.addToBackStack("");
            transaction.commit();
            fragmentArrayList.add(newEventFragment);

        }
        else if(fragmentName.equals("TaskDetailsFragment")){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(!fragmentArrayList.contains(taskDetailsFragment)) {
                taskDetailsFragment = new TaskDetailsFragment();
            }
            if(getSupportActionBar().getNavigationMode()!=ActionBar.NAVIGATION_MODE_STANDARD)
                getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            hideFragments();
            transaction.add(R.id.mainContent, taskDetailsFragment);
            transaction.show(taskDetailsFragment);
            transaction.addToBackStack("");
            transaction.commit();
            fragmentArrayList.add(taskDetailsFragment);
        }
        else if(fragmentName.equals("TaskListFragment")){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(!fragmentArrayList.contains(taskListFragment)) {
                taskListFragment = new TaskListFragment();
            }
            hideFragments();
            transaction.show(taskListFragment);
            transaction.addToBackStack("");
            transaction.commit();
            fragmentArrayList.add(taskListFragment);
            getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        }
        else if(fragmentName.equals("NewTaskFragment")){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(!fragmentArrayList.contains(newTaskFragment)) {
                newTaskFragment = new NewTaskFragment();
            }
            if(getSupportActionBar().getNavigationMode()!=ActionBar.NAVIGATION_MODE_STANDARD)
                getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            hideFragments();
            transaction.remove(newTaskFragment);
            transaction.add(R.id.mainContent, newTaskFragment);
            transaction.show(newTaskFragment);
            transaction.addToBackStack("");
            transaction.commit();
            fragmentArrayList.add(newTaskFragment);
        }
        else if(fragmentName.equals("GroupEventListFragment")){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(!fragmentArrayList.contains(groupEventListFragment)) {
                groupEventListFragment = new GroupEventListFragment();
            }
            hideFragments();
            transaction.show(groupEventListFragment);
            transaction.addToBackStack("");
            transaction.commit();
            fragmentArrayList.add(groupEventListFragment);
            getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        }
        else if(fragmentName.equals("NewGroupEventFragment")){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(!fragmentArrayList.contains(newGroupEventFragment)) {
                newGroupEventFragment = new NewGroupEventFragment();
            }
            if(getSupportActionBar().getNavigationMode()!=ActionBar.NAVIGATION_MODE_STANDARD)
                getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            hideFragments();
            transaction.remove(newGroupEventFragment);
            transaction.add(R.id.mainContent, newGroupEventFragment);
            transaction.show(newGroupEventFragment);
            transaction.addToBackStack("");
            transaction.commit();
            fragmentArrayList.add(newGroupEventFragment);

        }
        else if(fragmentName.equals("GroupsEventDetailsFragment")){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(!fragmentArrayList.contains(groupEventDetailsFragment)) {
                groupEventDetailsFragment = new GroupEventDetailsFragment();
            }
            if(getSupportActionBar().getNavigationMode()!=ActionBar.NAVIGATION_MODE_STANDARD)
                getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            transaction.add(R.id.mainContent, groupEventDetailsFragment);
            hideFragments();
            transaction.show(groupEventDetailsFragment);
            transaction.addToBackStack("");
            transaction.commit();
            fragmentArrayList.add(groupEventDetailsFragment);
        }
        else if(fragmentName.equals("GroupTaskListFragment")){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(!fragmentArrayList.contains(groupTaskListFragment)) {
                groupTaskListFragment = new GroupTaskListFragment();
            }
            hideFragments();
            transaction.show(groupTaskListFragment);
            transaction.addToBackStack("");
            transaction.commit();
            fragmentArrayList.add(groupTaskListFragment);
            getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        }
        else if(fragmentName.equals("NewGroupTaskFragment")){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(!fragmentArrayList.contains(newGroupTaskFragment)) {
                newGroupTaskFragment = new NewGroupTaskFragment();

            }
            hideFragments();
            transaction.remove(newGroupTaskFragment);
            transaction.add(R.id.mainContent, newGroupTaskFragment);
            if(getSupportActionBar().getNavigationMode()!=ActionBar.NAVIGATION_MODE_STANDARD)
                getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            transaction.show(newGroupTaskFragment);
            transaction.addToBackStack("");
            transaction.commit();
            fragmentArrayList.add(newGroupTaskFragment);
        }
        else if(fragmentName.equals("GroupTaskDetailsFragment")){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            if(!fragmentArrayList.contains(groupTaskDetailsFragment)) {
                groupTaskDetailsFragment = new GroupTaskDetailsFragment();
            }
            if(getSupportActionBar().getNavigationMode()!=ActionBar.NAVIGATION_MODE_STANDARD)
                getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            hideFragments();
            transaction.add(R.id.mainContent, groupTaskDetailsFragment);
            transaction.show(groupTaskDetailsFragment);
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


    private void hideFragments(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment :fragmentArrayList) {
            transaction.hide(fragment);
        }
        transaction.commit();
    }


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }



    // Method to start the service
    public void startService(View view) {
        Intent intent = new Intent(getBaseContext(), MyService.class);
        intent.putExtra("UserId",userId);
        startService(intent);
    }

    // Method to stop the service
    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), MyService.class));
    }


}
