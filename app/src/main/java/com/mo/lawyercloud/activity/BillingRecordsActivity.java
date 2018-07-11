package com.mo.lawyercloud.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.BillingRecordsQuickAdapter;
import com.mo.lawyercloud.adapter.loadMoreView.CustomLoadMoreView;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.BaseListEntity;
import com.mo.lawyercloud.beans.apiBeans.BillingRecordsBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by Mohaifeng on 18/6/16.
 */
public class BillingRecordsActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.bar_title)
    TextView barTitle;

    private int pageNo = 1;
    private int pageSize = 10;
    private BillingRecordsQuickAdapter mQuickAdapter;
    private List<BillingRecordsBean> mData;


    @Override
    public int getLayoutId() {
        return R.layout.activity_billing_records;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("账单记录");
        mData = new ArrayList<>();
        mQuickAdapter = new BillingRecordsQuickAdapter(mData);
        mQuickAdapter.setLoadMoreView(new CustomLoadMoreView());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(mQuickAdapter);
        mQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                BillingRecordsBean billingRecordsBean = mQuickAdapter.getData().get(position);
                if (billingRecordsBean.getType() !=4){
                    startActivity(new Intent(mContext,BillDetailActivity.class).putExtra("type",billingRecordsBean.getType())
                            .putExtra("id",billingRecordsBean.getId()));
                }

            }
        });
    }

    @Override
    public void initData() {
        loadData();
    }

    private void loadData() {
        Observable<BaseEntity<BaseListEntity<BillingRecordsBean>>> observable =
                RetrofitFactory.getInstance().billRecordList(pageNo, pageSize);
        observable.compose(this.<BaseEntity<BaseListEntity<BillingRecordsBean>>>rxSchedulers())
                .subscribe(new BaseObserver<BaseListEntity<BillingRecordsBean>>() {
                    @Override
                    protected void onHandleSuccess(BaseListEntity<BillingRecordsBean> listEntity, String msg) {
                        mData = listEntity.getResult();
                        Collections.reverse(mData);
                        loadComplete();
                        if (pageNo == 1) {//第一次加载或者是下拉加载
                            mQuickAdapter.setNewData(mData);
                            if (mQuickAdapter.getData().size() >= listEntity.getTotalCount()) {
                                mQuickAdapter.loadMoreEnd();
                            }
                        } else {
                            mQuickAdapter.addData(mData);
                            if (mQuickAdapter.getData().size() >= listEntity.getTotalCount()) {
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

    @Override
    public void onEvent() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                mQuickAdapter.setEnableLoadMore(false);
                loadData();
            }
        });
        mQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNo++;
                swipeRefresh.setEnabled(false);
                loadData();
            }
        },recyclerView);
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
