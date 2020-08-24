package com.ruyue.todolist.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.ruyue.todolist.models.Task;

import java.util.Date;

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
        return isFinished;
    }

    public ObservableField<Boolean> getIsAlert() {
        return isAlert;
    }

    public ObservableField<String> getDate() {
        return date;
    }

    public void insertToRoom() {

    }
}
