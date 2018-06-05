package com.mo.lawyercloud.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by mo on 2018/5/10.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public Context mContext;
    /**
     * 设置布局方法
     */
    public abstract int getLayoutId();
    /**初始化方法*/
    public abstract void initViews(Bundle savedInstanceState);
    /**初始化数据*/
    public abstract void initData();

    public abstract void onEvent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mContext = this;

        initViews(savedInstanceState);
        initData();
        onEvent();
    }



}
