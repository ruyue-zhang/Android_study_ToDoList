package com.ruyue.todolist.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ruyue.todolist.R;
import com.ruyue.todolist.databinding.ActivityCreateTaskBinding;
import com.ruyue.todolist.databinding.ActivityMainPageBinding;
import com.ruyue.todolist.models.LocalDataSource;
import com.ruyue.todolist.models.Task;
import com.ruyue.todolist.viewmodels.CreateTaskViewModel;
import com.ruyue.todolist.viewmodels.MainPageViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainPageActivity extends AppCompatActivity {
    MainPageViewModel mainPageViewModel;
 //   LocalDataSource localDataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainPageViewModel = ViewModelProviders.of(this).get(MainPageViewModel.class);
        ActivityMainPageBinding binding = DataBindingUtil.setContentView(MainPageActivity.this, R.layout.activity_main_page);

        binding.setLifecycleOwner(this);
        binding.setMainPageViewModel(mainPageViewModel);
        getSupportActionBar().setElevation(0);
//        localDataSource = LocalDataSource.getInstance(this);

//        taskList = new ArrayList<>();
//
//        findViewById(R.id.jump_to_create).setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Intent intent = new Intent(MainPageActivity.this, CreateTaskActivity.class);
//                startActivity(intent);
//            }
//        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                taskList = localDataSource.taskDao().getTaskList();
//                runOnUiThread(() -> Toast.makeText(MainPageActivity.this, taskList.get(taskList.size() -1).toString(), Toast.LENGTH_SHORT).show());
//            }
//        }).start();
//    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}