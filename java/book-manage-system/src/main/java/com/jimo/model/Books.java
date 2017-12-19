package com.jimo.model;

public class Books {
	private Integer id;
	private Integer bjsId;
	private Integer bookTypeId;
	private String bjsName;
	private String bookType;
	private String code;
	private String bookName;

	public Integer getId() {
		return id;
	}

	public Integer getBjsId() {
		return bjsId;
	}

	public void setBjsId(Integer bjsId) {
		this.bjsId = bjsId;
	}

	public Integer getBookTypeId() {
		return bookTypeId;
	}

	public void setBookTypeId(Integer bookTypeId) {
		this.bookTypeId = bookTypeId;
	}

	public String getBjsName() {
		return bjsName;
	}

	public void setBjsName(String bjsName) {
		this.bjsName = bjsName;
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
}
