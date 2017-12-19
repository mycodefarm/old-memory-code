package com.jimo.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.jimo.model.Sales;
import com.jimo.util.DBUtil;
import com.jimo.util.MyConst;

public class SaleDao {
	private QueryRunner run;

	public SaleDao() {
		run = new QueryRunner(DBUtil.getDataSource());
	}

	public List<Sales> getAllSaless(int page, String str) {
		String sql = "select * from Sales where saleDesc like '%" + str + "%' limit " + ((page - 1) * MyConst.PAGE_NUM)
				+ "," + MyConst.PAGE_NUM;
		List<Sales> res = new ArrayList<>();
		try {
			res = run.query(sql, new BeanListHandler<>(Sales.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public int addOneSales(Sales s) {
		String sql = "insert into Sales(saleDate,saleMoney,saleDesc) values(?,?,?)";
		Object[] pas = { s.getSaleDate(), s.getSaleMoney(), s.getSaleDesc() };
		try {
			return run.update(sql, pas);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int delOneSales(int id) {
		String sql = "delete from Sales where saleId = " + id;
		try {
			return run.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int updateOneSales(int id, Sales s) {
		String sql = "update Sales set saleDate=?,saleMoney=?,saleDesc=? where saleId=" + id;
		Object[] params = { s.getSaleDate(), s.getSaleMoney(), s.getSaleDesc() };
		try {
			return run.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getTotalPage() {
		// String sql = "";
		return 1;
	}
}
