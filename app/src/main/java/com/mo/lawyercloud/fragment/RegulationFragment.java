package com.mo.lawyercloud.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.RegulationQuickAdapter;
import com.mo.lawyercloud.adapter.loadMoreView.CustomLoadMoreView;
import com.mo.lawyercloud.base.BaseFragment;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.BaseListEntity;
import com.mo.lawyercloud.beans.apiBeans.LegalBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by mo on 2018/5/25.
 * 法规常识
 */

public class RegulationFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private int pageNo = 0;
    private int pageSize = 10;
    private RegulationQuickAdapter mQuickAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fg_regulation;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        onEvent();
        loadData();
    }

    private void onEvent() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                mSwipeRefreshLayout.setEnabled(false);
                loadData();
            }
        },mRecyclerView);

    }

    private void loadData() {
        Observable<BaseEntity<BaseListEntity<LegalBean>>> observable = RetrofitFactory.getInstance()
                .legalKnowledge(pageNo, pageSize);
        observable.compose(this.<BaseEntity<BaseListEntity<LegalBean>>>rxSchedulers()).subscribe(new BaseObserver<BaseListEntity<LegalBean>>() {

            @Override
            protected void onHandleSuccess(BaseListEntity<LegalBean> baseListEntity, String msg) {
                loadComplete();
                if (pageNo == 1) {//第一次加载或者是下拉加载
                    mQuickAdapter.setNewData(baseListEntity.getResult());
                    if (mQuickAdapter.getData().size() >= baseListEntity.getTotalCount()) {
                        mQuickAdapter.loadMoreEnd();
                    }
                } else {
                    mQuickAdapter.addData(baseListEntity.getResult());
                    if (mQuickAdapter.getData().size() >= baseListEntity.getTotalCount()) {
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

    private void initViews() {
        ArrayList<LegalBean> datas = new ArrayList<>();
        mQuickAdapter = new RegulationQuickAdapter(datas);
        mQuickAdapter.setLoadMoreView(new CustomLoadMoreView());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mQuickAdapter);
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
