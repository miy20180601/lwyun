package com.mo.lawyercloud.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
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

    //是否注册跳转
    private String type;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        super.onNewIntent(intent);
        type = intent.getStringExtra("type");
        if (!TextUtils.isEmpty(type) && type.equals("1")){
            mViewPager.setCurrentItem(0);
        }
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
        type = getIntent().getStringExtra("type");
        if (!TextUtils.isEmpty(type) && type.equals("1")){
            mViewPager.setCurrentItem(0);
        }
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
                startActivity(new Intent(mContext,MainActivity.class));
                break;
        }
    }

}
