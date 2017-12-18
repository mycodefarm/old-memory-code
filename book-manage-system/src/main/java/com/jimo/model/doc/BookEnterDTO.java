package com.jimo.model.doc;

public class BookEnterDTO extends BookEnter {
	protected String shum;// 书名
	protected String zuozhe;// 作者
	protected Double dj;// 单价
	private Double zmy;// 总码样

	public String getShum() {
		return shum;
	}

	public void setShum(String shum) {
		this.shum = shum;
	}

	public String getZuozhe() {
		return zuozhe;
	}

	public void setZuozhe(String zuozhe) {
		this.zuozhe = zuozhe;
	}

	public Double getDj() {
		return dj;
	}

	public void setDj(Double dj) {
		this.dj = dj;
	}

	public Double getZmy() {
		zmy = dj * rkcs;
		return zmy;
	}

	public void setZmy(Double zmy) {
		this.zmy = zmy;
	}

}
