package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.beans.apiBeans.LegalBean;

import java.util.List;

/**
 * Created by mo on 2018/5/25.
 */

public class RegulationQuickAdapter extends BaseQuickAdapter<LegalBean,BaseViewHolder>{
    public RegulationQuickAdapter(@Nullable List<LegalBean> data) {
        super(R.layout.item_regulation_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LegalBean item) {
        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_describe,item.getContent());
    }
}
