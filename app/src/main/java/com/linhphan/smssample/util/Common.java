package com.linhphan.smssample.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

import com.linhphan.androidboilerplate.util.Logger;

import java.util.ArrayList;

/**
 * Created by linh on 07/04/2016.
 */
public class Common {
    public static void sendMessageDirectly(Context context, String phoneNumber, String message){
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> parts= smsManager.divideMessage(message);
        int numParts = parts.size();

        ArrayList<PendingIntent> sentIntents = new ArrayList<>();
        ArrayList<PendingIntent> deliveryIntents = new ArrayList<>();

        for (int i = 0; i < numParts; i++) {
            Intent sentIntent = new Intent(Constant.INTENT_FLAG_SMS_SENT);
            sentIntent.putExtra(Constant.ARG_PHONE_NUMBER, phoneNumber);
            sentIntent.putExtra(Constant.ARG_INTENT_MESSAGE, parts.get(i));
            Intent deliveredIntent = new Intent(Constant.INTENT_FLAG_SMS_DELIVERY);
            deliveredIntent.putExtra(Constant.ARG_PHONE_NUMBER, phoneNumber);
            deliveredIntent.putExtra(Constant.ARG_INTENT_MESSAGE, parts.get(i));
            sentIntents.add(PendingIntent.getBroadcast(context, 0, sentIntent, 0));
            deliveryIntents.add(PendingIntent.getBroadcast(context, 0, deliveredIntent, 0));
        }

//        int randSentRequestCode = (int) (Math.random() * 100) + 200;
//        int randDeliveryRequestCode = (int) (Math.random() * 100) + 300;

//        PendingIntent sentPendingIntent = PendingIntent.getBroadcast(context, 0, sentIntent, 0);
//        PendingIntent deliveryPendingIntent =PendingIntent.getBroadcast(context, 0, deliveredIntent, 0);

        smsManager.sendMultipartTextMessage(phoneNumber, null, parts, sentIntents, deliveryIntents);
        Logger.d(Common.class.getName(), "message will be sent to " + phoneNumber + ", content: "+ message);
    }
}
