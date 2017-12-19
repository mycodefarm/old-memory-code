package com.jimo.util;

public enum ResultCode {
	OK(0, "OK"), ADD_OK(2, "添加成功"), UPDATE_OK(3, "更新成功"), SYSTEM_ERR(1, "未知系统错误"), INVALID_CLIENTID(30003,
			"Invalid clientid"), INVALID_PASSWORD(30004, "密码错误"), INVALID_CAPTCHA(30005,
					"Invalid captcha or captcha overdue"), INVALID_TOKEN(30006, "Invalid token"), INVALID_USER(30007,
							"用户名不存在"), USER_LOCK(30008,
									"用户已锁定"), OUT_OF_DATE(30009, "不在登录时间内"), NORMAL_ERROR(100, "业务错误");

	private int errcode;
	private String errmsg;

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	private ResultCode(int Errode, String ErrMsg) {
		this.errcode = Errode;
		this.errmsg = ErrMsg;
	}
}
