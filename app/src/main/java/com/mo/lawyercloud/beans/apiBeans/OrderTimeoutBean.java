package com.mo.lawyercloud.beans.apiBeans;

/**
 * Created by Mohaifeng on 18/7/10.
 * 视频超时订单返回
 */
public class OrderTimeoutBean {


    /**
     * id : 13
     * userId : 3
     * solicitorId : 6
     */

    private int id;
    private int userId;
    private int solicitorId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSolicitorId() {
        return solicitorId;
    }

    public void setSolicitorId(int solicitorId) {
        this.solicitorId = solicitorId;
    }
}
