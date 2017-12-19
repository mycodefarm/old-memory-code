package com.jimo;

public class Sign {
	private String sign;// 单词符号
	private int code;// 种别编码
	private String value;// 内码值
	private String readSign;// 助记符

	public Sign(String sign, String readSign) {
		this.sign = sign;
		this.readSign = readSign;
	}

	public Sign(String sign, int code, String value, String readSign) {
		super();
		this.sign = sign;
		this.code = code;
		this.value = value;
		this.readSign = readSign;
	}

	public Sign(int code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getReadSign() {
		return readSign;
	}

	public void setReadSign(String readSign) {
		this.readSign = readSign;
	}
}
