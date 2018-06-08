package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author CUI
 * @data 2018/06/08
 * @details 视频预约界面
 */
public class VideoAppointmentAcitivty extends BaseActivity {
    @BindView(R.id.bar_iv_back)
    ImageView barIvBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_tv_right)
    TextView barTvRight;
    @BindView(R.id.et_appointment_conetnt)
    EditText etAppointmentConetnt;
    @BindView(R.id.iv_appointment_accessory)
    ImageView ivAppointmentAccessory;
    @BindView(R.id.rv_appointment_time)
    RecyclerView rvAppointmentTime;
    @BindView(R.id.bt_appointment_submit)
    Button btAppointmentSubmit;
    @BindView(R.id.rl_appointment_succeed)
    RelativeLayout rl_appointment_succeed;
    @Override
    public int getLayoutId() {
        return R.layout.activity_video_appointment_acitivty;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("视频预约");
        rl_appointment_succeed.setVisibility(View.GONE);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }



    @OnClick({R.id.iv_appointment_accessory, R.id.bt_appointment_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_appointment_accessory:

                break;
            case R.id.bt_appointment_submit:

                break;
        }
    }
}
