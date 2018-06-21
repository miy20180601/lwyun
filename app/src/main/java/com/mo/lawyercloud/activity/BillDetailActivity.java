package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mo on 2018/6/21.
 */

public class BillDetailActivity extends BaseActivity {

    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.tv_income)
    TextView mTvIncome;
    @BindView(R.id.tv_pay_type)
    TextView mTvPayType;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.ll_recharge)
    LinearLayout mLlRecharge;
    @BindView(R.id.tv_lowyer_name)
    TextView mTvLowyerName;
    @BindView(R.id.tv_call_time)
    TextView mTvCallTime;
    @BindView(R.id.tv_expenditure_time)
    TextView mTvExpenditureTime;
    @BindView(R.id.tv_expenditure)
    TextView mTvExpenditure;
    @BindView(R.id.ll_expenditure_detail)
    LinearLayout mLlExpenditureDetail;

    private int mType; // 1为充值 2为支出

    @Override
    public int getLayoutId() {
        return R.layout.activity_bill_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mType = getIntent().getIntExtra("type", 1);
        if (mType == 1){
            mBarTitle.setText("充值记录");
            mLlRecharge.setVisibility(View.VISIBLE);
        }else if (mType == 2){
            mBarTitle.setText("账单详情");
            mLlExpenditureDetail.setVisibility(View.VISIBLE);
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
