package com.yqx.mamajh.bean;

/**
 * 提现申请保存
 */

public class WithdrawalsSave {

    /**
     * Status : 0
     * mes : 操作成功
     * OrderNumber : T20161013121058684
     */

    private int    Status;
    private String mes;
    private String OrderNumber;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String OrderNumber) {
        this.OrderNumber = OrderNumber;
    }
}
