package com.ruyue.todolist.models;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class LocalDataSource extends RoomDatabase {
    public abstract UserDao userDao();
}
