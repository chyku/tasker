package com.example.kchau.tasker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.kchau.tasker.data.TaskContract.TaskEntry;

public class TaskDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "tasks.db";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TaskEntry.TABLE_NAME + " ( "
                    + TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TaskEntry.COLUMN_TASK_NAME + " TEXT NOT NULL, "
                    + TaskEntry.COLUMN_TASK_START_TIME + " TEXT NOT NULL, "
                    + TaskEntry.COLUMN_TASK_END_TIME + " TEXT, "
                    + TaskEntry.COLUMN_TASK_IS_DONE + " INTEGER NOT NULL DEFAULT 0 );" ;

    public TaskDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // only upgrade when you increase version
    }
}