package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.beans.FileInfo;
import com.mo.lawyercloud.utils.FileUtil;

import java.util.List;

/**
 * Created by Mohaifeng on 18/6/27.
 */
public class FolderAdapter extends BaseQuickAdapter<FileInfo,BaseViewHolder>{

    private int selectItem = -1;
    public FolderAdapter(@Nullable List<FileInfo> data) {
        super(R.layout.adapter_folder_data_rv_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FileInfo item) {
        int position = helper.getLayoutPosition();
        helper.setText(R.id.tv_content,item.getFileName());
        helper.setText(R.id.tv_size,FileUtil.FormetFileSize(item.getFileSize()));
        helper.setText(R.id.tv_time,item.getTime());

        ImageView cover = helper.getView(R.id.iv_cover);
        CheckBox cb = helper.getView(R.id.cb_check_box);

//        if (FileUtil.checkSuffix(item.getFilePath(),new String[]{"png","jpg"})){
//            Glide.with(mContext).load(item.getFilePath()).into(cover);
//        }else {
//            RequestOptions requestOptions = new RequestOptions();
//            requestOptions.fitCenter();
//            Glide.with(mContext).load(FileUtil.getFileTypeImageId(mContext, item
//                    .getFilePath())).apply(requestOptions).into(cover);
//        }
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.fitCenter();
        Glide.with(mContext).load(R.mipmap.word).apply(requestOptions).into(cover);
        if (selectItem == position){
            cb.setChecked(true);
        }else {
            cb.setChecked(false);
        }
    }

    public void setSelectItem(int position){
        selectItem = position;
        notifyDataSetChanged();
    }
}
