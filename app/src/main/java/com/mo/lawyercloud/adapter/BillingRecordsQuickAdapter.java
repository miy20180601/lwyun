package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.beans.apiBeans.BillingRecordsBean;
import com.mo.lawyercloud.utils.TimeUtils;

import java.util.List;

/**
 * Created by Mohaifeng on 18/6/16.
 */
public class BillingRecordsQuickAdapter extends BaseQuickAdapter<BillingRecordsBean,BaseViewHolder>{
    public BillingRecordsQuickAdapter(@Nullable List<BillingRecordsBean> data) {
        super(R.layout.item_billing_records, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillingRecordsBean item) {
        if (item.getType()==1){
            helper.setText(R.id.tv_type,"收入");
            helper.setText(R.id.tv_price,"+"+item.getPrice());
        }else if (item.getType() == 2){
            helper.setText(R.id.tv_type,"支出");
            helper.setText(R.id.tv_price,"-"+item.getPrice());
        }else if (item.getType() == 3){
            helper.setText(R.id.tv_type,"提现");
            helper.setText(R.id.tv_price,"-"+item.getPrice());
        }
        helper.setText(R.id.tv_date, TimeUtils.dateFormatByType(item.getCreateTime(),
                "yyyy-MM-dd"));
    }
}
