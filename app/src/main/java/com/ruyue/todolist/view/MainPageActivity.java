package com.ruyue.todolist.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.ruyue.todolist.R;
import com.ruyue.todolist.databinding.ActivityCreateTaskBinding;
import com.ruyue.todolist.databinding.ActivityMainPageBinding;
import com.ruyue.todolist.models.LocalDataSource;
import com.ruyue.todolist.models.Task;
import com.ruyue.todolist.viewmodels.CreateTaskViewModel;
import com.ruyue.todolist.viewmodels.MainPageViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainPageActivity extends AppCompatActivity {
    private LocalDataSource localDataSource;
    private MainPageViewModel mainPageViewModel;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainPageViewModel = ViewModelProviders.of(this).get(MainPageViewModel.class);
        ActivityMainPageBinding binding = DataBindingUtil.setContentView(MainPageActivity.this, R.layout.activity_main_page);
        binding.setLifecycleOwner(this);
        binding.setMainPageViewModel(mainPageViewModel);
        getSupportActionBar().setElevation(0);

        while(taskList == null) {
            taskList = mainPageViewModel.getTaskList();
        }
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyƒÍMM‘¬dd»’");
//        taskList.stream().sorted(Comparator.comparing(simpleDateFormat.parse(task::getDate)));
        ListView listViewTask = findViewById(R.id.task_list_view);
        taskAdapter = new TaskAdapter(MainPageActivity.this, taskList);
        listViewTask.setAdapter(taskAdapter);

        findViewById(R.id.jump_to_create).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainPageActivity.this, CreateTaskActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}