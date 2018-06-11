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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
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
                TimeUtils.INSTANCE.showDatePickerDialog(MyLwyerTimeActivity.this
                        , 1
                        , calendar, onDateSetListener);
                break;
            case R.id.tv_time_select:
                TimeUtils.INSTANCE.showTimePickerDialog(MyLwyerTimeActivity.this
                        , 1
                        , calendar, onTimeSetListener);
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
