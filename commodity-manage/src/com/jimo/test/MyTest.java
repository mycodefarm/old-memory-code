package com.jimo.test;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.junit.Test;

import com.jimo.dao.CommodityDao;
import com.jimo.dao.FirmDao;
import com.jimo.dao.OrderDao;
import com.jimo.dao.SaleDao;
import com.jimo.model.COrder;
import com.jimo.model.Firm;
import com.jimo.model.Sales;
import com.jimo.util.DBUtil;
import com.jimo.util.ExcelUtil;

public class MyTest {

	@Test
	public void testCon() throws SQLException {
		QueryRunner run = new QueryRunner(DBUtil.getDataSource());
		String sql = "select *from Admins";
		Object[] res = run.query(sql, new ArrayHandler());
		System.out.println(res[0] + " " + res[1]);
	}

	@Test
	public void testComDao() {
		CommodityDao cd = new CommodityDao();
		// for (int i = 0; i < 100; i++) {
		// Book b = new Book();
		// b.setBookAuthor("寂寞" + i);
		// b.setBookName("书名" + i);
		// b.setBookPrice(10.1f + i);
		// cd.addOneBook(b);
		// }
		System.out.println(cd.getAllBooks(1, "").size());
	}

	@Test
	public void testFirmDao() {
		FirmDao fd = new FirmDao();
		for (int i = 0; i < 100; i++) {
			Firm f = new Firm();
			f.setFirmDesc("本公司做什的哈哈哈哈哈啊哈" + i);
			f.setFirmName("公司名称" + i);
			f.setFirmTel("" + new Random().nextInt(10000000));
			fd.addOneFirm(f);
		}
	}

	@Test
	public void testOrderDao() {
		OrderDao od = new OrderDao();
		FirmDao fd = new FirmDao();
		List<Firm> f = fd.getPageFirms(1, "");
		for (Firm ff : f) {
			COrder c = new COrder();
			c.setFirmName(ff.getFirmName());
			c.setOrderDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			c.setOrderDesc(" 订单描述");
			c.setOrderMoney(new Random().nextInt(1000));
			c.setOrderState("待定");
			od.addOneCOrder(c);
		}
	}

	@Test
	public void testUtil() {
//		CommodityDao cd = new CommodityDao();
		SaleDao sd = new SaleDao();
//		ExcelUtil.exportBookExcel("/root/桌面/2016-12-02-03:24:00.xls", cd.getAllBooks(1, ""));
		ExcelUtil.exportSaleExcel("/root/桌面/2016-12-02-03:24:00.xls", sd.getAllSaless(1, ""));
	}

	@Test
	public void testSaleDao() {
		SaleDao sd = new SaleDao();
		Random r = new Random();
		for (int i = 0; i < 100; i++) {
			Sales s = new Sales();
			int k = r.nextInt(20);
			s.setSaleDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			s.setSaleDesc("卖了" + k + "本书");
			s.setSaleMoney(k * r.nextInt(20));
			sd.addOneSales(s);
		}
	}
}
