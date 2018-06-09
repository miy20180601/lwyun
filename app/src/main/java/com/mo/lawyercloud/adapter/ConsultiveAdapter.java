package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.beans.apiBeans.ConsultiveBean;

import java.util.List;

/**
 * @author cui
 * @date 2018/6/9/009
 * @annotation
 */
public class ConsultiveAdapter  extends BaseQuickAdapter<ConsultiveBean,BaseViewHolder>{

    public ConsultiveAdapter(@Nullable List<ConsultiveBean> data) {
        super(R.layout.item_consultive_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ConsultiveBean item) {
        helper.setText(R.id.iv_consultive_name,item.getName());
        helper.setText(R.id.iv_consultive_state,item.getState());
        helper.setText(R.id.iv_consultive_content,item.getContent());
        helper.setText(R.id.iv_consultive_time,item.getTime());
      Button bt_goto_room=  helper.getView(R.id.bt_goto_room);
        if(item.getState().equals("预约成功")){
            bt_goto_room.setVisibility(View.VISIBLE);
        }else{
            bt_goto_room.setVisibility(View.GONE);
        }
        helper.addOnClickListener(R.id.bt_goto_room);
    }
}
