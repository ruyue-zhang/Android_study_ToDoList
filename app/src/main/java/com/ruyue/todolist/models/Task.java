package com.ruyue.todolist.models;

import java.util.Date;

public class Task {
    private int id;
    private String title;
    private String description;
    private Boolean isFinished;
    private Boolean isAlert;
    private Date date;

    public Task() {
    }

    public Task(int id, String title, String description, Boolean isFinished, Boolean isAlert, Date date) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
}
