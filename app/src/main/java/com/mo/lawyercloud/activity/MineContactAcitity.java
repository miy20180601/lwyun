package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的客服
 */
public class MineContactAcitity extends BaseActivity {

    @BindView(R.id.bar_iv_back)
    ImageView barIvBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_tv_right)
    TextView barTvRight;
    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_contact_acitity;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("我的客服");

    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }


}
