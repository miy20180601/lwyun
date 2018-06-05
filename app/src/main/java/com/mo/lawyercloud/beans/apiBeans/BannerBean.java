package com.mo.lawyercloud.beans.apiBeans;

/**
 * Created by Mohaifeng on 18/5/31.
 */
public class BannerBean {

    /**
     * id : 1
     * createTime : 1525849744000
     * image : http://localhost/store/uploads/1525849739884.jpg
     * rank : 0
     * name : 海报
     * status : 1
     * urlType : 1
     * url : 123
     */

    private int id;
    private long createTime;
    private String image;
    private int rank;
    private String name;
    private int status;
    private int urlType;
    private String url;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUrlType() {
        return urlType;
    }

    public void setUrlType(int urlType) {
        this.urlType = urlType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
