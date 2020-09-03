package com.ruyue.todolist.Utils;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.ruyue.todolist.MyApplication;
import com.ruyue.todolist.view.AlarmReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmUtil {
    private Context context;
    private MyNotification myNotification;

    public AlarmUtil() {
        this.context = MyApplication.getInstance().getApplicationContext();
        myNotification = new MyNotification(context);
    }

    public void cancelNotificationById(int id) {
        myNotification.cancelNotificationById(id);
    }

    public void addNotification(int id, String title, String date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String hitTimeStr = date + " 00:28:00";
        Date hitTime = null;
        try {
            hitTime = format.parse(hitTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(hitTime.getTime()  > System.currentTimeMillis()) {
            Intent intent = new Intent(context, AlarmReceiver.class);
            intent.putExtra("title", title);
            intent.putExtra("id",id);
            intent.setAction("NOTIFICATION");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, hitTime.getTime(), pendingIntent);
        }
    }
}
