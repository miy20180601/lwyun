package com.mo.lawyercloud.beans.apiBeans;

/**
 * Created by Mohaifeng on 18/6/16.
 */
public class BillingRecordsBean {

    /**
     * id : 1
     * source : 1
     * type : 2
     * price : 50
     * createTime : 1528877367000
     * orderId : 15
     */


    private int id;
    private int source;
    private int type;  //1为收入 2支出 3提现
    private double price;
    private long createTime;
    private int orderId;
    private int withdrawalStatus; //提现状态：0、提现中 1、提现成功 2、提现失败
    private String realName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getWithdrawalStatus() {
        return withdrawalStatus;
    }

    public void setWithdrawalStatus(int withdrawalStatus) {
        this.withdrawalStatus = withdrawalStatus;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
