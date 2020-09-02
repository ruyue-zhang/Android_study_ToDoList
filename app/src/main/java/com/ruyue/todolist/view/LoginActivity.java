package com.ruyue.todolist.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ruyue.todolist.R;
import com.ruyue.todolist.Utils.ConstUtils;
import com.ruyue.todolist.databinding.ActivityLoginBinding;
import com.ruyue.todolist.viewmodels.LoginViewModel;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;


public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    private SharedPreferences.Editor editorMain;
    private Boolean inputNameLegal = false;
    private Boolean inputPasswordLegal = false;

    @BindView(R.id.name)
    EditText editTextName;
    @BindView(R.id.password)
    EditText editTextPassword;
    @BindView(R.id.login)
    Button loginBtn;
    @BindDrawable(R.drawable.login_btn_enable)
    Drawable loginBtnEnable;
    @BindDrawable(R.drawable.login_btn_layout)
    Drawable loginBtnDisable;
    @BindColor(R.color.btn_enable_text_color)
    int btnEnableTextColor;
    @BindColor(R.color.btn_text_color)
    int btnDisableTextColor;

    @OnTextChanged(R.id.name)
    public void IsNameLegal() {
        inputNameLegal = loginViewModel.isInputLegal(editTextName, ConstUtils.NAME_PATTERN, ConstUtils.SET_NAME_ERROR);
        changeLoginBtnStatus();
    }

    @OnTextChanged(R.id.password)
    public void IsPsdLegal() {
        inputPasswordLegal = loginViewModel.isInputLegal(editTextPassword, ConstUtils.PSD_PATTERN, ConstUtils.SET_PSD_ERROR);
        changeLoginBtnStatus();
    }

    @OnClick({R.id.login})
    public void onClick() {
        switch (loginViewModel.login()) {
            case ConstUtils.SUCCESS:
                jumpToMainActivity();
                editorMain.putBoolean("main", true).apply();
                break;
            case ConstUtils.PASSWORD_ERROR:
                Toast.makeText(getApplicationContext(), ConstUtils.ERROR_PSD, Toast.LENGTH_SHORT).show();
                break;
            case ConstUtils.NAME_NOT_EXIST:
                Toast.makeText(getApplicationContext(), ConstUtils.ERROR_NAME, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginViewModel);
        ButterKnife.bind(this);
        isLogin();
        loginViewModel.getUserInfo();
    }

    private void isLogin() {
        SharedPreferences sharedPreferencesMain = PreferenceManager.getDefaultSharedPreferences(this);
        editorMain = sharedPreferencesMain.edit();
        if(sharedPreferencesMain.getBoolean("main",false)) {
            jumpToMainActivity();
        }
    }

    private void jumpToMainActivity() {
        Intent intent  = new Intent(LoginActivity.this, MainPageActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }

    private void changeLoginBtnStatus() {
        if(inputNameLegal && inputPasswordLegal) {
            loginBtn.setEnabled(true);
            loginBtn.setTextColor(btnEnableTextColor);
            loginBtn.setBackground(loginBtnEnable);
        } else {
            loginBtn.setEnabled(false);
            loginBtn.setTextColor(btnDisableTextColor);
            loginBtn.setBackground(loginBtnDisable);
        }
    }
}