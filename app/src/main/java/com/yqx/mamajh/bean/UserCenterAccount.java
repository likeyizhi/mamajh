package com.yqx.mamajh.bean;

/**
 * 我的资产
 */

public class UserCenterAccount {

    /**
     * ID : 52
     * MainPrice : 980.00
     * Score : 10000
     * Score2 : 0
     * CardCount : 1
     * VouchersCount : 0
     * Withdrawals : 1980.00
     */

    private int    ID;
    private double MainPrice;
    private int    Score;
    private int    Score2;
    private int    CardCount;
    private int    VouchersCount;
    private double Withdrawals;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getMainPrice() {
        return MainPrice;
    }

    public void setMainPrice(double MainPrice) {
        this.MainPrice = MainPrice;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int Score) {
        this.Score = Score;
    }

    public int getScore2() {
        return Score2;
    }

    public void setScore2(int Score2) {
        this.Score2 = Score2;
    }

    public int getCardCount() {
        return CardCount;
    }

    public void setCardCount(int CardCount) {
        this.CardCount = CardCount;
    }

    public int getVouchersCount() {
        return VouchersCount;
    }

    public void setVouchersCount(int VouchersCount) {
        this.VouchersCount = VouchersCount;
    }

    public double getWithdrawals() {
        return Withdrawals;
    }

    public void setWithdrawals(double Withdrawals) {
        this.Withdrawals = Withdrawals;
    }
}
