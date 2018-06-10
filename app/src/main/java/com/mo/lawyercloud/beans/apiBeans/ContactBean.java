package com.mo.lawyercloud.beans.apiBeans;

/**
 * @author cui
 * @date 2018/6/10/010
 * @annotation 客服信息
 */
public class ContactBean {

    /**
     * id : 1
     * phone : 123456
     * email : 123@51feijin.com
     * wechat : 51feijin
     */

    private int id;
    private String phone;
    private String email;
    private String wechat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }
}
