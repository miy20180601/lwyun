package com.mo.lawyercloud.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.base.Constant;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.PayResultBean;
import com.mo.lawyercloud.beans.apiBeans.WechatOrderBean;
import com.mo.lawyercloud.eventbus.HomeClickMessage;
import com.mo.lawyercloud.eventbus.PayResultMessage;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.NToast;
import com.mo.lawyercloud.utils.TimeUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * 充值
 */
public class RechargeActivity extends BaseActivity {

    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.edit_amount)
    EditText mEditAmount;
    @BindView(R.id.iv_recharge_wechat)
    ImageView ivRechargeWechat;
    @BindView(R.id.iv_recharge_alipay)
    ImageView ivRechargeAlipay;

    private int mAmount = 0;
    private WechatOrderBean mWechatOrderBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        barTitle.setText("充值");
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {
        mEditAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (TextUtils.isEmpty(str)){
                    mAmount = 0;
                }else {
                    mAmount = Integer.parseInt(str);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PayResultMessage msg) {
        patymentResults();
    }

    private void patymentResults() {
        Observable<BaseEntity<PayResultBean>> observable = RetrofitFactory
                .getInstance().paymentResults(mWechatOrderBean.getId());
        observable.compose(this.<BaseEntity<PayResultBean>>rxSchedulers()).subscribe(new BaseObserver<PayResultBean>() {
            @Override
            protected void onHandleSuccess(PayResultBean payResultBean, String msg) {
                if (payResultBean.isPayResult()){
                    startActivity(new Intent(mContext,RechargeResultActivity.class).putExtra
                            (Constant.RECHARGE_RESULT,1));
                }else {
                    startActivity(new Intent(mContext,RechargeResultActivity.class).putExtra
                            (Constant.RECHARGE_RESULT,2));
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @OnClick({R.id.bar_iv_back, R.id.rl_recharge_alipay, R.id.bt_recharge_apply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_iv_back:
                finish();
                break;
            case R.id.bt_recharge_apply:
                if (mAmount==0){
                    NToast.shortToast(mContext,"请输入大于0的金额");
                    return;
                }
                rechargeWechat();
                break;
        }
    }


    private void rechargeWechat() {
        Map<String,Object> params = new HashMap<>();
        params.put("amount",mAmount);
        Gson gson=new Gson();
        String strEntity = gson.toJson(params);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);

        Observable<BaseEntity<WechatOrderBean>> observable = RetrofitFactory
                .getInstance().rechargeWechat(body);
        observable.compose(this.<BaseEntity<WechatOrderBean>>rxSchedulers()).subscribe(new BaseObserver<WechatOrderBean>() {
                    @Override
                    protected void onHandleSuccess(WechatOrderBean wechatOrderBean, String msg) {
                        mWechatOrderBean = wechatOrderBean;
                        weixinPay(wechatOrderBean.getResponse());
                    }
                });
    }

    /**
     * 调用微信支付
     * @param payModel
     */
    private void weixinPay(WechatOrderBean.ResponseBean payModel) {
        IWXAPI wxapi = WXAPIFactory.createWXAPI(mContext, null);
        wxapi.registerApp(Constant.WX_APP_ID);
        //调用微信支付
        PayReq payReq = new PayReq();
        payReq.appId = Constant.WX_APP_ID;
        payReq.partnerId = payModel.getMchId();
        payReq.prepayId = payModel.getPrepayId();
        payReq.nonceStr =payModel.getNonceStr();
        payReq.timeStamp = payModel.getTimestamp();
        payReq.packageValue = payModel.getPackages();
        payReq.sign = payModel.getSign();
        wxapi.sendReq(payReq);
    }
}
