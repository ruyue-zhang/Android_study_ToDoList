package com.ruyue.todolist.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Task implements Comparable<Task> {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "is_finished")
    private Boolean isFinished;
    @ColumnInfo(name = "is_alert")
    private Boolean isAlert;
    @ColumnInfo(name = "date")
    private String date;

    public Task() {
    }

    @Ignore
    public Task(int id, String title, String description, Boolean isFinished, Boolean isAlert, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isFinished = isFinished;
        this.isAlert = isAlert;
        this.date = date;
    }

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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateString = null;
        Date date1 = null;
        Date date2 = null;
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
