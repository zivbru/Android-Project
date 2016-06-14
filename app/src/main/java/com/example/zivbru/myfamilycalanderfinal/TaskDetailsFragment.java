package com.example.zivbru.myfamilycalanderfinal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zivbru.myfamilycalanderfinal.Model.Model;
import com.example.zivbru.myfamilycalanderfinal.Model.Task;


public class TaskDetailsFragment extends Fragment {

    Task task;
    public TaskDetailsFragment(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_task_details, container, false);
        String userId=   ((ComingEventsTasksActivity) getActivity()).getUserId();
        String taskId=   ((ComingEventsTasksActivity) getActivity()).getTasksId();
        final TextView taskName = (TextView) view.findViewById(R.id.details_Task_name);
        final TextView targetDate = (TextView) view.findViewById(R.id.details_task_target_date);
        final TextView owner = (TextView) view.findViewById(R.id.details_task_owner);
        final TextView taskDescription = (TextView) view.findViewById(R.id.task_description);
        final TextView relatedEvent = (TextView) view.findViewById(R.id.details_task_related_event);

        Model.instance().getTask(userId, taskId, new Model.GetTaskListener() {
            @Override
            public void done(Task doneTask) {
                task = doneTask;
                taskName.setText(task.getTitle());
                targetDate.setText(task.getTargetDate());
                owner.setText(task.getOwnerId());
                taskDescription.setText(task.getDescription());
                relatedEvent.setText(task.getRelatedEvent());
            }
        });

//        EventImage.setBackground(Drawable.createFromPath(event.get));

        Button editEvent= (Button) view.findViewById(R.id.edit_event);
        editEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        return view;
    }

}
