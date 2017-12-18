package com.jimo.exception;

import com.jimo.util.ResultCode;

public class MyException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private Integer code;

	public MyException(ResultCode re) {
		super(re.getErrmsg());
		this.code = re.getErrcode();
	}

	public Integer getCode() {
		return code;
	}

}
