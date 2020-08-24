package com.ruyue.todolist.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    private int id;
    private String name;
    private String password;
    private Boolean LoginStatus;

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User(int id, String name, String password, Boolean loginStatus) {
        this.id = id;
        this.name = name;
        this.password = password;
        LoginStatus = loginStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getLoginStatus() {
        return LoginStatus;
    }

    public void setLoginStatus(Boolean loginStatus) {
        LoginStatus = loginStatus;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", LoginStatus=" + LoginStatus +
                '}';
    }
}
