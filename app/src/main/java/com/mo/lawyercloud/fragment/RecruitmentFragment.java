package com.mo.lawyercloud.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.RecruitmentQuickAdapter;
import com.mo.lawyercloud.base.BaseFragment;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.BaseListEntity;
import com.mo.lawyercloud.beans.apiBeans.RecruitmentBean;
import com.mo.lawyercloud.beans.apiBeans.WebViewBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;

/**
 * Created by mo on 2018/5/25.
 * 人才招聘
 */

public class RecruitmentFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private int pageNo = 0;
    private int pageSize = 10;
    private RecruitmentQuickAdapter mQuickAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fg_recruitment;
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
                mRecyclerView.setEnabled(false);
                loadData();
            }
        },mRecyclerView);

    }

    private void loadData() {
        Observable<BaseEntity<BaseListEntity<RecruitmentBean>>> observable = RetrofitFactory.getInstance()
                .recruitment(pageNo, pageSize);
        observable.compose(this.<BaseEntity<BaseListEntity<RecruitmentBean>>>rxSchedulers()).subscribe(new BaseObserver<BaseListEntity<RecruitmentBean>>() {

            @Override
            protected void onHandleSuccess(BaseListEntity<RecruitmentBean> baseListEntity, String msg) {
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
        ArrayList<RecruitmentBean> datas = new ArrayList<>();
        mQuickAdapter = new RecruitmentQuickAdapter(datas);
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
