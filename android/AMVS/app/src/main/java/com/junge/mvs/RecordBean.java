package com.junge.mvs;

/**
 * Created by TR on 2016/9/8.
 */

public class RecordBean {
    public String stationsName;
    public String peoNum;
    public String order;

    public RecordBean(String arrName, String peoNum,String order) {
        this.stationsName = arrName;
        this.peoNum = peoNum;
        this.order=order;
    }
}

