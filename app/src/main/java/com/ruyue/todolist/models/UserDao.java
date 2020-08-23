package com.ruyue.todolist.models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);
}
