package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;

import java.util.List;

/**
 * @author CUI
 * @data 2018/06/08
 * @details 时间管理item 的adapter
 * layout item_time_layout.xml
 */
public class TimeDataAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public TimeDataAdapter( @Nullable List<String> data ) {
        super(R.layout.item_time_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
      helper.setText(R.id.tv_time_data, item);
      helper.addOnClickListener(R.id.tv_delete);
    }
}
