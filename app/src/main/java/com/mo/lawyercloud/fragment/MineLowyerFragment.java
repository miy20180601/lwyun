package com.mo.lawyercloud.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.activity.ChangePwdActivity;
import com.mo.lawyercloud.activity.FeedbackActivity;
import com.mo.lawyercloud.activity.MyAdvisoryActivity;
import com.mo.lawyercloud.activity.MyContactAcitity;
import com.mo.lawyercloud.activity.MyLwyerActivity;
import com.mo.lawyercloud.activity.MyLwyerTimeActivity;
import com.mo.lawyercloud.activity.MyReserveOrderActivity;
import com.mo.lawyercloud.activity.MyWalletActivity;
import com.mo.lawyercloud.activity.PromotionImgActivity;
import com.mo.lawyercloud.base.BaseFragment;
import com.mo.lawyercloud.base.Constant;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.MemberBean;
import com.mo.lawyercloud.eventbus.HomeClickMessage;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.NToast;
import com.mo.lawyercloud.utils.SPUtil;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;

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
    private MemberBean mMember;


    public static MineLowyerFragment getInstance() {
        if (instance == null) {
            instance = new MineLowyerFragment();
        }
        return instance;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMember = (MemberBean) mACache.getAsObject(Constant.MEMBER_INFO);
        if (mMember != null){
            intoImg(mMember.getAvatar(),ivLwyerAvatar);
            tvLwyerUserName.setText(mMember.getRealName() == null? "":mMember.getRealName());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected void getMemberInfo() {
        Observable<BaseEntity<MemberBean>> observable = RetrofitFactory.getInstance()
                .getUserInfo();
        observable.compose(this.<BaseEntity<MemberBean>>rxSchedulers()).subscribe(new BaseObserver<MemberBean>() {

            @Override
            protected void onHandleSuccess(MemberBean memberBean, String msg) {
                intoImg(memberBean.getAvatar(),ivLwyerAvatar);
                tvLwyerUserName.setText(memberBean.getRealName() == null? "":memberBean.getRealName());
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fg_mine_lowyer;
    }

    public void intoImg(String imgUrl,ImageView view){
        RequestOptions options = new RequestOptions();
        options.error(R.mipmap.data_button_avatar_n);
        Glide.with(mContext).load(imgUrl).apply(options).into(view);
    }


    @OnClick({R.id.rl_mine_lwyer_data, R.id.rl_mine_lwyer_notification, R.id.rl_mine_lwyer_time,
            R.id.rl_mine_lwyer_wallet, R.id.rl_mine_lwyer_advisory, R.id
            .rl_mine_lwyer_change_password, R.id.rl_mine_lwyer_feedback, R.id
            .rl_mine_lwyer_contact, R.id.rl_mine_lwyer_promotion, R.id.tv_mine_lwyer_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_mine_lwyer_data:
                startActivity(MyLwyerActivity.class);
                break;
            case R.id.rl_mine_lwyer_notification:
                startActivity(new Intent(mContext,MyReserveOrderActivity.class).putExtra("type",
                        2));
                break;
            case R.id.rl_mine_lwyer_time:
                startActivity(MyLwyerTimeActivity.class);
                break;
            case R.id.rl_mine_lwyer_wallet:
                startActivity(new Intent(mContext,MyWalletActivity.class).putExtra("type",2));
                break;
            case R.id.rl_mine_lwyer_advisory:
                startActivity(new Intent(mContext, MyAdvisoryActivity.class).putExtra("type",2));
                break;
            case R.id.rl_mine_lwyer_change_password:
                startActivity(ChangePwdActivity.class);
                break;
            case R.id.rl_mine_lwyer_feedback:
                startActivity(FeedbackActivity.class);
                break;
            case R.id.rl_mine_lwyer_contact:
                startActivity(MyContactAcitity.class);
                break;
            case R.id.rl_mine_lwyer_promotion:
                startActivity(PromotionImgActivity.class);
                break;
            case R.id.tv_mine_lwyer_out:
                iLiveLogout();
                break;
        }
    }

    /**
     * 退出imsdk <p> 退出成功会调用退出AVSDK
     */
    public void iLiveLogout() {
        //TODO 新方式登出ILiveSDK
        ILiveLoginManager.getInstance().iLiveLogout(new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                //清除本地缓存
                Log.d("iLive", "IMLogout success ");
                logout();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                Log.e("iLive", "IMLogout fail ：" + module + "|" + errCode + " msg " + errMsg);
            }
        });
    }
    public void logout(){
        Observable<BaseEntity<Object>> observable = RetrofitFactory.getInstance().logout();
        observable.compose(this.<BaseEntity<Object>>rxSchedulers()).subscribe(new BaseObserver<Object>() {
            @Override
            protected void onHandleSuccess(Object registerResult, String msg) {
                NToast.shortToast(mContext,msg);
                SPUtil.remove(mContext, Constant.PHONE);
                SPUtil.remove(mContext, Constant.PASSWORD);
                SPUtil.remove(mContext,Constant.TXSIG);
                mACache.remove(Constant.MEMBER_INFO);
                HomeClickMessage homeMsg = new HomeClickMessage();
                homeMsg.type = 2;
                EventBus.getDefault().post(homeMsg);
            }

            @Override
            protected void onHandleError(int statusCode, String msg) {
                NToast.shortToast(mContext,msg);
            }
        });
    }

}
