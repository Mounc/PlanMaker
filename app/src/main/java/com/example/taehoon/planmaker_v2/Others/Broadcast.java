package com.example.taehoon.planmaker_v2.Others;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.taehoon.planmaker_v2.MainBase.MainActivity;
import com.example.taehoon.planmaker_v2.R;

/**
 * Created by TAEHOON on 2017-04-14.
 */

public class Broadcast extends BroadcastReceiver {
    String INTENT_ALCTION = Intent.ACTION_BOOT_COMPLETED;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.calendar).setTicker("HETT").setWhen(System.currentTimeMillis())
                .setNumber(1).setContentTitle("PlanMaker").setContentText("Plan \"Test1\" Alert")
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingIntent).setAutoCancel(true);
        notificationManager.notify(1, builder.build());
    }
}
