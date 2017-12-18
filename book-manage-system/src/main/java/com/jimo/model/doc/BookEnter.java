package com.jimo.model.doc;

import java.util.Date;

/**
 * 入库信息
 * 
 * @author root
 *
 */
public class BookEnter {
	protected Integer id;//
	protected Date rkrq;// 入库日期
	protected String shuh;// 书号
	protected Integer rkcs;// 入库册数

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getRkrq() {
		return rkrq;
	}

	public void setRkrq(Date rkrq) {
		this.rkrq = rkrq;
	}

	public String getShuh() {
		return shuh;
	}

	public void setShuh(String shuh) {
		this.shuh = shuh;
	}

	public Integer getRkcs() {
		return rkcs;
	}

	public void setRkcs(Integer rkcs) {
		this.rkcs = rkcs;
	}

}
