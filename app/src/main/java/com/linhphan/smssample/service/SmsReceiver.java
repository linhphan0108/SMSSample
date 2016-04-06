package com.linhphan.smssample.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.smssample.util.Constant;
import com.linhphan.smssample.util.NotificationUtil;

/**
 * Created by linh on 06/04/2016.
 */
public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.d(getClass().getName(), "got fired");
        String action = intent.getAction();
        if (Constant.INTENT_FLAG_SMS_SENT.equals(action)){
            Logger.d(getClass().getName(), "sms was sent");
            NotificationUtil.showNotificationProgress(context, "sms was sent", 999);
        }else if (Constant.INTENT_FLAG_SMS_DELIVERY.equals(action)){
            Logger.d(getClass().getName(), "sms was delivered");
            NotificationUtil.showNotificationProgress(context, "sms was delivered", 888);
        }
    }
}
