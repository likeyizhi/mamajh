package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by likey on 2017/4/11.
 */

public class UserCenterBankCard {
    /**    "status": 0,
     "mes": "成功",
     "res": [{"BankCard": [{"ID": "7", "BankName": "中国农业银行", "Number": "00922223232", "Name": "peter", "Img": "http://182.92.183.143:8002/webimg/img_bank/defaultupload.png"
     }, {"ID": "16", "BankName": "中国农业银行", "Number": "123456789", "Name": "给我", "Img": "http://182.92.183.143:8002/webimg/img_bank/defaultupload.png"
     }, {"ID": "8", "BankName": "中国工商银行", "Number": "6222024200014514862", "Name": "张震宇", "Img": "http://182.92.183.143:8002/webimg/img_bank/defaultupload.png"
     }, {"ID": "15", "BankName": "中国银行", "Number": "65858566558", "Name": "几点", "Img": "http://182.92.183.143:8002/webimg/img_bank/defaultupload.png"
     }, {"ID": "10", "BankName": "中国建设银行", "Number": "6855565856", "Name": "幾", "Img": "http://182.92.183.143:8002/webimg/img_bank/defaultupload.png"
     }, {"ID": "17", "BankName": "中国建设银行", "Number": "55", "Name": "嗯", "Img": "http://182.92.183.143:8002/webimg/img_bank/defaultupload.png"
     }, {"ID": "11", "BankName": "中信银行", "Number": "7558555575", "Name": "也不知道", "Img": "http://182.92.183.143:8002/webimg/img_bank/defaultupload.png"
     }, {"ID": "9", "BankName": "中国光大银行", "Number": "655455555575658", "Name": "张震宇2", "Img": "http://182.92.183.143:8002/webimg/img_bank/defaultupload.png"
     }
     ]
     }
     ],
     "account": "6669.00"*/

    private int status;
    private String mes;
    private List<UserCenterBankCardRes> res;
    private String account;

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

    public List<UserCenterBankCardRes> getRes() {
        return res;
    }

    public void setRes(List<UserCenterBankCardRes> res) {
        this.res = res;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public static class UserCenterBankCardRes{
        private List<UserCenterBankCards> BankCard;

        public List<UserCenterBankCards> getBankCard() {
            return BankCard;
        }

        public void setBankCard(List<UserCenterBankCards> bankCard) {
            BankCard = bankCard;
        }

        public static class UserCenterBankCards{
            private String ID;
            private String BankName;
            private String Number;
            private String Name;
            private String Img;

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getBankName() {
                return BankName;
            }

            public void setBankName(String bankName) {
                BankName = bankName;
            }

            public String getNumber() {
                return Number;
            }

            public void setNumber(String number) {
                Number = number;
            }

            public String getName() {
                return Name;
            }

            public void setName(String name) {
                Name = name;
            }

            public String getImg() {
                return Img;
            }

            public void setImg(String img) {
                Img = img;
            }
        }
    }
}
