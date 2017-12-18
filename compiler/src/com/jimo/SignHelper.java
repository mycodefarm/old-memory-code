package com.jimo;

public class SignHelper {
	public static Sign getKeySign(String sign) {
		return new Sign(sign, "关键字");
	}

	public static Sign getIDSign(String sign) {
		return new Sign(sign, "标识符");
	}

	public static Sign getOperatorSign(String sign) {
		return new Sign(sign, "运算符");
	}

	public static Sign getNumberSign(String sign) {
		return new Sign(sign, "常数");
	}

	public static Sign getDelimitorSign(String sign) {
		return new Sign(sign, "界符");
	}

	public static Sign getUnknownSign(String sign) {
		return new Sign(sign, "未知符号");
	}

	public static Sign getCodeSign(int code, String value, String sign) {
		return new Sign(sign, code, value, null);
	}
}
