package com.mo.lawyercloud.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.RegisterResult;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.NToast;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by mo on 2018/5/14.
 */

public class ForgetPasswordActivity extends BaseActivity {

    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.edit_phone)
    EditText mEditPhone;
    @BindView(R.id.edit_code)
    EditText mEditCode;
    @BindView(R.id.tv_get_code)
    TextView mTvGetCode;

    private String mPhone,mCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_foget_password;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mBarTitle.setText("忘记密码");
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

        mEditPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPhone = s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCode = s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            mTvGetCode.setText((millisUntilFinished / 1000) + "s");
        }

        @Override
        public void onFinish() {
            mTvGetCode.setEnabled(true);
            mTvGetCode.setText("重新获取");
        }
    };

    /*获取验证码*/
    private void getMsmCode() {
        Observable<BaseEntity<RegisterResult>> observable = RetrofitFactory.getInstance().getMsmCode
                (mPhone);
        observable.compose(this.<BaseEntity<RegisterResult>>rxSchedulers()).subscribe(new BaseObserver<RegisterResult>() {
            @Override
            protected void onHandleSuccess(RegisterResult s, String msg) {
                NToast.shortToast(mContext,msg);
            }
        });
    }

    @OnClick({R.id.bar_iv_back, R.id.tv_get_code,R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_iv_back:
                finish();
                break;
            case R.id.tv_get_code:
                if (TextUtils.isEmpty(mPhone)){
                    NToast.shortToast(mContext,"请输入手机号");
                    return;
                }
                timer.start();
                mTvGetCode.setEnabled(false);
                getMsmCode();
                break;
            case R.id.btn_register:
                if (TextUtils.isEmpty(mPhone)){
                    NToast.shortToast(mContext,"请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(mCode)){
                    NToast.shortToast(mContext,"请输入短信验证码");
                    return;
                }
                startActivity(new Intent(mContext,FogetPasswordNextActivity.class).putExtra("phone",
                        mPhone).putExtra("code",mCode));
                break;
        }
    }
}
