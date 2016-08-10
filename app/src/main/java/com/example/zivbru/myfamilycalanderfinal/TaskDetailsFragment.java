package com.example.zivbru.myfamilycalanderfinal;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zivbru.myfamilycalanderfinal.Model.Model;
import com.example.zivbru.myfamilycalanderfinal.Model.Task;


public class TaskDetailsFragment extends Fragment {
    String userId,taskId;
    Task task;
    public TaskDetailsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_task_details, container, false);
        userId=   ((ComingEventsTasksActivity) getActivity()).getUserId();
        taskId=   ((ComingEventsTasksActivity) getActivity()).getTasksId();
        Log.d("taskId",taskId+ " taskId");
        final TextView taskName = (TextView) view.findViewById(R.id.details_Task_name);
        final TextView targetDate = (TextView) view.findViewById(R.id.details_task_target_date);
        final TextView owner = (TextView) view.findViewById(R.id.details_task_owner);
        final TextView taskDescription = (TextView) view.findViewById(R.id.task_description);
        final TextView relatedEvent = (TextView) view.findViewById(R.id.details_task_related_event);
        final TextView relatedEventText = (TextView) view.findViewById(R.id.details_task_related_event_text);

        Model.instance().getTask(userId, taskId, new Model.GetTaskListener() {
            @Override
            public void done(Task doneTask) {
                task = doneTask;
                taskName.setText(task.getTitle());
                targetDate.setText(task.getTargetDate());
                owner.setText(task.getOwnerId());
                taskDescription.setText(task.getDescription());
                if(task.getRelatedEvent()!=null) {
                    relatedEvent.setText(task.getRelatedEvent());
                }
                else{
                    relatedEventText.setVisibility(View.INVISIBLE);
                }
            }
        });

//        EventImage.setBackground(Drawable.createFromPath(event.get));

        Button editTask= (Button) view.findViewById(R.id.edit_task);
        editTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent= new Intent(getActivity(),EditTaskActivity.class);
                        intent.putExtra("UserId", userId);
                        intent.putExtra("TaskId", taskId);
                        startActivity(intent);
                    }
                });
                t.start();

            }
        });




        return view;
    }

}
