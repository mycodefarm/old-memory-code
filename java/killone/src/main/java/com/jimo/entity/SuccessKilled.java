package com.jimo.entity;

import java.util.Date;

/**
 * Created by root on 17-5-21.
 */
public class SuccessKilled {
    private long killId;
    private long userPhone;
    private short state;
    private Date createTime;
    /*多对一关系*/
    private KillOne killOne;

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "killId=" + killId +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                ", killOne=" + killOne +
                '}';
    }

    public long getKillId() {
        return killId;
    }

    public void setKillId(long killId) {
        this.killId = killId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public KillOne getKillOne() {
        return killOne;
    }

    public void setKillOne(KillOne killOne) {
        this.killOne = killOne;
    }
}
