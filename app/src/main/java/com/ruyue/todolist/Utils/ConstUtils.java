package com.ruyue.todolist.Utils;

public class ConstUtils {
    public static final int SUCCESS = 0;
    public static final int PASSWORD_ERROR = 1;
    public static final int NAME_NOT_EXIST = 2;
    public static final String ERROR_NAME = "用户不存在";
    public static final String ERROR_PSD = "密码错误";
    public static final String NAME_PATTERN = "^[a-zA-Z0-9]{3,12}$";
    public static final String PSD_PATTERN = "^.{6,18}$";
    public static final String SET_NAME_ERROR = "用户名必须是3 ~ 12为字母或数字";
    public static final String SET_PSD_ERROR = "密码长度必须是6 ~ 18位字符";
    public static final String URL = "https://twc-android-bootcamp.github.io/fake-data/data/user.json";
    public static final String IS_LOGIN = "login";
    public static final int ADD_REQUEST_CODE = 0;
    public static final int ADD_RESULT_CODE = 1;
    public static final int CHANGE_REQUEST_CODE = 2;
    public static final int CHANGE_RESULT_CODE = 3;
}
