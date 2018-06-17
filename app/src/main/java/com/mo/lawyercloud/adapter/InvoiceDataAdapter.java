package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.beans.apiBeans.InvoiceListBean;
import com.mo.lawyercloud.utils.TimeUtils;

import java.util.List;

/**
 * @author cui
 * @date 2018/6/9/009
 * @annotation 我的发票 DataAdapter
 * item_invoice_data_layout
 */
public class InvoiceDataAdapter extends BaseQuickAdapter<InvoiceListBean.ResultBean,BaseViewHolder> {
    public InvoiceDataAdapter( @Nullable List<InvoiceListBean.ResultBean> data) {
        super(R.layout.item_invoice_data_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvoiceListBean.ResultBean item) {
        String orderId = item.getOrderNo();
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
        String channal = item.getChannel().getName();
        String money = item.getRealPrice()+"";
        String startT = TimeUtils.INSTANCE.timetodate(item.getTimeMsg().getStartTime()+"",
                "MM-dd HH:mm");
        String endT = TimeUtils.INSTANCE.timetodate(item.getTimeMsg().getEndTime()+"","HH:mm");
        helper.setText(R.id.tv_invoice_orderid,"订单号："+orderId);
        helper.setText(R.id.tv_invoice_state,state);
        helper.setText(R.id.tv_invoice_type,channal);
        helper.setText(R.id.tv_invoice_money,"￥"+money);
        helper.setText(R.id.tv_invoice_time,startT+"--"+endT);
        helper.addOnClickListener(R.id.tv_invoice_apply);
    }
}
