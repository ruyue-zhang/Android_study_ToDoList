package com.ruyue.todolist.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.ruyue.todolist.BR;
import com.ruyue.todolist.models.User;
import com.ruyue.todolist.R;
import com.ruyue.todolist.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        judgeNameIsLegal();
        judgePasswordIsLegal();
    }

    private void judgeNameIsLegal() {
        final EditText editTextName = findViewById(R.id.name);
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (editTextName.getText().length() > 0 && !editTextName.getText().toString().matches("^[a-zA-Z0-9]{3,12}$")) {
                    editTextName.setError("用户名必须是3 ~ 12为字母或数字");
                }
            }
        };
        editTextName.addTextChangedListener(watcher);
    }

    private void judgePasswordIsLegal() {
        final EditText editTextPassword = findViewById(R.id.user_password);
        TextWatcher watcherPassword = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (editTextPassword.getText().length() > 0 && !editTextPassword.getText().toString().matches("^.{6,18}$")) {
                    editTextPassword.setError("密码长度必须是6 ~ 18位字符");
                }
            }
        };
        editTextPassword.addTextChangedListener(watcherPassword);
    }
}