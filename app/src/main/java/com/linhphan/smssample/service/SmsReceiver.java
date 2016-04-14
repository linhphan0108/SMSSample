package com.linhphan.smssample.service;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;

import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.smssample.data.model.MessageWrapper;
import com.linhphan.smssample.util.Common;
import com.linhphan.smssample.util.Constant;
import com.linhphan.smssample.util.NotificationUtil;

import java.util.ArrayList;

/**
 * Created by linh on 06/04/2016.
 */
public class SmsReceiver extends BroadcastReceiver {
    private final int maxLengthNotificationMessage = 30;

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.d(getClass().getName(), "got fired");
        String action = intent.getAction();
        if (Constant.INTENT_FLAG_ALARM_SENT.equals(action)){
            int rand = (int) (Math.random() * 100);
            String json = intent.getStringExtra(Constant.ARG_INTENT_MESSAGE);
            MessageWrapper messageWrapper = new MessageWrapper();
            messageWrapper.jsonToObject(json);
            String message = messageWrapper.getContent();
            sendMessageDirectly(context, messageWrapper);

            NotificationUtil.showNotificationProgress(context, "alarm is sent with flag " + rand, message, rand);
            Logger.d(getClass().getName(), "alarm was sent, "+ json);
        }
    }

    private void sendMessageDirectly(Context context, final MessageWrapper messageWrapper){
        SmsManager smsManager = SmsManager.getDefault();
        String Destination = messageWrapper.getDestinationPhoneNumber();
        String message = messageWrapper.getContent();
        ArrayList<String> parts= smsManager.divideMessage(message);
        int numParts = parts.size();
        String intentFlagSent = Constant.INTENT_FLAG_SMS_SENT + messageWrapper.getSessionId();
        String intentFlagDelivered = Constant.INTENT_FLAG_SMS_DELIVERY + messageWrapper.getSessionId();

        BroadcastReceiver sentBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int rand = (int) (Math.random() * 300);
                int sentPart = messageWrapper.getPartsSize() - messageWrapper.getSentTrack() +1;
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        Logger.d(getClass().getName(), " part "+ sentPart + " of your sms was sent to " + messageWrapper.getDestinationPhoneNumber());
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                        int errorCode = intent.getExtras().getInt("errorCode");
                        Logger.d(getClass().getName(), "Generic failure. at part "+ sentPart);
                        messageWrapper.setError("Generic failure. at part " + sentPart);
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Logger.d(getClass().getName(), "No service at part "+ sentPart);
                        messageWrapper.setError("No service at part "+ sentPart);
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Logger.d(getClass().getName(), "No PDU at part "+ sentPart);
                        messageWrapper.setError("No PDU at part "+ sentPart);
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Logger.d(getClass().getName(), "Radio is off at part " + sentPart);
                        messageWrapper.setError("Radio is off at part "+ sentPart);
                        break;
                }
                messageWrapper.decreaseSentTrack();

                if (messageWrapper.getSentTrack() <= 0){
                    String title;
                    String content = messageWrapper.getContent();
                    if (content.length() > maxLengthNotificationMessage -3){
                        content = content.substring(0, maxLengthNotificationMessage) + "...";
                    }
                    if (TextUtils.isEmpty(messageWrapper.getError())){
                        title = "your message sent to " + messageWrapper.getDestinationPhoneNumber();
                    }else{
                        title = "failure to send your message to " + messageWrapper.getDestinationPhoneNumber();
                    }
                    NotificationUtil.showNotificationProgress(context, title, content, rand);
                    context.unregisterReceiver(this);
                }
            }
        };

        BroadcastReceiver deliveredBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Logger.d(Common.class.getName(), "on delivered broadcast receiver is called" );
                int rand = (int) (Math.random() * 200);
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        Logger.d(getClass().getName(), "sms was delivered successfully to "+  messageWrapper.getDestinationPhoneNumber());
                        break;
                    case Activity.RESULT_CANCELED:
                        Logger.d(getClass().getName(), "Error SMS wasn't delivered "+  messageWrapper.getDestinationPhoneNumber());
                        break;
                }
                messageWrapper.decreaseDeliveredTrack();
                if (messageWrapper.getDeliveredTrack() <= 0){
                    String title;
                    String content = messageWrapper.getContent();
                    if (content.length() > maxLengthNotificationMessage -3){
                        content = content.substring(0, maxLengthNotificationMessage) + "...";
                    }
                    if (TextUtils.isEmpty(messageWrapper.getError())){
                         title = "your sms was delivered to " + messageWrapper.getDestinationPhoneNumber();
                    }else{
                        title = "failure to delivery your message to " + messageWrapper.getDestinationPhoneNumber();
                    }
                    NotificationUtil.showNotificationProgress(context, title, content, rand);
                    context.unregisterReceiver(this);
                }
            }
        };
        //== register broadcast receiver
        context.getApplicationContext().registerReceiver(sentBroadcastReceiver, new IntentFilter(intentFlagSent));
        context.getApplicationContext().registerReceiver(deliveredBroadcastReceiver, new IntentFilter(intentFlagDelivered));

        ArrayList<PendingIntent> sentIntents = new ArrayList<>();
        ArrayList<PendingIntent> deliveryIntents = new ArrayList<>();

        for (int i = 0; i < numParts; i++) {
            Intent sentIntent = new Intent(intentFlagSent);
            Intent deliveredIntent = new Intent(intentFlagDelivered);
            sentIntents.add(PendingIntent.getBroadcast(context, 0, sentIntent, 0));
            deliveryIntents.add(PendingIntent.getBroadcast(context, 0, deliveredIntent, 0));
        }

        smsManager.sendMultipartTextMessage(Destination, null, parts, sentIntents, deliveryIntents);
        Logger.d(Common.class.getName(), "message will be sent to " + Destination + ", content: "+ message);
    }
}
