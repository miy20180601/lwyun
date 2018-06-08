package com.mo.lawyercloud.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.TimeDataAdapter;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author CUI
 * @data 2018/06/08
 * @details 时间管理
 */
public class MineLwyerTimeActivity extends BaseActivity {
    @BindView(R.id.bar_iv_back)
    ImageView barIvBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_tv_right)
    TextView barTvRight;
    @BindView(R.id.rv_time_data)
    RecyclerView rvTimeData;
    List<String> datalist = new ArrayList<>();
    TimeDataAdapter timeDataAdapter;
    @BindView(R.id.tv_date_select)
    TextView tvDateSelect;
    @BindView(R.id.tv_time_select)
    TextView tvTimeSelect;
    @BindView(R.id.ll_add_time)
    LinearLayout llAddTime;

    String date;
    String startTime;
    String endTime;
    boolean isTime = false;//用于获取两个时间
    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_lwyer_time;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("时间管理");
        barTvRight.setText("添加");
        barTvRight.setVisibility(View.VISIBLE);
        barTvRight.setTextColor(getResources().getColor(R.color.green_color));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        rvTimeData.setLayoutManager(linearLayoutManager);
        timeDataAdapter = new TimeDataAdapter(datalist);
        rvTimeData.setAdapter(timeDataAdapter);
        llAddTime.setVisibility(View.GONE);
        rvTimeData.setVisibility(View.VISIBLE);
        date= TimeUtils.INSTANCE.getTimeData("yyyy-MM-dd");
        startTime= TimeUtils.INSTANCE.getTimeData("HH:mm");
        endTime= TimeUtils.INSTANCE.getTimeData("HH:mm");
        tvDateSelect.setText(date);
        tvTimeSelect.setText(startTime+"-"+endTime);
        timeDataAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Log.i("itemClick","position="+position);
            }
        });

    }


    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }


    @OnClick({R.id.bar_iv_back, R.id.bar_tv_right,R.id.tv_date_select, R.id.tv_time_select})
    public void onViewClicked(View view) {
        Calendar calendar = Calendar.getInstance();

        switch (view.getId()) {
            case R.id.bar_iv_back:
                break;
            case R.id.bar_tv_right:
                if(barTvRight.getText().equals("确定")){
                    date=tvDateSelect.getText().toString();
//                    time=tvTimeSelect.getText().toString();
                    llAddTime.setVisibility(View.GONE);
                    rvTimeData.setVisibility(View.VISIBLE);
                    barTvRight.setText("添加");
                    datalist.add(date+" "+startTime+"--"+endTime);
                    timeDataAdapter.notifyDataSetChanged();
                }else{
                    llAddTime.setVisibility(View.VISIBLE);
                    rvTimeData.setVisibility(View.GONE);
                    barTvRight.setText("确定");
                }
                break;
            case R.id.tv_date_select:
                TimeUtils.INSTANCE.showDatePickerDialog(MineLwyerTimeActivity.this
                        , 1
                        , calendar,onDateSetListener);
                break;
            case R.id.tv_time_select:
                TimeUtils.INSTANCE.showTimePickerDialog(MineLwyerTimeActivity.this
                        , 1
                        , calendar,onTimeSetListener);
                break;
        }
    }
    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month+=1;
            tvDateSelect.setText(year+"-"+month+"-"+dayOfMonth);
        }
    };
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//            tvTimeSelect
            if(isTime){
                isTime=false;
                endTime=hourOfDay+":"+minute;
                tvTimeSelect.setText(startTime+"-"+endTime);
            }else{
                startTime=hourOfDay+":"+minute;
                Calendar calendar = Calendar.getInstance();
                TimeUtils.INSTANCE.showTimePickerDialog(MineLwyerTimeActivity.this
                        , 1
                        , calendar,onTimeSetListener);
                isTime=true;
            }
        }
    };
}
