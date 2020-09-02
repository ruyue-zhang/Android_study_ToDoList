package com.ruyue.todolist.models;

import android.annotation.SuppressLint;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Task implements Comparable<Task> {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private Boolean isFinished;
    private Boolean isAlert;
    private String date;

    @Ignore
    public Task() {
    }

    public Task(int id, String title, String description, Boolean isFinished, Boolean isAlert, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isFinished = isFinished;
        this.isAlert = isAlert;
        this.date = date;
    }

    @Ignore
    public Task(String title, String description, Boolean isFinished, Boolean isAlert, String date) {
        this.title = title;
        this.description = description;
        this.isFinished = isFinished;
        this.isAlert = isAlert;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public Boolean getAlert() {
        return isAlert;
    }

    public void setAlert(Boolean alert) {
        isAlert = alert;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @NotNull
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isFinished=" + isFinished +
                ", isAlert=" + isAlert +
                ", date=" + date +
                '}';
    }

    @Override
    public int compareTo(Task task) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1;
        Date date2;
        int compareTo = 0;
        if(this.getFinished() ^ task.getFinished()) {
            return task.getFinished() ? -1 : 1;
        } else {
            try {
                date1 = simpleDateFormat.parse(this.getDate());
                date2 = simpleDateFormat.parse(task.getDate());
                compareTo = date1.compareTo(date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return compareTo;
        }
    }
}
