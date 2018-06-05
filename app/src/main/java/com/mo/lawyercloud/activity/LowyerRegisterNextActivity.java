package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragAndDropPermissions;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.GridSpacingItemDecoration;
import com.mo.lawyercloud.adapter.RegisterGoodAtQuickAdapter;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.SerializableMap;
import com.mo.lawyercloud.beans.apiBeans.ChannelBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by mo on 2018/5/14.
 *
 */

public class LowyerRegisterNextActivity extends BaseActivity {
    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_register)
    Button mBtnRegister;

    List<ChannelBean> data;
    private RegisterGoodAtQuickAdapter mQuickAdapter;
    private Map<String, String> mParams;

    @Override
    public int getLayoutId() {
        return R.layout.activity_lowyer_register_next;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mBarTitle.setText("律师注册");
        Bundle bundle = getIntent().getExtras();
        SerializableMap serializableMap = (SerializableMap) bundle.get("data");
        mParams = serializableMap.getMap();

        data = new ArrayList<>();
        GridSpacingItemDecoration decoration = new GridSpacingItemDecoration(mContext, 2, 10, 10, false);
        mQuickAdapter = new RegisterGoodAtQuickAdapter(data);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(mQuickAdapter);
    }

    @Override
    public void initData() {
        Observable<BaseEntity<List<ChannelBean>>> observable = RetrofitFactory.getInstance().getChannels();
        observable.compose(this.<BaseEntity<List<ChannelBean>>>rxSchedulers()).subscribe(new BaseObserver<List<ChannelBean>>() {
            @Override
            protected void onHandleSuccess(List<ChannelBean> channelBeans, String msg) {
                mQuickAdapter.setNewData(channelBeans);
            }
        });
    }

    @Override
    public void onEvent() {

    }

    @OnClick({R.id.bar_iv_back, R.id.btn_register, R.id.tv_protocol})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bar_iv_back:
                finish();
                break;
            case R.id.btn_register:
                // TODO: 2018/5/14 注册
                break;
            case R.id.tv_protocol:
                // TODO: 2018/5/14 协议
                break;
        }
    }
}
