package com.mo.lawyercloud.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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


    /**
     * 将时间转换为时间戳
     * @param dates 时间 2018-09-01 12：20：10
     * @param regular 时间的格式 yyyy-MM-dd HH:mm:ss
     * @return
     * @throws ParseException
     */
    public String dateToStamp(String dates,String regular) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(regular);
        Date date = simpleDateFormat.parse(dates);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /**
     *  时间戳换时间
     * @param time 时间戳
     * @param regular //这里的格式可换"yyyy年-MM月dd日-HH时mm分ss秒"等等格式
     * @return
     */
    public String timetodate(String time,String regular) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.valueOf(time));
        SimpleDateFormat sf = new SimpleDateFormat(regular);
        String date = sf.format(calendar.getTime());
        return date;

    }
}
