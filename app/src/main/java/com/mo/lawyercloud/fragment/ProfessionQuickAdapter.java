package com.mo.lawyercloud.fragment;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;

import java.util.List;

/**
 * Created by Mohaifeng on 18/5/27.
 */
public class ProfessionQuickAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public ProfessionQuickAdapter(@Nullable List<String> data) {
        super(R.layout.item_profession,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_profession,item);
    }
}
