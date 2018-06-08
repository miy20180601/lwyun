package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.beans.apiBeans.ChannelBean;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mo on 2018/5/14.
 */

public class RegisterGoodAtQuickAdapter extends BaseQuickAdapter<ChannelBean,BaseViewHolder> {

    public static HashMap<Integer, Boolean> isSelected;


    public RegisterGoodAtQuickAdapter(@Nullable List<ChannelBean> data) {
        super(R.layout.item_reg_good_at, data);
        init();
    }

    // 初始化 设置所有item都为未选择
    public void init() {
        isSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i < mData.size(); i++) {
            isSelected.put(i, false);
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, ChannelBean item) {
        int position = helper.getLayoutPosition();
        helper.setText(R.id.tv_profession,item.getName());
        LinearLayout ll = helper.getView(R.id.ll_container);
        TextView profession = helper.getView(R.id.tv_profession);
        if (isSelected.get(position)){
            helper.setGone(R.id.iv_select,true);
            ll.setSelected(true);
            profession.setSelected(true);
        }else {
            helper.setGone(R.id.iv_select,false);
            ll.setSelected(false);
            profession.setSelected(false);
        }
    }
    public void setIsSelected(int position){
        isSelected.put(position,!isSelected.get(position));
        notifyDataSetChanged();
    }



}
