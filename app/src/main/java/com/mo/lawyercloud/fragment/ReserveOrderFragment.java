package com.mo.lawyercloud.fragment;

import android.content.Intent;
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
import com.mo.lawyercloud.activity.MemberVideoActivity;
import com.mo.lawyercloud.adapter.RecruitmentQuickAdapter;
import com.mo.lawyercloud.adapter.ReserveOrderQuickAdapter;
import com.mo.lawyercloud.base.BaseFragment;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.BaseListEntity;
import com.mo.lawyercloud.beans.apiBeans.RecruitmentBean;
import com.mo.lawyercloud.beans.apiBeans.ReserveOrderBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by Mohaifeng on 18/6/11.
 */
public class ReserveOrderFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private int pageNo = 0;
    private int pageSize = 10;
    private int status = 0;
    private ReserveOrderQuickAdapter mQuickAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fg_reserver_order;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        status = arguments.getInt("status");
        initViews();
        onEvent();
        loadData();

    }

    private void onEvent() {
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                mSwipeRefresh.setEnabled(false);
                loadData();
            }
        },mRecyclerView);
        mQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.btn_open_video:
                        // TODO: 18/6/12 进入直播间
                        ReserveOrderBean reserveOrderBean = mQuickAdapter.getData().get(position);
                        startActivity(new Intent(mContext, MemberVideoActivity.class).putExtra
                                ("roomId",reserveOrderBean.getId())
                                .putExtra("hostID", reserveOrderBean.getUserDTO().getUsername()));
                        break;
                }
            }
        });

    }

    private void loadData() {
        Observable<BaseEntity<BaseListEntity<ReserveOrderBean>>> observable = RetrofitFactory.getInstance()
                .myReserveOrder(pageNo, pageSize,status);
        observable.compose(this.<BaseEntity<BaseListEntity<ReserveOrderBean>>>rxSchedulers()).subscribe(new BaseObserver<BaseListEntity<ReserveOrderBean>>() {

            @Override
            protected void onHandleSuccess(BaseListEntity<ReserveOrderBean> baseListEntity, String msg) {
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
        ArrayList<ReserveOrderBean> datas = new ArrayList<>();
        mQuickAdapter = new ReserveOrderQuickAdapter(datas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mQuickAdapter);
    }

    /**
     * 关闭上拉加载
     */
    private void loadComplete() {
        if (mSwipeRefresh != null && mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
        if (mSwipeRefresh != null) mSwipeRefresh.setEnabled(true);
        mQuickAdapter.setEnableLoadMore(true);
    }
}
