package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 充值
 */
public class RechargeActivity extends BaseActivity {
    @BindView(R.id.bar_iv_back)
    ImageView barIvBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_tv_right)
    TextView barTvRight;
    @BindView(R.id.iv_recharge_wechat)
    ImageView ivRechargeWechat;
    @BindView(R.id.iv_recharge_alipay)
    ImageView ivRechargeAlipay;
    @BindView(R.id.bt_recharge_apply)
    Button btRechargeApply;
    @BindView(R.id.rl_recharge_ok)
    RelativeLayout rl_recharge_ok;

    boolean isWeChat = true;
    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("充值");
        rl_recharge_ok.setVisibility(View.GONE);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }


    @OnClick({R.id.rl_recharge_wechat, R.id.rl_recharge_alipay, R.id.bt_recharge_apply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_recharge_wechat:
                if(!isWeChat){
                    isWeChat=true;
                    ivRechargeWechat.setBackgroundResource(R.drawable.shape_mine_invoice_bg);
                    ivRechargeAlipay.setBackgroundResource(R.drawable.shape_yelow_stroke_bg);
                }

                break;
            case R.id.rl_recharge_alipay:
                if(isWeChat){
                    isWeChat=false;
                    ivRechargeWechat.setBackgroundResource(R.drawable.shape_yelow_stroke_bg);
                    ivRechargeAlipay.setBackgroundResource(R.drawable.shape_mine_invoice_bg);
                }


                break;
            case R.id.bt_recharge_apply:

                break;
        }
    }
}
