package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
                break;
        }
    }
}
