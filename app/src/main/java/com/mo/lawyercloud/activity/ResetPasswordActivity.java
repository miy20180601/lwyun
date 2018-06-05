package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by mo on 2018/5/14.
 */

public class ResetPasswordActivity extends BaseActivity {
    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.edit_new_password)
    EditText mEditNewPassword;
    @BindView(R.id.edit_repeat)
    EditText mEditRepeat;

    @Override
    public int getLayoutId() {
        return R.layout.activity_reset_password;
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


    @OnClick({R.id.bar_iv_back, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_iv_back:
                finish();
                break;
            case R.id.btn_register:

                break;
        }
    }
}
