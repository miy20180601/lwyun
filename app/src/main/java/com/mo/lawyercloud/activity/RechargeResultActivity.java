package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.base.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mohaifeng on 18/6/17.
 */
public class RechargeResultActivity extends BaseActivity {

    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.iv_recharge_result)
    ImageView ivRechargeResult;
    @BindView(R.id.tv_recharge_result)
    TextView tvRechargeResult;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge_result;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("充值");
        int result = getIntent().getIntExtra(Constant.RECHARGE_RESULT, 1);
        if (result == 1){
            ivRechargeResult.setImageResource(R.mipmap.recharge_icon_success);
            tvRechargeResult.setText("充值成功");
        }else {
            ivRechargeResult.setImageResource(R.mipmap.recharge_icon_fail);
            tvRechargeResult.setText("充值失败");
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }


    @OnClick(R.id.bar_iv_back)
    public void onViewClicked() {
        finish();
    }
}
