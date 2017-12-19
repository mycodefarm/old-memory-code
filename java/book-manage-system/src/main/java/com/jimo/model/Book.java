package com.jimo.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 2017-05-08
 * 
 * @author root
 *
 */
public class Book {
	private Integer id;
	private String bookName;
	private String author;
	private String bookNum;// 书号
	private String bookType;
	private String publishDate;
	private Integer publishNum;// 出版数
	private Integer bookSize;// 开本，16,32开
	private Float price;
	private String bjsName;
	private String editMan;

	public Integer getId() {
		return id;
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBookNum() {
		return bookNum;
	}

	public void setBookNum(String bookNum) {
		this.bookNum = bookNum;
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public String getPublishDate() {
		if ("".equals(publishDate)) {
			return publishDate;
		} else if (publishDate.contains("-")) {
			try {
				return new SimpleDateFormat("yyyy-MM-dd").parse(publishDate).getTime() + "";
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return new Date().getTime() + "";
		} else {
			long d = Long.parseLong(publishDate);
			return new SimpleDateFormat("yyyy-MM-dd").format(new Date(d));
		}
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public Integer getPublishNum() {
		return publishNum;
	}

	public void setPublishNum(Integer publishNum) {
		this.publishNum = publishNum;
	}

	public Integer getBookSize() {
		return bookSize;
	}

	public void setBookSize(Integer bookSize) {
		this.bookSize = bookSize;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getBjsName() {
		return bjsName;
	}

	public void setBjsName(String bjsName) {
		this.bjsName = bjsName;
	}

	public String getEditMan() {
		return editMan;
	}

	public void setEditMan(String editMan) {
		this.editMan = editMan;
	}
}
