package com.jimo.exception;

import com.jimo.enumm.ResultEnum;

public class MenException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Integer code;

	public MenException(ResultEnum e) {
		super(e.getMsg());
		this.code = e.getCode();
	}

	public Integer getCode() {
		return this.code;
	}
}
