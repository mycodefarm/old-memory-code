package com.jimo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jimo.dao.BookDao;
import com.jimo.model.Bjs;
import com.jimo.model.Book;
import com.jimo.model.BookType;
import com.jimo.model.Books;
import com.jimo.util.Result;
import com.jimo.util.ResultHelper;

@Service
public class BookService {
	@Autowired
	private BookDao bd;

	///////////////// bjs//////////////////
	public Result addBjs(Bjs b) {
		// 检查是否重复
		if (bd.checkBjs(b) > 0) {
			return ResultHelper.error("已存在该编辑室");
		}
		int re = bd.addBjs(b);
		if (re == 1) {
			return ResultHelper.success();
		} else {
			return ResultHelper.error("添加失败");
		}
	}

	public Result delBjs(Integer id) {
		int re = bd.delBjs(id);
		if (re == 1) {
			return ResultHelper.success();
		} else {
			return ResultHelper.error("删除失败");
		}
	}

	public Result getBjs() {
		List<Bjs> b = bd.getAllBjs();
		if (b == null) {
			return ResultHelper.error("编辑室获取失败");
		} else {
			return ResultHelper.success(b);
		}
	}

	public Result enableBjs(Integer use, Integer id) {
		int re = 0;
		if (use == 1) {
			re = bd.enableBjs("是", id);
		} else {
			re = bd.enableBjs("否", id);
		}
		if (re == 1) {
			return ResultHelper.success();
		} else {
			return ResultHelper.error("添加失败");
		}
	}

	//////////////// end bjs///////////////////

	////////////////// book type///////////////////
	public Result addBookType(BookType b) {
		// 检查是否重复
		if (bd.checkBookType(b) > 0) {
			return ResultHelper.error("已存在该类型");
		}
		int re = bd.addBookType(b);
		if (re == 1) {
			return ResultHelper.success();
		} else {
			return ResultHelper.error("添加失败");
		}
	}

	public Result delBookType(Integer id) {
		int re = bd.delBookType(id);
		if (re == 1) {
			return ResultHelper.success();
		} else {
			return ResultHelper.error("删除失败");
		}
	}

	public Result getBookTypes() {
		List<BookType> b = bd.getAllBookType();
		if (b == null) {
			return ResultHelper.error("类型列表获取失败");
		} else {
			return ResultHelper.success(b);
		}
	}

	////////////////// end book type///////////////////

	public Result addBook(Books b, int type) {
		int re = 0;
		switch (type) {
		case 1:
			re = bd.addBook1(b);
			break;
		case 2:
			re = bd.addOneBook2(b);
			break;
		case 3:
			re = bd.addBook3(b);
			break;
		}
		if (re == 1) {
			return ResultHelper.success();
		} else {
			return ResultHelper.error("添加失败");
		}
	}

	public Result delBook(Integer id, int type) {
		int re = 0;
		switch (type) {
		case 1:
			re = bd.delBook1(id);
			break;
		case 2:
			re = bd.delBook2(id);
			break;
		case 3:
			re = bd.delBook3(id);
			break;
		}
		if (re == 1) {
			return ResultHelper.success();
		} else {
			return ResultHelper.error("删除失败");
		}
	}

	public Result updateBook(Books b, int type) {
		int re = 0;
		switch (type) {
		case 1:
			re = bd.updateBook1(b);
			break;
		case 2:
			re = bd.updateBook2(b);
			break;
		case 3:
			re = bd.updateBook3(b);
			break;
		}
		if (re == 1) {
			return ResultHelper.success();
		} else {
			return ResultHelper.error("更新失败");
		}
	}

	public Result getOneBook(Integer id, int type) {
		Books b = null;
		switch (type) {
		case 1:
			b = bd.getOneBook1(id);
			break;
		case 2:
			b = bd.getOneBook2(id);
			break;
		case 3:
			b = bd.getOneBook3(id);
			break;
		}
		if (b != null) {
			return ResultHelper.success(b);
		} else {
			return ResultHelper.error("获取失败");
		}
	}

	public Result getBooks(int type) {
		List<Books> b = null;
		switch (type) {
		case 1:
			b = bd.getBooks1();
			break;
		case 2:
			b = bd.getBooks2();
			break;
		case 3:
			b = bd.getBooks3();
			break;
		}
		if (b == null) {
			return ResultHelper.error("书籍列表获取失败");
		} else {
			return ResultHelper.success(b);
		}
	}

	public Result getQueryBooks(Books bb, int type) {
		List<Books> b = null;
		switch (type) {
		case 1:
			b = bd.getBooksByCondition1(bb);
			break;
		case 2:
			b = bd.getBooksByCondition2(bb);
			break;
		case 3:
			b = bd.getBooksByCondition3(bb);
			break;
		}
		if (b == null) {
			return ResultHelper.error("书籍列表获取失败");
		} else {
			return ResultHelper.success(b);
		}
	}

	//////////////////// manageBook/////////////////////
	public Result addBook(Book b) {
		// 首先判断是否存在一样的书
		b.setId(0);
		if (bd.isBookExist(b) > 0) {
			return ResultHelper.error("已存在该书");
		}
		int re = bd.addOneBook(b);
		if (re == 1) {
			return ResultHelper.success();
		} else {
			return ResultHelper.error("添加失败");
		}
	}

	public Result delBook(Integer id) {
		int re = bd.delBook(id);
		if (re == 1) {
			return ResultHelper.success();
		} else {
			return ResultHelper.error("删除失败");
		}
	}

	public Result updateBook(Book b) {
		if (bd.isBookExist(b) > 0) {
			return ResultHelper.error("已存在该书");
		}
		int re = bd.updateBook(b);
		if (re == 1) {
			return ResultHelper.success();
		} else {
			return ResultHelper.error("更新失败");
		}
	}

	public Result getOneBook(Integer id) {
		Book b = bd.getOneBook(id);
		if (b != null) {
			return ResultHelper.success(b);
		} else {
			return ResultHelper.error("获取失败");
		}
	}

	public Result getBooks() {
		List<Book> b = bd.getBooks();
		if (b == null) {
			return ResultHelper.error("书籍列表获取失败");
		} else {
			return ResultHelper.success(b);
		}
	}

	public Result getQueryBooks(Book bb, String sort) {
		List<Book> b = bd.getBooksByCondition(bb, sort);
		if (b == null) {
			return ResultHelper.error("书籍列表获取失败");
		} else {
			return ResultHelper.success(b);
		}
	}

	public Result getQueryBook(String con, String val) {
		String sql = "select *from book where 1=1 " + con;
		String[] vs = val.split(",");
		String[] cs = con.split(" ");
		for (int i = 0; i < cs.length; i++) {
			if ("like".equals(cs[i])) {
				vs[i / 4] = "%" + vs[i / 4] + "%";
			}
			if ("publishDate".equals(cs[i])) {
				try {
					vs[i / 4] = new SimpleDateFormat("yyyy-MM-dd").parse(vs[i / 4]).getTime() + "";
				} catch (ParseException e) {
					e.printStackTrace();
					vs[i / 4] = 1000000 + "";
				}
			}
		}
		Object[] obj = vs;
		List<Book> b = bd.getBooksByCon(sql, obj);
		if (b == null) {
			return ResultHelper.error("书籍列表获取失败");
		} else {
			return ResultHelper.success(b);
		}
	}
}
