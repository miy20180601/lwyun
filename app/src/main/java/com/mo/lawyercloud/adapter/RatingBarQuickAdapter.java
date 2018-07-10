package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;

import java.util.List;

/**
 * Created by Mohaifeng on 18/7/10.
 */
public class RatingBarQuickAdapter extends BaseQuickAdapter<String,BaseViewHolder>{
    public RatingBarQuickAdapter(@Nullable List<String> data) {
        super(R.layout.item_rating_bar_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
