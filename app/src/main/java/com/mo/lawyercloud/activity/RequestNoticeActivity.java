package com.mo.lawyercloud.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.NoticeDataAdapter;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.apiBeans.NoticeDataBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 预约通知
 */
public class RequestNoticeActivity extends BaseActivity {
    @BindView(R.id.bar_iv_back)
    ImageView barIvBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_tv_right)
    TextView barTvRight;
    @BindView(R.id.rv_notice_data)
    RecyclerView rvNoticeData;
    List<NoticeDataBean> dataList = new ArrayList<>();
    NoticeDataAdapter noticeDataAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_request_notice;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("预约通知");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        noticeDataAdapter = new NoticeDataAdapter(dataList);
        rvNoticeData.setLayoutManager(linearLayoutManager);
        rvNoticeData.setAdapter(noticeDataAdapter);
    }

    @Override
    public void initData() {
        dataList.add(new NoticeDataBean("1","2018-03-18","刘先生","知识产权","可否跟公司拿回相应的福利","15:00-17:00"));
        noticeDataAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEvent() {

    }


}
