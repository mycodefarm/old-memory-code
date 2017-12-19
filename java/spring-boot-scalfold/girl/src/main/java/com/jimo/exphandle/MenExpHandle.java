package com.jimo.exphandle;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jimo.enumm.ResultEnum;
import com.jimo.exception.MenException;
import com.jimo.utils.Result;
import com.jimo.utils.ResultHelper;

@ControllerAdvice
public class MenExpHandle {

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Result<Object> handle(Exception e) {
		if (e instanceof MenException) {
			MenException me = (MenException) e;
			return ResultHelper.error(me.getCode(), me.getMessage());
		} else {
			return ResultHelper.error(ResultEnum.ERROR);
		}
	}
}
