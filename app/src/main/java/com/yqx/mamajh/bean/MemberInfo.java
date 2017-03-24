package com.yqx.mamajh.bean;

/**
 * Created by Drago on 2016/12/15 015.
 */

public class MemberInfo {
    /**
     * Id	Int	会员ID
     * nickname	String	昵称
     * levelName	String	级别名称
     * sex	Int	性别1=男
     * face	String	头像地址
     * mainprice	decimal	主账户
     * subprice	decimal	副账户
     * score	String	积分
     * provinceid	Int	省ID
     * cityid	Int	市ID
     * areaid	Int	区域ID
     * email	String	邮箱
     * address	String	地址
     * postalcode	String	邮编
     * mobile	String	电话
     * phone	String	固定电话
     */

    private int    id;
    private String nickname;
    private String account;
    private String name;
    private String levelName;
    private int    sex;
    private String face;
    private double mainprice;
    private double subprice;
    private String score;
    private int    provinceid;
    private int    cityid;
    private int    areaid;
    private String email;
    private String address;
    private String postalcode;
    private String mobile;
    private String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public double getMainprice() {
        return mainprice;
    }

    public void setMainprice(double mainprice) {
        this.mainprice = mainprice;
    }

    public double getSubprice() {
        return subprice;
    }

    public void setSubprice(double subprice) {
        this.subprice = subprice;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(int provinceid) {
        this.provinceid = provinceid;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public int getAreaid() {
        return areaid;
    }

    public void setAreaid(int areaid) {
        this.areaid = areaid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
