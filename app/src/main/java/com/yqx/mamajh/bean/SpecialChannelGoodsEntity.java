package com.yqx.mamajh.bean;

/**
 * Created by young on 16/1/8.
 */
public class SpecialChannelGoodsEntity {
    private String id;
    private String title;
    private String desc;
    private String img;
    private String startTime;
    private String endTime;
    private String SysTime;

    public String getDesc() {
        return desc;
    }

    public String getSysTime() {
        return SysTime;
    }

    public void setSysTime(String sysTime) {
        SysTime = sysTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {

        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getImg() {

        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setDesc(String desc) {

        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
