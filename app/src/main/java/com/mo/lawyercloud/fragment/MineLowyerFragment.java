package com.mo.lawyercloud.fragment;

import android.view.View;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.activity.MineLwyerActivity;
import com.mo.lawyercloud.activity.MineLwyerTimeActivity;
import com.mo.lawyercloud.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mohaifeng on 18/5/27.
 * 我的页面--律师
 */
public class MineLowyerFragment extends BaseFragment {


    private static MineLowyerFragment instance = null;
    @BindView(R.id.iv_lwyer_avatar)
    CircleImageView ivLwyerAvatar;
    @BindView(R.id.tv_lwyer_user_name)
    TextView tvLwyerUserName;


    public static MineLowyerFragment getInstance() {
        if (instance == null) {
            instance = new MineLowyerFragment();
        }
        return instance;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fg_mine_lowyer;
    }


    @OnClick({R.id.rl_mine_lwyer_data, R.id.rl_mine_lwyer_notification, R.id.rl_mine_lwyer_time,
            R.id.rl_mine_lwyer_wallet, R.id.rl_mine_lwyer_advisory, R.id
            .rl_mine_lwyer_change_password, R.id.rl_mine_lwyer_feedback, R.id
            .rl_mine_lwyer_contact, R.id.rl_mine_lwyer_promotion, R.id.tv_mine_lwyer_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_mine_lwyer_data:
                startActivity(MineLwyerActivity.class);
                break;
            case R.id.rl_mine_lwyer_notification:
                break;
            case R.id.rl_mine_lwyer_time:
                startActivity(MineLwyerTimeActivity.class);
                break;
            case R.id.rl_mine_lwyer_wallet:
                break;
            case R.id.rl_mine_lwyer_advisory:
                break;
            case R.id.rl_mine_lwyer_change_password:
                break;
            case R.id.rl_mine_lwyer_feedback:
                break;
            case R.id.rl_mine_lwyer_contact:
                break;
            case R.id.rl_mine_lwyer_promotion:
                break;
            case R.id.tv_mine_lwyer_out:
                break;
        }
    }
}
