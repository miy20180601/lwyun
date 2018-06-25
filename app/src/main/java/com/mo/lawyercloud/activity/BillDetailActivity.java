package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.BillDetailBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by mo on 2018/6/21.
 */

public class BillDetailActivity extends BaseActivity {

    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.tv_income)
    TextView mTvIncome;
    @BindView(R.id.tv_pay_type)
    TextView mTvPayType;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.ll_recharge)
    LinearLayout mLlRecharge;
    @BindView(R.id.tv_lowyer_name)
    TextView mTvLowyerName;
    @BindView(R.id.tv_call_time)
    TextView mTvCallTime;
    @BindView(R.id.tv_expenditure_time)
    TextView mTvExpenditureTime;
    @BindView(R.id.tv_expenditure)
    TextView mTvExpenditure;
    @BindView(R.id.ll_expenditure_detail)
    LinearLayout mLlExpenditureDetail;

    private int mType; // 1为充值 2为支出
    private int mId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bill_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mId = getIntent().getIntExtra("id", 0);
        mType = getIntent().getIntExtra("type", 1);
        if (mType == 1){
            mBarTitle.setText("充值记录");
            mLlRecharge.setVisibility(View.VISIBLE);
        }else if (mType == 2){
            mBarTitle.setText("账单详情");
            mLlExpenditureDetail.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initData() {
        Observable<BaseEntity<BillDetailBean>> observable = RetrofitFactory.getInstance
                ().billRecordDetail(mId);
        observable.compose(this.<BaseEntity<BillDetailBean>>rxSchedulers()).subscribe(new BaseObserver<BillDetailBean>() {
            @Override
            protected void onHandleSuccess(BillDetailBean billDetailBean, String msg) {
                if (mType == 1){
                    mTvIncome.setText(billDetailBean.getPrice()+"");
                    if (billDetailBean.getPayType()==1){
                        mTvPayType.setText("支付宝");
                    }else if (billDetailBean.getPayType() ==2 ){
                        mTvPayType.setText("微信");
                    }
                    mTvTime.setText(TimeUtils.dateFormatByType(billDetailBean.getTime(),
                            "yyyy-MM-dd HH:mm:ss"));
                }else if (mType == 2){
                    mTvLowyerName.setText(billDetailBean.getUsername());
                    String durationTime = null;
                    long duration = billDetailBean.getDuration();
                    int m = (int) (duration/1000/60);
                    int s = (int) (duration / 1000 % 60);
                    durationTime = m+"\""+s+"\'";
                    mTvCallTime.setText(durationTime);
                    mTvExpenditureTime.setText(TimeUtils.dateFormatByType(billDetailBean.getTime(),
                            "yyyy-MM-dd HH:mm:ss"));
                    mTvExpenditure.setText(billDetailBean.getPrice()+"");
                }
            }
        });
    }

    @Override
    public void onEvent() {

    }

    @OnClick(R.id.bar_iv_back)
    public void onViewClicked() {
        finish();
    }
}
