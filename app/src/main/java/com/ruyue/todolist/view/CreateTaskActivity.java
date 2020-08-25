package com.ruyue.todolist.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ruyue.todolist.R;
import com.ruyue.todolist.databinding.ActivityCreateTaskBinding;
import com.ruyue.todolist.models.LocalDataSource;
import com.ruyue.todolist.models.Task;
import com.ruyue.todolist.viewmodels.CreateTaskViewModel;
import com.ruyue.todolist.viewmodels.LoginViewModel;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class CreateTaskActivity extends AppCompatActivity {
    CreateTaskViewModel createTaskViewModel;
    Calendar calendar;
    CalendarView calendarView;
    Button createSuccessBtn;
    EditText editTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createTaskViewModel = ViewModelProviders.of(this).get(CreateTaskViewModel.class);
        ActivityCreateTaskBinding binding = DataBindingUtil.setContentView(CreateTaskActivity.this, R.layout.activity_create_task);

        binding.setLifecycleOwner(this);
        binding.setCreateTaskViewModel(createTaskViewModel);
        getSupportActionBar().setElevation(0);
        Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.btn_text_color), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        editTitle = findViewById(R.id.title);
        Button dateButton = findViewById(R.id.date);
        createSuccessBtn = findViewById(R.id.create_success);
        Button dateBtn = findViewById(R.id.date);

        createTaskViewModel.inputNotEmpty(editTitle, createSuccessBtn);
        createTaskViewModel.dateNotEmpty(dateButton, createSuccessBtn, dateBtn);

        createSuccessBtn.setOnClickListener(v -> {
            createTaskViewModel.insertToRoom();
            jumpToMainPageWithData();
        });

        findViewById(R.id.date).setOnClickListener(v -> openCalendarDialog());
    }



    public void jumpToMainPageWithData() {
        Intent intent = new Intent(CreateTaskActivity.this, MainPageActivity.class);
        startActivity(intent);
    }

    private void openCalendarDialog() {
        View customView = this.getLayoutInflater().inflate(R.layout.calendar, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(CreateTaskActivity.this);
        AlertDialog dialog = builder.create();
        calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 9);
        calendar.set(Calendar.YEAR, 2020);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.YEAR, 1);
        calendarView = customView.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener((calendarView, year, month, day) -> {
            String date = year + "Äê" + (month +1) + "ÔÂ" + day + "ÈÕ";
            createTaskViewModel.getDateFromCalendar(date);
            dialog.dismiss();
        });
        dialog.setView(customView);
        dialog.show();
    }
}