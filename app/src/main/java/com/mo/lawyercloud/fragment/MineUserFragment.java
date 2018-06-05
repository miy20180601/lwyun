package com.mo.lawyercloud.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.activity.MineActivity;
import com.mo.lawyercloud.activity.MineAdvisoryActivity;
import com.mo.lawyercloud.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mohaifeng on 18/5/27.
 */
public class MineUserFragment extends BaseFragment {


    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_balance)
    TextView tvBalance;

    private static MineUserFragment instance = null;
    public static MineUserFragment getInstance() {
        if (instance == null) {
            instance = new MineUserFragment();
        }
        return instance;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fg_mine_user;
    }



    @OnClick({R.id.tv_apply_invoice, R.id.rl_mine_data, R.id.rl_mine_advisory, R.id
            .rl_mine_reservation, R.id.rl_mine_wallet, R.id.rl_mine_contact, R.id
            .rl_apply_invoice, R.id.rl_promotion})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_apply_invoice:

                break;
            case R.id.rl_mine_data:
                startActivity(new Intent(mContext, MineActivity.class));
                break;
            case R.id.rl_mine_advisory:
                startActivity(new Intent(mContext, MineAdvisoryActivity.class));
                break;
            case R.id.rl_mine_reservation:

                break;
            case R.id.rl_mine_wallet:

                break;
            case R.id.rl_mine_contact:

                break;
            case R.id.rl_apply_invoice:

                break;
            case R.id.rl_promotion:

                break;
        }
    }
}
