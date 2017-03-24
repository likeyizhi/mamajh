package com.yqx.mamajh.bean;

/**
 * 个人中心
 */

public class MemeberIndex {

    /**
     * Id	Int	会员ID
     * HeadPhoto	String	头像地址
     * Account	String	账号
     * Sex	Int	1=男
     * MainPrice	Decimal	主账号余额
     * Score	Int	账号金币
     * Score2	Int	银币
     * Level	Int	级别
     * PostalCode	String	邮编
     * MobilePhone	String	电话
     * dfk	Int	代付款数量
     * dfh	int	代发货数量
     * dsh	int	待收货数量
     * dpj	int	待评价数量
     */

    private int    ID;
    private String HeadPhoto;
    private String NickName;
    private String Account;
    private int    Sex;
    private double MainPrice;
    private String Score;
    private int    Score2;
    private String Level;
    private String PostalCode;
    private String MobilePhone;
    private int    dfk;
    private int    dfh;
    private int    dsh;
    private int    dpj;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getHeadPhoto() {
        return HeadPhoto;
    }

    public void setHeadPhoto(String HeadPhoto) {
        this.HeadPhoto = HeadPhoto;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String Account) {
        this.Account = Account;
    }

    public int getSex() {
        return Sex;
    }

    public void setSex(int Sex) {
        this.Sex = Sex;
    }

    public double getMainPrice() {
        return MainPrice;
    }

    public void setMainPrice(double MainPrice) {
        this.MainPrice = MainPrice;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String Score) {
        this.Score = Score;
    }

    public int getScore2() {
        return Score2;
    }

    public void setScore2(int Score2) {
        this.Score2 = Score2;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String Level) {
        this.Level = Level;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String PostalCode) {
        this.PostalCode = PostalCode;
    }

    public String getMobilePhone() {
        return MobilePhone;
    }

    public void setMobilePhone(String MobilePhone) {
        this.MobilePhone = MobilePhone;
    }

    public int getDfk() {
        return dfk;
    }

    public void setDfk(int dfk) {
        this.dfk = dfk;
    }

    public int getDfh() {
        return dfh;
    }

    public void setDfh(int dfh) {
        this.dfh = dfh;
    }

    public int getDsh() {
        return dsh;
    }

    public void setDsh(int dsh) {
        this.dsh = dsh;
    }

    public int getDpj() {
        return dpj;
    }

    public void setDpj(int dpj) {
        this.dpj = dpj;
    }
}
