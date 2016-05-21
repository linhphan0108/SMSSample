package com.linhphan.smssample.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.linhphan.smssample.R;
import com.linhphan.smssample.ui.fragment.AllSentSmsFragment;
import com.linhphan.smssample.ui.fragment.ErrorSmsFragment;
import com.linhphan.smssample.ui.fragment.PendingSmsFragment;
import com.linhphan.smssample.ui.fragment.SuccessfullySentSmsFragment;

/**
 * Created by linh on 5/20/2016.
 */
public class SentSmsViewPagerAdapter extends FragmentPagerAdapter {
    private Context mContent;
    public SentSmsViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContent = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = Fragment.instantiate(mContent, AllSentSmsFragment.class.getName());
                break;

            case 1:
                fragment = Fragment.instantiate(mContent, SuccessfullySentSmsFragment.class.getName());
                break;

            case 2:
                fragment = Fragment.instantiate(mContent, PendingSmsFragment.class.getName());
                break;

            case 3:
                fragment = Fragment.instantiate(mContent, ErrorSmsFragment.class.getName());
                break;

            default:
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = mContent.getString(R.string.all);
                break;

            case 1:
                title = mContent.getString(R.string.sent);
                break;

            case 2:
                title = mContent.getString(R.string.pending);
                break;

            case 3:
                title = mContent.getString(R.string.error);
                break;
        }
        return title;
    }
}
