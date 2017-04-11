package com.yqx.mamajh.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 银行卡
 */

public class BankCard {

    private List<BankCardBean> BankCard;

    public List<BankCardBean> getBankCard() {
        return BankCard;
    }

    public void setBankCard(List<BankCardBean> BankCard) {
        this.BankCard = BankCard;
    }

    public static class BankCardBean implements Serializable {
        /**
         * ID : 6
         * BankName : 中国农业银行
         * Number : 6217458963258789961
         * Name : 粤语
         */

        private int    ID;
        private String BankName;
        private String Number;
        private String Name;
        private String Img;

        public String getImg() {
            return Img;
        }

        public void setImg(String img) {
            Img = img;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
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
    }
}
