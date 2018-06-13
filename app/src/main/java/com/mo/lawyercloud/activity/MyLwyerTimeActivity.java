package com.mo.lawyercloud.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.TimeDataAdapter;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.apiBeans.TimeMsgBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.NToast;
import com.mo.lawyercloud.utils.TimeUtils;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author CUI
 * @data 2018/06/08
 * @details 时间管理
 */
public class MyLwyerTimeActivity extends BaseActivity {
    @BindView(R.id.bar_iv_back)
    ImageView barIvBack;
    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.bar_tv_right)
    TextView barTvRight;
    @BindView(R.id.rv_time_data)
    RecyclerView rvTimeData;
    List<TimeMsgBean.ResultBean> datalist = new ArrayList<>();
    TimeDataAdapter timeDataAdapter;
    @BindView(R.id.tv_date_select)
    TextView tvDateSelect;
    @BindView(R.id.tv_time_select)
    TextView tvTimeSelect;
    @BindView(R.id.ll_add_time)
    LinearLayout llAddTime;
    private int pageNo = 1;
    private int pageSize = 10;
    String date;
    String startTime;
    String endTime;
    boolean isTime = false;//用于获取两个时间
    TimePickerView   pvDateTime;
    TimePickerView   pvTime;
    TimePickerView   pvEndTime;
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
        rvTimeData.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        llAddTime.setVisibility(View.GONE);
        rvTimeData.setVisibility(View.VISIBLE);
        date = TimeUtils.INSTANCE.getTimeData("yyyy-MM-dd");
        startTime = TimeUtils.INSTANCE.getTimeData("HH:mm");
        endTime = TimeUtils.INSTANCE.getTimeData("HH:mm");
        tvDateSelect.setText(date);
        tvTimeSelect.setText(startTime + "-" + endTime);
        timeDataAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Log.i("itemClick", "position=" + position);
                TimeMsgBean.ResultBean resultBean = datalist.get(position);
                removeTime(resultBean.getId()+"",position);
            }
        });
        initShowDate();
        initShowTime();
        initEndShowTime();
    }


    @Override
    public void initData() {
        getTimeMsgList();
    }

    @Override
    public void onEvent() {

    }


    @OnClick({R.id.bar_iv_back, R.id.bar_tv_right, R.id.tv_date_select, R.id.tv_time_select})
    public void onViewClicked(View view) {
        Calendar calendar = Calendar.getInstance();

        switch (view.getId()) {
            case R.id.bar_iv_back:
                break;
            case R.id.bar_tv_right:
                if (barTvRight.getText().equals("确定")) {
                    date = tvDateSelect.getText().toString();
//                    time=tvTimeSelect.getText().toString();
//                    llAddTime.setVisibility(View.GONE);
//                    rvTimeData.setVisibility(View.VISIBLE);
//                    barTvRight.setText("添加");
//                    datalist.add(date+" "+startTime+"--"+endTime);
//                    timeDataAdapter.notifyDataSetChanged();
                    try {
                        String startT = TimeUtils.INSTANCE.dateToStamp(date + " " + startTime, "yyyy-MM-dd HH:mm");
                        String endT = TimeUtils.INSTANCE.dateToStamp(date + " " + endTime, "yyyy-MM-dd HH:mm");
                        subscribe(startT, endT);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } else {
                    llAddTime.setVisibility(View.VISIBLE);
                    rvTimeData.setVisibility(View.GONE);
                    barTvRight.setText("确定");
                }
                break;
            case R.id.tv_date_select:
//                TimeUtils.INSTANCE.showDatePickerDialog(MyLwyerTimeActivity.this
//                        , 1
//                        , calendar, onDateSetListener);
                if(pvDateTime!=null){
                    pvDateTime.show();
                }
                break;
            case R.id.tv_time_select:
//                TimeUtils.INSTANCE.showTimePickerDialog(MyLwyerTimeActivity.this
//                        , 1
//                        , calendar, onTimeSetListener);
                if(pvTime!=null){
                    pvTime.show();
                }
                break;
        }
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            month += 1;
            tvDateSelect.setText(year + "-" + oneTotwo(month + "") + "-" + oneTotwo(dayOfMonth + ""));
        }
    };

    public String oneTotwo(String one) {
        StringBuffer two = new StringBuffer(one);
        char[] arr = two.toString().toCharArray();
        if (arr.length == 1) {
            two.insert(0, "0");
        }
        return two.toString();
    }
    public void initShowDate(){
           pvDateTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
//                tvTime.setText(getTime(date));
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(date);
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH)+1;
                int day=calendar.get(Calendar.DAY_OF_MONTH);
               Logger.i("year="+year);
               Logger.i("month="+month);
               Logger.i("day="+day);
                tvDateSelect.setText(year + "-" + oneTotwo(month + "") + "-" + oneTotwo(day + ""));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .setContentTextSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setTitleBgColor(0xFFffffff)//标题背景颜色 Night mode
                .setBgColor(0xffffffff)//滚轮背景颜色 Night mode
