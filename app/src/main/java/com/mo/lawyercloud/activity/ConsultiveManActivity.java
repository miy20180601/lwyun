package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.ConsultiveAdapter;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.OrderAdvisoryBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.NToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * 咨询管理
 */
public class ConsultiveManActivity extends BaseActivity {
    @BindView(R.id.bar_iv_back)
    ImageView barIvBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_tv_right)
    TextView barTvRight;
    @BindView(R.id.rv_con_data)
    RecyclerView rvConData;
    ConsultiveAdapter consultiveAdapter;
    List<OrderAdvisoryBean.ResultBean> dataList = new ArrayList<>();
    int pageNo = 1;
    int pageSize = 10;

    @Override
    public int getLayoutId() {
        return R.layout.activity_consultive_man;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("咨询管理");
        barTvRight.setVisibility(View.VISIBLE);
        barTvRight.setText("刷新");
        barTvRight.setTextColor(getResources().getColor(R.color.green_color));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        rvConData.setLayoutManager(linearLayoutManager);
        consultiveAdapter = new ConsultiveAdapter(dataList);
        rvConData.setAdapter(consultiveAdapter);
    }

    @Override
    public void initData() {
        getOrderAdvisory();
    }

    @Override
    public void onEvent() {

    }

    public void getOrderAdvisory() {

        Observable<BaseEntity<OrderAdvisoryBean>> observable = RetrofitFactory.getInstance().getOrderAdvisory(pageNo, pageSize);
        observable.compose(this.<BaseEntity<OrderAdvisoryBean>>rxSchedulers()).subscribe(new BaseObserver<OrderAdvisoryBean>() {
            @Override
            protected void onHandleSuccess(OrderAdvisoryBean registerResult, String msg) {
                List<OrderAdvisoryBean.ResultBean> result = registerResult.getResult();
                dataList.clear();
                if (result != null) {
                    dataList.addAll(result);
                }
                consultiveAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onHandleError(int statusCode, String msg) {
                NToast.shortToast(mContext, msg);
            }
        });
    }


    @OnClick({R.id.bar_iv_back, R.id.bar_tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_iv_back:
                finish();
                break;
            case R.id.bar_tv_right:
                pageNo=1;
                getOrderAdvisory();
                break;
        }
    }
}
