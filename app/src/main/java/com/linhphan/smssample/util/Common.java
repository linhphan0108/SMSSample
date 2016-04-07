package com.linhphan.smssample.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

import com.linhphan.androidboilerplate.util.Logger;

/**
 * Created by linh on 07/04/2016.
 */
public class Common {
    public static void sendMessageDirectly(Context context, String phoneNumber, String message){
        Intent sentIntent = new Intent(Constant.INTENT_FLAG_SMS_SENT);
        sentIntent.putExtra(Constant.ARG_PHONE_NUMBER, phoneNumber);
        sentIntent.putExtra(Constant.ARG_MESSAGE, message);
        Intent deliveryIntent = new Intent(Constant.INTENT_FLAG_SMS_DELIVERY);
        deliveryIntent.putExtra(Constant.ARG_PHONE_NUMBER, phoneNumber);
        deliveryIntent.putExtra(Constant.ARG_MESSAGE, message);

        int randSentRequestCode = (int) (Math.random() * 100) + 200;
        int randDeliveryRequestCode = (int) (Math.random() * 100) + 300;
        PendingIntent sentPendingIntent = PendingIntent.getBroadcast(context, randSentRequestCode, sentIntent, 0);
        PendingIntent deliveryPendingIntent =PendingIntent.getBroadcast(context, randDeliveryRequestCode, deliveryIntent, 0);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, sentPendingIntent, deliveryPendingIntent);
        Logger.d(Common.class.getName(), "message will be sent to " + phoneNumber + ", content: "+ message);
    }
}
