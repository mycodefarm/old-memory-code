package com.jimo.grammer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jimo.Sign;
import com.jimo.SignHelper;

public class Word {
	private Set<String> keywords;
	private Map<String, Integer> codes = null;

	public Word() {
		keywords = new HashSet<>();
		keywords.add("clear");
		// 初始化种别码
		codes = new HashMap<>();
		codes.put("=", 1);
		codes.put("?", 2);
		codes.put("+", 3);
		codes.put("-", 4);
		codes.put("*", 5);
		codes.put("/", 6);
		codes.put("(", 7);
		codes.put(")", 8);
		codes.put("var", 9);
		codes.put("const", 10);
		codes.put("#", 11);
		codes.put("clear", 12);
		codes.put("N", 13);
	}

	public Map<String, Integer> getCodes() {
		return this.codes;
	}

	public List<Sign> wordDeal(String s) {
		List<Sign> re = new ArrayList<>();
		int state = 0;// 状态
		int i = 0;
		int len = s.length() - 1;
		StringBuilder sb = new StringBuilder();
		String temp = null;
		char c;
		while (i <= len) {
			switch (state) {
			case -1:
				// 错误处理
				// System.err.println("词法解析出错");
				state = 0;
				sb.delete(0, sb.length());// 清空
				break;
			case 0:
				// 空格和换行符处理
				while (i < len && (s.charAt(i) == ' ' || s.charAt(i) == '\n' || s.charAt(i) == '\t')) {
					i++;
				}
				c = s.charAt(i);
				sb.append(c);
				if (c == '_' || Character.isLetter(c)) {
					state = 1;
				} else if (Character.isDigit(c)) {
					state = 3;
				} else {
					switch (c) {
					case '+':
						state = 8;
						break;
					case '-':
						state = 9;
						break;
					case '*':
						state = 10;
						break;
					case '/':
						state = 11;
						break;
					case '=':
						state = 12;
						break;
					case '(':
						state = 13;
						break;
					case ')':
						state = 14;
						break;
					case '?':
						state = 15;
						break;
					default:
						state = -1;
						break;
					}
				}
				++i;
				break;
			case 1:
				c = s.charAt(i);
				while (i < len && (c == '_' || Character.isLetterOrDigit(c))) {
					sb.append(c);
					c = s.charAt(++i);
				}
				state = 2;
				break;
			case 2:
				// 标识符终止,判断是否为关键字，否则存储下这个标识符
				temp = sb.toString();
				if (keywords.contains(temp)) {// 是关键字
					re.add(SignHelper.getCodeSign(codes.get("clear"), "", temp));
				} else {// 普通标识符
					re.add(SignHelper.getCodeSign(codes.get("var"), "", temp));
				}
				sb.delete(0, sb.length());// 清空
				state = 0;
				break;
			case 3:
				c = s.charAt(i);
				while (i < len && Character.isDigit(c)) {
					sb.append(c);
					c = s.charAt(++i);
				}
				if (c == '.') {
					sb.append(c);
					state = 5;
					++i;
				} else {
					state = 4;// 整数
				}
				break;
			case 4:
				// 常数终止
				state = 0;
				re.add(SignHelper.getCodeSign(codes.get("const"), sb.toString(), ""));
				sb.delete(0, sb.length());// 清空
				break;
			case 5:
				c = s.charAt(i);
				if (!Character.isDigit(c)) {
					state = -1;// 出错,不是浮点数
				} else {
					sb.append(c);
					state = 6;
					++i;
				}
				break;
			case 6:
				c = s.charAt(i);
				while (i < len && Character.isDigit(c)) {
					sb.append(c);
					c = s.charAt(++i);
				}
				state = 4;
				break;
			case 8:
				// +号运算符
				re.add(SignHelper.getCodeSign(codes.get("+"), "", sb.toString()));
				state = 0;
				sb.delete(0, sb.length());// 清空
				break;
			case 9:
				// -运算符
				state = 0;
				re.add(SignHelper.getCodeSign(codes.get("-"), "", sb.toString()));
				sb.delete(0, sb.length());// 清空
				break;
			case 10:
				re.add(SignHelper.getCodeSign(codes.get("*"), "", sb.toString()));
				state = 0;
				sb.delete(0, sb.length());// 清空
				break;
			case 11:
				re.add(SignHelper.getCodeSign(codes.get("/"), "", sb.toString()));
				state = 0;
				sb.delete(0, sb.length());// 清空
				break;
			case 12:
				// =号
				re.add(SignHelper.getCodeSign(codes.get("="), "", sb.toString()));
				state = 0;
				sb.delete(0, sb.length());// 清空
				break;
			case 13:
				// (
				re.add(SignHelper.getCodeSign(codes.get("("), "", sb.toString()));
				state = 0;
				sb.delete(0, sb.length());// 清空
				break;
			case 14:
				// )
				re.add(SignHelper.getCodeSign(codes.get(")"), "", sb.toString()));
				state = 0;
				sb.delete(0, sb.length());// 清空
				break;
			case 15:
				// ?
				re.add(SignHelper.getCodeSign(codes.get("?"), "", sb.toString()));
				state = 0;
				sb.delete(0, sb.length());// 清空
				break;
			default:
				state = -1;
				break;
			}
		}
		// 最后一个放入#
		re.add(SignHelper.getCodeSign(codes.get("#"), "", "#"));
		return re;
	}
}
