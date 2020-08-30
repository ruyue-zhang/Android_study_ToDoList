package com.ruyue.todolist.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.ruyue.todolist.R;
import com.ruyue.todolist.Utils.AlarmUtil;
import com.ruyue.todolist.databinding.ActivityCreateTaskBinding;
import com.ruyue.todolist.models.Task;
import com.ruyue.todolist.viewmodels.CreateTaskViewModel;

import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity {
    private CreateTaskViewModel createTaskViewModel;
    private String dateInsert;
    private Task changeTask;
    private AlarmUtil alarmUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createTaskViewModel = ViewModelProviders.of(this).get(CreateTaskViewModel.class);
        ActivityCreateTaskBinding binding = DataBindingUtil.setContentView(CreateTaskActivity.this, R.layout.activity_create_task);

        alarmUtil = new AlarmUtil();
        binding.setLifecycleOwner(this);
        binding.setCreateTaskViewModel(createTaskViewModel);
        getSupportActionBar().setElevation(0);
        Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.btn_text_color), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        EditText editTitle = findViewById(R.id.title);
        Button dateButton = findViewById(R.id.date);
        FloatingActionButton createSuccessBtn = findViewById(R.id.create_success);
        FloatingActionButton deleteTaskBtn = findViewById(R.id.delete_task);

        createTaskViewModel.inputNotEmpty(editTitle, createSuccessBtn);
        createTaskViewModel.dateNotEmpty(dateButton, createSuccessBtn);

        createSuccessBtn.setOnClickListener(v -> {
            createTaskViewModel.insertToRoom(dateInsert);

            jumpToMainPageWithData();
        });

        deleteTaskBtn.setOnClickListener(v -> {
            if(!changeTask.getFinished() && changeTask.getAlert()) {
                alarmUtil.cancelNotificationById(changeTask.getId());
            }
            createTaskViewModel.deleteFromRoom(changeTask);
            jumpToMainPageWithData();
        });

        findViewById(R.id.date).setOnClickListener(v -> openCalendarDialog());


        Intent intent = getIntent();
        if(intent.getBooleanExtra("flag", true)) {
            String changeTaskString = intent.getStringExtra("changeTask");
            changeTask = new Gson().fromJson(changeTaskString, Task.class);
            createTaskViewModel.initInterface(changeTask);
            CreateTaskViewModel.isChange = true;
            deleteTaskBtn.setEnabled(true);
            deleteTaskBtn.setVisibility(View.VISIBLE);
        } else {
            deleteTaskBtn.setEnabled(false);
            deleteTaskBtn.setVisibility(View.INVISIBLE);
            CreateTaskViewModel.isChange = false;
        }
    }



    public void jumpToMainPageWithData() {
        Intent intent = new Intent(CreateTaskActivity.this, MainPageActivity.class);
        startActivity(intent);
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
            dateInsert = year + "-" + (month + 1) + "-" + day;
            String date = year + "Äê" + (month +1) + "ÔÂ" + day + "ÈÕ";
            createTaskViewModel.getDateFromCalendar(date);
            dialog.dismiss();
        });
        dialog.setView(customView);
        dialog.show();
    }
}