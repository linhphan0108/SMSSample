package com.linhphan.smssample.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.linhphan.androidboilerplate.ui.activity.BaseActivity;
import com.linhphan.androidboilerplate.util.Logger;
import com.linhphan.smssample.R;
import com.linhphan.smssample.ui.adapter.SentSmsViewPagerAdapter;

/**
 * Created by linh on 5/20/2016.
 */
public class SmsLogsActivity extends BaseActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    //========== inherited methods =================================================================
    @Override
    protected int getActivityLayoutResource() {
        return R.layout.activity_sent_sms;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void getWidgets(Bundle savedInstanceState) {

        //== setup tool bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //== view pager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        SentSmsViewPagerAdapter adapter = new SentSmsViewPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        //== tabs layout
        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);
//        }

    }

    @Override
    protected void registerEventHandler() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    //========== implemented methods ===============================================================


    //========== inner methods methods =============================================================
}