//                .setRangDate(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                .setDate(new Date())// 默认是系统时间*/
                .setLabel("年","月","日","时","分","秒")
                .build();
    }
    public void initShowTime(){
        pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
//                tvTime.setText(getTime(date));
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(date);
                int HH=calendar.get(Calendar.HOUR_OF_DAY);
                int mm=calendar.get(Calendar.MINUTE);

                if(pvTime!=null){
                    if (isTime) {
                        Logger.i("HH="+HH);
                        Logger.i("mm="+mm);
                        isTime = false;
                        endTime = oneTotwo(HH + "") + ":" + oneTotwo(mm + "");
                        tvTimeSelect.setText(startTime + "-" + endTime);
                    } else {
                        Logger.i("HH="+HH);
                        Logger.i("mm="+mm);
                        startTime = oneTotwo(HH + "") + ":" + oneTotwo(mm + "");
                        pvEndTime.show();
                        isTime = true;
                    }
                }
            }
        })
                .setType(new boolean[]{false, false, false, true, true, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .setContentTextSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setTitleBgColor(0xFFffffff)//标题背景颜色 Night mode
                .setBgColor(0xffffffff)//滚轮背景颜色 Night mode
//                .setRangDate(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                .setDate(new Date())// 默认是系统时间*/
                .setLabel("年","月","日","时","分","秒")
                .build();
    }
    public void initEndShowTime(){
        pvEndTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
//                tvTime.setText(getTime(date));
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(date);
                int HH=calendar.get(Calendar.HOUR_OF_DAY);
                int mm=calendar.get(Calendar.MINUTE);

                if(pvTime!=null){
                    if (isTime) {
                        Logger.i("HH="+HH);
                        Logger.i("mm="+mm);
                        isTime = false;
                        endTime = oneTotwo(HH + "") + ":" + oneTotwo(mm + "");
                        tvTimeSelect.setText(startTime + "-" + endTime);
                    } else {
                        Logger.i("HH="+HH);
                        Logger.i("mm="+mm);
                        startTime = oneTotwo(HH + "") + ":" + oneTotwo(mm + "");
                        isTime = true;
                    }
                }
            }
        })
                .setType(new boolean[]{false, false, false, true, true, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .setContentTextSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setTitleBgColor(0xFFffffff)//标题背景颜色 Night mode
                .setBgColor(0xffffffff)//滚轮背景颜色 Night mode
//                .setRangDate(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                .setDate(new Date())// 默认是系统时间*/
                .setLabel("年","月","日","时","分","秒")
                .build();
    }
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//            tvTimeSelect
            if (isTime) {
                isTime = false;
                endTime = oneTotwo(hourOfDay + "") + ":" + oneTotwo(minute + "");
                tvTimeSelect.setText(startTime + "-" + endTime);
            } else {
                startTime = oneTotwo(hourOfDay + "") + ":" + oneTotwo(minute + "");
                Calendar calendar = Calendar.getInstance();
                TimeUtils.INSTANCE.showTimePickerDialog(MyLwyerTimeActivity.this
                        , 1
                        , calendar, onTimeSetListener);
                isTime = true;
            }
        }
    };

    public void subscribe(final String startTime, final String endTime) {
        Map<String, String> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        Gson gson = new Gson();
        String strEntity = gson.toJson(params);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);
        Observable<BaseEntity<Object>> observable = RetrofitFactory.getInstance().createTimeMsg(body);
        observable.compose(this.<BaseEntity<Object>>rxSchedulers()).subscribe(new BaseObserver<Object>() {
            @Override
            protected void onHandleSuccess(Object registerResult, String msg) {
                llAddTime.setVisibility(View.GONE);
                rvTimeData.setVisibility(View.VISIBLE);
                getTimeMsgList();
            }

            @Override
            protected void onHandleError(int statusCode, String msg) {
                NToast.shortToast(mContext, msg);
            }
        });
    }

    private void getTimeMsgList() {
        Observable<BaseEntity<TimeMsgBean>> observable = RetrofitFactory.getInstance()
                .getTimeMsgList(pageNo, pageSize);
        observable.compose(this.<BaseEntity<TimeMsgBean>>rxSchedulers()).subscribe(new BaseObserver<TimeMsgBean>() {

            @Override
            protected void onHandleSuccess(TimeMsgBean baseListEntity, String msg) {
//                loadComplete();
//                if (pageNo == 1) {//第一次加载或者是下拉加载
//                    mQuickAdapter.setNewData(baseListEntity.getResult());
//                    if (mQuickAdapter.getData().size() >= baseListEntity.getTotalCount()) {
//                        mQuickAdapter.loadMoreEnd();
//                    }
//                } else {
//                    mQuickAdapter.addData(baseListEntity.getResult());
//                    if (mQuickAdapter.getData().size() >= baseListEntity.getTotalCount()) {
//                        mQuickAdapter.loadMoreEnd();
//                    } else {
//                        mQuickAdapter.loadMoreComplete();
//                    }
//                }
                List<TimeMsgBean.ResultBean> resultBeans = baseListEntity.getResult();
                datalist.clear();
                if (resultBeans != null) {
                    datalist.addAll(resultBeans);
                }
                timeDataAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onHandleError(int statusCode, String msg) {
//                loadComplete();
//                if (pageNo>1){
//                    mQuickAdapter.loadMoreFail();
//                }
            }
        });
    }
    public void removeTime( String id,final int position) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        Gson gson = new Gson();
        String strEntity = gson.toJson(params);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);
        Observable<BaseEntity<Object>> observable = RetrofitFactory.getInstance().removeTime(body);
        observable.compose(this.<BaseEntity<Object>>rxSchedulers()).subscribe(new BaseObserver<Object>() {
            @Override
            protected void onHandleSuccess(Object registerResult, String msg) {
                datalist.remove(position);
                timeDataAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onHandleError(int statusCode, String msg) {
                NToast.shortToast(mContext, msg);
            }
        });
    }
}