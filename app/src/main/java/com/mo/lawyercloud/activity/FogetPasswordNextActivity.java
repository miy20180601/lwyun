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
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.MD5util;
import com.mo.lawyercloud.utils.NToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Created by mo on 2018/5/14.
 */

public class FogetPasswordNextActivity extends BaseActivity {
    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.edit_new_password)
    EditText mEditNewPassword;
    @BindView(R.id.edit_repeat)
    EditText mEditRepeat;
    private String mPhone,mCode,mNewPwd,mRenewPwd;


    @Override
    public int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mBarTitle.setText("忘记密码");
        mPhone = getIntent().getStringExtra("phone");
        mCode = getIntent().getStringExtra("code");
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {
        mEditNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNewPwd = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditRepeat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRenewPwd = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @OnClick({R.id.bar_iv_back, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_iv_back:
                finish();
                break;
            case R.id.btn_register:
                if (TextUtils.isEmpty(mNewPwd)||TextUtils.isEmpty(mRenewPwd)){
                    NToast.shortToast(mContext,"输入密码不能为空");
                    return;
                }
                if (!mNewPwd.equals(mRenewPwd)){
                    NToast.shortToast(mContext,"两次输入的密码不一致");
                    return;
                }
                Map<String, String> params = new HashMap<>();
                params.put("username",  mPhone);
                params.put("password", MD5util.getMd5Value(mNewPwd));
                params.put("mobileCode", mCode);
                Gson gson=new Gson();
                String strEntity = gson.toJson(params);
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
                Observable<BaseEntity<Object>> observable = RetrofitFactory.getInstance().forgetPassword(body);
                observable.compose(this.<BaseEntity<Object>>rxSchedulers()).subscribe(new BaseObserver<Object>() {
                    @Override
                    protected void onHandleSuccess(Object o, String msg) {
                        NToast.shortToast(mContext,msg);
                        startActivity(LoginActivity.class);
                    }
                });
                break;
        }
    }


}
