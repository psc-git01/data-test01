package com.example.lightapp.data;

import org.litepal.crud.DataSupport;

public class Floor extends DataSupport {
    private int fid;
    private int tid;
    private String fname;
    private int floorCode;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public int getFloorCode() {
        return floorCode;
    }

    public void setFloorCode(int floorCode) {
        this.floorCode = floorCode;
    }
}
