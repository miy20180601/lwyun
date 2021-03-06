package com.mo.lawyercloud.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.activity.ForgetPasswordActivity;
import com.mo.lawyercloud.activity.MainActivity;
import com.mo.lawyercloud.base.BaseFragment;
import com.mo.lawyercloud.base.Constant;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.MemberBean;
import com.mo.lawyercloud.beans.apiBeans.RegisterResult;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.ACache;
import com.mo.lawyercloud.utils.MD5util;
import com.mo.lawyercloud.utils.NToast;
import com.mo.lawyercloud.utils.SPUtil;
import com.orhanobut.logger.Logger;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.ILiveSDK;
import com.tencent.ilivesdk.core.ILiveLoginManager;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Created by mo on 2018/5/10.
 */

public class LoginFragment extends BaseFragment {
    @BindView(R.id.edit_phone)
    EditText mEditPhone;
    @BindView(R.id.edit_password)
    EditText mEditPassword;

    private String mPhone,mPwd;
    private ACache mACache;

    @Override
    public int getLayoutId() {
        return R.layout.fg_login;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mACache = ACache.get(mContext);
        onEvent();
    }

    private void onEvent() {
        mEditPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPhone = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditPassword.addTextChangedListener(new TextWatcher() {
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
    }


    @OnClick({R.id.tv_forget_password, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_password:
                startActivity(new Intent(mContext, ForgetPasswordActivity.class));
                break;
            case R.id.btn_login:
                if (TextUtils.isEmpty(mPhone)){
                    NToast.shortToast(mContext,"请输入手机号码");
                    return;
                }
                if (TextUtils.isEmpty(mPwd)||mPwd.length()<6){
                    NToast.shortToast(mContext,"请输入完整密码");
                    return;
                }
                login();
                break;
        }
    }

    private void login() {
        Map<String, String> params = new HashMap<>();
        params.put("username", mPhone);
        params.put("password", MD5util.getMd5Value(mPwd));
        Gson gson=new Gson();
        String strEntity = gson.toJson(params);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        Observable<BaseEntity<RegisterResult>> observable = RetrofitFactory.getInstance().login(body);
        observable.compose(this.<BaseEntity<RegisterResult>>rxSchedulers()).subscribe(new BaseObserver<RegisterResult>() {
            @Override
            protected void onHandleSuccess(RegisterResult registerResult, String msg) {
                NToast.shortToast(mContext,"登录成功");
                txLogin(registerResult.getTxSig());
                SPUtil.put(mContext,Constant.PHONE,mPhone);
                SPUtil.put(mContext,Constant.PASSWORD,MD5util.getMd5Value(mPwd));
                SPUtil.put(mContext,Constant.TXSIG,registerResult.getTxSig());
                startActivity(new Intent(mContext, MainActivity.class).putExtra(Constant.ISLOGIN, true));
            }

            @Override
            protected void onHandleError(int statusCode, String msg) {
                NToast.shortToast(mContext,msg);
            }
        });
    }

    /*腾讯视频登录*/
    private void txLogin(String txSig) {
        ILiveLoginManager.getInstance().iLiveLogin(mPhone, txSig, new
                ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                Logger.d("tx登录成功");
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
            }
        });
    }


}
