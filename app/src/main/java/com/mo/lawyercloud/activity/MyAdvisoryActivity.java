package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.MineAdvisoryQuickAdapter;
import com.mo.lawyercloud.adapter.loadMoreView.CustomLoadMoreView;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.AdvisoryOrderBean;
import com.mo.lawyercloud.beans.apiBeans.BaseListEntity;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by Mohaifeng on 18/5/27.
 * 我的咨询页面
 */
public class MyAdvisoryActivity extends BaseActivity {


    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private List<AdvisoryOrderBean> mDatas;
    private MineAdvisoryQuickAdapter mQuickAdapter;

    private int pageNo = 1;
    private int pageSize = 10;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_advisory;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("我的咨询");
        barTitle.setVisibility(View.VISIBLE);
        mDatas = new ArrayList<>();
        mQuickAdapter = new MineAdvisoryQuickAdapter(mDatas);
        mQuickAdapter.setLoadMoreView(new CustomLoadMoreView());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(mQuickAdapter);

    }

    @Override
    public void initData() {
        getMyAdvisoryOrder();
    }

    private void getMyAdvisoryOrder() {
        Observable<BaseEntity<BaseListEntity<AdvisoryOrderBean>>> observable =
                RetrofitFactory.getInstance().myAdvisoryOrder(pageNo, pageSize);
        observable.compose(this.<BaseEntity<BaseListEntity<AdvisoryOrderBean>>>rxSchedulers())
                .subscribe(new BaseObserver<BaseListEntity<AdvisoryOrderBean>>() {
                    @Override
                    protected void onHandleSuccess(BaseListEntity<AdvisoryOrderBean> dataList, String msg) {
                        loadComplete();
                        if (pageNo == 1) {//第一次加载或者是下拉加载
                            mQuickAdapter.setNewData(dataList.getResult());
                            if (mQuickAdapter.getData().size() >= dataList.getTotalCount()) {
                                mQuickAdapter.loadMoreEnd();
                            }
                        } else {
                            mQuickAdapter.addData(dataList.getResult());
                            if (mQuickAdapter.getData().size() >= dataList.getTotalCount()) {
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
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                mQuickAdapter.setEnableLoadMore(false);
                getMyAdvisoryOrder();
            }
        });
        mQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNo++;
                mSwipeRefreshLayout.setEnabled(false);
                getMyAdvisoryOrder();
            }
        },recyclerView);
    }



    @OnClick(R.id.bar_iv_back)
    public void onViewClicked() {
        finish();
    }

    /**
     * 关闭上拉加载
     */
    private void loadComplete() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (mSwipeRefreshLayout != null) mSwipeRefreshLayout.setEnabled(true);
        mQuickAdapter.setEnableLoadMore(true);
    }


}
