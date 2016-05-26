package com.linhphan.smssample.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.linhphan.androidboilerplate.ui.activity.BaseActivity;
import com.linhphan.androidboilerplate.util.ViewUtil;
import com.linhphan.smssample.R;

public class LanguageChoiceActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImgVietnamese;
    private ImageView mImgEnglish;
    private ImageView mImgJapanese;
    private ImageView mImgKorean;
    private ImageView mImgChinese;
    private ImageView mImgThai;
//    private ImageView mImgSentSms;
//    private ImageView mImgYourCustomSms;
    private Toolbar toolbar;

    //============ overridden methods ==============================================================
    @Override
    protected int getActivityLayoutResource() {
        return R.layout.activity_lange_choice;
    }

    @Override
    protected void init() {
        ViewUtil.logPhysicalDeviceScreen(this);
    }

    @Override
    protected void getWidgets(Bundle savedInstanceState) {
        mImgVietnamese = (ImageView) findViewById(R.id.img_vietnamese);
        mImgEnglish = (ImageView) findViewById(R.id.img_english);
        mImgJapanese = (ImageView) findViewById(R.id.img_japanese);
        mImgKorean = (ImageView) findViewById(R.id.img_korean);
        mImgChinese = (ImageView) findViewById(R.id.img_chinese);
        mImgThai = (ImageView) findViewById(R.id.img_thai);
//        mImgSentSms = (ImageView) findViewById(R.id.img_sent_sms);

        setupToolbar();//setup toolbar
    }

    @Override
    protected void registerEventHandler() {
        mImgVietnamese.setOnClickListener(this);
        mImgEnglish.setOnClickListener(this);
        mImgJapanese.setOnClickListener(this);
        mImgKorean.setOnClickListener(this);
        mImgChinese.setOnClickListener(this);
        mImgThai.setOnClickListener(this);
//        mImgSentSms.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;//return true so that the menu pop up is opened
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_custom_sms:

                return true;

            case R.id.menu_item_logs:
                gotoLogsActivity();
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.which_language));
        setSupportActionBar(toolbar);
    }

    private void gotoMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void gotoLogsActivity(){
        Intent intent = new Intent(this, SmsLogsActivity.class);
        startActivity(intent);
    }
}
