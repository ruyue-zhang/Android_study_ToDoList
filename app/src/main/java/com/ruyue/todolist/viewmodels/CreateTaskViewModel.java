package com.ruyue.todolist.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ruyue.todolist.Utils.AlarmUtil;
import com.ruyue.todolist.models.LocalDataSource;
import com.ruyue.todolist.models.Task;

import java.util.List;

public class CreateTaskViewModel extends AndroidViewModel {
    public static Boolean isChange = false;
    private int changeId;
    private String insertDateFormat;
    private Task task;
    private AlarmUtil alarmUtil;
    public LocalDataSource localDataSource;

    private ObservableField<String> title = new ObservableField<>();
    private ObservableField<String> description = new ObservableField<>();
    private ObservableField<Boolean> isFinished = new ObservableField<>();
    private ObservableField<Boolean> isAlert = new ObservableField<>();
    private ObservableField<String> date = new ObservableField<>();

    public CreateTaskViewModel(@NonNull Application application) {
        super(application);
        localDataSource = LocalDataSource.getInstance(this.getApplication());
        alarmUtil = new AlarmUtil();
        date.set("日期");
    }

    public void initInterface(Task changeTask) {
        changeId = changeTask.getId();
        title.set(changeTask.getTitle());
        description.set(changeTask.getDescription());
        isFinished.set(changeTask.getFinished());
        isAlert.set(changeTask.getAlert());
        insertDateFormat = changeTask.getDate();
        String[] split = changeTask.getDate().split("-");
        String dateString = split[0] + "年" + split[1] + "月" + split[2] + "日";
        date.set(dateString);
    }

    public ObservableField<String> getTitle() {
        return title;
    }

    public ObservableField<String> getDescription() {
        return description;
    }

    public ObservableField<Boolean> getIsFinished() {
        if(isFinished.get() == null) {
            isFinished.set(false);
        }
            return isFinished;
    }

    public ObservableField<Boolean> getIsAlert() {
        if(isAlert.get() == null) {
            isAlert.set(false);
        }
        return isAlert;
    }

    public ObservableField<String> getDate() {
        return date;
    }

    public void setTitle(ObservableField<String> title) {
        this.title = title;
    }

    public void setDescription(ObservableField<String> description) {
        this.description = description;
    }

    public void setIsFinished(ObservableField<Boolean> isFinished) {
        this.isFinished = isFinished;
    }

    public void setIsAlert(ObservableField<Boolean> isAlert) {
        this.isAlert = isAlert;
    }

    public void setDate(ObservableField<String> date) {
        this.date = date;
    }

    public void insertToRoom(String dateInsert) {
        if(isChange) {
            String date = dateInsert == null ? insertDateFormat : dateInsert;
            task = new Task(changeId, title.get(), description.get(), isFinished.get(), isAlert.get(), date);
            new Thread(() -> {
                localDataSource.taskDao().updateTask(task);
                if(!task.getFinished() && task.getAlert()) {
                    alarmUtil.addNotification(task.getId(), task.getTitle(), task.getDate());
                } else {
                    alarmUtil.cancelNotificationById(task.getId());
                }
            }).start();
        } else {
            task = new Task(title.get(), description.get(), isFinished.get(), isAlert.get(), dateInsert);
            String title = task.getTitle();
            String date = task.getDate();
            new Thread(() -> {
                localDataSource.taskDao().insertTask(task);
                task = localDataSource.taskDao().getTaskByTitleAndDate(title, date);
                if(!task.getFinished() && task.getAlert()) {
                    alarmUtil.addNotification(task.getId(), task.getTitle(), task.getDate());
                }
            }).start();
        }
    }

    public void getDateFromCalendar(String date) {
        this.date.set(date);
    }

    public void deleteFromRoom(Task task) {
        new Thread(() -> localDataSource.taskDao().deleteTask(task)).start();
    }
}
