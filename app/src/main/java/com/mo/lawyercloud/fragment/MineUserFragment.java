package com.mo.lawyercloud.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.activity.MineActivity;
import com.mo.lawyercloud.activity.MineAdvisoryActivity;
import com.mo.lawyercloud.activity.MineLwyerActivity;
import com.mo.lawyercloud.activity.MineLwyerTimeActivity;
import com.mo.lawyercloud.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mohaifeng on 18/5/27.
 * 我的页面
 */
public class MineUserFragment extends BaseFragment {


    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_balance)
    TextView tvBalance;



    private boolean isType=true;//true 为一般用户界面 F为律师界面

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
            .rl_apply_invoice, R.id.rl_promotion,R.id.tv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_apply_invoice:
                // TODO: 18/6/10 发票申请

                break;
            case R.id.rl_mine_data:
                startActivity(new Intent(mContext, MineActivity.class));
                break;
            case R.id.rl_mine_advisory:
                startActivity(new Intent(mContext, MineAdvisoryActivity.class));
                break;
            case R.id.rl_mine_reservation:
                // TODO: 18/6/10 我的预约

                break;
            case R.id.rl_mine_wallet:
                // TODO: 18/6/10 我的钱包

                break;
            case R.id.rl_mine_contact:
                // TODO: 18/6/10 我的客服

                break;
            case R.id.rl_apply_invoice:
                // TODO: 18/6/10 发票申请

                break;
            case R.id.rl_promotion:
                // TODO: 18/6/10 推广图

                break;
            case R.id.tv_logout:
                // TODO: 18/6/10 登出

                break;
        }
    }


}
