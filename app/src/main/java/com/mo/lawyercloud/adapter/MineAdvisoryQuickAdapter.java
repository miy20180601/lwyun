package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.beans.apiBeans.AdvisoryOrderBean;
import com.mo.lawyercloud.utils.TimeUtils;

import java.util.List;

/**
 * Created by Mohaifeng on 18/5/27.
 */
public class MineAdvisoryQuickAdapter extends BaseQuickAdapter<AdvisoryOrderBean,BaseViewHolder> {


    public MineAdvisoryQuickAdapter( @Nullable List<AdvisoryOrderBean> data) {
        super(R.layout.item_mine_advisory, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AdvisoryOrderBean item) {
        ImageView avatar = helper.getView(R.id.iv_avatar);
        RequestOptions options = new RequestOptions();
        options.error(R.mipmap.data_button_avatar_n);
        Glide.with(avatar.getContext()).load(item.getUserDTO().getAvatar()).apply(options).into(avatar);

        helper.setText(R.id.tv_nickname,item.getUserDTO().getRealName());
        String state = null;
        if (item.getStatus()==1){
            state = "待审核";
        }else if (item.getStatus()==2){
            state = "预约成功";
        }else if (item.getStatus()==3){
            state = "交易完成";
        }else if (item.getStatus()==4){
            state = "预约失败";
        }
        helper.setText(R.id.tv_state,state);
        helper.setText(R.id.tv_title,item.getProblem());
        helper.setText(R.id.tv_date, TimeUtils.dateFormatByType(item.getFinishTime(),
                "yyyy/MM/dd"));

    }
}
