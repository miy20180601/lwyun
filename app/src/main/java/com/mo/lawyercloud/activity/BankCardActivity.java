package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.BankCardInfo;
import com.mo.lawyercloud.beans.apiBeans.FeeDescriptionlBean;
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
 * Created by mo on 2018/7/2.
 */

public class BankCardActivity extends BaseActivity {

    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.edit_account)
    EditText mEditAccount;
    @BindView(R.id.edit_card_number)
    EditText mEditCardNumber;
    @BindView(R.id.edit_bank)
    EditText mEditBank;

    private String mAccount,mCardNumber,mBank;

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
        bankCardInfo();
    }

    private void bankCardInfo() {
        Observable<BaseEntity<BankCardInfo>> observable = RetrofitFactory.getInstance().bankCardInfo();
        observable.compose(this.<BaseEntity<BankCardInfo>>rxSchedulers()).subscribe(new BaseObserver<BankCardInfo>() {
            @Override
            protected void onHandleSuccess(BankCardInfo bankCardInfo, String msg) {
                if (bankCardInfo!=null){
                    mEditAccount.setText(bankCardInfo.getAccount());
                    mEditCardNumber.setText(bankCardInfo.getCardNumber());
                    mEditBank.setText(bankCardInfo.getBank());
                }
            }
        });
    }

    @Override
    public void onEvent() {
        mEditAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAccount = s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCardNumber=s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditBank.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBank = s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.bar_iv_back, R.id.bt_recharge_apply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_iv_back:
                finish();
                break;
            case R.id.bt_recharge_apply:
                if (TextUtils.isEmpty(mAccount)){
                    NToast.shortToast(mContext,"请输入持卡人姓名");
                    return;
                }
                if (TextUtils.isEmpty(mCardNumber)){
                    NToast.shortToast(mContext,"请输入卡号");
                    return;
                }
                if (TextUtils.isEmpty(mBank)){
                    NToast.shortToast(mContext,"请输入开户银行");
                    return;
                }
                modifyBankCard();
                break;
        }
    }

    private void modifyBankCard() {
        Map<String, Object> params = new HashMap<>();
        params.put("cardNumber", mCardNumber); //卡号
        params.put("account",mAccount);//持卡人姓名
        params.put("bank",mBank);//开户银行
        Gson gson = new Gson();
        String strEntity = gson.toJson(params);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;" +
                "charset=UTF-8"), strEntity);
        Observable<BaseEntity<Object>> observable = RetrofitFactory.getInstance().modifyBankCard(body);
        observable.compose(this.<BaseEntity<Object>>rxSchedulers()).subscribe(new BaseObserver<Object>() {
            @Override
            protected void onHandleSuccess(Object o, String msg) {
                NToast.shortToast(mContext,msg);
                finish();
            }
        });
    }
}
