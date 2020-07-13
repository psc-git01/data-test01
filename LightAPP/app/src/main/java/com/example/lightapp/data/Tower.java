package com.example.lightapp.data;

import org.litepal.crud.DataSupport;

public class Tower extends DataSupport {
    private int tid;
    private String tname;
    private String address;
    private int towerCode;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTowerCode() {
        return towerCode;
    }

    public void setTowerCode(int towerCode) {
        this.towerCode = towerCode;
    }

    @Override
    public String toString() {
        return "Tower{" +
                "tid=" + tid +
                ", tname='" + tname + '\'' +
                ", address='" + address + '\'' +
                ", towerCode=" + towerCode +
                '}';
    }
}
