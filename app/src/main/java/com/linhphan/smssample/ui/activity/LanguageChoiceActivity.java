package com.linhphan.smssample.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.linhphan.androidboilerplate.ui.activity.BaseActivity;
import com.linhphan.smssample.R;

public class LanguageChoiceActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImgVietnamese;
    private ImageView mImgEnglish;
    private ImageView mImgJapanese;
    private ImageView mImgKorean;
    private ImageView mImgChinese;
    private ImageView mImgThai;

    //============ overridden methods ==============================================================
    @Override
    protected int getActivityLayoutResource() {
        return R.layout.activity_lange_choice;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void getWidgets(Bundle savedInstanceState) {
        mImgVietnamese = (ImageView) findViewById(R.id.img_vietnamese);
        mImgEnglish = (ImageView) findViewById(R.id.img_english);
        mImgJapanese = (ImageView) findViewById(R.id.img_japanese);
        mImgKorean = (ImageView) findViewById(R.id.img_korean);
        mImgChinese = (ImageView) findViewById(R.id.img_chinese);
        mImgThai = (ImageView) findViewById(R.id.img_thai);
    }

    @Override
    protected void registerEventHandler() {
        mImgVietnamese.setOnClickListener(this);
        mImgEnglish.setOnClickListener(this);
        mImgJapanese.setOnClickListener(this);
        mImgKorean.setOnClickListener(this);
        mImgChinese.setOnClickListener(this);
        mImgThai.setOnClickListener(this);
    }

    //=========== implemented methods ==============================================================
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_vietnamese:
            case R.id.img_english:
            case R.id.img_japanese:
            case R.id.img_korean:
            case R.id.img_chinese:
            case R.id.img_thai:
                gotoMainActivity();
                break;

            default:
                break;

        }
    }

    //=========== inner methods ====================================================================
    private void gotoMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
