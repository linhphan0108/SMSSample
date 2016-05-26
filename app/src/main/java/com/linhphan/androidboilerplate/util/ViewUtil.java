package com.linhphan.androidboilerplate.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by linhphan on 11/11/15.
 */
public class ViewUtil {
    /**
     * convert pixel unit to dp unit
     * @param px the width in pixel
     * @return the width in dp
     */
    public static float convertPx2Dp(float px){
        float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        return px /(densityDpi /160f);
    }

    /**
     * convert pixel unit to dp unit
     * @param dp the width in dp
     * @return the width in pixel
     */
    public static float convertDp2Px(float dp){
        float density = Resources.getSystem().getDisplayMetrics().densityDpi;
        return dp * density;
    }

    public static void hideKeyBoard(Activity activity){
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }

    public void showSoftKeyboard(Activity activity, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

    public Bitmap getScreenShot(View view){
        view.buildDrawingCache();
        Bitmap screenShot = view.getDrawingCache();
        view.destroyDrawingCache();
        return screenShot;
    }

    /**
     * disable a view in 2 seconds, then re-enable it after then.
     * @param view will be disabled
     */
    public static void lockViewTemporary(final View view){
        view.setEnabled(false);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
            }
        }, 2000);
    }

    public static void logPhysicalDeviceScreen(Activity activity){
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        Logger.d("****", "****************** physical screen size ******************");
        Logger.d("****", "width "+ metrics.widthPixels + "px => " + convertPx2Dp(metrics.widthPixels) + "dp");
        Logger.d("****", "height "+ metrics.heightPixels + "px => " + convertPx2Dp(metrics.heightPixels) + "dp");
        Logger.d("****", "****************** physical screen size ******************");


    }

}
