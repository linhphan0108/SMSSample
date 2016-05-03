package com.linhphan.smssample.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.smssample.data.model.SmsWrapper;
import com.linhphan.smssample.util.Common;
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
        if (Constant.INTENT_FLAG_ALARM_SENT.equals(action)){
            int rand = (int) (Math.random() * 100);
            String json = intent.getStringExtra(Constant.ARG_INTENT_MESSAGE);
            SmsWrapper messageWrapper = new SmsWrapper();
            messageWrapper.jsonToObject(json);
            String message = messageWrapper.getContent();
            Common.sendMessageDirectly(context, messageWrapper);

            NotificationUtil.showNotification(context, "alarm is sent with flag " + rand, message, rand);
            Logger.d(getClass().getName(), "alarm was sent, "+ json);
        }
    }


}
