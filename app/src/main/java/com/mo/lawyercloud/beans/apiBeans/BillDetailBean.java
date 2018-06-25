package com.mo.lawyercloud.beans.apiBeans;

/**
 * Created by Mohaifeng on 18/6/25.
 */
public class BillDetailBean {


    /**
     * username : grin
     * duration : 0
     * time : 1527575745000
     * price : 50
     * payType : 0
     */

    private String username;
    private long duration;  //通话时长
    private long time;
    private double price;
    private int payType;  //支付方式 1支付宝 2微信

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }
}
