package com.jimo.grammer;

import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		// System.out.println("输入读取的源文件路径：");
		// String path = in.nextLine();
		String path = "/home/workspace/eclipse/Compiler/c.txt";
		Grammer gm = new Grammer(path);
		gm.grammerDeal();
		in.close();
	}
}
