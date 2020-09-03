package com.ruyue.todolist.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ruyue.todolist.Utils.MyNotification;

import java.util.Objects;

import static android.content.ContentValues.TAG;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), "NOTIFICATION")) {
            String title = intent.getStringExtra("title");
            int id = intent.getIntExtra("id", 0);
            Log.d(TAG, "onReceive: " + title + "," + id);
            MyNotification myNotification = new MyNotification(context);
            myNotification.sendNotification(context, title, id);
        }
    }
}
