package com.jimo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordAnalyze {

	private Set<String> keywords;
	private List<Sign> re;// 返回的符号列表

	public WordAnalyze() {
		re = new ArrayList<>();
		keywords = new HashSet<>();
		keywords.add("auto");
		keywords.add("break");
		keywords.add("case");
		keywords.add("char");
		keywords.add("const");
		keywords.add("continue");
		keywords.add("default");
		keywords.add("do");
		keywords.add("double");
		keywords.add("else");
		keywords.add("enum");
		keywords.add("extern");
		keywords.add("float");
		keywords.add("for");
		keywords.add("goto");
		keywords.add("if");
		keywords.add("int");
		keywords.add("long");
		keywords.add("register");
		keywords.add("return");
		keywords.add("short");
		keywords.add("signed");
		keywords.add("sizeof");
		keywords.add("static");
		keywords.add("struct");
		keywords.add("switch");
		keywords.add("typedef");
		keywords.add("union");
		keywords.add("unsigned");
		keywords.add("void");
		keywords.add("volatile");
		keywords.add("while");
	}

	public void print() {
		for (Sign s : re) {
			System.out.println("(" + s.getSign() + "," + s.getReadSign() + ")");
		}
	}

	/**
	 * 保存到文件里
	 * 
	 * @param path
	 */
	public void saveToFile(String path) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(new File(path + File.separator + "word.txt")));
			for (Sign s : re) {
				bw.write("(" + s.getSign() + "," + s.getReadSign() + ")");
			}
		} catch (IOException e) {
			System.err.println("写入文件出错");
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("写入成功");
	}

	/**
	 * 未优化的词法分析器 输入：C语言源程序； 输出：单词符号二元组
	 */
	public void dealWordsNotOptimization(String s) {
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
				state = 0;
				re.add(SignHelper.getUnknownSign(sb.toString()));
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
						state = 10;
						break;
					case '*':
						state = 12;
						break;
					case '/':
						state = 15;
						break;
					case '%':
						state = 18;
						break;
					case ',':
					case ';':
					case '(':
					case ')':
					case '{':
					case '}':
						state = 19;
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
					re.add(SignHelper.getKeySign(temp));
				} else {// 普通标识符
					re.add(SignHelper.getIDSign(temp));
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
				// 整数终止
				state = 0;
				re.add(SignHelper.getNumberSign(sb.toString()));
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
				state = 7;
				break;
			case 7:
				// 小数终止
				state = 0;
				re.add(SignHelper.getNumberSign(sb.toString()));
				sb.delete(0, sb.length());// 清空
				break;
			case 8:
				// +号运算符
				c = s.charAt(i);
				if (c == '+') {
					sb.append(c);// ++
				}
				state = 9;
				++i;
				break;
			case 9:
				// +或++运算符终止
				state = 0;
				re.add(SignHelper.getOperatorSign(sb.toString()));
				sb.delete(0, sb.length());// 清空
				break;
			case 10:
				// -或--运算符
				c = s.charAt(i);
				if (c == '-') {
					sb.append(c);
				}
				state = 11;
				++i;
				break;
			case 11:
				// -或--运算符终止
				state = 0;
				re.add(SignHelper.getOperatorSign(sb.toString()));
				sb.delete(0, sb.length());// 清空
				break;
			case 12:
				c = s.charAt(i);
				if (c == '/') {
					sb.append(c);
					state = 13;
				} else {
					state = 14;
				}
				++i;
				break;
			case 13:
				// 注释符*/终止
				state = 0;
				re.add(SignHelper.getDelimitorSign(sb.toString()));
				sb.delete(0, sb.length());// 清空
				break;
			case 14:
				// 运算符*号终止
				state = 0;
				re.add(SignHelper.getOperatorSign(sb.toString()));
				sb.delete(0, sb.length());// 清空
				break;
			case 15:
				c = s.charAt(i);
				if (c == '/') {
					sb.append(c);
					state = 16;
				} else {
					state = 17;
				}
				++i;
				break;
			case 16:
				// 注释符//终止
				state = 0;
				re.add(SignHelper.getDelimitorSign(sb.toString()));
				sb.delete(0, sb.length());// 清空
				break;
			case 17:
				// 运算符/号终止
				state = 0;
				re.add(SignHelper.getOperatorSign(sb.toString()));
				sb.delete(0, sb.length());// 清空
				break;
			case 18:
				// 运算符%号终止
				state = 0;
				re.add(SignHelper.getOperatorSign(sb.toString()));
				sb.delete(0, sb.length());// 清空
				break;
			case 19:
				// 界符终止
				state = 0;
				re.add(SignHelper.getDelimitorSign(sb.toString()));
				sb.delete(0, sb.length());// 清空
				break;
			default:
				state = -1;
				break;
			}
		}
	}

	/**
	 * 优化后的词法分析器
	 */
	public void dealWordsOptimized(String s) {
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
				state = 0;
				re.add(SignHelper.getUnknownSign(sb.toString()));
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
						state = 10;
						break;
					case '*':
						state = 12;
						break;
					case '/':
						state = 15;
						break;
					case '%':
						state = 18;
						break;
					case ',':
					case ';':
					case '(':
					case ')':
					case '{':
					case '}':
						state = 19;
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
					re.add(SignHelper.getKeySign(temp));
				} else {// 普通标识符
					re.add(SignHelper.getIDSign(temp));
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
				re.add(SignHelper.getNumberSign(sb.toString()));
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
				c = s.charAt(i);
				if (c == '+') {
					sb.append(c);// ++
				}
				state = 18;
				++i;
				break;
			case 10:
				// -或--运算符
				c = s.charAt(i);
				if (c == '-') {
					sb.append(c);
				}
				state = 18;
				++i;
				break;
			case 12:
				c = s.charAt(i);
				if (c == '/') {
					sb.append(c);
					state = 19;
				} else {
					state = 18;
				}
				++i;
				break;
			case 15:
				c = s.charAt(i);
				if (c == '/') {
					sb.append(c);
					state = 19;
				} else {
					state = 18;
				}
				++i;
				break;
			case 18:
				// 运算符终止
				state = 0;
				re.add(SignHelper.getOperatorSign(sb.toString()));
				sb.delete(0, sb.length());// 清空
				break;
			case 19:
				// 界符终止
				state = 0;
				re.add(SignHelper.getDelimitorSign(sb.toString()));
				sb.delete(0, sb.length());// 清空
				break;
			default:
				state = -1;
				break;
			}
		}
	}
}
