package com.example.kchau.tasker;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.kchau.tasker.data.TaskContract.TaskEntry;

import java.security.Provider;

public class EditActivity extends AppCompatActivity {

    private int recentPosition;
    private EditText mNameEdit;
    private EditText mStartTimeEdit;
    private EditText mEndTimeEdit;
    private CheckBox mIsDoneCheckBox;

    // TODO: change endtime to duration??

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        FloatingActionButton saveButton = (FloatingActionButton) findViewById(R.id.save_actionbutton);
        saveButton.setImageResource(R.drawable.ic_baseline_done_24px);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
                finish();
            }
        });

        mNameEdit = (EditText) findViewById(R.id.et_name);
        // TODO: use a DialogFragment with a time picker
        mStartTimeEdit = (EditText) findViewById(R.id.et_start_time);
        mEndTimeEdit = (EditText) findViewById(R.id.et_end_time);
        mIsDoneCheckBox = (CheckBox) findViewById(R.id.edit_checkbox);

        Intent intent = getIntent();
        recentPosition = intent.getIntExtra("position", -1);

        if (recentPosition != -1){
            Uri uri = ContentUris.withAppendedId(TaskEntry.CONTENT_URI, recentPosition);
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            cursor.moveToFirst();
            String name = cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_NAME));
            String startTime = cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_START_TIME));
            String endTime = cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_END_TIME));
            boolean isDone = (cursor.getInt(cursor.getColumnIndexOrThrow(TaskEntry.COLUMN_TASK_IS_DONE)) == TaskEntry.DONE_TRUE);

            mNameEdit.setText(name);
            mStartTimeEdit.setText(startTime);
            mEndTimeEdit.setText(endTime);
            mIsDoneCheckBox.setChecked(isDone);

        }
    }

    private int saveTask(){
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_TASK_NAME, String.valueOf(mNameEdit.getText()));
        values.put(TaskEntry.COLUMN_TASK_START_TIME, String.valueOf(mStartTimeEdit.getText()));
        values.put(TaskEntry.COLUMN_TASK_END_TIME, String.valueOf(mEndTimeEdit.getText()));
        boolean isDone = mIsDoneCheckBox.isChecked();
        if (isDone){
            values.put(TaskEntry.COLUMN_TASK_IS_DONE, TaskEntry.DONE_TRUE);
        } else {
            values.put(TaskEntry.COLUMN_TASK_IS_DONE, TaskEntry.DONE_FALSE);
        }

        if (recentPosition == -1){
            getContentResolver().insert(TaskEntry.CONTENT_URI, values);
            return 1;
        }

        Uri uri = ContentUris.withAppendedId(TaskEntry.CONTENT_URI, recentPosition);
        return getContentResolver().update(uri, values, null, null);
    }

    // TODO: add delete button when updating

    // TODO: after deleting all and inserting new, app stops on tapping new
    // feels like an index problem tbh
}