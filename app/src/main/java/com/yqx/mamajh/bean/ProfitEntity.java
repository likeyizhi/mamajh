package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by young on 2017/3/8.
 */

public class ProfitEntity {

    /**
     * status : 0
     * mes : 成功
     * Count : 0
     * Gole : 0
     * Silver : 0
     * res : [{"PointEarn":[{"ID":"4","Title":"注册获银币","Score":"50","Remark":"新用户完成注册，即可获得50枚银币。","NoText":"未获得","Text":"已获得","Link":"","IsEveryday":"0","Count":"0","SumCount":"0","TodayCount":"0"}]}]
     */

    private int status;
    private String mes;
    private String Count;
    private String Gole;
    private String Silver;
    private List<ResEntity> res;

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

    public String getCount() {
        return Count;
    }

    public void setCount(String Count) {
        this.Count = Count;
    }

    public String getGole() {
        return Gole;
    }

    public void setGole(String Gole) {
        this.Gole = Gole;
    }

    public String getSilver() {
        return Silver;
    }

    public void setSilver(String Silver) {
        this.Silver = Silver;
    }

    public List<ResEntity> getRes() {
        return res;
    }

    public void setRes(List<ResEntity> res) {
        this.res = res;
    }

    public static class ResEntity {
        private List<PointEarnEntity> PointEarn;

        public List<PointEarnEntity> getPointEarn() {
            return PointEarn;
        }

        public void setPointEarn(List<PointEarnEntity> PointEarn) {
            this.PointEarn = PointEarn;
        }

        public static class PointEarnEntity {
            /**
             * ID : 4
             * Title : 注册获银币
             * Score : 50
             * Remark : 新用户完成注册，即可获得50枚银币。
             * NoText : 未获得
             * Text : 已获得
             * Link :
             * IsEveryday : 0
             * Count : 0
             * SumCount : 0
             * TodayCount : 0
             */

            private String ID;
            private String Title;
            private String Score;
            private String Remark;
            private String NoText;
            private String Text;
            private String Link;
            private String IsEveryday;
            private String Count;
            private String SumCount;
            private String TodayCount;

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getScore() {
                return Score;
            }

            public void setScore(String Score) {
                this.Score = Score;
            }

            public String getRemark() {
                return Remark;
            }

            public void setRemark(String Remark) {
                this.Remark = Remark;
            }

            public String getNoText() {
                return NoText;
            }

            public void setNoText(String NoText) {
                this.NoText = NoText;
            }

            public String getText() {
                return Text;
            }

            public void setText(String Text) {
                this.Text = Text;
            }

            public String getLink() {
                return Link;
            }

            public void setLink(String Link) {
                this.Link = Link;
            }

            public String getIsEveryday() {
                return IsEveryday;
            }

            public void setIsEveryday(String IsEveryday) {
                this.IsEveryday = IsEveryday;
            }

            public String getCount() {
                return Count;
            }

            public void setCount(String Count) {
                this.Count = Count;
            }

            public String getSumCount() {
                return SumCount;
            }

            public void setSumCount(String SumCount) {
                this.SumCount = SumCount;
            }

            public String getTodayCount() {
                return TodayCount;
            }

            public void setTodayCount(String TodayCount) {
                this.TodayCount = TodayCount;
            }
        }
    }
}
