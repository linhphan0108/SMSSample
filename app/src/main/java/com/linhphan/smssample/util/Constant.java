package com.linhphan.smssample.util;

/**
 * Created by linh on 06/04/2016.
 */
public class Constant {
    public static final String INTENT_FLAG_ALARM_SENT = "com.linhphan.smssample.alarm_sent";
    public static final String INTENT_FLAG_SMS_SENT = "com.linhphan.smssample.sms_sent";
    public static final String INTENT_FLAG_SMS_DELIVERY = "com.linhphan.smssample.sms_delivery";

    public static final int REQUEST_CODE_ALARM_SENT = 1;
    public static final int REQUEST_CODE_MESSAGE_SENT = 2;
    public static final int REQUEST_CODE_MESSAGE_DELIVERED = 3;

    public static final String ARG_INTENT_MESSAGE = "ARG_INTENT_MESSAGE";
    public static final String ARG_BUNDLE_MESSAGE = "ARG_BUNDLE_MESSAGE";
    public static final String ARG_PHONE_NUMBER = "ARG_PHONE_NUMBER";

}
