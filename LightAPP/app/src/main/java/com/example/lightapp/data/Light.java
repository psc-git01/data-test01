package com.example.lightapp.data;

import org.litepal.crud.DataSupport;

public class Light extends DataSupport {
    private int lid;
    private int cid;
    private String lname;
    private int state;

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
