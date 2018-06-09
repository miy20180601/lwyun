package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author CUI
 * @data 2018/06/08
 * @details 律师我的资料界面
 */
public class MineLwyerActivity extends BaseActivity {
    @BindView(R.id.bar_iv_back)
    ImageView barIvBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_tv_right)
    TextView barTvRight;


    @BindView(R.id.civ_lwyer_avatar)
    CircleImageView civLwyerAvatar;
    @BindView(R.id.tv_lwyer_phone)
    TextView tvLwyerPhone;
    @BindView(R.id.tv_lwyer_nickname)
    TextView tvLwyerNickname;
    @BindView(R.id.tv_lwyer_sex)
    TextView tvLwyerSex;
    @BindView(R.id.tv_lwyer_corporation)
    TextView tvLwyerCorporation;
    @BindView(R.id.tv_lwyer_address)
    TextView tvLwyerAddress;
    @BindView(R.id.tv_lwyer_territory)
    TextView tvLwyerTerritory;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_lwyer;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("我的资料");
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }


    @OnClick({R.id.rl_lwyer_avatar, R.id.rl_lwyer_phone, R.id.rl_lwyer_nickname, R.id.rl_lwyer_sex, R.id.rl_lwyer_corporation, R.id.rl_lwyer_address, R.id.rl_lwyer_synopsis, R.id.tv_lwyer_territory})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_lwyer_avatar:
                break;
            case R.id.rl_lwyer_phone:
                break;
            case R.id.rl_lwyer_nickname:
                break;
            case R.id.rl_lwyer_sex:
                break;
            case R.id.rl_lwyer_corporation:
                break;
            case R.id.rl_lwyer_address:
                break;
            case R.id.rl_lwyer_synopsis:
                break;
            case R.id.tv_lwyer_territory:
                break;
        }
    }
}
