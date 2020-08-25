package com.ruyue.todolist.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
        getSupportActionBar().setElevation(0); //去掉ActionBar的分割线
        Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.btn_text_color), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

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