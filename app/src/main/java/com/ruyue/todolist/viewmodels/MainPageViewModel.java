package com.ruyue.todolist.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.ruyue.todolist.models.LocalDataSource;
import com.ruyue.todolist.models.Task;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainPageViewModel extends AndroidViewModel {
    private ObservableField<String> curDate = new ObservableField<>();
    private ObservableField<String> curMonth = new ObservableField<>();
    private ObservableField<String> taskCount = new ObservableField<>();
    private LocalDataSource localDataSource;
    private List<Task> taskList;

    public MainPageViewModel(@NonNull Application application) {
        super(application);
        localDataSource = LocalDataSource.getInstance(application);
    }

    public ObservableField<String> getCurDate() {
        return curDate;
    }

    public ObservableField<String> getCurMonth() {
        return curMonth;
    }

    public ObservableField<String> getTaskCount() {
        return taskCount;
    }

    public void setCurDate(ObservableField<String> curDate) {
        this.curDate = curDate;
    }

    public void setCurMonth(ObservableField<String> curMonth) {
        this.curMonth = curMonth;
    }

    public void setTaskCount(ObservableField<String> taskCount) {
        this.taskCount = taskCount;
    }

    public List<Task> getTaskList() {
        new Thread(() -> {
            taskList = localDataSource.taskDao().getTaskList();
            Collections.sort(taskList);
        }).start();
        return taskList;
    }

    public void displayHeadInfo(List<Task> taskList) {
        taskCount.set(taskList.size() + "¸öÈÎÎñ");

        long time = System.currentTimeMillis();
        Date date = new Date(time);
        curMonth.set(new SimpleDateFormat("MMMM", Locale.US).format(date));
        String week = new SimpleDateFormat("EEEE", Locale.US).format(date);
        String day = new SimpleDateFormat("dd", Locale.US).format(date);
        switch (day) {
            case "01":
                curDate.set(week + ", First");
                break;
            case "02":
                curDate.set(week + ", Second");
                break;
            case "03":
                curDate.set(week + ", Third");
                break;
            default:
                curDate.set(week + ", " + day + "th");
        }
    }
}
