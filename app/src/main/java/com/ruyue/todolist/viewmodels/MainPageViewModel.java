package com.ruyue.todolist.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.ruyue.todolist.models.LocalDataSource;
import com.ruyue.todolist.models.Task;

import java.util.List;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                taskList = localDataSource.taskDao().getTaskList();
                //runOnUiThread(() -> Toast.makeText(MainPageActivity.this, taskList.get(taskList.size() -1).toString(), Toast.LENGTH_SHORT).show());
            }
        }).start();
        return taskList;
    }
}
