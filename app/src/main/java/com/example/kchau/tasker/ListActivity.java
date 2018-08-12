package com.example.kchau.tasker;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kchau.tasker.data.TaskContract.TaskEntry;

public class ListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private ListView mList;
    private TaskAdapter taskAdapter;

    private final int TASK_LOADER_ID = 0;

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

        mList = (ListView) findViewById(R.id.listview_task);
        taskAdapter = new TaskAdapter(this, null);
        mList.setAdapter(taskAdapter);

        getLoaderManager().initLoader(TASK_LOADER_ID, null, this);

    }

    private void insertDummyData(){
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_TASK_NAME, "Meditate");
        values.put(TaskEntry.COLUMN_TASK_START_TIME, "05:00 AM");
        values.put(TaskEntry.COLUMN_TASK_END_TIME, "06:00 AM");
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

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, TaskEntry.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        taskAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        taskAdapter.swapCursor(null);
    }

    // TODO: add checkbutton onclick functionality- set isdone to true/false
}
