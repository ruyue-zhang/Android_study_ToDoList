package com.ruyue.todolist.view;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruyue.todolist.R;
import com.ruyue.todolist.databinding.ActivityMainPageBinding;
import com.ruyue.todolist.models.LocalDataSource;
import com.ruyue.todolist.models.Task;
import com.ruyue.todolist.viewmodels.MainPageViewModel;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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

        while (taskList == null) {
            taskList = mainPageViewModel.getTaskList();
        }
        Collections.sort(taskList);
        ListView listViewTask = findViewById(R.id.task_list_view);
        taskAdapter = new TaskAdapter(MainPageActivity.this, taskList);
        listViewTask.setAdapter(taskAdapter);

        listViewTask.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Adapter adapter = parent.getAdapter();
                Task task = (Task) adapter.getItem(position);
                Intent intent = new Intent(MainPageActivity.this, CreateTaskActivity.class);
                intent.putExtra("changeTask", new Gson().toJson(task));
                intent.putExtra("flag",true);
                startActivity(intent);
            }
        });

        findViewById(R.id.jump_to_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, CreateTaskActivity.class);
                intent.putExtra("flag",false);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}