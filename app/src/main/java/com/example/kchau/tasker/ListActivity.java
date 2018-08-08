package com.example.kchau.tasker;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.kchau.tasker.data.TaskContract.TaskEntry;

public class ListActivity extends AppCompatActivity {

    private TextView mDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDisplay = (TextView) findViewById(R.id.list_textview);

        displayTasks();

    }

    // Temporary function that displays database contents in a TextView
    // TODO: implement Loaders to take accessing the database off the main thread
    // TODO: implement an Adapter to properly display data
    private void displayTasks(){

        mDisplay.setText(TaskEntry._ID + " | " +
                TaskEntry.COLUMN_TASK_NAME + " | " +
                TaskEntry.COLUMN_TASK_START_TIME + " | " +
                TaskEntry.COLUMN_TASK_END_TIME + " | " +
                TaskEntry.COLUMN_TASK_IS_DONE);

        Cursor cursor = getContentResolver().query(TaskEntry.CONTENT_URI, null, null, null, null);

        if (cursor == null) {return;}

        cursor.moveToFirst();
        int idIndex = cursor.getColumnIndex(TaskEntry._ID);
        int nameIndex = cursor.getColumnIndex(TaskEntry.COLUMN_TASK_NAME);
        int startIndex = cursor.getColumnIndex(TaskEntry.COLUMN_TASK_START_TIME);
        int endIndex = cursor.getColumnIndex(TaskEntry.COLUMN_TASK_END_TIME);
        int doneIndex = cursor.getColumnIndex(TaskEntry.COLUMN_TASK_IS_DONE);

        while (cursor.moveToNext()){
            mDisplay.append( "\n" +
                    cursor.getInt(idIndex) + " | " +
                    cursor.getString(nameIndex) + " | " +
                    cursor.getString(startIndex) + " | " +
                    cursor.getString(endIndex) + " | " +
                    cursor.getInt(doneIndex)
            );
        }

    }

    private void insertDummyData(){
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_TASK_NAME, "Meditate");
        values.put(TaskEntry.COLUMN_TASK_START_TIME, "2018-08-15 08:30:00.000");
        values.put(TaskEntry.COLUMN_TASK_END_TIME, "2018-08-15 08:45:00.000");
        values.put(TaskEntry.COLUMN_TASK_IS_DONE, TaskEntry.DONE_FALSE);

        getContentResolver().insert(TaskEntry.CONTENT_URI, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_insert_dummy){
            insertDummyData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
