package com.yqx.mamajh.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 充值订单返回数据
 */

public class TopUpOrder {

    /**
     * order	String	充值单号
     * money	Int	充值金额
     * payInfo	String	微信返回信息
     * appId	String
     * partnerId	String
     * prepayId	String
     * timeStamp	String
     * nonceStr	String
     * package	String
     * signType	String
     * paySign	String
     */

    private String  order;
    private String  money;
    private PayInfo payInfo;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public PayInfo getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(PayInfo payInfo) {
        this.payInfo = payInfo;
    }

    public static class PayInfo {
        /**
         * appId : wxd678efh567hg6787
         * partnerId : 1230000109
         * prepayId : 33999232222
         * timeStamp : 2016101222111
         * nonceStr : 78843433rr4
         * package : 32233
         * signType : md5
         * paySign : C380BEC2BFD727A4B6845133519F3AD6
         */

        private String appId;
        private String partnerId;
        private String prepayId;
        private String timeStamp;
        private String nonceStr;
        @SerializedName("package")
        private String packageX;
        private String signType;
        private String paySign;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getPartnerId() {
            return partnerId;
        }

        public void setPartnerId(String partnerId) {
            this.partnerId = partnerId;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getSignType() {
            return signType;
        }

        public void setSignType(String signType) {
            this.signType = signType;
        }

        public String getPaySign() {
            return paySign;
        }

        public void setPaySign(String paySign) {
            this.paySign = paySign;
        }
    }
}
