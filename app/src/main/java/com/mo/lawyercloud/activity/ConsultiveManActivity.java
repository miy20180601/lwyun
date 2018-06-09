package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.ConsultiveAdapter;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.apiBeans.ConsultiveBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConsultiveManActivity extends BaseActivity {
    @BindView(R.id.bar_iv_back)
    ImageView barIvBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_tv_right)
    TextView barTvRight;
    @BindView(R.id.rv_con_data)
    RecyclerView rvConData;
    ConsultiveAdapter consultiveAdapter;
    List<ConsultiveBean> dataList = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_consultive_man;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("咨询管理");
        barTvRight.setVisibility(View.VISIBLE);
        barTvRight.setText("刷新");
        barTvRight.setTextColor(getResources().getColor(R.color.green_color));
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(mContext);
        rvConData.setLayoutManager(linearLayoutManager);
        consultiveAdapter = new ConsultiveAdapter(dataList);
        rvConData.setAdapter(consultiveAdapter);
    }

    @Override
    public void initData() {
        dataList.add(new ConsultiveBean("1","赵子龙","","预约成功","公司并购需要准备什么法律资料？","2018/03/28"));
        dataList.add(new ConsultiveBean("2","张三","","交易成功","婚姻家产如何分配？","2018/03/29"));
        consultiveAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEvent() {

    }

}
