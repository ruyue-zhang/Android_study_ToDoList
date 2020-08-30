package com.ruyue.todolist.viewmodels;

import android.app.Application;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ruyue.todolist.R;
import com.ruyue.todolist.Utils.AlarmUtil;
import com.ruyue.todolist.models.LocalDataSource;
import com.ruyue.todolist.models.Task;
import com.ruyue.todolist.view.MyNotification;

public class CreateTaskViewModel extends AndroidViewModel {
    private final Context mContext;
    public static Boolean isChange = false;
    private int changeId;
    private String insertDateFormat;
    private Task task;
    private AlarmUtil alarmUtil;

    public LocalDataSource localDataSource;

    private ObservableField<Integer> id = new ObservableField<>();
    private ObservableField<String> title = new ObservableField<>();
    private ObservableField<String> description = new ObservableField<>();
    private ObservableField<Boolean> isFinished = new ObservableField<>();
    private ObservableField<Boolean> isAlert = new ObservableField<>();
    private ObservableField<String> date = new ObservableField<>();
    private Boolean titleLegal = false;
    private Boolean dateLegal = false;

    public CreateTaskViewModel(@NonNull Application application) {
        super(application);
        this.mContext = application.getApplicationContext();
        localDataSource = LocalDataSource.getInstance(this.mContext);
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

    public Context getmContext() {
        return mContext;
    }

    public ObservableField<Integer> getId() {
        return id;
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

    public void setId(ObservableField<Integer> id) {
        this.id = id;
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

    public void inputNotEmpty(EditText inputTitle, FloatingActionButton createBtn) {
        TextWatcher watcherTitle = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (title.get().length() <= 0) {
                    titleLegal = false;
                    createBtn.setEnabled(false);
                } else {
                    titleLegal = true;
                    if(dateLegal) {
                        createBtn.setEnabled(true);
                    } else {
                        createBtn.setEnabled(false);
                    }
                }
            }
        };
        inputTitle.addTextChangedListener(watcherTitle);
    }

    public void dateNotEmpty(Button dateButton, FloatingActionButton createBtn) {
        TextWatcher watcherDate = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (date.get().equals("日期")) {
                    dateButton.setTextColor(mContext.getResources().getColor(R.color.btn_text_color));
                    dateLegal = false;
                    createBtn.setEnabled(false);
                } else {
                    dateButton.setTextColor(mContext.getResources().getColor(R.color.sys_blue));
                    dateLegal = true;
                    if(titleLegal) {
                        createBtn.setEnabled(true);
                    } else {
                        createBtn.setEnabled(false);
                    }
                }
            }
        };
        dateButton.addTextChangedListener(watcherDate);
    }

    public void deleteFromRoom(Task task) {
        new Thread(() -> localDataSource.taskDao().deleteTask(task)).start();
    }
}
