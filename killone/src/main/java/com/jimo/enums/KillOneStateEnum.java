package com.jimo.enums;

/**
 * Created by root on 17-5-26.
 */
public enum KillOneStateEnum {
    SUCCESS(1, "秒杀成功"),
    END(0, "秒杀结束"),
    INNER_ERROR(-1, "内部错误"),
    REPEAT_KILL(-2, "重复秒杀"),
    DATA_REWRITE(-3, "数据篡改");

    private int state;
    private String stateInfo;

    KillOneStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static KillOneStateEnum stateOf(int index) {
        for (KillOneStateEnum ke : values()) {
            if (ke.getState() == index) {
                return ke;
            }
        }
        return null;
    }
}
