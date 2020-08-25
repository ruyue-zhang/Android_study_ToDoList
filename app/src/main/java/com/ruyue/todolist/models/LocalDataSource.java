package com.ruyue.todolist.models;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Task.class}, version = 1)
public abstract class LocalDataSource extends RoomDatabase {
    private static final String DATABASE_NAME = "myDateBase";
    private static LocalDataSource LocalDataSource;

    public static synchronized LocalDataSource getInstance(Context context)
    {
        if(LocalDataSource == null)
        {
            LocalDataSource = Room
                    .databaseBuilder(context.getApplicationContext(), LocalDataSource.class, DATABASE_NAME)
                    .build();
        }
        return LocalDataSource;
    }

    public abstract UserDao userDao();
    public abstract TaskDao taskDao();

}
