package com.mo.lawyercloud.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.LoginPagerAdapter;
import com.mo.lawyercloud.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by mo on 2018/5/10.
 */

public class LoginActivity extends BaseActivity {


    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.bar_iv_back)
    ImageView mBarIvBack;
    @BindView(R.id.bar_tv_right)
    TextView mBarTvRight;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mBarTitle.setText("登录/注册");
        mBarTvRight.setVisibility(View.VISIBLE);
        LoginPagerAdapter loginPagerAdapter = new LoginPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(loginPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0)
                    mBarTvRight.setVisibility(View.VISIBLE);
                else
                    mBarTvRight.setVisibility(View.GONE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }

    @OnClick({R.id.bar_iv_back, R.id.bar_tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_iv_back:
                finish();
                break;
            case R.id.bar_tv_right:
                // TODO: 2018/5/14 跳过登录
//                startActivity(new Intent(mContext,MainActivity.class));
                startActivity(ConsultiveManActivity.class);
                break;
        }
    }

}
