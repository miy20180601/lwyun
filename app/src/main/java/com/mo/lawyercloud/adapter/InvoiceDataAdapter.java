package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.beans.apiBeans.InvoiceDataBean;

import java.util.List;

/**
 * @author cui
 * @date 2018/6/9/009
 * @annotation 我的发票 DataAdapter
 * item_invoice_data_layout
 */
public class InvoiceDataAdapter extends BaseQuickAdapter<InvoiceDataBean,BaseViewHolder> {
    public InvoiceDataAdapter( @Nullable List<InvoiceDataBean> data) {
        super(R.layout.item_invoice_data_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvoiceDataBean item) {
        helper.setText(R.id.tv_invoice_orderid,"订单号："+item.getOrderID());
        helper.setText(R.id.tv_invoice_state,item.getState());
        helper.setText(R.id.tv_invoice_type,item.getType());
        helper.setText(R.id.tv_invoice_money,"￥"+item.getMoney());
        helper.setText(R.id.tv_invoice_time,item.getTime());
        helper.addOnClickListener(R.id.tv_invoice_apply);
    }
}
