package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.beans.apiBeans.SolicitorDetailBean;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mohaifeng on 18/5/27.
 */
public class LawyerProfileQuickAdapter extends BaseQuickAdapter<SolicitorDetailBean, BaseViewHolder> {


    private final String[] mChannelArray;

    public LawyerProfileQuickAdapter(@Nullable List<SolicitorDetailBean> data,String[]
            channelArray) {
        super(R.layout.item_lawyer_profile, data);
        mChannelArray = channelArray;
    }

    @Override
    protected void convert(BaseViewHolder helper, final SolicitorDetailBean item) {
        CircleImageView ivAvatar = helper.getView(R.id.iv_avatar);
        RequestOptions options = new RequestOptions();
        options.error(R.mipmap.data_button_avatar_n);
        Glide.with(mContext).load(item.getAvatar()).apply(options).into(ivAvatar);

        if (item.getGender() ==1){
            helper.setImageResource(R.id.iv_sex,R.mipmap.common_icon_male);
        } else {
            helper.setImageResource(R.id.iv_sex,R.mipmap.common_icon_female);
        }


        helper.setText(R.id.tv_name, item.getRealName());
        helper.setText(R.id.tv_resume, item.getResume());

        List<String> datas = new ArrayList<>();
        String[] split = item.getChannels().split(",");
        for (String s : split) {
            datas.add(mChannelArray[Integer.parseInt(s)]);
        }
        ProfessionQuickAdapter adapter = new ProfessionQuickAdapter(datas);
        RecyclerView recyclerView = helper.getView(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
//                .HORIZONTAL, false));
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
        recyclerView.setAdapter(adapter);
    }
}
