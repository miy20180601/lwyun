package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.interfaces.OnRecyclerViewItemClickListener;

/**
 * @author CUI
 * @data 2018/06/08
 * @details 时间管理item 的adapter
 * layout item_time_layout.xml
 */
public class TimeDataAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    OnRecyclerViewItemClickListener mListener;
    public TimeDataAdapter( @Nullable List<String> data , OnRecyclerViewItemClickListener listener) {
        super(R.layout.item_time_layout, data);
        this.mListener=listener;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
      helper.setText(R.id.tv_time_data, item);
      TextView tv_delete= helper.getView(R.id.tv_delete);
      final int position = helper.getPosition();
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v,position);
            }
        });
    }
}
