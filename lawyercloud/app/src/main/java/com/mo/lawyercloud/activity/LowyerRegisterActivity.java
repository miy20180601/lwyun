package com.mo.lawyercloud.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by mo on 2018/5/11.
 */

public class LowyerRegisterActivity extends BaseActivity {

    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.edit_phone)
    EditText mEditPhone;
    @BindView(R.id.edit_password)
    EditText mEditPassword;
    @BindView(R.id.edit_code)
    EditText mEditCode;
    @BindView(R.id.tv_get_code)
    TextView mTvGetCode;
    @BindView(R.id.edit_name)
    EditText mEditName;
    @BindView(R.id.edit_organization)
    EditText mEditOrganization;
    @BindView(R.id.edit_address)
    EditText mEditAddress;
    @BindView(R.id.edit_professional)
    EditText mEditProfessional;
    @BindView(R.id.edit_resume)
    EditText mEditResume;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_protocol)
    TextView mTvProtocol;

    @Override
    public int getLayoutId() {
        return R.layout.activity_lowyer_register;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }

    @OnClick({R.id.bar_iv_back, R.id.tv_get_code, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_iv_back:
                finish();
                break;
            case R.id.tv_get_code:
                // TODO: 2018/5/14 获取验证码
                break;
            case R.id.btn_register:
                startActivity(new Intent(mContext,LowyerRegisterNextActivity.class));
                break;
        }
    }
}
