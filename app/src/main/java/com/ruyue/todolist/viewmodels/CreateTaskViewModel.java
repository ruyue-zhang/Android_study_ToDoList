package com.ruyue.todolist.viewmodels;

import android.app.Application;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

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
    private Boolean titleLegal = false;
    private Boolean dateLegal = false;

    public CreateTaskViewModel(@NonNull Application application) {
        super(application);
        this.mContext = application.getApplicationContext();
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

    public void insertToRoom() {
        Task task = new Task(1,title.get(),description.get(),isFinished.get(),isAlert.get(),date.get());
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

    public void dateNotEmpty(Button dateButton, Button createBtn) {
        TextWatcher watcherdate = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (date.get().equals("日期")) {
                    dateLegal = false;
                    createBtn.setEnabled(false);
                } else {
                    dateLegal = true;
                    if(titleLegal) {
                        createBtn.setEnabled(true);
                    } else {
                        createBtn.setEnabled(false);
                    }
                }
            }
        };
        dateButton.addTextChangedListener(watcherdate);
    }
}
