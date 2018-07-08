package com.mo.lawyercloud.beans.apiBeans;

import java.util.List;

/**
 * Created by Mohaifeng on 18/6/11.
 * 咨询管理返回类
 */
public class AdvisoryOrderBean {


    /**
     * id : 15
     * userDTO : {"id":6,"username":null,"password":null,"newPassword":null,"realName":"grin",
     * "nickname":null,"mobileCode":null,"accessToken":null,"gender":1,"location":null,
     * "company":null,"resume":null,"avatar":"http://localhost/store/avatar/1526263513505.jpg",
     * "channels":null,"jsessionid":null}
     * orderNo : 437730466458
     * createTime : 1527575045000
     * status : 3
     * finishTime : 1527575745000
     * problem : 问题问题问题问题
     * attachments : [{"id":21,"image":"http://localhost/store/image/133213213.png"},{"id":22,
     * "image":"http://localhost/store/image/234231321.png"}]
     * timeMsg : {"id":11,"startTime":1527577200000,"endTime":1527580800000,"status":2}
     * channel : {"id":1,"name":"婚姻家事","icon":null}
     * realPrice : 0
     * videoUrl : http://1256263304.vod2.myqcloud
     * .com/106e5bffvodgzp1256263304/48f0812e7447398156310578344/GCcGMmcwztwA.mp4
     * comment : {"id":4,"score":5,"quickReply":null,"content":"不错","isDefault":0}
     */

    private int id;
    private UserDTOBean userDTO;
    private String orderNo;
    private long createTime;
    private int status;
    private long finishTime;
    private String problem;
    private TimeMsgBean timeMsg;
    private ChannelBean channel;
    private int realPrice;
    private String videoUrl;
    private CommentBean comment;
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

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
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

    public int getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(int realPrice) {
        this.realPrice = realPrice;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public CommentBean getComment() {
        return comment;
    }

    public void setComment(CommentBean comment) {
        this.comment = comment;
    }

    public List<AttachmentsBean> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentsBean> attachments) {
        this.attachments = attachments;
    }

    public static class UserDTOBean {
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
         * resume : null
         * avatar : http://localhost/store/avatar/1526263513505.jpg
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
        private String avatar;
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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
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
         * id : 11
         * startTime : 1527577200000
         * endTime : 1527580800000
         * status : 2
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

    public static class ChannelBean {
        /**
         * id : 1
         * name : 婚姻家事
         * icon : null
         */

        private int id;
        private String name;
        private Object icon;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getIcon() {
            return icon;
        }

        public void setIcon(Object icon) {
            this.icon = icon;
        }
    }

    public static class CommentBean {
        /**
         * id : 4
         * score : 5
         * quickReply : null
         * content : 不错
         * isDefault : 0
         */

        private int id;
        private int score;
        private String quickReply;
        private String content;
        private int isDefault;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getQuickReply() {
            return quickReply;
        }

        public void setQuickReply(String quickReply) {
            this.quickReply = quickReply;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }
    }

    public static class AttachmentsBean {
        /**
         * id : 21
         * image : http://localhost/store/image/133213213.png
         */

        private int id;
        private String image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
