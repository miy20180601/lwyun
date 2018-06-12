package com.mo.lawyercloud.beans.apiBeans;

import java.util.List;

/**
 * Created by Mohaifeng on 18/6/11.
 */
public class ReserveOrderBean {

    /**
     * id : 6
     * userDTO : {"id":4,"username":null,"password":null,"newPassword":null,"realName":"张三",
     * "nickname":null,"mobileCode":null,"accessToken":null,"gender":0,"location":null,
     * "company":null,"resume":null,"avatar":null,"channels":null,"jsessionid":null}
     * orderNo : 930592403955
     * createTime : 1526113923000
     * status : 4
     * finishTime : null
     * problem : 问题问题问题问题
     * attachments : [{"id":3,"image":"http://localhost/store/image/123.png"},{"id":4,
     * "image":"http://localhost/store/image/234.png"}]
     * timeMsg : {"id":3,"startTime":1526094000000,"endTime":1526097600000,"status":1}
     * channel : {"id":1,"name":"婚姻家事","icon":null}
     */

    private int id;
    private UserDTOBean userDTO;
    private String orderNo;
    private long createTime;
    private int status;
    private Object finishTime;
    private String problem;
    private TimeMsgBean timeMsg;
    private ChannelBean channel;
    private List<AttachmentsBean> attachments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDTOBean getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTOBean userDTO) {
        this.userDTO = userDTO;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Object finishTime) {
        this.finishTime = finishTime;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public TimeMsgBean getTimeMsg() {
        return timeMsg;
    }

    public void setTimeMsg(TimeMsgBean timeMsg) {
        this.timeMsg = timeMsg;
    }

    public ChannelBean getChannel() {
        return channel;
    }

    public void setChannel(ChannelBean channel) {
        this.channel = channel;
    }

    public List<AttachmentsBean> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentsBean> attachments) {
        this.attachments = attachments;
    }

    public static class UserDTOBean {
        /**
         * id : 4
         * username : null
         * password : null
         * newPassword : null
         * realName : 张三
         * nickname : null
         * mobileCode : null
         * accessToken : null
         * gender : 0
         * location : null
         * company : null
         * resume : null
         * avatar : null
         * channels : null
         * jsessionid : null
         */

        private int id;
        private Object username;
        private Object password;
        private Object newPassword;
        private String realName;
        private Object nickname;
        private Object mobileCode;
        private Object accessToken;
        private int gender;
        private Object location;
        private Object company;
        private Object resume;
        private Object avatar;
        private Object channels;
        private Object jsessionid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getUsername() {
            return username;
        }

        public void setUsername(Object username) {
            this.username = username;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public Object getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(Object newPassword) {
            this.newPassword = newPassword;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public Object getNickname() {
            return nickname;
        }

        public void setNickname(Object nickname) {
            this.nickname = nickname;
        }

        public Object getMobileCode() {
            return mobileCode;
        }

        public void setMobileCode(Object mobileCode) {
            this.mobileCode = mobileCode;
        }

        public Object getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(Object accessToken) {
            this.accessToken = accessToken;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public Object getLocation() {
            return location;
        }

        public void setLocation(Object location) {
            this.location = location;
        }

        public Object getCompany() {
            return company;
        }

        public void setCompany(Object company) {
            this.company = company;
        }

        public Object getResume() {
            return resume;
        }

        public void setResume(Object resume) {
            this.resume = resume;
        }

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
        }

        public Object getChannels() {
            return channels;
        }

        public void setChannels(Object channels) {
            this.channels = channels;
        }

        public Object getJsessionid() {
            return jsessionid;
        }

        public void setJsessionid(Object jsessionid) {
            this.jsessionid = jsessionid;
        }
    }

    public static class TimeMsgBean {
        /**
         * id : 3
         * startTime : 1526094000000
         * endTime : 1526097600000
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


}
