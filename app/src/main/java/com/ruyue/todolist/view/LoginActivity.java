package com.ruyue.todolist.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.text.Editable;

import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruyue.todolist.models.User;
import com.ruyue.todolist.R;
import com.ruyue.todolist.databinding.ActivityLoginBinding;
import com.ruyue.todolist.models.UserDao;
import com.ruyue.todolist.viewmodels.LoginViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        com.ruyue.todolist.databinding.ActivityLoginBinding binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);

        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginViewModel);

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