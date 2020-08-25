package com.ruyue.todolist.models;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public interface TaskDao {
    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Update
    void updateTask(Task task);

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getTaskList();//希望监听学生表的变化，为其加上LiveData

    @Query("SELECT * FROM task WHERE id = :id")
    Task getTaskById(int id);
}
