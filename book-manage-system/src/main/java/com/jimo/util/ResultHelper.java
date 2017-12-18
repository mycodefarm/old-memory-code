package com.jimo.util;

public class ResultHelper {

	public static Result success() {
		Result re = new Result();
		re.setCode(ResultCode.OK.getErrcode());
		re.setMsg(ResultCode.OK.getErrmsg());
		re.setData(null);
		return re;
	}

	public static Result success(Object obj) {
		Result re = new Result();
		re.setCode(ResultCode.OK.getErrcode());
		re.setMsg(ResultCode.OK.getErrmsg());
		re.setData(obj);
		return re;
	}

	public static Result success(ResultCode rc) {
		Result re = new Result();
		re.setCode(rc.getErrcode());
		re.setMsg(rc.getErrmsg());
		re.setData(null);
		return re;
	}

	public static Result error(Integer code, String msg) {
		Result re = new Result();
		re.setCode(code);
		re.setMsg(msg);
		re.setData(null);
		return re;
	}

	public static Result error(String msg) {
		return error(ResultCode.NORMAL_ERROR.getErrcode(), msg);
	}

	public static Result error(ResultCode rc) {
		Result re = new Result();
		re.setCode(rc.getErrcode());
		re.setMsg(rc.getErrmsg());
		re.setData(null);
		return re;
	}

	public static Result error() {
		return error(ResultCode.SYSTEM_ERR.getErrcode(), ResultCode.SYSTEM_ERR.getErrmsg());
	}
}
