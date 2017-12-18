package com.jimo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
//		System.out.println("输入读取的C源文件路径：");
		// String path = in.nextLine();
		String path = "/home/workspace/eclipse/Compiler/s.c";
		if (!path.endsWith(".c")) {
			System.err.println("不是合法的C语言文件");
			in.close();
			return;
		}
		BufferedReader br = null;
		try {
			// 读取源文件
			br = new BufferedReader(new FileReader(new File(path)));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			// 得到C语言源程序，这里一次性处理，不按行处理
			// System.out.println(sb.length() + "," + sb.toString());
			WordAnalyze wa = new WordAnalyze();
			sb.append("`");
			// wa.dealWordsNotOptimization(sb.toString());
			wa.dealWordsOptimized(sb.toString());
			wa.print();
			wa.saveToFile("/home/workspace/eclipse/Compiler");
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
		in.close();
	}
}
