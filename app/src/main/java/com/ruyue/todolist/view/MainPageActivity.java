package com.ruyue.todolist.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ruyue.todolist.R;
import com.ruyue.todolist.models.LocalDataSource;
import com.ruyue.todolist.models.Task;

import java.util.ArrayList;
import java.util.List;

public class MainPageActivity extends AppCompatActivity {
    LocalDataSource localDataSource;
    List<Task> taskList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        localDataSource = LocalDataSource.getInstance(this);

        taskList = new ArrayList<>();

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                taskList = localDataSource.taskDao().getTaskList();
                runOnUiThread(() -> Toast.makeText(MainPageActivity.this, taskList.get(taskList.size() -1).toString(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}