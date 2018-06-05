package com.mo.lawyercloud.fragment;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;

import java.util.List;

/**
 * Created by mo on 2018/5/25.
 */

public class RegulationQuickAdapter extends BaseQuickAdapter<String,BaseViewHolder>{
    public RegulationQuickAdapter(@Nullable List<String> data) {
        super(R.layout.item_regulation_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_title,"你敢说");
        helper.setText(R.id.tv_describe,"科技司机告诉那个点了空间隔离霜");
    }
}
