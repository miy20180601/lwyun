package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.beans.apiBeans.AttachmentsBean;
import com.mo.lawyercloud.beans.apiBeans.UploadFileBean;

import java.util.List;

/**
 * Created by Mohaifeng on 18/6/10.
 */
public class AnnexImgQuickAdapter extends BaseQuickAdapter<AttachmentsBean,BaseViewHolder>{

    public AnnexImgQuickAdapter(@Nullable List<AttachmentsBean> data) {
        super(R.layout.item_annex_img,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AttachmentsBean item) {
        ImageView img = helper.getView(R.id.iv_img);
        Glide.with(img.getContext()).load(item.getImage()).into(img);
    }
}
