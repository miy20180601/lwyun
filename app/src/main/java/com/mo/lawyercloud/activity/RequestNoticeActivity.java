package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.NoticeDataAdapter;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.OrderListBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.NToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * 预约通知
 */
public class RequestNoticeActivity extends BaseActivity {
    @BindView(R.id.bar_iv_back)
    ImageView barIvBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_tv_right)
    TextView barTvRight;
    @BindView(R.id.rv_notice_data)
    RecyclerView rvNoticeData;
    List<OrderListBean.ResultBean> dataList = new ArrayList<>();
    NoticeDataAdapter noticeDataAdapter;
    int pageNo=1;
    int pageSize=10;
    int status=0;
    String type ;//1为用户2位律师
    @Override
    public int getLayoutId() {
        return R.layout.activity_request_notice;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        type=(String) savedInstanceState.get("type");
        if(type.trim().equals("1")){
            barTitle.setText("我的预约");
        }else{
            barTitle.setText("预约通知");
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        noticeDataAdapter = new NoticeDataAdapter(dataList);
        rvNoticeData.setLayoutManager(linearLayoutManager);
        rvNoticeData.setAdapter(noticeDataAdapter);
    }

    @Override
    public void initData() {
        getOrderList();
    }

    @Override
    public void onEvent() {

    }
    public void getOrderList(){

        Observable<BaseEntity<OrderListBean>> observable = RetrofitFactory.getInstance().getOrderList(pageNo,pageSize,status);
        observable.compose(this.<BaseEntity<OrderListBean>>rxSchedulers()).subscribe(new BaseObserver<OrderListBean>() {
            @Override
            protected void onHandleSuccess(OrderListBean registerResult, String msg) {
                List<OrderListBean.ResultBean> result = registerResult.getResult();
                dataList.clear();
                if(result!=null){
                    dataList.addAll(result);
                }
                noticeDataAdapter.notifyDataSetChanged();

            }

            @Override
            protected void onHandleError(int statusCode, String msg) {
                NToast.shortToast(mContext,msg);
            }
        });
    }

}
