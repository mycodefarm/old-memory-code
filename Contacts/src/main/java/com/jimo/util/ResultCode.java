package com.jimo.util;

public enum ResultCode {
	OK(1, "ok"), SYSTEM_ERROR(0, "系统故障"), NORMAL_ERROR(5, "一般错误");
	private int code;
	private String msg;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	private ResultCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}
