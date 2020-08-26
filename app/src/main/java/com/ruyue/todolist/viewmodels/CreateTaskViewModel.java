package com.ruyue.todolist.viewmodels;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ruyue.todolist.R;
import com.ruyue.todolist.models.LocalDataSource;
import com.ruyue.todolist.models.Task;

import java.util.List;

public class CreateTaskViewModel extends AndroidViewModel {
    private final Context mContext;

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
        date.set("日期");
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

    public void insertToRoom(String dateInsert) {
        Task task = new Task(title.get(),description.get(),isFinished.get(),isAlert.get(),dateInsert);
        new Thread(() -> localDataSource.taskDao().insertTask(task)).start();
    }

    public void getDateFromCalendar(String date) {
        this.date.set(date);
    }

    public void inputNotEmpty(EditText inputTitle, Button createBtn) {
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

    public void dateNotEmpty(Button dateButton, Button createBtn, Button dateBtn) {
        TextWatcher watcherDate = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (date.get().equals("日期")) {
                    dateBtn.setTextColor(mContext.getResources().getColor(R.color.btn_text_color));
                    dateLegal = false;
                    createBtn.setEnabled(false);
                } else {
                    dateBtn.setTextColor(mContext.getResources().getColor(R.color.sys_blue));
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
//
//    public LiveData<List<Task>> getLiveDataTask() {
//        return liveDataTask;
//    }
}
