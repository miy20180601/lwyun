package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hedgehog.ratingbar.RatingBar;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.beans.apiBeans.AdvisoryOrderBean;
import com.mo.lawyercloud.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohaifeng on 18/5/27.
 */
public class MineAdvisoryQuickAdapter extends BaseQuickAdapter<AdvisoryOrderBean,BaseViewHolder> {

    private int type;//1为用户，2为律师

    public MineAdvisoryQuickAdapter(@Nullable List<AdvisoryOrderBean> data, int type) {
        super(R.layout.item_mine_advisory, data);
        this.type = type;
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
            AdvisoryOrderBean.CommentBean comment = item.getComment();
            if (comment == null){
                helper.setGone(R.id.rl_satisfaction,false);
                helper.setGone(R.id.ll_quick_reply,false);
                helper.setGone(R.id.rl_remarks,false);
                if (type == 1){
                    helper.setGone(R.id.tv_btn_score,true);
                }else {
                    helper.setGone(R.id.tv_btn_score,false);
                }
            }else {
                helper.setGone(R.id.tv_btn_score,false);
                helper.setGone(R.id.rl_satisfaction,true);
                RecyclerView recyclerView = helper.getView(R.id.recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false));

                ArrayList<String> stars = new ArrayList<>();
                for (int i=0; i<comment.getScore();i++){
                    stars.add("");
                }
                RatingBarQuickAdapter adapter = new RatingBarQuickAdapter(stars);
                recyclerView.setAdapter(adapter);
                if (comment.getIsDefault() == 1){ //是否默认系统好评 0否，1是
                    helper.setText(R.id.tv_satisfaction,"系统默认好评");
                }else {
                    switch (comment.getScore()) {
                        case 1:
                            helper.setText(R.id.tv_satisfaction,"非常不满意");

                            break;
                        case 2:
                            helper.setText(R.id.tv_satisfaction,"不满意");
                            break;
                        case 3:
                            helper.setText(R.id.tv_satisfaction,"满意");
                            break;
                        case 4:
                            helper.setText(R.id.tv_satisfaction,"很满意");
                            break;
                        case 5:
                            helper.setText(R.id.tv_satisfaction,"非常满意");
                            break;
                    }
                }
                if (!TextUtils.isEmpty(comment.getQuickReply())){
                    helper.setGone(R.id.ll_quick_reply,true);
                    helper.setText(R.id.tv_quick_reply,comment.getQuickReply());
                }else {
                    helper.setGone(R.id.ll_quick_reply,false);
                }
                if (!TextUtils.isEmpty(comment.getContent())){
                    helper.setGone(R.id.rl_remarks,true);
                    helper.setText(R.id.tv_remarks,comment.getContent());
                }else {
                    helper.setGone(R.id.rl_remarks,false);
                }
            }

        }else if (item.getStatus()==4){
            state = "预约失败";
        }
        helper.setText(R.id.tv_state,state);
        helper.setText(R.id.tv_title,item.getProblem());
        helper.setText(R.id.tv_date, TimeUtils.dateFormatByType(item.getFinishTime(),
                "yyyy/MM/dd"));

        helper.addOnClickListener(R.id.tv_btn_score);

    }
}
