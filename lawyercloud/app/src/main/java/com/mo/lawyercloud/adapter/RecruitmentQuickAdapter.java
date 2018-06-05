package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;

import java.util.List;

/**
 * Created by mo on 2018/5/25.
 */

public class RecruitmentQuickAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public RecruitmentQuickAdapter(@Nullable List<String> data) {
        super(R.layout.item_recruitment_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_title,"北京实习");
        helper.setText(R.id.tv_number,"5");
        helper.setText(R.id.tv_city,"北京");
        helper.setText(R.id.tv_describe,"完成上级交代任务");
    }

}
