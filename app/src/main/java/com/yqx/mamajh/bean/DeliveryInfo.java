package com.yqx.mamajh.bean;

/**
 * Created by likey on 2017/3/17.
 */

public class DeliveryInfo {
    /**"status":0,
     "mes":"成功",
     "res":{}*/
    private  int status;
    private String mes;
    private DeliveryInfoRes res;

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

    public DeliveryInfoRes getRes() {
        return res;
    }

    public void setRes(DeliveryInfoRes res) {
        this.res = res;
    }

    public static class DeliveryInfoRes {
        /**"name":"lkt",
         "address":"北京八里庄",
         "phone":"13716974109",
         "phone2":"820820",
         "postcode":"10000",
         "provinceid":"2",
         "cityid":"36",
         "areaid":"42"*/

        private String name;
        private String address;
        private String phone;
        private String phone2;
        private String postcode;
        private String provinceid;
        private String cityid;
        private String areaid;
        private String x;
        private String y;

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhone2() {
            return phone2;
        }

        public void setPhone2(String phone2) {
            this.phone2 = phone2;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getProvinceid() {
            return provinceid;
        }

        public void setProvinceid(String provinceid) {
            this.provinceid = provinceid;
        }

        public String getCityid() {
            return cityid;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public String getAreaid() {
            return areaid;
        }

        public void setAreaid(String areaid) {
            this.areaid = areaid;
        }
    }
}
