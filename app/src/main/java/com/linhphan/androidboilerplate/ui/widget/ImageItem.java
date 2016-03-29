package com.linhphan.androidboilerplate.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by linhphan on 3/17/16.
 */
public class ImageItem extends StickItem {

    private ImageView mImg;

    //============ constructors ====================================================================
    public ImageItem(Context context) {
        super(context);
    }

    public ImageItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ImageItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected View getMainView() {
        if (mImg == null){
            mImg = new ImageView(getContext());
            mImg.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        return mImg;
    }

    public void setImageDrawable(Drawable drawable){
        mImg.setImageDrawable(drawable);
    }
}
