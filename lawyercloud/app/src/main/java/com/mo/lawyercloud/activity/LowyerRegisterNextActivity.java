package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.DragAndDropPermissions;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.GridSpacingItemDecoration;
import com.mo.lawyercloud.adapter.RegisterGoodAtQuickAdapter;
import com.mo.lawyercloud.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by mo on 2018/5/14.
 */

public class LowyerRegisterNextActivity extends BaseActivity {
    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_register)
    Button mBtnRegister;

    List<String> data;
    private RegisterGoodAtQuickAdapter mQuickAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_lowyer_register_next;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        data = new ArrayList<>();
        data.add("知识产权");
        data.add("知识产权");
        data.add("知识产权");
        data.add("知识产权");
        data.add("知识产权");
        data.add("知识产权");
        data.add("知识产权");
        data.add("知识产权");
        GridSpacingItemDecoration decoration = new GridSpacingItemDecoration(mContext, 2, 10, 10, false);
        mQuickAdapter = new RegisterGoodAtQuickAdapter(data);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(mQuickAdapter);
    }

    @Override
    public void initData() {

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
