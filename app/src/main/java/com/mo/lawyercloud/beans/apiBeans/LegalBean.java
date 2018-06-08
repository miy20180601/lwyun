package com.mo.lawyercloud.beans.apiBeans;

/**
 * Created by Mohaifeng on 18/6/6.
 * 法规常识
 */
public class LegalBean {

    /**
     * id : 1
     * time : 1525773955000
     * title : 法律常识1
     * content : <p>法律常识法律常识</p>
     * status : 2
     */

    private int id;
    private long time;
    private String title;
    private String content;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
