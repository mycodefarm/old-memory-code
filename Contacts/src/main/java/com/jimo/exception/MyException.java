package com.jimo.exception;

import com.jimo.util.Result;
import com.jimo.util.ResultCode;

public class MyException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private Integer code;

	public MyException(ResultCode re) {
		super(re.getMsg());
		this.code = re.getCode();
	}

	public MyException(Result re) {
		super(re.getMsg());
		this.code = re.getCode();
	}

	public Integer getCode() {
		return code;
	}
}
