package com.ruyue.todolist.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Update
    void updateTask(Task task);

    @Query("SELECT * FROM task")
    List<Task> getTaskList();

    @Query("SELECT * FROM task WHERE id = :id")
    Task getTaskById(int id);

    @Query("SELECT * FROM task WHERE title = :title AND date = :date")
    Task getTaskByTitleAndDate(String title, String date);
}
