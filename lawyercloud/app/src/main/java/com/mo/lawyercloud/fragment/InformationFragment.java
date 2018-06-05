package com.mo.lawyercloud.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.InformationPagerAdapter;
import com.mo.lawyercloud.adapter.LoginPagerAdapter;
import com.mo.lawyercloud.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by Mohaifeng on 18/5/24.
 */
public class InformationFragment extends BaseFragment {

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
        return R.layout.fg_information;
    }

    private static InformationFragment instance = null;

    public static InformationFragment getInstance() {
        if (instance == null) {
            instance = new InformationFragment();
        }
        return instance;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        barIvBack.setVisibility(View.GONE);
        barTitle.setVisibility(View.VISIBLE);
        barTitle.setText("资讯页面");
        InformationPagerAdapter pagerAdapter = new InformationPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
