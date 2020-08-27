package com.ruyue.todolist.view;


import androidx.appcompat.app.AppCompatActivity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.ruyue.todolist.R;
import com.ruyue.todolist.databinding.ActivityMainPageBinding;
import com.ruyue.todolist.models.LocalDataSource;
import com.ruyue.todolist.models.Task;
import com.ruyue.todolist.viewmodels.MainPageViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MainPageActivity extends AppCompatActivity {
    private LocalDataSource localDataSource;
    private MainPageViewModel mainPageViewModel;
    private TaskAdapter taskAdapter;
    private MyNotification myNotification;
    private List<Task> taskList;
    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myNotification = new MyNotification(this);
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
        updateNotification();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SubMenu addMenu = menu.addSubMenu(0, 1, 2, "overflow");
        addMenu.add(0, 2, 0, "ÍË³ö");
        MenuItem overFlowItem = addMenu.getItem();
        overFlowItem.setIcon(R.drawable.ic_baseline_more_vert_24);
        overFlowItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 2) {
            resetSprfMain();
            Intent intent = new Intent(MainPageActivity.this, LoginActivity.class);
            startActivity(intent);
            MainPageActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void resetSprfMain(){
        sprfMain = PreferenceManager.getDefaultSharedPreferences(this);
        editorMain = sprfMain.edit();
        editorMain.putBoolean("main",false);
        editorMain.apply();
    }


    private void updateNotification() {
        myNotification.cancelAllNotification();
        List<Task> hitTaskList = taskList.stream().filter(task -> !task.getFinished() && task.getAlert()).collect(Collectors.toList());
        for (Task task : hitTaskList) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String hitTimeStr = task.getDate() + " 19:22:00";
            Date hitTime = null;
            try {
                hitTime = format.parse(hitTimeStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(hitTime.getTime() > System.currentTimeMillis()) {
                Intent intent = new Intent(this, AlarmReceiver.class);
                intent.setAction("NOTIFICATION");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, hitTime.getTime(), pendingIntent);
            }
        }
    }
}