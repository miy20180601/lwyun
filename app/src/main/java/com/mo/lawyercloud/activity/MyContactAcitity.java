package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.base.Constant;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.ContactBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.MD5util;
import com.mo.lawyercloud.utils.NToast;
import com.mo.lawyercloud.utils.SPUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * 我的客服
 */
public class MyContactAcitity extends BaseActivity {

    @BindView(R.id.bar_iv_back)
    ImageView barIvBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_tv_right)
    TextView barTvRight;
    @BindView(R.id.tv_contact_phone)
    TextView tvContactPhone;
    @BindView(R.id.tv_contact_email)
    TextView tvContactEmail;
    @BindView(R.id.tv_contact_wechat)
    TextView tvContactWechat;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_contact_acitity;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("我的客服");

    }

    @Override
    public void initData() {
        getcontact();
    }

    @Override
    public void onEvent() {

    }
    public void getcontact(){

        Observable<BaseEntity<ContactBean>> observable = RetrofitFactory.getInstance().customerService();
        observable.compose(this.<BaseEntity<ContactBean>>rxSchedulers()).subscribe(new BaseObserver<ContactBean>() {
            @Override
            protected void onHandleSuccess(ContactBean registerResult, String msg) {
                tvContactPhone.setText(registerResult.getPhone());
                tvContactEmail.setText(registerResult.getEmail());
                tvContactWechat.setText(registerResult.getWechat());
            }

            @Override
            protected void onHandleError(int statusCode, String msg) {
                NToast.shortToast(mContext,msg);
            }
        });
    }

}
