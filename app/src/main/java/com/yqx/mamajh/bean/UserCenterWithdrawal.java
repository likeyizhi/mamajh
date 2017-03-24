package com.yqx.mamajh.bean;

/**
 * 会员中心提现
 * 返回申请提现银行、限额、可提取金额
 */

public class UserCenterWithdrawal {

    /**
     * status : 0
     * mes : 成功
     * BankName : 中国银行
     * BankNum : 0000000000000000000
     * UserName : 测试
     * Limit : 2000
     * CanOut : 2000.00
     * Balance : 2531.0000
     */

    private int    status;
    private String mes;
    private String BankName;
    private String BankNum;
    private String UserName;
    private double Limit;
    private String CanOut;
    private String Balance;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String BankName) {
        this.BankName = BankName;
    }

    public String getBankNum() {
        return BankNum;
    }

    public void setBankNum(String BankNum) {
        this.BankNum = BankNum;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public double getLimit() {
        return Limit;
    }

    public void setLimit(double Limit) {
        this.Limit = Limit;
    }

    public String getCanOut() {
        return CanOut;
    }

    public void setCanOut(String CanOut) {
        this.CanOut = CanOut;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String Balance) {
        this.Balance = Balance;
    }
}
