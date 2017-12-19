package com.jimo.dto;

/**
 * Created by root on 17-5-26.
 * 返回的json结果
 */
public class KillResult<T> {
    private boolean isSuccess;
    private T data;
    private String errorMsg;

    public KillResult(boolean isSuccess, T data) {
        this.isSuccess = isSuccess;
        this.data = data;
    }

    public KillResult(boolean isSuccess, String errorMsg) {
        this.isSuccess = isSuccess;
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
