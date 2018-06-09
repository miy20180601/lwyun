package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.beans.apiBeans.NoticeDataBean;

import java.util.List;

/**
 * @author cui
 * @date 2018/6/9/009
 * @annotation 预约通知的adapter R.layout.item_notice_data_layout
 */
public class NoticeDataAdapter extends BaseQuickAdapter<NoticeDataBean,BaseViewHolder> {
    public NoticeDataAdapter(@Nullable List<NoticeDataBean> data) {
        super(R.layout.item_notice_data_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeDataBean item) {
        helper.setText(R.id.tv_notice_date,item.getDate());
        helper.setText(R.id.tv_notice_name,item.getName());
        helper.setText(R.id.tv_notice_type,item.getType());
        helper.setText(R.id.tv_notice_question,item.getQuestion());
        helper.setText(R.id.tv_notice_time,item.getTime());

    }
}
