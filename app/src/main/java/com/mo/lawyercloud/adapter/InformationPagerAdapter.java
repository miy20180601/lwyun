package com.mo.lawyercloud.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mo.lawyercloud.fragment.AboutUsFragment;
import com.mo.lawyercloud.fragment.LoginFragment;
import com.mo.lawyercloud.fragment.RecruitmentFragment;
import com.mo.lawyercloud.fragment.RegisterFragment;
import com.mo.lawyercloud.fragment.RegulationFragment;

/**
 * Created by Mohaifeng on 2017/6/9.
 */

public class InformationPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"关于我们", "人才招聘","法规常识"};

    public InformationPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //此处根据不同的position返回不同的Fragment

        if (position==0) {
            return AboutUsFragment.getInstance();
        }else if (position == 1) {
            return new RecruitmentFragment();
        }else {
            return new RegulationFragment();
        }
//        Bundle bundle = new Bundle();
//        bundle.putString("type",position+"");
//        fragment.setArguments(bundle);
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
