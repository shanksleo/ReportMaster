package com.example.shanks.reportmaster;

import android.app.Notification;
import android.app.NotificationManager;

/**
 * Created by shanks on 15/2/2.
 */
public class NotificationMe {
//
    public void notifyTip() {


        Notification.Builder mBuilder = new Notification.Builder(YoApplication.getContext());
        mBuilder.setSmallIcon(R.drawable.i001);
        mBuilder.setTicker("你点我试试");
        mBuilder.setContentTitle("好消息好消息");
        mBuilder.setContentText("你明天要带伞了");
        Notification notification =  mBuilder.build();
        NotificationManager notificationManager = (NotificationManager)YoApplication.getContext().getSystemService(YoApplication.getContext().NOTIFICATION_SERVICE);
        notificationManager.notify(0x122,notification);

    }
}
