package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;

import java.util.List;

/**
 * Created by mo on 2018/5/14.
 */

public class RegisterGoodAtQuickAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public RegisterGoodAtQuickAdapter(@Nullable List<String> data) {
        super(R.layout.item_reg_good_at, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_profession,item);
    }
}
