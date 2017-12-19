package com.jimo.dto;

/**
 * 返回秒杀接口或时间的传递对象
 * Created by root on 17-5-24.
 */
public class Exposer {

    //是否开启秒杀
    private boolean isExposed;

    //加密
    private String md5;

    private long killId;

    //系统当前时间
    private long now;

    private long start;

    private long end;

    public Exposer(boolean isExposed, String md5, long killId) {
        this.isExposed = isExposed;
        this.md5 = md5;
        this.killId = killId;
    }

    public Exposer(boolean isExposed, long now, long start, long end, long killId) {
        this.isExposed = isExposed;
        this.now = now;
        this.start = start;
        this.end = end;
        this.killId = killId;
    }

    public Exposer(boolean isExposed, long killId) {
        this.isExposed = isExposed;
        this.killId = killId;
    }

    public boolean isExposed() {
        return isExposed;
    }

    public void setExposed(boolean exposed) {
        isExposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getKillId() {
        return killId;
    }

    public void setKillId(long killId) {
        this.killId = killId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "isExposed=" + isExposed +
                ", md5='" + md5 + '\'' +
                ", killId=" + killId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
