package com.mo.lawyercloud.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.base.Constant;
import com.mo.lawyercloud.beans.apiBeans.MemberBean;
import com.mo.lawyercloud.network.RetrofitFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的钱包
 */
public class MyWalletActivity extends BaseActivity {
    @BindView(R.id.bar_iv_back)
    ImageView barIvBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.tv_balance)
    TextView mTvBalance;
    @BindView(R.id.rl_wallet_recharge)
    RelativeLayout rlWalletRecharge;
    @BindView(R.id.rl_wallet_withdraw)
    RelativeLayout rlWalletWithdraw;


    private int mType;//1为普通用户，2为律师
    private MemberBean mMemberBean;
    private final static int REQUEST_WITHDRAW = 0x11;
    private final static int REQUEST_RECHARGE = 0x22;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_wallet;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("我的钱包");
        mMemberBean = (MemberBean) mACache.getAsObject(Constant.MEMBER_INFO);
        mType = getIntent().getIntExtra("type", 1);
        if (mType == 1) {
            rlWalletRecharge.setVisibility(View.VISIBLE);
        } else if (mType == 2) {
            rlWalletWithdraw.setVisibility(View.VISIBLE);
        }
        mTvBalance.setText(mMemberBean.getBalance() + "元");

    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }


    @OnClick({R.id.bar_iv_back, R.id.rl_wallet_recharge, R.id.rl_wallet_withdraw, R.id
            .rl_billing_records})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_iv_back:
                finish();
                break;
            case R.id.rl_wallet_recharge:
                startActivityForResult(new Intent(mContext, RechargeActivity.class), REQUEST_RECHARGE);
                break;
            case R.id.rl_wallet_withdraw:
                startActivityForResult(new Intent(mContext, WithdrawActivity.class), REQUEST_WITHDRAW);
                break;
            case R.id.rl_billing_records:
                if (mType == 1) {
                    startActivity(BillingRecordsActivity.class);
                } else if (mType == 2) {
                    startActivity(BillingRecordsLowyerActivity.class);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK ) {
            if (requestCode == REQUEST_WITHDRAW){
                updateBanlance(data);
            }else if (requestCode == REQUEST_RECHARGE){
                if(data != null){
                    updateBanlance(data);
                }
            }
        }
    }

    private void updateBanlance(Intent data) {
        int amount = data.getIntExtra("amount", 0);
        mTvBalance.setText(mMemberBean.getBalance() - amount + "元");
        mMemberBean.setBalance(mMemberBean.getBalance() - amount);
        mACache.put(Constant.MEMBER_INFO, mMemberBean);
    }
}
