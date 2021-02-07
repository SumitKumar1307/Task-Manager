package com.sumitkumar.taskmanager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlarmReciver extends BroadcastReceiver {

    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID =
            "alert_notification_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        mNotificationManager = (NotificationManager)
                context.getSystemService
                        (Context.NOTIFICATION_SERVICE);
        deliverNotification(context, "Random Task Name");
    }

    private void deliverNotification(Context context, String name) {
        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(
                context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_work_24)
                .setContentTitle("Task Alert")
                .setContentText(name)
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
