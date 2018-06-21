package com.mo.lawyercloud.adapter;

import android.graphics.Color;
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
public class BillingRecordsLowyerQuickAdapter extends BaseQuickAdapter<BillingRecordsBean,BaseViewHolder>{
    public BillingRecordsLowyerQuickAdapter(@Nullable List<BillingRecordsBean> data) {
        super(R.layout.item_bill_lowyer, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillingRecordsBean item) {
        helper.setText(R.id.tv_name,item.getRealName());
        if (item.getType()==1){
            helper.setText(R.id.tv_type,"收入");
            helper.setText(R.id.tv_price,""+item.getPrice());
            helper.setGone(R.id.rl_withdraw_status,false);
        } else if (item.getType() == 3){
            helper.setText(R.id.tv_type,"提现");
            helper.setText(R.id.tv_price,""+item.getPrice());
            helper.setGone(R.id.rl_withdraw_status,true);
            if (item.getWithdrawalStatus()==0){
                helper.setText(R.id.tv_withdraw_status,"提现中");
                helper.setTextColor(R.id.tv_withdraw_status, Color.parseColor("#FCCA3B"));
            }else if (item.getWithdrawalStatus() == 1){
                helper.setText(R.id.tv_withdraw_status,"提现成功");
                helper.setTextColor(R.id.tv_withdraw_status, Color.parseColor("#5DEB47"));
            }else if (item.getWithdrawalStatus()==2){
                helper.setText(R.id.tv_withdraw_status,"提现失败");
                helper.setTextColor(R.id.tv_withdraw_status, Color.parseColor("#EC5660"));
            }
        }
        helper.setText(R.id.tv_date, TimeUtils.dateFormatByType(item.getCreateTime(),
                "yyyy-MM-dd"));
    }
}
