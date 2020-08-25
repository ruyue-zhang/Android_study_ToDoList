package com.ruyue.todolist.viewmodels;

import android.app.Application;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.google.gson.Gson;
import com.ruyue.todolist.R;
import com.ruyue.todolist.models.User;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginViewModel extends AndroidViewModel {
    private final Context mContext;
    private ObservableField<String> name = new ObservableField<>();
    private ObservableField<String> password = new ObservableField<>();
    private Boolean inputNameLegal = false;
    private Boolean inputPasswordLegal = false;
    private static final String URL = "https://twc-android-bootcamp.github.io/fake-data/data/user.json";
    private User serviceUser;
    private static final int SUCCESS = 0;
    private static final int PASSWORD_ERROR = 1;
    private static final int NAME_NOT_EXIST = 2;
//    private LocalDataSource userDb;
//    private UserDao userDao;


    public LoginViewModel(@NonNull Application mContext) {
        super(mContext);
        this.mContext = mContext.getApplicationContext();
//        userDb = LocalDataSource.getInstance(mContext);
//        userDao = userDb.userDao();
    }

    public ObservableField<String> getName() {
        return name;
    }

    public ObservableField<String> getPassword() {
        return password;
    }

    public int login() {
        String inputName = name.get();
        String inputPassword = password.get();
        String md5Password = null;
        try {
            md5Password = getMD5(inputPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (inputName.equals(serviceUser.getName())) {
            if(md5Password.equals(serviceUser.getPassword())) {
                return SUCCESS;
            } else {
                return PASSWORD_ERROR;
            }
        } else {
            return NAME_NOT_EXIST;
        }
    }

    public void judgeNameIsLegal(EditText editTextName, Button loginBtn) {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (name.get().length() > 0) {
                    if(!name.get().matches("^[a-zA-Z0-9]{3,12}$")) {
                        editTextName.setError("用户名必须是3 ~ 12为字母或数字");
                        inputNameLegal = false;
                        loginBtnDisable(loginBtn);
                    } else {
                        inputNameLegal = true;
                        if(inputPasswordLegal) {
                            loginBtnEnable(loginBtn);
                        }
                    }
                }
            }
        };
        editTextName.addTextChangedListener(watcher);
    }

    public void judgePasswordIsLegal(EditText editTextPassword, Button loginBtn) {
        TextWatcher watcherPassword = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (password.get().length() > 0) {
                    if(!password.get().matches("^.{6,18}$")) {
                        editTextPassword.setError("密码长度必须是6 ~ 18位字符");
                        inputPasswordLegal = false;
                        loginBtnDisable(loginBtn);
                    } else {
                        inputPasswordLegal = true;
                        if(inputNameLegal) {
                            loginBtnEnable(loginBtn);
                        }
                    }
                }
            }
        };
        editTextPassword.addTextChangedListener(watcherPassword);
    }

    private void loginBtnEnable(Button loginBtn) {
        loginBtn.setEnabled(true);
        loginBtn.setTextColor(mContext.getResources().getColor(R.color.btn_enable_text_color));
        loginBtn.setBackground(mContext.getResources().getDrawable(R.drawable.login_btn_enable, null));
    }

    private void loginBtnDisable(Button loginBtn) {
        loginBtn.setEnabled(false);
        loginBtn.setTextColor(mContext.getResources().getColor(R.color.btn_text_color));
        loginBtn.setBackground(mContext.getResources().getDrawable(R.drawable.login_btn_layout, null));
    }

    public static String getMD5(String str) throws NoSuchAlgorithmException {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
    }

    public void getUserInfo() {
        final Request request = new Request.Builder().url(URL).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String result = Objects.requireNonNull(response.body()).string();
                jsonStringToUserList(result);

            }
        });
    }

    public void jsonStringToUserList(String result) {
        Gson gson = new Gson();
        serviceUser = gson.fromJson(result, User.class);
        //serviceUser.setLoginStatus(true);
        //insertUserInRoom(serviceUser);
    }

//    public void insertUserInRoom(User user) {
//        userDao.insertUser(user);
//    }
}
