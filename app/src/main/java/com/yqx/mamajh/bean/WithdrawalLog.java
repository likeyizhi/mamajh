package com.yqx.mamajh.bean;

import java.util.List;

/**
 * 提现记录
 */

public class WithdrawalLog {

    /**
     * status : 0
     * mes : 成功
     * res : [{"WithdrawalList":[{"ID":"8","BankName":"中国农业银行","Number":"6217458963258789961","Name":"粤语","Money":"20.00","Time":"2016-11-25 13:10:25"},{"ID":"9","BankName":"中国农业银行","Number":"6217458963258789961","Name":"粤语","Money":"20.00","Time":"2016-11-25 14:13:25"},{"ID":"10","BankName":"中国农业银行","Number":"6217458963258789961","Name":"粤语","Money":"20.00","Time":"2016-11-25 14:15:25"},{"ID":"11","BankName":"中国农业银行","Number":"6217458963258789961","Name":"粤语","Money":"1.00","Time":"2016-12-01 15:44:01"}]}]
     */

    private int status;
    private String        mes;
    private List<ResBean> res;

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

    public List<ResBean> getRes() {
        return res;
    }

    public void setRes(List<ResBean> res) {
        this.res = res;
    }

    public static class ResBean {
        private List<WithdrawalListBean> WithdrawalList;

        public List<WithdrawalListBean> getWithdrawalList() {
            return WithdrawalList;
        }

        public void setWithdrawalList(List<WithdrawalListBean> WithdrawalList) {
            this.WithdrawalList = WithdrawalList;
        }

        public static class WithdrawalListBean {
            /**
             * ID : 8
             * BankName : 中国农业银行
             * Number : 6217458963258789961
             * Name : 粤语
             * Money : 20.00
             * Time : 2016-11-25 13:10:25
             */

            private String ID;
            private String BankName;
            private String Number;
            private String Name;
            private String Money;
            private String Time;

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getBankName() {
                return BankName;
            }

            public void setBankName(String BankName) {
                this.BankName = BankName;
            }

            public String getNumber() {
                return Number;
            }

            public void setNumber(String Number) {
                this.Number = Number;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getMoney() {
                return Money;
            }

            public void setMoney(String Money) {
                this.Money = Money;
            }

            public String getTime() {
                return Time;
            }

            public void setTime(String Time) {
                this.Time = Time;
            }
        }
    }
}
