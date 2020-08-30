package com.ruyue.todolist.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ruyue.todolist.R;
import com.ruyue.todolist.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
//    private UserDao userDao;
    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        com.ruyue.todolist.databinding.ActivityLoginBinding binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);

        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginViewModel);
//        MyApplication myApplication = (MyApplication)getApplication();
//        userDao = myApplication.getLocalDataSource().userDao();


        sprfMain = PreferenceManager.getDefaultSharedPreferences(this);
        editorMain = sprfMain.edit();
        if(sprfMain.getBoolean("main",false)) {
            Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        }

        EditText editTextName = findViewById(R.id.name);
        EditText editTextPassword = findViewById(R.id.password);
        Button loginBtn = findViewById(R.id.login);

        loginViewModel.judgeNameIsLegal(editTextName, loginBtn);
        loginViewModel.judgePasswordIsLegal(editTextPassword, loginBtn);
        loginViewModel.getUserInfo();

        loginBtn.setOnClickListener(v -> {
            switch (loginViewModel.login()) {
                case 0:
                    Intent intent  = new Intent(LoginActivity.this, MainPageActivity.class);
                    editorMain.putBoolean("main",true).apply();
                    startActivity(intent);
                    LoginActivity.this.finish();
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(), "用户不存在", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }
}