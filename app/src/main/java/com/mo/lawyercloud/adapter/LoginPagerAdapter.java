package com.mo.lawyercloud.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.TextView;

import com.mo.lawyercloud.fragment.LoginFragment;
import com.mo.lawyercloud.fragment.RegisterFragment;

/**
 * Created by Mohaifeng on 2017/6/9.
 */

public class LoginPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"登录", "注册"};

    public LoginPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //此处根据不同的position返回不同的Fragment
        Fragment fragment;
        if (position==0)
            fragment = new LoginFragment();
        else
            fragment = new RegisterFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("type",position+"");
//        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        //此处返回Tab的数目
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //此处返回每个Tab的title
        return mTitles[position];
    }
}
