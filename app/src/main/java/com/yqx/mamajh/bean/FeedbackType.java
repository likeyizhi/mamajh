package com.yqx.mamajh.bean;

/**
 * 反馈建议
 */

public class FeedbackType {

    /**
     * name : 功能建议
     */

    private String name;

    private boolean isCheck = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean check) {
        isCheck = check;
    }
}
