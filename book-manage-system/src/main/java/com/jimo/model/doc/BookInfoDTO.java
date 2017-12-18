package com.jimo.model.doc;

public class BookInfoDTO extends BookInfo {
	private Integer cs;// 库存册数
	private Double zmy;// 总码样 = 单价×库存

	public Integer getCs() {
		return cs;
	}

	public void setCs(Integer cs) {
		this.cs = cs;
	}

	public Double getZmy() {
		zmy = cs * dj;
		return zmy;
	}

	public void setZmy(Double zmy) {
		this.zmy = zmy;
	}

}
