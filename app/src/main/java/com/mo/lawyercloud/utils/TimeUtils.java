package com.mo.lawyercloud.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author CUI
 * @data 2018/06/08
 * @details 时间工具类
 */
public enum  TimeUtils {
    INSTANCE;

    /**
     * 根据需求回调系统时间
     * @param regular 回调时间的格式  24小时制用大写的HH-mm-ss ，12小时制用小写的hh-mm-ss
     * @return
     * "yyyy-MM-dd HH-mm-ss"
     */
    public String getTimeData(String regular){
        SimpleDateFormat sdf = new SimpleDateFormat(regular, Locale.getDefault());
        String date = sdf.format(new java.util.Date());
        return date;
    }

    /**
     * 日期选择
     *
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public void showDatePickerDialog(Activity activity, int themeResId, Calendar calendar,DatePickerDialog.OnDateSetListener listener) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId, listener
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    /**
     * 时间选择
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public void showTimePickerDialog(Activity activity,int themeResId, Calendar calendar,TimePickerDialog.OnTimeSetListener listener) {
        // Calendar c = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来
        // 解释一哈，Activity是context的子类
        new TimePickerDialog( activity,themeResId,
                // 绑定监听器
                listener
                // 设置初始时间
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true表示采用24小时制
                ,true).show();
    }
}
