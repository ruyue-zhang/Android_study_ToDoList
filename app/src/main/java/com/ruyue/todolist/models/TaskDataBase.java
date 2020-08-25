package com.ruyue.todolist.models;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, exportSchema = false, version = 1)
public abstract class TaskDataBase extends RoomDatabase {
    private static final String DATABASE_NAME = "task";

    private static TaskDataBase databaseInstance;

    public static synchronized TaskDataBase getInstance(Context context)
    {
        if(databaseInstance == null)
        {
            databaseInstance = Room
                    .databaseBuilder(context.getApplicationContext(), TaskDataBase.class, DATABASE_NAME)
                    .build();
        }
        return databaseInstance;
    }

    public abstract TaskDao taskDao();
}
