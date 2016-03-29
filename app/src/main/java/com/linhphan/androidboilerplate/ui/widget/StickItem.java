package com.linhphan.androidboilerplate.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.androidboilerplate.util.ViewUtil;

/**
 * Created by linhphan on 3/17/16.
 */
public abstract class StickItem extends FrameLayout implements View.OnTouchListener, View.OnClickListener {

    private final String THIS_TAG = "StickItem";
    private final String BUTTON_SCALE_TAG = "BUTTON_SCALE_TAG";
    private final String BUTTON_DELETE_TAG = "BUTTON_DELETE_TAG";
    private final String BUTTON_FLIP_TAG = "BUTTON_FLIP_TAG";

    private final int MIN_SIZE_DP = 150;
    private final int BORDER_THIN_DP = 2;
    private final int BUTTON_SIZE_DP = 30;

    private BorderView mBorder;
    private ImageView mBtnDelete;
    private ImageView mBtnScale;
    private ImageView mBtnFlip;

    private float mThisX;
    private float mThisY;
    float mEventRawX = 0f;
    float mEventRawY = 0f;

    //=========== constructors ======================================================================
    public StickItem(Context context) {
        super(context);

        init(context);
    }

    public StickItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StickItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public StickItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    //============== implemented methods ===========================================================
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (BUTTON_SCALE_TAG.equals(v.getTag())){//==== scaling
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mEventRawX = event.getRawX();
                    mEventRawY = event.getRawY();
                    break;

                case MotionEvent.ACTION_MOVE:

                    break;

                case MotionEvent.ACTION_UP:

                    break;

                default:
                    break;
            }
        }else { //===== dragging
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mEventRawX = event.getRawX();
                    mEventRawY = event.getRawY();
                    break;

                case MotionEvent.ACTION_MOVE:
                    float offsetX = event.getRawX() - mEventRawX;
                    float offsetY = event.getRawY() - mEventRawY;
                    this.setX(this.getX() + offsetX);
                    this.setY(this.getY() + offsetY);

                    mEventRawX = event.getRawX();
                    mEventRawY = event.getRawY();
                    break;

                case MotionEvent.ACTION_UP:

                    break;

                default:
                    break;
            }
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getTag().equals(BUTTON_DELETE_TAG)){
            ViewGroup viewGroup = (ViewGroup) this.getParent();
            viewGroup.removeView(StickItem.this);
            Logger.d(StickItem.class.getName(), "delete button is clicked");

        }else if(v.getTag().equals(BUTTON_FLIP_TAG)){

            Logger.d(StickItem.class.getName(), "flip button is clicked");
        }
    }


    //============== inner methods =================================================================
    private void init(Context context) {
        int size = (int) ViewUtil.convertDp2Px(MIN_SIZE_DP);
        int buttonSize = (int) ViewUtil.convertDp2Px(BUTTON_SIZE_DP);

        LayoutParams layoutParams = new LayoutParams(size, size, Gravity.CENTER);
        this.setLayoutParams(layoutParams);
        this.setOnTouchListener(this);

        //===== main view
        LayoutParams mainViewLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        mainViewLayoutParams.setMargins(buttonSize, buttonSize, buttonSize, buttonSize);

        //===== rounded border
        mBorder = new BorderView(context);
        int borderMargin = buttonSize /2;
        LayoutParams borderLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        borderLayoutParams.setMargins(borderMargin, borderMargin, borderMargin, borderMargin);
        mBorder.setLayoutParams(borderLayoutParams);

        //===== flip button
        mBtnFlip = new ImageView(context);
        mBtnFlip.setTag(BUTTON_FLIP_TAG);
        mBtnFlip.setImageResource(android.R.drawable.ic_menu_directions);
        mBtnFlip.setOnClickListener(this);
        LayoutParams btnFlipLayoutParams = new LayoutParams(buttonSize, buttonSize);
        btnFlipLayoutParams.gravity = Gravity.START | Gravity.TOP;

        //===== delete button
        mBtnDelete = new ImageView(context);
        mBtnDelete.setTag(BUTTON_DELETE_TAG);
        mBtnDelete.setImageResource(android.R.drawable.ic_delete);
        mBtnDelete.setOnClickListener(this);
        LayoutParams btnDeleteLayoutParams = new LayoutParams(buttonSize, buttonSize);
        btnDeleteLayoutParams.gravity = Gravity.TOP | Gravity.END;

        //===== scale button
        mBtnScale = new ImageView(context);
        mBtnScale.setTag(BUTTON_SCALE_TAG);
        mBtnScale.setImageResource(android.R.drawable.ic_menu_crop);
        LayoutParams btnScaleLayoutParams = new LayoutParams(buttonSize, buttonSize);
        btnScaleLayoutParams.gravity = Gravity.END | Gravity.BOTTOM;

        //===== add views
        addView(getMainView(), mainViewLayoutParams);
        addView(mBorder);
        addView(mBtnDelete, btnDeleteLayoutParams);
        addView(mBtnScale, btnScaleLayoutParams);
        addView(mBtnFlip, btnFlipLayoutParams);
    }

    protected abstract View getMainView();

    //========= inner classes ======================================================================

    /**
     * this class will make an thin border around item.
     */
    class BorderView extends View{

        private Rect round;
        private Paint paint;

        public BorderView(Context context) {
            super(context);
            round = new Rect();
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.GRAY);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(ViewUtil.convertDp2Px(BORDER_THIN_DP));
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

        }

        @Override
        protected void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            LayoutParams layoutParams = (LayoutParams) getLayoutParams();
            round.left = getLeft() - getPaddingLeft() - layoutParams.leftMargin;
            round.right = getRight() - getPaddingRight() - layoutParams.rightMargin;
            round.top = getTop() - getPaddingTop() - layoutParams.topMargin;
            round.bottom = getBottom() - getPaddingBottom() - layoutParams.bottomMargin;
            canvas.drawRect(round, paint);
        }
    }


}
