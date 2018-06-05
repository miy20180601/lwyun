package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.MineAdvisoryQuickAdapter;
import com.mo.lawyercloud.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mohaifeng on 18/5/27.
 * 我的咨询页面
 */
public class MineAdvisoryActivity extends BaseActivity {


    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<String> mDatas;
    private MineAdvisoryQuickAdapter mQuickAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_advisory;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("我的咨询");
        barTitle.setVisibility(View.VISIBLE);
        mDatas = new ArrayList<>();
        mDatas.add(" ");
        mDatas.add(" ");
        mDatas.add(" ");
        mQuickAdapter = new MineAdvisoryQuickAdapter(mDatas);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(mQuickAdapter);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }



    @OnClick(R.id.bar_iv_back)
    public void onViewClicked() {

    }


}
