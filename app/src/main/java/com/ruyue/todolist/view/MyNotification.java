package com.ruyue.todolist.view;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.ruyue.todolist.R;

public class MyNotification {
    public static final int NOTIFY_ID = 1;
    private NotificationManagerCompat notificationManager;
    private static final String CHANNEL_ID = "messageReminding";
    private static final String CHANNEL_NAME = "noFinishHit";

    public MyNotification(Context context) {
        this.notificationManager = NotificationManagerCompat.from(context);
    }

    public void sendNotification(Context context, String title, int id) {
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        Intent intent = new Intent(context, MainPageActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Notification notification = builder.setSmallIcon(R.drawable.todo_list_logo)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("【任务】" + title)
                .setContentText("今天还未完成呦~")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(id, notification);
    }

    public void cancelNotificationById(int id) {
        notificationManager.cancel(id);
    }
}
