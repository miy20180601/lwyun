package com.mo.lawyercloud.beans.apiBeans;

/**
 * Created by Mohaifeng on 18/6/10.
 * 律师可预约时间
 */
public class ReserveTimeBean {

    /**
     * id : 4
     * startTime : 1526353200000
     * endTime : 1526356800000
     * status : 1
     */

    private int id;
    private long startTime;
    private long endTime;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
