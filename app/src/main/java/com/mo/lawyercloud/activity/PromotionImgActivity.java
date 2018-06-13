package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mohaifeng on 18/6/13.
 */
public class PromotionImgActivity extends BaseActivity {

    @BindView(R.id.bar_title)
    TextView barTitle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_promotion_img;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("推广图");
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bar_iv_back)
    public void onViewClicked() {
        finish();
    }
}
