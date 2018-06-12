package com.mo.lawyercloud.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.activity.MyContactAcitity;
import com.mo.lawyercloud.activity.MyInvoiceActivity;
import com.mo.lawyercloud.activity.MyReserveOrderActivity;
import com.mo.lawyercloud.activity.MyUserActivity;
import com.mo.lawyercloud.activity.MyAdvisoryActivity;
import com.mo.lawyercloud.activity.MyWalletActivity;
import com.mo.lawyercloud.activity.RequestNoticeActivity;
import com.mo.lawyercloud.base.BaseFragment;
import com.mo.lawyercloud.base.Constant;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.MemberBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.NToast;
import com.mo.lawyercloud.utils.SPUtil;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;

/**
 * Created by Mohaifeng on 18/5/27.
 * 我的页面-普通用户
 */
public class MineUserFragment extends BaseFragment {


    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_balance)
    TextView tvBalance;

    private MemberBean mMember;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMember = (MemberBean) mACache.getAsObject(Constant.MEMBER_INFO);
        if (mMember != null){
            intoImg(mMember.getAvatar(),ivAvatar);
            tvUserName.setText(mMember.getRealName() == null? "":mMember.getRealName());
            tvBalance.setText("¥ "+mMember.getBalance());
        }
    }

    public void intoImg(String imgUrl,ImageView view){
        RequestOptions options = new RequestOptions();
        options.error(R.mipmap.data_button_avatar_n);
        Glide.with(mContext).load(imgUrl).apply(options).into(view);
    }

    @OnClick({R.id.tv_apply_invoice, R.id.rl_mine_data, R.id.rl_mine_advisory, R.id
            .rl_mine_reservation, R.id.rl_mine_wallet, R.id.rl_mine_contact, R.id
            .rl_apply_invoice, R.id.rl_promotion,R.id.tv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_mine_data:
                startActivity(new Intent(mContext, MyUserActivity.class));
                break;
            case R.id.rl_mine_advisory:
                startActivity(new Intent(mContext, MyAdvisoryActivity.class));
                break;
            case R.id.rl_mine_reservation:
                // TODO: 18/6/10 我的预约
                startActivity(MyReserveOrderActivity.class);
                break;
            case R.id.rl_mine_wallet:
                // TODO: 18/6/10 我的钱包
                startActivity(MyWalletActivity.class);
                break;
            case R.id.rl_mine_contact:
                // TODO: 18/6/10 我的客服
                startActivity(MyContactAcitity.class);
                break;
            case R.id.tv_apply_invoice:
            case R.id.rl_apply_invoice:
                // TODO: 18/6/10 发票申请
                startActivity(MyInvoiceActivity.class);
                break;
            case R.id.rl_promotion:
                // TODO: 18/6/10 推广图

                break;
            case R.id.tv_logout:
                // TODO: 18/6/10 登出
                logout();
                break;
        }
    }

    private void logout() {
        Observable<BaseEntity<Object>> observable = RetrofitFactory.getInstance().logout();
        observable.compose(this.<BaseEntity<Object>>rxSchedulers()).subscribe(new BaseObserver<Object>() {
            @Override
            protected void onHandleSuccess(Object o, String msg) {
                NToast.shortToast(mContext,msg);
                SPUtil.remove(mContext, Constant.PHONE);
                SPUtil.remove(mContext, Constant.PASSWORD);
                SPUtil.remove(mContext,Constant.TXSIG);
                mACache.remove(Constant.MEMBER_INFO);
            }
        });
    }


}
