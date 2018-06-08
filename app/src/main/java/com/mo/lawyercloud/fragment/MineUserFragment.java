package com.mo.lawyercloud.fragment;

import android.content.Intent;
import android.os.Bundle;
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
 */
public class MineUserFragment extends BaseFragment {


    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_balance)
    TextView tvBalance;

    @BindView(R.id.il_user_layout)
    LinearLayout il_user_layout;
    @BindView(R.id.il_lwyer_layout)
    LinearLayout il_lwyer_layout;
    private static MineUserFragment instance = null;
    @BindView(R.id.iv_lwyer_avatar)
    CircleImageView ivLwyerAvatar;
    @BindView(R.id.tv_lwyer_user_name)
    TextView tvLwyerUserName;
    Unbinder unbinder;

    private boolean isType=false;//true 为一般用户界面 F为律师界面

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


    @Override
    public void onResume() {
        super.onResume();
        if(isType){
            il_user_layout.setVisibility(View.VISIBLE);
            il_lwyer_layout.setVisibility(View.GONE);
        }else{
            il_user_layout.setVisibility(View.GONE);
            il_lwyer_layout.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.tv_apply_invoice, R.id.rl_mine_data, R.id.rl_mine_advisory, R.id
            .rl_mine_reservation, R.id.rl_mine_wallet, R.id.rl_mine_contact, R.id
            .rl_apply_invoice, R.id.rl_promotion,R.id.iv_lwyer_avatar
            , R.id.rl_mine_lwyer_data, R.id.rl_mine_lwyer_notification
            , R.id.rl_mine_lwyer_time, R.id.rl_mine_lwyer_wallet
            , R.id.rl_mine_lwyer_advisory, R.id.rl_mine_lwyer_change_password
            , R.id.rl_mine_lwyer_feedback, R.id.rl_mine_lwyer_contact
            , R.id.rl_mine_lwyer_promotion
            , R.id.tv_mine_lwyer_out})
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
            case R.id.iv_lwyer_avatar:   //律师界面的控件
                break;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
