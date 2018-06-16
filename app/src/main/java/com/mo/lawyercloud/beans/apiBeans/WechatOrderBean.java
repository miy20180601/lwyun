package com.mo.lawyercloud.beans.apiBeans;

/**
 * Created by mo on 2018/6/16.
 */

public class WechatOrderBean {

    /**
     * response : {"sign":"17D4CB58705929C9DB4B948262AC07AE","mchId":"1485509562","nonceStr":"E2g5Y3lYjOIZ3MK6","prepayId":"wx081649557432922a846a51d23628264732","resultCode":"SUCCESS","returnCode":"SUCCESS","timestamp":"1525769395","packages":"Sign=WXPay"}
     * id : 20
     */

    private ResponseBean response;
    private int id;

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class ResponseBean {
        /**
         * sign : 17D4CB58705929C9DB4B948262AC07AE
         * mchId : 1485509562
         * nonceStr : E2g5Y3lYjOIZ3MK6
         * prepayId : wx081649557432922a846a51d23628264732
         * resultCode : SUCCESS
         * returnCode : SUCCESS
         * timestamp : 1525769395
         * packages : Sign=WXPay
         */

        private String sign;
        private String mchId;
        private String nonceStr;
        private String prepayId;
        private String resultCode;
        private String returnCode;
        private String timestamp;
        private String packages;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getMchId() {
            return mchId;
        }

        public void setMchId(String mchId) {
            this.mchId = mchId;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public String getReturnCode() {
            return returnCode;
        }

        public void setReturnCode(String returnCode) {
            this.returnCode = returnCode;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getPackages() {
            return packages;
        }

        public void setPackages(String packages) {
            this.packages = packages;
        }
    }
}
