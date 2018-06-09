package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 申请发票
 */
public class ApplyInvoiceActivity extends BaseActivity {
    @BindView(R.id.bar_iv_back)
    ImageView barIvBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_tv_right)
    TextView barTvRight;
    @BindView(R.id.tv_apply_orderid)
    TextView tvApplyOrderid;
    @BindView(R.id.tv_apply_money)
    TextView tvApplyMoney;
    @BindView(R.id.rg_invoice_type)
    RadioGroup rgInvoiceType;
    @BindView(R.id.et_invoice_title)
    EditText etInvoiceTitle;
    @BindView(R.id.rg_title_type)
    RadioGroup rgTitleType;
    @BindView(R.id.et_apply_dutynum)
    EditText etApplyDutynum;
    @BindView(R.id.tv_apply_apply)
    TextView tvApplyApply;

    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_invoice;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("申请发票");

    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }



    @OnClick({R.id.bar_iv_back, R.id.bar_tv_right, R.id.tv_apply_apply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_iv_back:
                break;
            case R.id.bar_tv_right:
                break;
            case R.id.tv_apply_apply:
                int invoiceType = rgInvoiceType.getCheckedRadioButtonId();
                if (invoiceType == R.id.rb_type_paper){

                }
                if (invoiceType == R.id.rb_type_ele){

                }
                int titleType = rgTitleType.getCheckedRadioButtonId();
                if (titleType == R.id.rb_title_type_one){

                }
                if (titleType == R.id.rb_title_type_company){
                    //
                }
                break;
        }
    }
}
