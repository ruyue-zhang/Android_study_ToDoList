package com.ruyue.todolist.view;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

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
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.ruyue.todolist.R;
import com.ruyue.todolist.Utils.ConstUtils;
import com.ruyue.todolist.databinding.ActivityMainPageBinding;
import com.ruyue.todolist.models.Task;
import com.ruyue.todolist.viewmodels.MainPageViewModel;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class MainPageActivity extends AppCompatActivity {
    private List<Task> taskList = null;
    private MainPageViewModel mainPageViewModel;

    @BindView(R.id.task_list_view)
    ListView listViewTask;
    @BindView(R.id.jump_to_create)
    FloatingActionButton addBtn;

    @OnClick(R.id.jump_to_create)
    public void onClickAddBtn() {
        jumpToCreateTask();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainPageViewModel = ViewModelProviders.of(this).get(MainPageViewModel.class);
        ActivityMainPageBinding binding = DataBindingUtil.setContentView(MainPageActivity.this, R.layout.activity_main_page);
        binding.setLifecycleOwner(this);
        binding.setMainPageViewModel(mainPageViewModel);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        ButterKnife.bind(this);

        while(taskList == null) {
            taskList = mainPageViewModel.getTaskList();
        }
        addInListView();
    }

    private void jumpToLogin() {
        Intent intent = new Intent(MainPageActivity.this, LoginActivity.class);
        startActivity(intent);
        MainPageActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void jumpToCreateTask() {
        Intent intent = new Intent(MainPageActivity.this, CreateTaskActivity.class);
        intent.putExtra("flag", false);
        startActivityForResult(intent, ConstUtils.ADD_REQUEST_CODE);
    }

    private void jumpToChangeTask(String key, String value) {
        Intent intent = new Intent(MainPageActivity.this, CreateTaskActivity.class);
        intent.putExtra(key, value);
        intent.putExtra("flag", true);
        startActivityForResult(intent, ConstUtils.CHANGE_REQUEST_CODE);
    }

    private void addInListView() {
        mainPageViewModel.displayHeadInfo(taskList);

        TaskAdapter taskAdapter = new TaskAdapter(MainPageActivity.this, taskList);
        listViewTask.setAdapter(taskAdapter);
        listViewTask.setOnItemClickListener((parent, view, position, id) -> {
            Adapter adapter = parent.getAdapter();
            Task task = (Task) adapter.getItem(position);
            jumpToChangeTask("changeTask", new Gson().toJson(task));
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == ConstUtils.ADD_REQUEST_CODE && resultCode == ConstUtils.ADD_RESULT_CODE) {
            //刷新界面
        } else if(requestCode == ConstUtils.CHANGE_REQUEST_CODE && resultCode == ConstUtils.CHANGE_RESULT_CODE) {

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SubMenu addMenu = menu.addSubMenu(0, 1, 2, "overflow");
        addMenu.add(0, 2, 0, "退出");
        MenuItem overFlowItem = addMenu.getItem();
        overFlowItem.setIcon(R.drawable.ic_baseline_more_vert_24);
        overFlowItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 2) {
            resetShowPreMain();
            jumpToLogin();
        }
        return super.onOptionsItemSelected(item);
    }

    public void resetShowPreMain(){
        SharedPreferences sharedPreferencesMain = this.getSharedPreferences(ConstUtils.IS_LOGIN, Context.MODE_PRIVATE);
        sharedPreferencesMain.edit().putBoolean(ConstUtils.IS_LOGIN, false).apply();
    }
}