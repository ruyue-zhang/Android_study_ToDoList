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
import com.ruyue.todolist.Utils.ConstUtils;
import com.ruyue.todolist.models.User;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginViewModel extends AndroidViewModel {
    private final Context mContext;
    private ObservableField<String> name = new ObservableField<>();
    private ObservableField<String> password = new ObservableField<>();
    private User serviceUser;

    public LoginViewModel(@NonNull Application mContext) {
        super(mContext);
        this.mContext = mContext.getApplicationContext();
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
                return ConstUtils.SUCCESS;
            } else {
                return ConstUtils.PASSWORD_ERROR;
            }
        } else {
            return ConstUtils.NAME_NOT_EXIST;
        }
    }
    public Boolean isInputLegal(EditText inputView, String namePattern, String errorInfo) {
        if(inputView.getText().toString().matches(namePattern)) {
            return true;
        } else {
            inputView.setError(errorInfo);
            return false;
        }
    }

    public static String getMD5(String str) throws NoSuchAlgorithmException {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
    }

    public void getUserInfo() {
        final Request request = new Request.Builder().url(ConstUtils.URL).build();
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
    }
}
