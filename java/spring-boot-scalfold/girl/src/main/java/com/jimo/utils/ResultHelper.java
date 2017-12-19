package com.jimo.utils;

import com.jimo.enumm.ResultEnum;

public class ResultHelper {
	public static Result<Object> success(Object obj) {
		Result<Object> re = new Result<>();
		re.setCode(0);// 假设成功为0
		re.setMsg("OK");
		re.setData(obj);
		return re;
	}

	public static Result<Object> success() {
		Result<Object> re = new Result<>();
		re.setCode(0);// 假设成功为0
		re.setMsg("OK");
		re.setData(null);
		return re;
	}

	public static Result<Object> error(Integer code, String msg) {
		Result<Object> re = new Result<>();
		re.setCode(code);
		re.setMsg(msg);
		re.setData(null);
		return re;
	}

	public static Result<Object> error(ResultEnum e) {
		Result<Object> re = new Result<>();
		re.setCode(e.getCode());
		re.setMsg(e.getMsg());
		re.setData(null);
		return re;
	}
}
