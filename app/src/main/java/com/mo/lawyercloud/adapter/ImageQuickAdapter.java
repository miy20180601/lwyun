package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.beans.apiBeans.UploadFileBean;
import com.mo.lawyercloud.utils.TimeUtils;

import java.util.List;

/**
 * Created by Mohaifeng on 18/6/10.
 */
public class ImageQuickAdapter extends BaseQuickAdapter<UploadFileBean,BaseViewHolder>{

    public ImageQuickAdapter(@Nullable List<UploadFileBean> data) {
        super(R.layout.item_image_adapter,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UploadFileBean item) {
        ImageView img = helper.getView(R.id.iv_img);
        if (TextUtils.isEmpty(item.getSrc())){
            Glide.with(img.getContext()).load(R.mipmap.word).into(img);

        }else {
            Glide.with(img.getContext()).load(item.getSrc()).into(img);
        }
        helper.addOnClickListener(R.id.iv_delete);
    }
}
