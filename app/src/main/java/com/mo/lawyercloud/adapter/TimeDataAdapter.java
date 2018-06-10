package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.beans.apiBeans.TimeMsgBean;
import com.mo.lawyercloud.utils.TimeUtils;

import java.util.List;

/**
 * @author CUI
 * @data 2018/06/08
 * @details 时间管理item 的adapter
 * layout item_time_layout.xml
 */
public class TimeDataAdapter extends BaseQuickAdapter<TimeMsgBean.ResultBean, BaseViewHolder> {
    public TimeDataAdapter( @Nullable List<TimeMsgBean.ResultBean> data ) {
        super(R.layout.item_time_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TimeMsgBean.ResultBean item) {
        String startT= TimeUtils.INSTANCE.timetodate(item.getStartTime(),"MM-dd HH:mm");
        String endT= TimeUtils.INSTANCE.timetodate(item.getEndTime(),"HH:mm");
      helper.setText(R.id.tv_time_data, startT+"--"+endT);
      helper.addOnClickListener(R.id.tv_delete);
    }
}
