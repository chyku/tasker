package com.example.kchau.tasker;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.kchau.tasker.data.TaskContract.TaskEntry;
import com.example.kchau.tasker.data.TaskContract;

public class TaskAdapter extends CursorAdapter {

    public TaskAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView taskName = (TextView) view.findViewById(R.id.tv_task_name);
        TextView taskStartTime = (TextView) view.findViewById(R.id.tv_task_start_time);
        TextView taskEndTime = (TextView) view.findViewById(R.id.tv_task_end_time);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.item_checkbox);

        String name = cursor.getString(cursor.getColumnIndexOrThrow(TaskEntry.COLUMN_TASK_NAME));
        String startTime = cursor.getString(cursor.getColumnIndexOrThrow(TaskEntry.COLUMN_TASK_START_TIME));
        String endTime = cursor.getString(cursor.getColumnIndexOrThrow(TaskEntry.COLUMN_TASK_END_TIME));
        int isDone = cursor.getInt(cursor.getColumnIndexOrThrow(TaskEntry.COLUMN_TASK_IS_DONE));

        taskName.setText(name);
        taskStartTime.setText(startTime);
        taskEndTime.setText(endTime);

        if (isDone == TaskEntry.DONE_TRUE){
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
    }
}
