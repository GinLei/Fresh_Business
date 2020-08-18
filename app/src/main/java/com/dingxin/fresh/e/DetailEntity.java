package com.dingxin.fresh.e;

public class DetailEntity {
    private String change_time;
    private String change_money;
    int cate;
    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getChange_time() {
        return change_time;
    }

    public void setChange_time(String change_time) {
        this.change_time = change_time;
    }

    public String getChange_money() {
        return change_money;
    }

    public void setChange_money(String change_money) {
        this.change_money = change_money;
    }

    public int getCate() {
        return cate;
    }

    public void setCate(int cate) {
        this.cate = cate;
    }
}
