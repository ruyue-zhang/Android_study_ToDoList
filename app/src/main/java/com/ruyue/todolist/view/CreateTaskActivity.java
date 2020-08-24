package com.ruyue.todolist.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.ruyue.todolist.R;
import com.ruyue.todolist.models.Task;
import com.ruyue.todolist.viewmodels.CreateTaskViewModel;
import com.ruyue.todolist.viewmodels.LoginViewModel;

import java.util.GregorianCalendar;

public class CreateTaskActivity extends AppCompatActivity {
    private CreateTaskViewModel createTaskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createTaskViewModel = ViewModelProviders.of(this).get(CreateTaskViewModel.class);
        com.ruyue.todolist.databinding.ActivityCreateTaskBinding binding = DataBindingUtil.setContentView(CreateTaskActivity.this, R.layout.activity_create_task);

        binding.setLifecycleOwner(this);
        binding.setCreateTaskViewModel(createTaskViewModel);

        //如果创建成功（create_success按钮被点击），jumpToMainPageWithData函数
        findViewById(R.id.create_success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTaskViewModel.insertToRoom();
                jumpToMainPageWithData();
            }
        });
    }

    public void jumpToMainPageWithData() {
        Intent intent = new Intent(CreateTaskActivity.this, MainPageActivity.class);
        startActivity(intent);
    }
}