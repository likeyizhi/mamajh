package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by likey on 2017/4/7.
 */

public class DictionaryBank {
    /**"status": 0,
     "mes":
     "成功",
     "res": {}*/
    private int status;
    private String mes;
    private DictionaryBankRes res;

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

    public DictionaryBankRes getRes() {
        return res;
    }

    public void setRes(DictionaryBankRes res) {
        this.res = res;
    }

    public static class DictionaryBankRes{
        private List<DictionaryBankList> list;

        public List<DictionaryBankList> getList() {
            return list;
        }

        public void setList(List<DictionaryBankList> list) {
            this.list = list;
        }

        public static class DictionaryBankList{
            /**"Id": 1,
             "Name": "中国农业银行",
             "Logo": "http://182.92.183.143:8002/webimg/img_bank/defaultupload.png",
             "BankNo": "123456789"*/
            private int Id;
            private String Name;
            private String Logo;
            private String BankNo;

            public int getId() {
                return Id;
            }

            public void setId(int id) {
                Id = id;
            }

            public String getName() {
                return Name;
            }

            public void setName(String name) {
                Name = name;
            }

            public String getLogo() {
                return Logo;
            }

            public void setLogo(String logo) {
                Logo = logo;
            }

            public String getBankNo() {
                return BankNo;
            }

            public void setBankNo(String bankNo) {
                BankNo = bankNo;
            }
        }
    }
}
