package com.mo.lawyercloud.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.activity.LowyerRegisterActivity;
import com.mo.lawyercloud.activity.UserRegisterActivity;
import com.mo.lawyercloud.base.BaseFragment;
import com.mo.lawyercloud.utils.NToast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by mo on 2018/5/10.
 */

public class RegisterFragment extends BaseFragment {
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
                startActivity(new Intent(mContext, UserRegisterActivity.class));
                break;
            case R.id.btn_lowyer_register:
                startActivity(new Intent(mContext, LowyerRegisterActivity.class));
                break;
            case R.id.tv_protocol:
                NToast.shortToast(mContext,"注册协议");
                break;
        }
    }
}
