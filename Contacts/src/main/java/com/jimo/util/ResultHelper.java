package com.jimo.util;

public class ResultHelper {

	public static Result success() {
		Result re = new Result();
		re.setCode(ResultCode.OK.getCode());
		re.setMsg(ResultCode.OK.getMsg());
		return re;
	}

	public static Result success(Object obj) {
		Result re = new Result();
		re.setCode(ResultCode.OK.getCode());
		re.setMsg(ResultCode.OK.getMsg());
		re.setData(obj);
		return re;
	}

	public static Result error() {
		Result re = new Result();
		re.setCode(ResultCode.NORMAL_ERROR.getCode());
		re.setMsg(ResultCode.NORMAL_ERROR.getMsg());
		return re;
	}

	public static Result error(String msg) {
		Result re = new Result();
		re.setCode(ResultCode.NORMAL_ERROR.getCode());
		re.setMsg(msg);
		return re;
	}

	public static Result error(Integer code, String msg) {
		Result re = new Result();
		re.setCode(code);
		re.setMsg(msg);
		return re;
	}
}
