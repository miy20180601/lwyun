package com.mo.lawyercloud.beans.apiBeans;

/**
 * @author cui
 * @date 2018/6/9/009
 * @annotation 我的发票 数据类
 */
public class InvoiceDataBean {
    String id;
    String  orderID;//订单号
    String state;//交易状态
    String type;//咨询类目
    String time;//时间
    String money;//金额

    public InvoiceDataBean(String id, String orderID, String state, String type, String time, String money) {
        this.id = id;
        this.orderID = orderID;
        this.state = state;
        this.type = type;
        this.time = time;
        this.money = money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
