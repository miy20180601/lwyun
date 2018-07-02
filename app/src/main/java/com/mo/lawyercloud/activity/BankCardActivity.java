package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mo on 2018/7/2.
 */

public class BankCardActivity extends BaseActivity {

    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.edit_cardholder)
    EditText mEditCardholder;
    @BindView(R.id.edit_card_number)
    EditText mEditCardNumber;
    @BindView(R.id.edit_bank)
    EditText mEditBank;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bank_card;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mBarTitle.setText("添加银行卡");
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }

    @OnClick({R.id.bar_iv_back, R.id.bt_recharge_apply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_iv_back:
                break;
            case R.id.bt_recharge_apply:
                break;
        }
    }
}
