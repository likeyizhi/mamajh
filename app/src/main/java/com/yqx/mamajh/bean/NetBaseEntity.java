package com.yqx.mamajh.bean;

public class NetBaseEntity<T> {


    /**
     * status : 2
     * msg : xxx
     */

    private int    status;
    private String mes;
    private T      res;

    public T getRes() {
        return res;
    }

    public void setRes(T res) {
        this.res = res;
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

    public int getStatus() {
        return status;
    }

}
