package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.NToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * 申请发票
 */
public class ApplyInvoiceActivity extends BaseActivity {

    String id = "";//订单号
    String money = "";//金额
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
    @BindView(R.id.et_invoice_mail)
    EditText etInvoiceMail;
    @BindView(R.id.et_invoice_num)
    EditText etInvoiceNum;
    @BindView(R.id.ll_invoice_num)
    LinearLayout llInvoiceNum;
    @BindView(R.id.et_invoice_bank)
    EditText etInvoiceBank;
    @BindView(R.id.ll_invoice_bank)
    LinearLayout llInvoiceBank;
    @BindView(R.id.tv_apply_apply)
    TextView tvApplyApply;

    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_invoice;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        id = (String) savedInstanceState.get("id");
        money = (String) savedInstanceState.get("money");
        barTitle.setText("申请发票");
        barTvRight.setText("+");
        barTvRight.setPadding(5, 5, 5, 5);
        barTvRight.setVisibility(View.VISIBLE);
        tvApplyOrderid.setText("订单编号  " + id);
        tvApplyMoney.setText("交易金额  " + money);
        llInvoiceNum.setVisibility(View.GONE);
        llInvoiceBank.setVisibility(View.GONE);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }


    public void apply() {
        int titleType = 1;
        int titleId = rgInvoiceType.getCheckedRadioButtonId();//个人 和公司
        if (titleId == R.id.rb_type_paper) {
            titleType = 1;
        }
        if (titleId == R.id.rb_type_ele) {
            titleType = 2;
        }
        String invoiceTitle = etInvoiceTitle.getText().toString().trim();
        if (TextUtils.isEmpty(invoiceTitle)) {
            NToast.shortToast(mContext, "抬头不能为空");
            return;
        }
        String mail = etInvoiceMail.getText().toString().trim();
        if (TextUtils.isEmpty(mail)) {
            NToast.shortToast(mContext, "邮箱不能为空");
            return;
        }
        String invoiceTaxpayersNo = etInvoiceNum.getText().toString().trim();
        if (TextUtils.isEmpty(invoiceTaxpayersNo) && titleType == 2) {
            NToast.shortToast(mContext, "税号不能为空");
            return;
        }
        String bank = etInvoiceBank.getText().toString().trim();
        if (TextUtils.isEmpty(bank) && titleType == 2) {
            NToast.shortToast(mContext, "公司开户行不能为空");
            return;
        }
        if(titleType==1){
            registerInvoice(titleType+"",invoiceTitle,mail,invoiceTaxpayersNo,bank);
        }else{
            registerInvoice(titleType+"",invoiceTitle,mail,null,null);

        }
    }

    /**
     *申请发票
     * @param invoiceType 发票抬头类型 1、个人  2、企业
     * @param invoiceTitle 发票抬头
     * @param mail 邮箱
     * @param invoiceTaxpayersNo 税号
     * @param bank 公司开户行
     */
    public void registerInvoice( String invoiceType
            ,  String invoiceTitle
            ,  String mail
            ,  String invoiceTaxpayersNo
            ,  String bank
    ) {
        Map<String, String> params = new HashMap<>();
        params.put("invoiceType", invoiceType);
        params.put("invoiceTitle", invoiceTitle);
        params.put("id", id);
        params.put("mail", mail);
        if(invoiceType.trim().endsWith("1")){
            params.put("invoiceTaxpayersNo", invoiceTaxpayersNo);
            params.put("bank", bank);
        }
        Gson gson = new Gson();
        String strEntity = gson.toJson(params);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);
        Observable<BaseEntity<Object>> observable = RetrofitFactory.getInstance().registerInvoice(body);
        observable.compose(this.<BaseEntity<Object>>rxSchedulers()).subscribe(new BaseObserver<Object>() {
            @Override
            protected void onHandleSuccess(Object registerResult, String msg) {
                NToast.shortToast(mContext,"提交成功");
            }

            @Override
            protected void onHandleError(int statusCode, String msg) {
                NToast.shortToast(mContext, msg);
            }
        });
    }

    @OnClick({R.id.bar_iv_back, R.id.bar_tv_right, R.id.rb_type_paper, R.id.rb_type_ele, R.id.tv_apply_apply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_iv_back:
                finish();
                break;
            case R.id.bar_tv_right:
                apply();
                break;
            case R.id.rb_type_paper:
                llInvoiceNum.setVisibility(View.GONE);
                llInvoiceBank.setVisibility(View.GONE);
                break;
            case R.id.rb_type_ele:
                llInvoiceNum.setVisibility(View.VISIBLE);
                llInvoiceBank.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_apply_apply:
                apply();
                break;
        }
    }
}
