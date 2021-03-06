package com.mo.lawyercloud.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.activity.VideoLowyerActivity;
import com.mo.lawyercloud.activity.VideoMemberActivity;
import com.mo.lawyercloud.adapter.ReserveOrderQuickAdapter;
import com.mo.lawyercloud.adapter.loadMoreView.CustomLoadMoreView;
import com.mo.lawyercloud.base.BaseFragment;
import com.mo.lawyercloud.base.Constant;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.BaseListEntity;
import com.mo.lawyercloud.beans.apiBeans.ReserveOrderBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.SPUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.Observable;
import okhttp3.RequestBody;

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
    private int type = 1; //1为普同用户，2为律师
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
        type = arguments.getInt("type");
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
        }, mRecyclerView);
        mQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ReserveOrderBean reserveOrderBean = mQuickAdapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.btn_open_video:
                        Intent intent = null;
                        if (type == 1) { //普通用户
                            intent = new Intent(mContext, VideoMemberActivity.class);
                            intent.putExtra("roomId", reserveOrderBean.getId())
                                  .putExtra("hostID", reserveOrderBean.getUserDTO().getUsername());
                        } else if (type == 2) { //律师用户
                            intent = new Intent(mContext, VideoLowyerActivity.class);
                            intent.putExtra("roomId", reserveOrderBean.getId())
                            .putExtra("userName",reserveOrderBean.getUserDTO().getUsername());
                        }
                        startActivity(intent);
                        break;
                    case R.id.tv_cancel:
                        unreserve(position);
                        break;
                    case R.id.tv_sure:
                        reserve(position);
                        break;
                }
            }

        });

    }

    private void reserve(final int position) {
        ReserveOrderBean reserveOrderBean = mQuickAdapter.getData().get(position);
        Map<String, Object> params = new HashMap<>();
        params.put("id", reserveOrderBean.getId());
        Gson gson = new Gson();
        String strEntity = gson.toJson(params);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;" +
                "charset=UTF-8"), strEntity);

        Observable<BaseEntity<Object>> observable = RetrofitFactory.getInstance().reserve(body);
        observable.compose(this.<BaseEntity<Object>>rxSchedulers()).subscribe(new BaseObserver<Object>() {
            @Override
            protected void onHandleSuccess(Object o, String msg) {
                mQuickAdapter.getData().get(position).setStatus(2);
                mQuickAdapter.notifyDataSetChanged();
//                pushMessage();
            }
        });
    }

    private void pushMessage(){
        Map<String, Object> params = new HashMap<>();
        params.put("alias", SPUtil.get(mContext, Constant.PHONE,""));
        params.put("alert", "预约审核通过");
        params.put("content", "你的预约审核已经通过了");
        Gson gson = new Gson();
        String strEntity = gson.toJson(params);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;" +
                "charset=UTF-8"), strEntity);
        Observable<BaseEntity<Object>> observable = RetrofitFactory.getInstance()
                .pushMessageToOne(body);
        observable.compose(this.<BaseEntity<Object>>rxSchedulers()).subscribe(new BaseObserver<Object>() {

            @Override
            protected void onHandleSuccess(Object o, String msg) {

            }
        });
    }

    private void unreserve(final int position) {
        ReserveOrderBean reserveOrderBean = mQuickAdapter.getData().get(position);
        Map<String, Object> params = new HashMap<>();
        params.put("id", reserveOrderBean.getId());
        Gson gson = new Gson();
        String strEntity = gson.toJson(params);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;" +
                "charset=UTF-8"), strEntity);

        Observable<BaseEntity<Object>> observable = RetrofitFactory.getInstance().unreserve(body);
        observable.compose(this.<BaseEntity<Object>>rxSchedulers()).subscribe(new BaseObserver<Object>() {
            @Override
            protected void onHandleSuccess(Object o, String msg) {
                mQuickAdapter.getData().get(position).setStatus(4);
                mQuickAdapter.notifyDataSetChanged();
            }
        });
    }

    private void loadData() {
        Observable<BaseEntity<BaseListEntity<ReserveOrderBean>>> observable = RetrofitFactory
                .getInstance()
                .myReserveOrder(pageNo, pageSize, status);
        observable.compose(this.<BaseEntity<BaseListEntity<ReserveOrderBean>>>rxSchedulers())
                .subscribe(new BaseObserver<BaseListEntity<ReserveOrderBean>>() {

                    @Override
                    protected void onHandleSuccess(BaseListEntity<ReserveOrderBean> baseListEntity,
                                                   String msg) {
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
                        if (pageNo > 1) {
                            mQuickAdapter.loadMoreFail();
                        }
                    }
                });
    }

    private void initViews() {
        ArrayList<ReserveOrderBean> datas = new ArrayList<>();
        mQuickAdapter = new ReserveOrderQuickAdapter(type,getActivity(), datas);
        mQuickAdapter.setLoadMoreView(new CustomLoadMoreView());
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
