package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.beans.apiBeans.OrderListBean;
import com.mo.lawyercloud.utils.TimeUtils;

import java.util.List;

/**
 * @author cui
 * @date 2018/6/9/009
 * @annotation 预约通知的adapter R.layout.item_notice_data_layout
 */
public class NoticeDataAdapter extends BaseQuickAdapter<OrderListBean.ResultBean, BaseViewHolder> {
    public NoticeDataAdapter(@Nullable List<OrderListBean.ResultBean> data) {
        super(R.layout.item_notice_data_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListBean.ResultBean item) {
        String data = TimeUtils.INSTANCE.timetodate(item.getCreateTime() + "", "yyyy-MM-dd");
        String name = item.getUserDTO().getRealName();
        String type = item.getChannel().getName();
        String question = item.getProblem();
        String startT = TimeUtils.INSTANCE.timetodate(item.getTimeMsg().getStartTime() + "", "HH:mm");
        String endT = TimeUtils.INSTANCE.timetodate(item.getTimeMsg().getEndTime() + "", "HH:mm");
        String state = "";
        switch (item.getStatus()) {
            case 1:
                state = "待审核";
                break;
            case 2:
                state = "预约成功";
                break;
            case 3:
                state = "交易成功";
                break;
            case 4:
                state = "预约失败";
                break;
        }
        helper.setText(R.id.tv_notice_date, data);
        helper.setText(R.id.tv_notice_name, name);
        helper.setText(R.id.tv_notice_type, type);
        helper.setText(R.id.tv_notice_question, question);
        helper.setText(R.id.tv_notice_state, state);
        helper.setText(R.id.tv_notice_time, startT + "-" + endT);

    }
}
