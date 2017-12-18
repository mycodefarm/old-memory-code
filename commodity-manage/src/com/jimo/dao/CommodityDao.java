package com.jimo.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.jimo.model.Book;
import com.jimo.util.DBUtil;
import com.jimo.util.MyConst;

public class CommodityDao {
	private QueryRunner run;

	public CommodityDao() {
		run = new QueryRunner(DBUtil.getDataSource());
	}

	public List<Book> getAllBooks(int page, String str) {
		String sql = "select * from Book where bookName like '%" + str + "%' limit "
				+ ((page - 1) * MyConst.PAGE_NUM) + "," + MyConst.PAGE_NUM;
		List<Book> res = new ArrayList<>();
		try {
			res = run.query(sql, new BeanListHandler<>(Book.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 加一本书
	 * 
	 * @param b
	 * @return
	 */
	public int addOneBook(Book b) {
		String sql = "insert into Book(bookName,bookPrice,bookAuthor) values(?,?,?)";
		Object[] pas = { b.getBookName(), b.getBookPrice(), b.getBookAuthor() };
		try {
			return run.update(sql, pas);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int delOneBook(int id) {
		String sql = "delete from Book where bookId = " + id;
		try {
			return run.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int updateOneBook(int id, Book b) {
		String sql = "update Book set bookName=?,bookPrice=?,bookAuthor=? where bookId=" + id;
		Object[] params = { b.getBookName(), b.getBookPrice(), b.getBookAuthor() };
		try {
			return run.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getTotalPage() {
//		String sql = "";
		return 1;
	}
}
