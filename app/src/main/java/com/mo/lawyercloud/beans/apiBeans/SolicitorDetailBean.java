package com.mo.lawyercloud.beans.apiBeans;

import java.io.Serializable;

/**
 * Created by Mohaifeng on 18/6/10.
 */
public class SolicitorDetailBean implements Serializable {

    /**
     * id : 6
     * username : null
     * password : null
     * newPassword : null
     * realName : grin
     * nickname : null
     * mobileCode : null
     * accessToken : null
     * gender : 1
     * location : null
     * company : null
     * resume : 本人擅长处理婚姻家事、公司工商、合同纠纷等。
     * avatar : http://localhost/store/avatar/1526263513505.jpg
     * channels : 1,2
     * jsessionid : null
     */

    private int id;
    private String username;
    private String password;
    private String newPassword;
    private String realName;
    private String nickname;
    private String mobileCode;
    private String accessToken;
    private int gender;
    private String location;
    private String company;
    private String resume;
    private String avatar;
    private String channels;
    private String jsessionid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getChannels() {
        return channels;
    }

    public void setChannels(String channels) {
        this.channels = channels;
    }

    public String getJsessionid() {
        return jsessionid;
    }

    public void setJsessionid(String jsessionid) {
        this.jsessionid = jsessionid;
    }
}
