package com.melonltd.naberc.vo;

public abstract class ColumnAbs implements ColumnIntf {
    private String uid;

    public ColumnAbs() {
    }

    public ColumnAbs(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
