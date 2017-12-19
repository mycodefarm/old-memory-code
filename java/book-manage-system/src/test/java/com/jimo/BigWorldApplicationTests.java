package com.jimo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jimo.dao.BookDao;
import com.jimo.dao.DocDao;
import com.jimo.util.ExcelUtil;
import com.jimo.util.ModelUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BigWorldApplicationTests {

	@Autowired
	private ModelUtil mu;
	@Autowired
	private BookDao bd;
	@Autowired
	private DocDao dd;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testUtil() {
		// mu.generateBeanByMysql("book_info");
		// mu.generateBeanByMysql("book_enter");
		mu.generateBeanByMysql("book_storage");
	}

	@Test
	public void testBD() {
		System.out.println(bd.getBooks2().size());
	}

	@Test
	public void testDD() {
		ExcelUtil.export(dd.getBookInfos(""), "/home");
	}
}
