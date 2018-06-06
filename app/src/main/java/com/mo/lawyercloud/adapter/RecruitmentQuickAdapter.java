package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.beans.apiBeans.RecruitmentBean;

import java.util.List;

/**
 * Created by mo on 2018/5/25.
 */

public class RecruitmentQuickAdapter extends BaseQuickAdapter<RecruitmentBean,BaseViewHolder> {

    public RecruitmentQuickAdapter(@Nullable List<RecruitmentBean> data) {
        super(R.layout.item_recruitment_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecruitmentBean item) {
        helper.setText(R.id.tv_title,item.getPost());
        helper.setText(R.id.tv_number,item.getCount()+"人");
        helper.setText(R.id.tv_city,item.getWorkPlace());
        helper.setText(R.id.tv_describe,item.getContent());
    }

}
