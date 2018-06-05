package com.mo.lawyercloud.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.activity.ForgetPasswordActivity;
import com.mo.lawyercloud.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by mo on 2018/5/10.
 */

public class LoginFragment extends BaseFragment {
    @BindView(R.id.edit_phone)
    EditText mEditPhone;
    @BindView(R.id.edit_password)
    EditText mEditPassword;


    @Override
    public int getLayoutId() {
        return R.layout.fg_login;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @OnClick({R.id.tv_forget_password, R.id.btn_login, R.id.tv_protocol})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_password:
                startActivity(new Intent(mContext, ForgetPasswordActivity.class));
                break;
            case R.id.btn_login:
                break;
            case R.id.tv_protocol:
                break;
        }
    }
}
