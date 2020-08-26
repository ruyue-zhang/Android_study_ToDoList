package com.ruyue.todolist.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.ruyue.todolist.models.LocalDataSource;
import com.ruyue.todolist.models.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MainPageViewModel extends AndroidViewModel {
    private ObservableField<String> curDate = new ObservableField<>();
    private ObservableField<String> curMonth = new ObservableField<>();
    private ObservableField<String> taskCount = new ObservableField<>();
    private LocalDataSource localDataSource;
    private List<Task> taskList;
    Calendar calendar = Calendar.getInstance();

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
                int size = taskList.size();
            }
        }).start();
        if(taskList != null) {
            taskCount.set(taskList.size()+"个任务");

            long time = System.currentTimeMillis();
            Date date=new Date(time);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEEE");
            format = new SimpleDateFormat("MMMM",Locale.US);
            curMonth.set(format.format(date));
            format = new SimpleDateFormat("EEEE",Locale.US);
            String week = format.format(date);
            format = new SimpleDateFormat("dd");
            String day = format.format(date);
            curDate.set(week + ", "+ day +"th");

            //先转化为date时间，进行排序
            //将date的格式变化为X月XX日的字符串
            //sortByDate();
        }
        return taskList;
    }

    private void sortByDate() {
        taskList.stream()
                .sorted(Comparator.comparing(Task::getDate));
    }

}
