package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.InformationPagerAdapter;
import com.mo.lawyercloud.adapter.MyReservePagerAdapter;
import com.mo.lawyercloud.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mohaifeng on 18/6/11.
 * 我的预约-普通用户
 */
public class MyReserveOrderActivity extends BaseActivity {


    @BindView(R.id.bar_iv_back)
    ImageView barIvBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_reserve_order;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("我的预约");
        MyReservePagerAdapter pagerAdapter = new MyReservePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }



    @OnClick(R.id.bar_iv_back)
    public void onViewClicked() {
    }
}
