package com.jimo.model.doc;

public class BookInfo {
	protected Integer id;// id
	protected String shuh;// 书号
	protected String shum;// 书名
	protected String zuozhe;// 作者
	protected String tsfl;// 图书分类
	protected String kb;// 开本
	protected Double dj;// 单价

	public String getShuh() {
		return shuh;
	}

	public void setShuh(String shuh) {
		this.shuh = shuh;
	}

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

	public String getTsfl() {
		return tsfl;
	}

	public void setTsfl(String tsfl) {
		this.tsfl = tsfl;
	}

	public String getKb() {
		return kb;
	}

	public void setKb(String kb) {
		this.kb = kb;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getDj() {
		return dj;
	}

	public void setDj(Double dj) {
		this.dj = dj;
	}

}
