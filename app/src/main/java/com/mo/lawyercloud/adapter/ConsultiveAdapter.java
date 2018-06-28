package com.mo.lawyercloud.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.beans.apiBeans.AdvisoryOrderBean;
import com.mo.lawyercloud.beans.apiBeans.OrderAdvisoryBean;
import com.mo.lawyercloud.utils.TimeUtils;

import java.util.List;

/**
 * @author cui
 * @date 2018/6/9/009
 * @annotation
 */
public class ConsultiveAdapter  extends BaseQuickAdapter<AdvisoryOrderBean,BaseViewHolder>{

    public ConsultiveAdapter(@Nullable List<AdvisoryOrderBean> data) {
        super(R.layout.item_consultive_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AdvisoryOrderBean item) {
        String name =item.getUserDTO().getRealName();
        String content =item.getProblem();
        String date = TimeUtils.INSTANCE.timetodate(item.getCreateTime()+"","yyyy/MM/dd");
        String state = "";
        switch (item.getStatus()) {
            case 1:
                state = "待审核";
                break;
            case 2:
                state = "预约成功";
                break;
            case 3:
                state = "交易成功";
                break;
            case 4:
                state = "预约失败";
                break;
        }
        helper.setText(R.id.iv_consultive_name,name);
        helper.setText(R.id.iv_consultive_state,state);
        helper.setText(R.id.iv_consultive_content,content);
        helper.setText(R.id.iv_consultive_time,date);
      Button bt_goto_room=  helper.getView(R.id.bt_goto_room);
        if(state.equals("预约成功")){
            bt_goto_room.setVisibility(View.VISIBLE);
        }else{
            bt_goto_room.setVisibility(View.GONE);
        }
        helper.addOnClickListener(R.id.bt_goto_room);
    }
}
