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
