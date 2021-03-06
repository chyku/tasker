package com.example.kchau.tasker.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.example.kchau.tasker.data.TaskContract.TaskEntry;

public class TaskProvider extends ContentProvider {

    // TODO: sanity checks

    /** Tag for the log messages */
    public static final String LOG_TAG = TaskProvider.class.getSimpleName();
    private TaskDbHelper mTaskDbHelper;

    /** URI matcher code for the content URI for the pets table */
    private static final int TASKS = 100;

    /** URI matcher code for the content URI for a single pet in the pets table */
    private static final int TASK_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(TaskContract.CONTENT_AUTHORITY, TaskContract.PATH_TASKS, TASKS);
        sUriMatcher.addURI(TaskContract.CONTENT_AUTHORITY, TaskContract.PATH_TASKS + "/#", TASK_ID);
    }

    @Override
    public boolean onCreate() {
        mTaskDbHelper = new TaskDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mTaskDbHelper.getReadableDatabase();

        int uriCode = sUriMatcher.match(uri);

        //Return obj
        Cursor cursor;

        switch(uriCode){
            case TASKS:
                cursor = database.query(TaskEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case TASK_ID:
                selection = TaskEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = database.query(TaskEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);

        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        // TODO: in editor activity- set done time to 5 min after if not specified, autoset done to false

        SQLiteDatabase database = mTaskDbHelper.getWritableDatabase();
        long newId = database.insert(TaskEntry.TABLE_NAME, null, contentValues);
        Uri insertedUri = ContentUris.withAppendedId(TaskEntry.CONTENT_URI, newId);

        // Notify that this Uri has updated
        getContext().getContentResolver().notifyChange(insertedUri, null);

        return insertedUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mTaskDbHelper.getWritableDatabase();

        int uriCode = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (uriCode) {
            case TASKS:
                // Delete the whole thing
                rowsDeleted = database.delete(TaskEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case TASK_ID:
                // set the delete to only delete the number on the URI
                selection = TaskEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(TaskEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("cannot delete from this URI: " + uri);
        }

        if (rowsDeleted != 0) {
            // Notify of change of this particular URI
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {

        int rowsUpdated;
        int uriCode = sUriMatcher.match(uri);

        switch (uriCode) {
            case TASKS:
                return updateTask(uri, contentValues, selection, selectionArgs);

            case TASK_ID:
                selection = TaskEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateTask(uri, contentValues, selection, selectionArgs);

            default:
                throw new IllegalArgumentException("Uri not available");
        }
    }

    private int updateTask(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs){
        // TODO: sanity checks here

        SQLiteDatabase database = mTaskDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(TaskEntry.TABLE_NAME, contentValues, selection, selectionArgs);

        if (rowsUpdated != 0){
            // Notify of change of this particular URI- I still don't fully understand this
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}

