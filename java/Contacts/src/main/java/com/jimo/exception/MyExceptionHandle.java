package com.jimo.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jimo.util.Result;
import com.jimo.util.ResultHelper;

@ControllerAdvice
public class MyExceptionHandle {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Result handleException(Exception exception) {
		if (exception instanceof MyException) {
			MyException me = (MyException) exception;
			return ResultHelper.error(me.getCode(), me.getMessage());
		}
		return ResultHelper.error();
	}
}
