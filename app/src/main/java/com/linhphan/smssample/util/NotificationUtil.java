package com.linhphan.smssample.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.linhphan.smssample.R;

/**
 * Created by linh on 06/04/2016.
 */
public class NotificationUtil {
    /**
     * show notification progress on notification bar.
     * @param contentText the message will be showed in the notification
     */
    public static void showNotification(Context context, String title, String contentText, int NotificationId) {
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(contentText)
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NotificationId, notification);
    }
}
