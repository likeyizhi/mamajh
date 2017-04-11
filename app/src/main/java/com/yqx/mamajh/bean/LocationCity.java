package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by likey on 2017/3/30.
 */

public class LocationCity {
    /**"status": 0,
     "mes": "成功",
     "res": {*/
    private int status;
    private String mes;
    private LocationCityRes res;

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

    public LocationCityRes getRes() {
        return res;
    }

    public void setRes(LocationCityRes res) {
        this.res = res;
    }

    public static class LocationCityRes{
        /*"List": [*/
        private List<LocationCityList> List;

        public java.util.List<LocationCityList> getList() {
            return List;
        }

        public void setList(java.util.List<LocationCityList> list) {
            List = list;
        }

        public static class LocationCityList{
            /**"Key": "A",
             "CityList": [*/
            private String Key;
            private List<LocationCityListCityList> CityList;

            public String getKey() {
                return Key;
            }

            public void setKey(String key) {
                Key = key;
            }

            public java.util.List<LocationCityListCityList> getCityList() {
                return CityList;
            }

            public void setCityList(java.util.List<LocationCityListCityList> cityList) {
                CityList = cityList;
            }

            public static class LocationCityListCityList{
                /**"ID": 654,
                 "Head": "A",
                 "Name": "安图",
                 "Name2": "安图县",
                 "X": "128.90625",
                 "Y": "43.11533"*/
                private int ID;
                private String Head;
                private String Name;
                private String Name2;
                private String X;
                private String Y;

                public int getID() {
                    return ID;
                }

                public void setID(int ID) {
                    this.ID = ID;
                }

                public String getHead() {
                    return Head;
                }

                public void setHead(String head) {
                    Head = head;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String name) {
                    Name = name;
                }

                public String getName2() {
                    return Name2;
                }

                public void setName2(String name2) {
                    Name2 = name2;
                }

                public String getX() {
                    return X;
                }

                public void setX(String x) {
                    X = x;
                }

                public String getY() {
                    return Y;
                }

                public void setY(String y) {
                    Y = y;
                }
            }
        }
    }
}
