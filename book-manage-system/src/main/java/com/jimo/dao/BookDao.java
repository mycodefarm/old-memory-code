package com.jimo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jimo.model.Bjs;
import com.jimo.model.Book;
import com.jimo.model.BookType;
import com.jimo.model.Books;
import com.jimo.util.MyUtils;

@Repository
public class BookDao {
	@Autowired
	JdbcTemplate jt;
	////////////////////////////// bjs//////////////////////////////

	public int addBjs(Bjs b) {
		String sql = "insert into bjs values(?,?,?)";
		int re = 0;
		try {
			re = jt.update(sql, b.getNum(), b.getBjsName(), b.getInUse());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public int checkBjs(Bjs b) {
		String sql = "select count(*) from bjs where bjsName=?";
		int re = 0;
		try {
			re = jt.queryForObject(sql, Integer.class, b.getBjsName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public int delBjs(Integer id) {
		String sql = "delete from bjs where id=?";
		int re = 0;
		try {
			re = jt.update(sql, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	/**
	 * 取得所有编辑室
	 * 
	 * @return
	 */
	public List<Bjs> getAllBjs() {
		String sql = "select *from bjs order by num asc";
		List<Bjs> re = null;
		try {
			re = jt.query(sql, new BeanPropertyRowMapper<Bjs>(Bjs.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public int enableBjs(String use, Integer id) {
		int re = 0;
		String sql = "update bjs set inUse=? where id=?";
		try {
			re = jt.update(sql, use, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	//////////////////////////// end bjs/////////////////////////////////

	////////////////// book type///////////////////
	public int addBookType(BookType b) {
		String sql = "insert into book_type values(?,?)";
		int re = 0;
		try {
			re = jt.update(sql, b.getCode(), b.getBookType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public int checkBookType(BookType b) {
		String sql = "select count(*) from book_type where code=? and bookType=?";
		int re = 0;
		try {
			re = jt.queryForObject(sql, Integer.class, b.getCode(), b.getBookType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public int delBookType(Integer id) {
		String sql = "delete from book_type where id=?";
		int re = 0;
		try {
			re = jt.update(sql, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	/**
	 * 取得图书类型
	 * 
	 * @return
	 */
	public List<BookType> getAllBookType() {
		String sql = "select *from book_type";
		List<BookType> re = null;
		try {
			re = jt.query(sql, new BeanPropertyRowMapper<BookType>(BookType.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	////////////////// end book type///////////////////

	// 2017-05-08
	// ==================manageBook===================
	public int addOneBook(Book b) {
		String sql = "insert into book values(?,?,?,?,?,?,?,?,?,?)";
		int re = 0;
		try {
			re = jt.update(sql, b.getBookName(), b.getAuthor(), b.getBookNum(), b.getBookType(), b.getPublishDate(),
					b.getPublishNum(), b.getBookSize(), b.getPrice(), b.getBjsName(), b.getEditMan());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public int isBookExist(Book b) {
		int re = 0;
		String sql = "select count(*) from book where (bookNum=? or (bookName=? and author=?)) and id<>?";
		try {
			re = jt.queryForObject(sql, Integer.class, b.getBookNum(), b.getBookName(), b.getAuthor(), b.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public int delBook(Integer id) {
		String sql = "delete from book where id=?";
		int re = 0;
		try {
			re = jt.update(sql, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public int updateBook(Book b) {
		String sql = "update book set bookName=?,author=?," + "bookNum=?,bookType=?,publishDate=?,publishNum=?,"
				+ "bookSize=?,price=?,bjsName=?,editMan=? where id=?";
		int re = 0;
		try {
			re = jt.update(sql, b.getBookName(), b.getAuthor(), b.getBookNum(), b.getBookType(), b.getPublishDate(),
					b.getPublishNum(), b.getBookSize(), b.getPrice(), b.getBjsName(), b.getEditMan(), b.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public Book getOneBook(Integer id) {
		String sql = "select * from book where id=?";
		Book b = null;
		try {
			b = jt.queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper<Book>(Book.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public List<Book> getBooks() {
		String sql = "select *from book order by id desc";
		List<Book> re = null;
		try {
			re = jt.query(sql, new BeanPropertyRowMapper<Book>(Book.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public List<Book> getBooksByCondition(Book b, String sort) {
		String sql = "select *from book where 1=1";
		StringBuffer sb = new StringBuffer();
		Object[] obj = null;
		List<Object> c = new ArrayList<>();
		if (!MyUtils.isEmpty(b.getBookName())) {
			sb.append(" and bookName like ? ");
			c.add("%" + b.getBookName() + "%");
		}
		if (!MyUtils.isEmpty(b.getAuthor())) {
			sb.append(" and author like ? ");
			c.add("%" + b.getAuthor() + "%");
		}
		if (!MyUtils.isEmpty(b.getBookNum())) {
			sb.append(" and bookNum like ? ");
			c.add("%" + b.getBookNum() + "%");
		}
		if (!MyUtils.isEmpty(b.getBookType())) {
			sb.append(" and bookType=?");
			c.add(b.getBookType());
		}
		if (!MyUtils.isEmpty(b.getPublishDate())) {
			sb.append(" and publishDate <= ? ");
			c.add(b.getPublishDate());
		}
		if (!MyUtils.isEmpty(b.getPublishNum())) {
			sb.append(" and publishNum=? ");
			c.add(b.getPublishNum());
		}
		if (!MyUtils.isEmpty(b.getBookSize())) {
			sb.append(" and bookSize=? ");
			c.add(b.getBookSize());
		}
		if (!MyUtils.isEmpty(b.getPrice())) {
			sb.append(" and price>? and price<? ");
			c.add(b.getPrice() - 5);
			c.add(b.getPrice() + 5);
		}
		if (!MyUtils.isEmpty(b.getBjsName())) {
			sb.append(" and bjsName=?");
			c.add(b.getBjsName());
		}
		if (!MyUtils.isEmpty(b.getEditMan())) {
			sb.append(" and editMan like ? ");
			c.add("%" + b.getEditMan() + "%");
		}
		if ("asc".equals(sort)) {
			sb.append(" order by id asc");
		} else {
			sb.append(" order by id desc");
		}
		obj = c.toArray();
		List<Book> re = null;
		try {
			re = jt.query(sql + sb.toString(), obj, new BeanPropertyRowMapper<Book>(Book.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}
	// ==================end manageBook===================

	// ==================book2===================
	public int addOneBook2(Books b) {
		String sql = "insert into book2 values(?,?,?)";
		int re = 0;
		try {
			re = jt.update(sql, b.getBjsName(), b.getBookType(), b.getBookName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public int delBook2(Integer id) {
		String sql = "delete from book2 where id=?";
		int re = 0;
		try {
			re = jt.update(sql, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public int updateBook2(Books b) {
		String sql = "update book2 set bjsName=?,bookType=?,bookName=? where id=?";
		int re = 0;
		try {
			re = jt.update(sql, b.getBjsName(), b.getBookType(), b.getBookName(), b.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public Books getOneBook2(Integer id) {
		String sql = "select * from book2 where id=?";
		Books b = null;
		try {
			b = jt.queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper<Books>(Books.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public List<Books> getBooks2() {
		String sql = "select *from book2";
		List<Books> re = null;
		try {
			re = jt.query(sql, new BeanPropertyRowMapper<Books>(Books.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public List<Books> getBooksByCondition2(Books b) {
		String sql = "select *from book2 where 1=1";
		StringBuffer sb = new StringBuffer();
		Object[] obj = null;
		List<Object> c = new ArrayList<>();
		if (!MyUtils.isEmpty(b.getBookName())) {
			sb.append(" and bookName like ? ");
			c.add("%" + b.getBookName() + "%");
		}
		if (null != b.getBjsId()) {
			sb.append(" and bjsName=?");
			c.add(b.getBjsName());
		}
		if (b.getBookTypeId() != null) {
			sb.append(" and bookType=?");
			c.add(b.getBookType());
		}
		obj = c.toArray();
		List<Books> re = null;
		try {
			re = jt.query(sql + sb.toString(), obj, new BeanPropertyRowMapper<Books>(Books.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}
	// ==================end book2===================

	// ==================book1===================//
	public int addBook1(Books b) {
		String sql = "insert into books values(?,?,?)";
		int re = 0;
		try {
			re = jt.update(sql, b.getBjsId(), b.getBookTypeId(), b.getBookName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public int delBook1(Integer id) {
		String sql = "delete from books where id=?";
		int re = 0;
		try {
			re = jt.update(sql, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public int updateBook1(Books b) {
		String sql = "update books set bjsId=?,bookTypeId=?,bookName=? where id=?";
		int re = 0;
		try {
			re = jt.update(sql, b.getBjsId(), b.getBookTypeId(), b.getBookName(), b.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public Books getOneBook1(Integer id) {
		String sql = "select * from v_books where id=?";
		Books b = null;
		try {
			b = jt.queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper<Books>(Books.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public List<Books> getBooks1() {
		String sql = "select *from v_books ";
		List<Books> re = null;
		try {
			re = jt.query(sql, new BeanPropertyRowMapper<Books>(Books.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	/**
	 * 取得所有符合条件的书籍
	 * 
	 * @return
	 */
	public List<Books> getBooksByCondition1(Books b) {
		String sql = "select *from v_books where 1=1";
		StringBuffer sb = new StringBuffer();
		Object[] obj = null;
		List<Object> c = new ArrayList<>();
		if (!MyUtils.isEmpty(b.getBookName())) {
			sb.append(" and bookName like ? ");
			c.add("%" + b.getBookName() + "%");
		}
		if (null != b.getBjsId()) {
			sb.append(" and bjsId=?");
			c.add(b.getBjsId());
		}
		if (b.getBookTypeId() != null) {
			sb.append(" and bookTypeId=?");
			c.add(b.getBookTypeId());
		}
		obj = c.toArray();
		List<Books> re = null;
		try {
			re = jt.query(sql + sb.toString(), obj, new BeanPropertyRowMapper<Books>(Books.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}
	// ==================end book1===================
	// ==================book3===================//

	public int addBook3(Books b) {
		String sql = "insert into book3 values(?,?,?)";
		int re = 0;
		try {
			re = jt.update(sql, b.getBjsId(), b.getCode(), b.getBookName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public int delBook3(Integer id) {
		String sql = "delete from book3 where id=?";
		int re = 0;
		try {
			re = jt.update(sql, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public int updateBook3(Books b) {
		String sql = "update book3 set bjsId=?,code=?,bookName=? where id=?";
		int re = 0;
		try {
			re = jt.update(sql, b.getBjsId(), b.getCode(), b.getBookName(), b.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public Books getOneBook3(Integer id) {
		String sql = "select b.*,bj.bjsName,bt.bookType from book3 b "
				+ "JOIN bjs bj on b.bjsId=bj.id JOIN book_type bt on b.code=bt.code  where b.id=?";
		Books b = null;
		try {
			b = jt.queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper<Books>(Books.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public List<Books> getBooks3() {
		String sql = "select b.*,bj.bjsName,bt.bookType from book3 b JOIN bjs bj on b.bjsId=bj.id JOIN book_type bt on b.code=bt.code ";
		List<Books> re = null;
		try {
			re = jt.query(sql, new BeanPropertyRowMapper<Books>(Books.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	public List<Books> getBooksByCondition3(Books b) {
		String sql = "select b.*,bj.bjsName,bt.bookType from book3 b "
				+ "JOIN bjs bj on b.bjsId=bj.id JOIN book_type bt on b.code=bt.code where 1=1";
		StringBuffer sb = new StringBuffer();
		Object[] obj = null;
		List<Object> c = new ArrayList<>();
		if (!MyUtils.isEmpty(b.getBookName())) {
			sb.append(" and bookName like ? ");
			c.add("%" + b.getBookName() + "%");
		}
		if (null != b.getBjsId()) {
			sb.append(" and bj.id=?");
			c.add(b.getBjsId());
		}
		if (b.getBookTypeId() != null) {
			sb.append(" and b.code=?");
			c.add(b.getBookTypeId());
		}
		obj = c.toArray();
		List<Books> re = null;
		try {
			re = jt.query(sql + sb.toString(), obj, new BeanPropertyRowMapper<Books>(Books.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}
	// ==================end book3===================

	public List<Book> getBooksByCon(String sql, Object[] obj) {
		List<Book> re = null;
		try {
			re = jt.query(sql, obj, new BeanPropertyRowMapper<Book>(Book.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}
}
