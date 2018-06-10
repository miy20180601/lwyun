package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.beans.apiBeans.ChannelBean;
import com.mo.lawyercloud.beans.apiBeans.ReserveTimeBean;
import com.mo.lawyercloud.utils.TimeUtils;

import java.util.List;

/**
 * Created by Mohaifeng on 18/6/10.
 */
public class ReserveTimeQuickAdapter extends BaseQuickAdapter<ReserveTimeBean, BaseViewHolder> {
    private int selectId = -1;

    public ReserveTimeQuickAdapter(@Nullable List<ReserveTimeBean> data) {
        super(R.layout.item_reserve_channel,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReserveTimeBean item) {
        TextView view = helper.getView(R.id.tv_item);
        helper.setText(R.id.tv_item, TimeUtils.dateFormatByType(item.getStartTime(),
                "MM/dd HH:mm")+"-"+TimeUtils.dateFormatByType(item.getEndTime(),
                "HH:mm"));
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
