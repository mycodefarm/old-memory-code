package com.jimo.grammer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jimo.Sign;

public class Grammer {

	// 不存在，<,=,>分别为0,1,2,3
	public int[][] pt = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, /* = */{ 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 3 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3 }, { 0, 0, 3, 3, 3, 1, 1, 1, 3, 1, 1, 3 },
			{ 0, 0, 3, 3, 3, 1, 1, 1, 3, 1, 1, 3 }, { 0, 0, 3, 3, 3, 3, 3, 1, 3, 1, 1, 3 },
			{ 0, 0, 3, 3, 3, 3, 3, 1, 3, 1, 1, 3 }, { 0, 0, 0, 1, 1, 1, 1, 1, 2, 1, 1, 3 },
			{ 0, 0, 3, 3, 3, 3, 3, 0, 3, 0, 0, 3 }, { 0, 2, 3, 3, 3, 3, 3, 0, 3, 0, 0, 3 },
			{ 0, 0, 3, 3, 3, 3, 3, 0, 3, 0, 0, 3 }, { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2 } };

	private String path;//源码路径
	private Word word;
	private Map<String, Integer> codes = null;// 种别码
	private List<Sign> signs;// 词法分析结果
	private List<Sign> vars;// 存储变量

	public Grammer(String path) {
		this.path = path;
		word = new Word();
		codes = word.getCodes();
		signs = new ArrayList<>();
		vars = new ArrayList<>();
	}

	public void grammerDeal() {
		BufferedReader br = null;
		try {
			// 读取源文件
			br = new BufferedReader(new FileReader(new File(path)));
			String line = null;
			// 按行处理
			while ((line = br.readLine()) != null) {
				String sentence = line.trim();
				if (!"".equals(sentence)) {
					System.out.println();
					System.out.println("---------------------------解析句子[" + sentence + "]-------------------------");
					line += "`";
					List<Sign> ts = word.wordDeal(line);
					System.out.println("(种别码，内码值，符号)");
					print(ts);
					System.out.println(
							"---------------------------句子[" + sentence + "]的词法分析完成-----------------------------");
					if (grammerAnalyze(ts)) {
						System.out.println(
								"---------------------------句子[" + sentence + "]句法正确---------------------------");
						signs.addAll(ts);
					} else {
						System.err.println(
								"---------------------------句子[" + sentence + "]句法错误---------------------------");
						System.err.println("---------------------------编译失败------------------------------");
						return;
					}
				}
			}
			System.out.println("---------------------------编译成功---------------------------------");
		} catch (FileNotFoundException e) {
			System.err.println("找不到该文件");
		} catch (IOException e) {
			System.err.println("读取文件出错");
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean grammerAnalyze(List<Sign> sign) {
		List<Sign> st = new ArrayList<>();// 符号栈,这里用list替代，原因我不说，明白人都懂
		st.add(new Sign("#", codes.get("#"), "", ""));
		int i = 0;// 控制输入符号的下标
		int j = 0;// 控制
		int k = 0;// 控制栈下标
		Sign si = sign.get(i);
		Sign s = null;
		while (si.getCode() != codes.get("#")) {
			si = sign.get(i);// 读入下一个符号
			s = st.get(k);
			if (isVT(s)) {// 栈顶必须是终结符
				j = k;
			} else {
				j = k - 1;
			}
			s = st.get(j);// 栈顶
			// 若当前栈顶的符号优先级高于准备输入的符号，则进行归约
			while (pt[st.get(j).getCode()][si.getCode()] == 3) {
				Sign Q = null;
				do {
					Q = st.get(j);
					if (j < 1) {
						break;
					}
					if (isVT(st.get(j - 1))) {// 若Q的前一个是终结符,则指针指向前一个，否则指向再前一个
						j = j - 1;
					} else {
						j = j - 2;
					}
				} while (pt[st.get(j).getCode()][Q.getCode()] == 3 || pt[st.get(j).getCode()][Q.getCode()] == 2);// 找到最前面一个不小于的
				// 将j+1到k的进行归约为N
				String v = Cal(st, j + 1, k);
				if (v == null) {
					return false;
				}
				k = j + 1;// 重新设置栈顶指针
				st.get(k).setCode(codes.get("N"));// 并设置为归约串
				st.get(k).setValue(v);
				st = st.subList(0, k + 1);// 对列表栈进行调整
			}
			s = st.get(j);// 刷新当前栈顶
			// 优先级<或者=，则入栈
			if (pt[s.getCode()][si.getCode()] == 1 || pt[s.getCode()][si.getCode()] == 2) {
				k++;
				st.add(si);
//				System.out.println("---------------------------当前栈内容---------------------------");
//				print(st);
			} else {// 否则，肯定是出现了不存在的优先级，即语法错误
				System.err.println("出现了不存在的优先级");
				return false;
			}
			i++;
		}
		return true;
	}

	// 是否是终结符
	private boolean isVT(Sign s) {
		int code = s.getCode();
		if (code == codes.get("N")) {
			return false;
		}
		return true;
	}

	// 归约
	public String Cal(List<Sign> s, int i, int j) {
		Sign re = new Sign(codes.get("N"), "");
		int len = j - i + 1;
		if (len == 1) {
			Sign t = s.get(j);
			if (t.getCode() == codes.get("var")) {// N->v
				System.out.println("N->v,具体内容：N->" + t.getSign());
				re.setSign(t.getSign());
			} else if (t.getCode() == codes.get("const")) {// N->c
				System.out.println("N->c,具体内容：N->" + t.getValue());
				re.setValue(t.getValue());
			} else if (t.getCode() == codes.get("clear")) {
				// 清除所有变量
				vars.clear();
				System.out.println("clear:清楚了所有变量");
			} else {
				System.err.println("未知符号:" + t.getSign());
				return null;
			}
		} else if (len == 2) {
			Sign a = s.get(i);
			Sign b = s.get(j);
			if (a.getCode() == codes.get("N") && b.getCode() == codes.get("?")) {// N->N?
				if ("".equals(a.getValue())) {
					for (Sign ss : vars) {
						if (ss.getSign().equals(a.getSign())) {
							a = ss;
							break;
						}
					}
				}
				try {
					int x = Integer.parseInt(a.getValue());
					re.setValue(x + "");
					System.out.println("N->N?,具体内容：输出" + x);
				} catch (Exception e) {
					System.err.println("变量" + a.getSign() + "未声明或未定义值");
					return null;
				}
			} else {
				System.err.println("N->N? 错误");
				return null;
			}
		} else if (len == 3) {
			Sign a = s.get(j - 2);
			Sign b = s.get(j - 1);
			Sign c = s.get(j);
			// N->v=N
			if (a.getCode() == codes.get("var") && b.getCode() == codes.get("=")
					&& (c.getCode() == codes.get("N") || c.getCode() == codes.get("const"))) {
				System.out.println("N->v=N|v=c,具体内容：" + a.getSign() + "=" + c.getValue());
				re.setValue(c.getValue());
				re.setSign(a.getSign());
				vars.add(re);// 加入变量表
			} else if (a.getCode() == codes.get("N") && c.getCode() == codes.get("N")) {// +-*/
				// 在前面找变量的值，同时判断是否定义,N可能来自变量，可能来自常量，也可能是表达式的值
				// 因为常量和表达式的值肯定是在本句中计算的，所以只需关注变量是否存在
				if ("".equals(a.getValue())) {// 为空表示用的以前的变量
					for (Sign ss : vars) {
						if (ss.getSign().equals(a.getSign())) {
							a = ss;
							break;
						}
					}
				}
				if ("".equals(c.getValue())) {
					for (Sign ss : vars) {
						if (ss.getSign().equals(c.getSign())) {
							c = ss;
							break;
						}
					}
				}
				int x = 0, y = 0;
				try {
					x = Integer.parseInt(a.getValue());
				} catch (Exception e) {
					System.err.println("变量" + a.getSign() + "未声明");
					return null;
				}
				try {
					y = Integer.parseInt(c.getValue());
				} catch (Exception e) {
					System.err.println("变量" + c.getSign() + "未声明");
					return null;
				}
				int cc = b.getCode();
				if (cc == codes.get("+")) {
					re.setValue((x + y) + "");
				} else if (cc == codes.get("-")) {
					re.setValue((x - y) + "");
				} else if (cc == codes.get("*")) {
					re.setValue((x * y) + "");
				} else if (cc == codes.get("/")) {
					re.setValue((x / y) + "");
				} else {
					System.err.println("未知运算符:" + b.getSign());
					return null;
				}
				System.out.println("N->N" + b.getSign() + "N,具体内容为：" + x + b.getSign() + y);
			} else if (a.getCode() == codes.get("(") && b.getCode() == codes.get("N")
					&& c.getCode() == codes.get(")")) {
				System.out.println("N->(N)");
				re.setValue(b.getValue());
			} else {
				System.err.println("运算错误");
				return null;
			}
		}
		return re.getValue();
	}

	public void print(List<Sign> signs) {
		for (Sign s : signs) {
			System.out.print("(" + s.getCode() + " , " + s.getValue() + " , " + s.getSign() + ")  ");
		}
		System.out.println();
	}
}
