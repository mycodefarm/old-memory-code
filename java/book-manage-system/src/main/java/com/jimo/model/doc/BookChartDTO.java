package com.jimo.model.doc;

import java.util.List;

public class BookChartDTO {
	private List<String> month;
	private List<Integer> cs;// 册数

	public List<String> getMonth() {
		return month;
	}

	public void setMonth(List<String> month) {
		this.month = month;
	}

	public List<Integer> getCs() {
		return cs;
	}

	public void setCs(List<Integer> cs) {
		this.cs = cs;
	}

}
