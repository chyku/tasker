package com.example.kchau.tasker.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class TaskContract {
    public static final String CONTENT_AUTHORITY = "com.example.kchau.tasker";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_TASKS = "tasks";


    public static abstract class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TASKS);

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TASK_NAME = "name";
        public static final String COLUMN_TASK_START_TIME = "start_time";
        public static final String COLUMN_TASK_END_TIME = "end_time";
        public static final String COLUMN_TASK_IS_DONE = "is_done";

        // Defines done boolean (has to be stored by sqlite as int)

        public static final int DONE_TRUE = 1;
        public static final int DONE_FALSE = 0;
    }
}
