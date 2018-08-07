package com.example.kchau.tasker.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class TaskContract {
    public static final String CONTENT_AUTHORITY = "com.example.android.tasks";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_TASKS = "tasks";


    public static abstract class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TASKS);

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TASK_NAME = "name";
        public static final String COLUMN_TASK_START = "start";
        public static final String COLUMN_TASK_END = "end";
        public static final String COLUMN_TASK_DATE = "date";
        public static final String COLUMN_TASK_DONE = "done";

        // Defines done boolean (has to be stored by sqlite as int)

        public static final int DONE_TRUE = 0;
    }
}
