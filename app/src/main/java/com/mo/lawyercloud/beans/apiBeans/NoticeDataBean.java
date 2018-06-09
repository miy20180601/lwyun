package com.mo.lawyercloud.beans.apiBeans;

/**
 * @author cui
 * @date 2018/6/9/009
 * @annotation 预约通知的数据类
 */
public class NoticeDataBean {

    String id;
    String date;//日期
    String name;//姓名
    String type;//咨询类型
    String question;//  咨询题目
    String time;//预约时间

    public NoticeDataBean(String id, String date, String name, String type, String question, String time) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.type = type;
        this.question = question;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
