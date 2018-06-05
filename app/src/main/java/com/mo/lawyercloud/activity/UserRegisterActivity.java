package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.RegisterResult;
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
 * Created by mo on 2018/5/10.
 */

public class UserRegisterActivity extends BaseActivity {

    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.cb_pwd_eye)
    CheckBox mCbEye;
    @BindView(R.id.edit_code)
    EditText editCode;


    private String mPhone,mPwd,mCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_register;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("客户注册");
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {
        editPhone.addTextChangedListener(new TextWatcher() {
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
        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPwd = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCode = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCbEye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //选中显示密码
                    editPassword.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());
                } else {
                    //隐藏密码
                    editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                //把光标设置在文字结尾
                editPassword.setSelection(editPassword.getText().length());
            }
        });

    }

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            tvGetCode.setText((millisUntilFinished / 1000) + "s");
        }

        @Override
        public void onFinish() {
            tvGetCode.setEnabled(true);
            tvGetCode.setText("重新获取");
        }
    };


    @OnClick({R.id.bar_iv_back, R.id.tv_get_code, R.id.btn_register})
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
                tvGetCode.setEnabled(false);
                getMsmCode();
                break;
            case R.id.btn_register:
                if (TextUtils.isEmpty(mPhone)){
                    NToast.shortToast(mContext,"请输入手机号");
                    return;
                }

                if (TextUtils.isEmpty(mPwd)||mPwd.length()<6){
                    NToast.shortToast(mContext,"密码不能少于6位");
                    return;
                }
                if (TextUtils.isEmpty(mCode)){
                    NToast.shortToast(mContext,"请输入短信验证码");
                    return;
                }
                register();

                break;
        }
    }

    private void register() {
        Map<String, String> params = new HashMap<>();
        params.put("username", mPhone);
        params.put("password", MD5util.getMd5Value(mPwd));
        params.put("realName", mPhone);
        params.put("type", "1");
        params.put("mobileCode", mCode);
        Gson gson=new Gson();
        String strEntity = gson.toJson(params);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        Observable<BaseEntity<String>> observable = RetrofitFactory.getInstance().register(body);
        observable.compose(this.<BaseEntity<String>>rxSchedulers()).subscribe(new BaseObserver<String>() {
            @Override
            protected void onHandleSuccess(String s, String msg) {
                finish();
            }
        });
    }

    /*获取验证码*/
    private void getMsmCode() {
        Observable<BaseEntity<RegisterResult>> observable = RetrofitFactory.getInstance().getMsmCode
        (mPhone);
        observable.compose(this.<BaseEntity<RegisterResult>>rxSchedulers()).subscribe(new BaseObserver<RegisterResult>() {
            @Override
            protected void onHandleSuccess(RegisterResult s, String msg) {

            }
        });
    }
}
