package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.interfaces.OnRecyclerViewItemClickListener;
import com.mo.lawyercloud.fragment.ProfessionQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohaifeng on 18/5/27.
 */
public class LawyerProfileQuickAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    OnRecyclerViewItemClickListener mListener;
    List<String> mData;

    public LawyerProfileQuickAdapter(@Nullable List<String> data, OnRecyclerViewItemClickListener listener) {
        super(R.layout.item_lawyer_profile, data);
        mData = data;
        mListener = listener;
    }

    @Override
    protected void convert(BaseViewHolder helper, final String item) {
        helper.setText(R.id.tv_level, "lv.10");
        helper.setText(R.id.tv_name, "张子龙");

        helper.setText(R.id.tv_level, "lv.10");
        final int pisition = helper.getPosition();
        helper.setOnClickListener(R.id.cl_lawayer_profile, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v, pisition);
            }
        });
        List<String> datas = new ArrayList<>();
        datas.add("家庭纠纷");
        datas.add("公司并购");
        ProfessionQuickAdapter adapter = new ProfessionQuickAdapter(datas);
        RecyclerView recyclerView = helper.getView(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                .HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }
}
