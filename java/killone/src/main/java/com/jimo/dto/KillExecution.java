package com.jimo.dto;

import com.jimo.entity.SuccessKilled;
import com.jimo.enums.KillOneStateEnum;

/**
 * 执行秒杀操作的返回结果
 * Created by root on 17-5-24.
 */
public class KillExecution {
    private long killId;

    //状态
    private int state;

    //状态描述
    private String stateInfo;

    //秒杀成功对象
    private SuccessKilled successKilled;

    public KillExecution(long killId, KillOneStateEnum stateEnum) {
        this.killId = killId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public KillExecution(long killId, KillOneStateEnum stateEnum, SuccessKilled successKilled) {
        this.killId = killId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    public long getKillId() {
        return killId;
    }

    public void setKillId(long killId) {
        this.killId = killId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }
}
