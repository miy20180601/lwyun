package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.InvoiceDataAdapter;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.InvoiceListBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.NToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * 我的发票
 */
public class MyInvoiceActivity extends BaseActivity {

    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.rv_invoic_data)
    RecyclerView rvInvoicData;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    int pageNo = 1;
    int pageSize = 10;
    InvoiceDataAdapter mQuickAdapter;
    List<InvoiceListBean.ResultBean> dataList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_invoice;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("我的发票");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        rvInvoicData.setLayoutManager(linearLayoutManager);
        mQuickAdapter = new InvoiceDataAdapter(dataList);
        rvInvoicData.setAdapter(mQuickAdapter);
        mQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter
                .OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                InvoiceListBean.ResultBean resultBean = mQuickAdapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("orderId", resultBean.getId());//订单号
                bundle.putString("orderNo", resultBean.getOrderNo());//订单编号号
                bundle.putString("money", resultBean.getRealPrice() + "");//金额
                startActivity(ApplyInvoiceActivity.class, bundle);
            }
        });
    }

    @Override
    public void initData() {
        getInvoiceList();
    }

    @Override
    public void onEvent() {

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                mQuickAdapter.setEnableLoadMore(false);
                getInvoiceList();
            }
        });
        mQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNo++;
                swipeRefresh.setEnabled(false);
                getInvoiceList();
            }
        },rvInvoicData);

    }


    public void getInvoiceList() {

        Observable<BaseEntity<InvoiceListBean>> observable = RetrofitFactory.getInstance()
                .getInvoiceList(pageNo, pageSize);
        observable.compose(this.<BaseEntity<InvoiceListBean>>rxSchedulers()).subscribe(new BaseObserver<InvoiceListBean>() {
            @Override
            protected void onHandleSuccess(InvoiceListBean registerResult, String msg) {
                loadComplete();
                if (pageNo == 1) {//第一次加载或者是下拉加载
                    mQuickAdapter.setNewData(registerResult.getResult());
                    if (mQuickAdapter.getData().size() >= registerResult.getTotalCount()) {
                        mQuickAdapter.loadMoreEnd();
                    }
                } else {
                    mQuickAdapter.addData(registerResult.getResult());
                    if (mQuickAdapter.getData().size() >= registerResult.getTotalCount()) {
                        mQuickAdapter.loadMoreEnd();
                    } else {
                        mQuickAdapter.loadMoreComplete();
                    }
                }
            }

            @Override
            protected void onHandleError(int statusCode, String msg) {
                loadComplete();
                if (pageNo>1){
                    mQuickAdapter.loadMoreFail();
                }
            }
        });
    }

    /**
     * 关闭上拉加载
     */
    private void loadComplete() {
        if (swipeRefresh != null && swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        if (swipeRefresh != null) swipeRefresh.setEnabled(true);
        mQuickAdapter.setEnableLoadMore(true);
    }

    @OnClick(R.id.bar_iv_back)
    public void onViewClicked() {
        finish();
    }
}
