package com.yqx.mamajh.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 微信支付
 */

public class WeiXinPay implements Parcelable {

    /**
     * payFinish : 0
     * appid : wx864005a376a0e25c
     * partnerid : 1429465902
     * prepayid : wx201703070207524d66c5055e0048079164
     * timestamp : 1488823673
     * noncestr : 01386BD6D8E091C2AB4C7C7DE644D37B
     * package : Sign=WXPay
     * signType : MD5
     * sign : 688982B88395170AC6FF3401336F5623
     */

    private String payFinish;
    private String appid;
    private String partnerid;
    private String prepayid;
    private String timestamp;
    private String noncestr;
    @SerializedName("package")
    private String packageX;
    private String signType;
    private String sign;

    public String getPayFinish() {
        return payFinish;
    }

    public void setPayFinish(String payFinish) {
        this.payFinish = payFinish;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.payFinish);
        dest.writeString(this.appid);
        dest.writeString(this.partnerid);
        dest.writeString(this.prepayid);
        dest.writeString(this.timestamp);
        dest.writeString(this.noncestr);
        dest.writeString(this.packageX);
        dest.writeString(this.signType);
        dest.writeString(this.sign);
    }

    public WeiXinPay() {
    }

    protected WeiXinPay(Parcel in) {
        this.payFinish = in.readString();
        this.appid = in.readString();
        this.partnerid = in.readString();
        this.prepayid = in.readString();
        this.timestamp = in.readString();
        this.noncestr = in.readString();
        this.packageX = in.readString();
        this.signType = in.readString();
        this.sign = in.readString();
    }

    public static final Parcelable.Creator<WeiXinPay> CREATOR = new Parcelable.Creator<WeiXinPay>() {
        @Override
        public WeiXinPay createFromParcel(Parcel source) {
            return new WeiXinPay(source);
        }

        @Override
        public WeiXinPay[] newArray(int size) {
            return new WeiXinPay[size];
        }
    };
}
