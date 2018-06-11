package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.base.Constant;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.MD5util;
import com.mo.lawyercloud.utils.NToast;
import com.mo.lawyercloud.utils.SPUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * 更改密码
 */
public class ChangePwdActivity extends BaseActivity {

    @BindView(R.id.bar_iv_back)
    ImageView barIvBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_tv_right)
    TextView barTvRight;
    @BindView(R.id.et_change_oldpwd)
    EditText etChangeOldpwd;
    @BindView(R.id.et_change_newpwd)
    EditText etChangeNewpwd;
    @BindView(R.id.et_change_againpwd)
    EditText etChangeAgainpwd;
    @BindView(R.id.bt_change_submit)
    Button btChangeSubmit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("修改密码");
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }


    @OnClick({R.id.bar_iv_back, R.id.bt_change_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_iv_back:
                break;
            case R.id.bt_change_submit:
                check();
                break;
        }
    }
    public void check(){
        String  password =etChangeOldpwd.getText().toString().trim();
        if(TextUtils.isEmpty(password)){
            NToast.shortToast(mContext,"旧密码不能为空");
            return;
        }
        String  newPassword =etChangeNewpwd.getText().toString().trim();
        if(TextUtils.isEmpty(newPassword)){
            NToast.shortToast(mContext,"新密码不能为空");
            return;

        }
        String  againPwd =etChangeAgainpwd.getText().toString().trim();
        if(!againPwd.equals(newPassword)){
            NToast.shortToast(mContext,"两次密码不一样");
            return;
        }
        changePwd();
    }
    public void changePwd(){
        String  password =etChangeOldpwd.getText().toString().trim();
       final String  newPassword =etChangeNewpwd.getText().toString().trim();
        Map<String, String> params = new HashMap<>();
        params.put("password",  MD5util.getMd5Value(password));
        params.put("newPassword", MD5util.getMd5Value(newPassword));
        Gson gson=new Gson();
        String strEntity = gson.toJson(params);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        Observable<BaseEntity<Object>> observable = RetrofitFactory.getInstance().updatePassword(body);
        observable.compose(this.<BaseEntity<Object>>rxSchedulers()).subscribe(new BaseObserver<Object>() {
            @Override
            protected void onHandleSuccess(Object registerResult, String msg) {
                NToast.shortToast(mContext,"修改密码成功");
                SPUtil.put(mContext,Constant.PASSWORD, MD5util.getMd5Value(newPassword));
                finish();
            }

            @Override
            protected void onHandleError(int statusCode, String msg) {
                NToast.shortToast(mContext,msg);
            }
        });
    }
}
