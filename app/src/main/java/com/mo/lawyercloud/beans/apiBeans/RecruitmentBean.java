package com.mo.lawyercloud.beans.apiBeans;

/**
 * Created by Mohaifeng on 18/6/6.
 * 人才招聘
 */
public class RecruitmentBean {

    /**
     * id : 3
     * createTime : 1525847493000
     * post : 实习律师
     * count : 5
     * workPlace : 北京
     * content : 完成总监交代的工作完成总监交代的工作！
     * status : 1
     */

    private int id;
    private long createTime;
    private String post;
    private int count;
    private String workPlace;
    private String content;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
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
