package com.ruyue.todolist.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.ruyue.todolist.models.Task;

public class CreateTaskViewModel extends AndroidViewModel {
    private final Context mContext;
    private ObservableField<Integer> id = new ObservableField<>();
    private ObservableField<String> title = new ObservableField<>();
    private ObservableField<String> description = new ObservableField<>();
    private ObservableField<Boolean> isFinished = new ObservableField<>();
    private ObservableField<Boolean> isAlert = new ObservableField<>();
    private ObservableField<String> date = new ObservableField<>();

    public CreateTaskViewModel(@NonNull Application application) {
        super(application);
        this.mContext = application.getApplicationContext();
        date.set("ÈÕÆÚ");
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

    public void insertToRoom() {
        Task task = new Task(1,title.get(),description.get(),isFinished.get(),isAlert.get(),date.get());
    }

    public void getDateFromCalendar(String date) {
        this.date.set(date);
    }
}
