package com.mo.lawyercloud.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.activity.LowyerRegisterActivity;
import com.mo.lawyercloud.activity.ServiceAgreement;
import com.mo.lawyercloud.activity.UserRegisterActivity;
import com.mo.lawyercloud.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by mo on 2018/5/10.
 */

public class RegisterFragment extends BaseFragment {

    @BindView(R.id.cb_sevice_agreement)
    CheckBox mCb;



    @Override
    public int getLayoutId() {
        return R.layout.fg_register;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.btn_user_register, R.id.btn_lowyer_register, R.id.tv_protocol})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_user_register:
                if (mCb.isChecked()){
                    startActivity(new Intent(mContext, UserRegisterActivity.class));
                }else {
                    showTips();
                }
                break;
            case R.id.btn_lowyer_register:
                if (mCb.isChecked()){
                    startActivity(new Intent(mContext, LowyerRegisterActivity.class));
                }else {
                    showTips();
                }
                break;
            case R.id.tv_protocol:
                startActivity(ServiceAgreement.class);
                break;
        }
    }

    private void showTips() {
        new AlertDialog.Builder(mContext).setTitle("温馨提示").setMessage("请勾选注册服务协议")
                .setNeutralButton("确定",null).show();
    }

}
