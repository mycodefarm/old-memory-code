package com.jimo.enumm;

public enum ResultEnum {
	ERROR(-1, "系统错误"), OK(0, "OK"), OLD(10, "太老了"), YOUNG(11, "太年轻");

	private Integer code;
	private String msg;

	private ResultEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
