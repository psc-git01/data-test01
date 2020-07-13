package com.example.lightapp.data;

import org.litepal.crud.DataSupport;

public class Clazz extends DataSupport {
    private int cid;
    private int fid;
    private String classCode;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }
}
