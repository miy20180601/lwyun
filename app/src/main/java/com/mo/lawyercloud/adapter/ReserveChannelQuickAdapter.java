package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.beans.apiBeans.ChannelBean;

import java.util.List;

/**
 * Created by Mohaifeng on 18/6/10.
 */
public class ReserveChannelQuickAdapter extends BaseQuickAdapter<ChannelBean, BaseViewHolder> {
    private int selectId = -1;

    public ReserveChannelQuickAdapter(@Nullable List<ChannelBean> data) {
        super(R.layout.item_reserve_channel,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChannelBean item) {
        TextView view = helper.getView(R.id.tv_item);
        helper.setText(R.id.tv_item,item.getName());
        if (item.getId()  == selectId){
            view.setSelected(true);
        }else {
            view.setSelected(false);
        }
    }
    public void setSelectId(int selectId){
        this.selectId = selectId;
        notifyDataSetChanged();
    }
}
