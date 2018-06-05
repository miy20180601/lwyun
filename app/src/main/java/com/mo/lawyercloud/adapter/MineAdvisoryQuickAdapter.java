package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;

import java.util.List;

/**
 * Created by Mohaifeng on 18/5/27.
 */
public class MineAdvisoryQuickAdapter extends BaseQuickAdapter<String,BaseViewHolder> {


    public MineAdvisoryQuickAdapter( @Nullable List<String> data) {
        super(R.layout.item_mine_advisory, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView avatar = helper.getView(R.id.iv_avatar);

        helper.setText(R.id.tv_nickname,"赵子龙");
        helper.setText(R.id.tv_state,"预约成功");
        helper.setText(R.id.tv_title,"公司并购需要什么材料");
        helper.setText(R.id.tv_date,"2018/05/25");

    }
}
