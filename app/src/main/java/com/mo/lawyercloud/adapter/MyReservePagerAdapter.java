package com.mo.lawyercloud.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mo.lawyercloud.fragment.AboutUsFragment;
import com.mo.lawyercloud.fragment.RecruitmentFragment;
import com.mo.lawyercloud.fragment.RegulationFragment;
import com.mo.lawyercloud.fragment.ReserveOrderFragment;

/**
 * Created by Mohaifeng on 2017/6/9.
 */

public class MyReservePagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"全部", "审核中","已审核"};

    public MyReservePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //此处根据不同的position返回不同的Fragment
        Fragment fragment = new ReserveOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("status",position);
        fragment.setArguments(bundle);
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
