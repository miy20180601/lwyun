package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.NToast;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * 充值
 */
public class WithdrawActivity extends BaseActivity {

    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.edit_amount)
    EditText mEditAmount;

    private int mAmount = 0; //金额


    @Override
    public int getLayoutId() {
        return R.layout.activity_withdraw;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        barTitle.setText("提现");
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


    @OnClick({R.id.bar_iv_back,R.id.bt_recharge_apply})
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
                wuthdrawal();
                break;
        }
    }

    private void wuthdrawal() {
        Map<String,Object> params = new HashMap<>();
        params.put("price",mAmount);
        Gson gson=new Gson();
        String strEntity = gson.toJson(params);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);

        Observable<BaseEntity<Object>> observable = RetrofitFactory.getInstance().withdrawal(body);
        observable.compose(this.<BaseEntity<Object>>rxSchedulers()).subscribe(new BaseObserver<Object>() {

            @Override
            protected void onHandleSuccess(Object o, String msg) {
                NToast.shortToast(mContext,msg);
            }
        });
    }


}
