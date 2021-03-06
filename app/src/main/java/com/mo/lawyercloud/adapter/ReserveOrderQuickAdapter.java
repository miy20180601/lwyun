package com.mo.lawyercloud.adapter;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.mo.lawyercloud.BuildConfig;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.activity.MainActivity;
import com.mo.lawyercloud.beans.apiBeans.AttachmentsBean;
import com.mo.lawyercloud.beans.apiBeans.ReserveOrderBean;
import com.mo.lawyercloud.utils.FileUtil;
import com.mo.lawyercloud.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPreview;

/**
 * Created by Mohaifeng on 18/6/11.
 * 我的预约adapter
 */
public class ReserveOrderQuickAdapter extends BaseQuickAdapter<ReserveOrderBean, BaseViewHolder> {
    int type;
    private Activity activity;

    public ReserveOrderQuickAdapter(int type, Activity activity, @Nullable List<ReserveOrderBean>
            data) {
        super(R.layout.item_reserve_order, data);
        this.type = type;
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, ReserveOrderBean item) {
        ReserveOrderBean.TimeMsgBean timeMsg = item.getTimeMsg();
        helper.setText(R.id.tv_date, TimeUtils.dateFormatByType(item.getCreateTime(),
                "yyyy-MM-dd"));
        String state = null;
        if (item.getStatus() == 1) {
            state = "待审核";

        } else if (item.getStatus() == 2) {
            state = "预约成功";

        } else if (item.getStatus() == 3) {
            state = "交易完成";
        } else if (item.getStatus() == 4) {
            state = "预约失败";
        }

        helper.setText(R.id.tv_status, state);
        helper.setText(R.id.tv_name, item.getUserDTO().getRealName());
        helper.setText(R.id.tv_channel, item.getChannel().getName());
        helper.setText(R.id.tv_problem, item.getProblem());
        if (item.getAttachments().size() > 0) {
            helper.setGone(R.id.ll_annex, true);
            RecyclerView recyclerView = helper.getView(R.id.recycler_img);
            List<AttachmentsBean> attachments = item.getAttachments();
            final ArrayList<String> paths = new ArrayList<>();
            for (AttachmentsBean attachment : attachments) {
                paths.add(attachment.getImage());
            }
            AnnexImgQuickAdapter adapter = new AnnexImgQuickAdapter(item.getAttachments());
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    AttachmentsBean attachment = (AttachmentsBean) adapter.getData().get(position);
                    if (FileUtil.checkSuffix(attachment.getImage(), new String[]{"png", "jpg"})) {
                        PhotoPreview.builder()
                                .setPhotos(paths)
                                .setCurrentItem(position)
                                .setShowDeleteButton(false)
                                .start(activity);
                    } else {

                    }

                }
            });
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(adapter);
        } else {
            helper.setGone(R.id.ll_annex, false);
        }

        helper.setText(R.id.tv_time, TimeUtils.dateFormatByType(timeMsg.getStartTime(),
                "MM-dd HH:mm") + "-" + TimeUtils.dateFormatByType(timeMsg.getEndTime(),
                "HH:mm"));
        if (item.getStatus() == 1 && type == 2) {
            helper.setGone(R.id.rl_buttom, true);
        } else {
            helper.setGone(R.id.rl_buttom, false);
        }

        long currentTime = System.currentTimeMillis();
        if (item.getStatus() == 2 && currentTime >= timeMsg.getStartTime() && currentTime <= timeMsg
                .getEndTime()) {
            helper.setGone(R.id.btn_open_video, true);
        } else {
            if (BuildConfig.DEBUG && item.getStatus() != 1 && item.getStatus() != 4) {
                helper.setGone(R.id.btn_open_video, true);
            } else {
                helper.setGone(R.id.btn_open_video, false);
            }
        }

        helper.addOnClickListener(R.id.btn_open_video)
                .addOnClickListener(R.id.tv_channel)
                .addOnClickListener(R.id.tv_sure);
    }
}
