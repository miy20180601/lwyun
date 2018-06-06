package com.mo.lawyercloud.beans.apiBeans;

import java.io.Serializable;

/**
 * Created by Mohaifeng on 18/5/31.
 */
public class MemberBean implements Serializable{

    /**
     * id : 6
     * username : 13267140989
     * password :
     * role : null
     * accountNonLocked : true
     * accessToken : SEWN6IVQQWF1g5PvlPl0IA==
     * refreshToken : X0EvuEZWNWU7wnDd/KFcIg==
     * createTime : 1525946307000
     * lastLoginTime : 1526262833000
     * loginTime : 1526262833000
     * loginIp : null
     * nickname : null
     * realName : grin
     * mobile : 13267140989
     * avatar : http://localhost/store/avatar/1526263513505.jpg
     * type : 2
     * mail : null
     * company : 广州律师事务所
     * mobileCode : null
     * formatDateStr : null
     * profession : 律师
     * resume : 个人简历个人简历个人简历
     * location : 广州
     * paperwork : 12345.png
     * channels : 1,2
     * gender : 0
     * balance : 0
     * txSig : null
     */

    private int id;
    private String username;
    private String password;
    private String role;
    private boolean accountNonLocked;
    private String accessToken;
    private String refreshToken;
    private long createTime;
    private long lastLoginTime;
    private long loginTime;
    private String loginIp;
    private String nickname;
    private String realName;
    private String mobile;
    private String avatar;
    private int type;
    private String mail;
    private String company;
    private String mobileCode;
    private String formatDateStr;
    private String profession;
    private String resume;
    private String location;
    private String paperwork;
    private String channels;
    private int gender;
    private int balance;
    private String txSig;


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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }

    public String getFormatDateStr() {
        return formatDateStr;
    }

    public void setFormatDateStr(String formatDateStr) {
        this.formatDateStr = formatDateStr;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPaperwork() {
        return paperwork;
    }

    public void setPaperwork(String paperwork) {
        this.paperwork = paperwork;
    }

    public String getChannels() {
        return channels;
    }

    public void setChannels(String channels) {
        this.channels = channels;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getTxSig() {
        return txSig;
    }

    public void setTxSig(String txSig) {
        this.txSig = txSig;
    }
}
