package com.linhphan.smssample.service;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.smssample.util.Common;
import com.linhphan.smssample.util.Constant;
import com.linhphan.smssample.util.NotificationUtil;

import java.util.Random;

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
            int rand = (int) (Math.random() * 300);
            switch (getResultCode()){
                case Activity.RESULT_OK:
                    String title = "sms was sent to "+ intent.getStringExtra(Constant.ARG_PHONE_NUMBER);
                    String message = intent.getStringExtra(Constant.ARG_MESSAGE);
                    NotificationUtil.showNotificationProgress(context, title, message, rand);
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    NotificationUtil.showNotificationProgress(context, "Error", "Generic failure", rand);
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE:
                    NotificationUtil.showNotificationProgress(context, "Error", "No service", rand);
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    NotificationUtil.showNotificationProgress(context, "Error", "No PDU", rand);
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    NotificationUtil.showNotificationProgress(context, "Error", "Radio is off", rand);
                    break;
            }

        }else if (Constant.INTENT_FLAG_SMS_DELIVERY.equals(action)){
            Logger.d(getClass().getName(), "sms was delivered");
            int rand = (int) (Math.random() * 200);
            switch (getResultCode()){
                case Activity.RESULT_OK:
                    String title = "sms was delivered to "+ intent.getStringExtra(Constant.ARG_PHONE_NUMBER);
                    String message = intent.getStringExtra(Constant.ARG_MESSAGE);
                    NotificationUtil.showNotificationProgress(context, title, message, rand);
                    break;
                case Activity.RESULT_CANCELED:
                    NotificationUtil.showNotificationProgress(context, "Error", "SMS wasn't delivered", rand);
                    break;
            }

        }else if (Constant.INTENT_FLAG_ALARM_SENT.equals(action)){
            int rand = (int) (Math.random() * 100);
            String message = intent.getStringExtra(Constant.ARG_MESSAGE);
            String tamPHoneNumber = "0978992209";
            Common.sendMessageDirectly(context, tamPHoneNumber, message);

            NotificationUtil.showNotificationProgress(context, "alarm is sent with flag "+ rand, message,rand);
            Logger.d(getClass().getName(), "alarm was sent");
        }
    }
}
