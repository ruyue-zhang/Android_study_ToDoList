package com.ruyue.todolist.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;

import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruyue.todolist.models.User;
import com.ruyue.todolist.R;
import com.ruyue.todolist.databinding.ActivityLoginBinding;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String URL = "https://twc-android-bootcamp.github.io/fake-data/data/user.json";
    private Button loginBtn;
    private Boolean inputNameLegal = false;
    private Boolean inputPasswordLegal = false;
    private EditText editTextName;
    private User ServiceUser;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        editTextName = findViewById(R.id.name);
        editTextPassword = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login);

        loginBtn.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String password = editTextPassword.getText().toString();
            String md5Password = null;
            try {
                md5Password = getMD5(password);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            getUserInfo(name, md5Password);
        });

        judgeNameIsLegal();
        judgePasswordIsLegal();
    }

    private void judgeNameIsLegal() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (editTextName.getText().length() > 0) {
                    if(!editTextName.getText().toString().matches("^[a-zA-Z0-9]{3,12}$")) {
                        editTextName.setError("用户名必须是3 ~ 12为字母或数字");
                        loginBtn.setEnabled(false);
                        inputNameLegal = false;
                        loginBtn.setBackground(getResources().getDrawable(R.drawable.login_btn_layout, null));
                    } else {
                        inputNameLegal = true;
                        if(inputPasswordLegal) {
                            loginBtn.setEnabled(true);
                            loginBtn.setBackground(getResources().getDrawable(R.drawable.login_btn_enable, null));
                        }
                    }
                }
            }
        };
        editTextName.addTextChangedListener(watcher);
    }

    private void judgePasswordIsLegal() {
        TextWatcher watcherPassword = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (editTextPassword.getText().length() > 0) {
                    if (!editTextPassword.getText().toString().matches("^.{6,18}$")) {
                        editTextPassword.setError("密码长度必须是6 ~ 18位字符");
                        loginBtn.setEnabled(false);
                        inputPasswordLegal = false;
                        loginBtn.setBackground(getResources().getDrawable(R.drawable.login_btn_layout, null));
                    } else {
                        inputPasswordLegal = true;
                        if(inputNameLegal) {
                            loginBtn.setEnabled(true);
                            loginBtn.setBackground(getResources().getDrawable(R.drawable.login_btn_enable, null));
                        }
                    }
                }
            }
        };
        editTextPassword.addTextChangedListener(watcherPassword);
    }

    private void getUserInfo(String name, String password) {
        final Request request = new Request.Builder().url(URL).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String result = Objects.requireNonNull(response.body()).string();
                jsonStringToUserList(result);
                runOnUiThread(() -> {
                    judgeUser(name, password);
                });
            }
        });
    }

    public void jsonStringToUserList(String result) {
        Gson gson = new Gson();
        ServiceUser = gson.fromJson(result, User.class);
    }

    public void judgeUser(String name, String password) {
        if(name.equals(ServiceUser.getName())) {
            if(!password.equals(ServiceUser.getPassword())) {
                Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent  = new Intent(LoginActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        } else {
            Toast.makeText(getApplicationContext(), "用户不存在", Toast.LENGTH_SHORT).show();
        }
    }

    public static String getMD5(String str) throws NoSuchAlgorithmException {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
    }
}