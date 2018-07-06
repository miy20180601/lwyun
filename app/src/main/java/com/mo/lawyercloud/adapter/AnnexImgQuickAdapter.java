package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.beans.apiBeans.AttachmentsBean;
import com.mo.lawyercloud.beans.apiBeans.UploadFileBean;
import com.mo.lawyercloud.utils.FileUtil;

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

        if (FileUtil.checkSuffix(item.getImage(),new String[]{"png","jpg"})){
            Glide.with(mContext).load(item.getImage()).into(img);
        }else {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.fitCenter();
            Glide.with(mContext).load(R.mipmap.word).apply(requestOptions).into(img);
        }
    }
}
