package com.linhphan.androidboilerplate.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by linhphan on 11/12/15.
 */
public class DateUtil {
    //=== dd-MM-yyyy hh:mm:ss => Tue Aug 31 10:20:56 SGT 1982
    //=== dd/MM/yyyy => 15/10/2013
    //=== yyyy/MM/dd HH:mm:ss => 2013/10/15 16:16:39
    //=== yyyy MMM dd HH:mm:ss => 2013 Jan 31 00:00:00

    public static String parseDateTime(long timeInMillis){
        String pattern = "yyyy-mm-dd HH:mm";
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(timeInMillis);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        return sdf.format(calendar.getTime());
    }

    /**
     * @param intervalMillis interval time in millis seconds
     * @return a string time parsed in hh:mm:ss format
     */
    public static String parseIntervalTime(long intervalMillis){
        int hour = (int) (intervalMillis / (1000*60*60));
        int minute = (int) ((intervalMillis % (1000*60*60)) / (1000*60));
        int second = (int) ((intervalMillis % (1000*60*60) % (1000*60)) / 1000);

        String result, h="", m="", s="";

        if (minute<10)
            m = "0"+minute;
        else m = minute+"";


        if (second<10)
            s = "0"+second;
        else s = second +"";


        if(hour <= 0) {
            result = m +":"+ s;
        }else if(hour < 10) {
            h = "0" + hour;
            result = h +":"+ m +":"+ s;
        }else{
            result = h +":"+ m +":"+ s;
        }


        return result;
    }

    public static long getCurrentTimeInMillis(){
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        return calendar.getTimeInMillis();
    }
}
