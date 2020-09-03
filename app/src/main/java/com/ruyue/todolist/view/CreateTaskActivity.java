package com.ruyue.todolist.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.ruyue.todolist.R;
import com.ruyue.todolist.Utils.AlarmUtil;
import com.ruyue.todolist.Utils.ConstUtils;
import com.ruyue.todolist.databinding.ActivityCreateTaskBinding;
import com.ruyue.todolist.models.Task;
import com.ruyue.todolist.viewmodels.CreateTaskViewModel;

import java.util.Calendar;
import java.util.Objects;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class CreateTaskActivity extends AppCompatActivity {
    private CreateTaskViewModel createTaskViewModel;
    private String dateInsert;
    private Task changeTask;
    private AlarmUtil alarmUtil;
    private Boolean isTitleNotEmpty = false;
    private Boolean isDateNotEmpty = false;

    @BindView(R.id.title)
    EditText editTitle;
    @BindView(R.id.date)
    Button dateButton;
    @BindView(R.id.create_success)
    FloatingActionButton createSuccessBtn;
    @BindView(R.id.delete_task)
    FloatingActionButton deleteTaskBtn;

    @OnClick(R.id.date)
    public void onDateClick() {
        openCalendarDialog();
    }

    @OnClick(R.id.create_success)
    public void onSuccessClick() {
        createTaskViewModel.insertToRoom(dateInsert);
        jumpToMainPageWithData();
    }

    @OnClick(R.id.delete_task)
    public void onDeleteClick() {
        if(!changeTask.getFinished() && changeTask.getAlert()) {
            alarmUtil.cancelNotificationById(changeTask.getId());
        }
        createTaskViewModel.deleteFromRoom(changeTask);
        jumpToMainPageWithData();
    }

    @OnTextChanged(R.id.title)
    public void onTitleChange() {
        isTitleNotEmpty = editTitle.getText().toString().length() > 0;
        changeSaveBtnStatus();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createTaskViewModel = ViewModelProviders.of(this).get(CreateTaskViewModel.class);
        ActivityCreateTaskBinding binding = DataBindingUtil.setContentView(CreateTaskActivity.this, R.layout.activity_create_task);
        binding.setLifecycleOwner(this);
        binding.setCreateTaskViewModel(createTaskViewModel);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        ButterKnife.bind(this);

        alarmUtil = new AlarmUtil();
        changeOrCreateManage();
    }

    public void jumpToMainPageWithData() {
        Intent intent = new Intent(CreateTaskActivity.this, MainPageActivity.class);
        setResult(ConstUtils.CHANGE_RESULT_CODE, intent);
        finish();
    }

    private void openCalendarDialog() {
        View customView = this.getLayoutInflater().inflate(R.layout.calendar, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(CreateTaskActivity.this);
        AlertDialog dialog = builder.create();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 9);
        calendar.set(Calendar.YEAR, 2020);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.YEAR, 1);
        CalendarView calendarView1 = customView.findViewById(R.id.calendarView);
        calendarView1.setOnDateChangeListener((calendarView, year, month, day) -> {
            isDateNotEmpty = true;
            changeSaveBtnStatus();
            dateInsert = year + "-" + (month + 1) + "-" + day;
            String date = year + "Äê" + (month +1) + "ÔÂ" + day + "ÈÕ";
            createTaskViewModel.getDateFromCalendar(date);
            dialog.dismiss();
        });
        dialog.setView(customView);
        dialog.show();
    }

    private void changeSaveBtnStatus() {
        if(isTitleNotEmpty && isDateNotEmpty) {
            createSuccessBtn.setEnabled(true);
        } else {
            createSuccessBtn.setEnabled(false);
        }
    }

    private void changeOrCreateManage() {
        Intent intent = getIntent();
        if(intent.getBooleanExtra(ConstUtils.ADD_OR_CHANGE, true)) {
            String changeTaskString = intent.getStringExtra(ConstUtils.CHANGE_TASK_KEY);
            changeTask = new Gson().fromJson(changeTaskString, Task.class);
            createTaskViewModel.initInterface(changeTask);
            CreateTaskViewModel.isChange = true;
            isDateNotEmpty = true;
            deleteTaskBtn.setEnabled(true);
            createSuccessBtn.setEnabled(true);
            deleteTaskBtn.setVisibility(View.VISIBLE);
        } else {
            CreateTaskViewModel.isChange = false;
            deleteTaskBtn.setEnabled(false);
            deleteTaskBtn.setVisibility(View.INVISIBLE);
        }
    }
}