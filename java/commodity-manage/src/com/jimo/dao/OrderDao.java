package com.jimo.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.jimo.model.COrder;
import com.jimo.util.DBUtil;
import com.jimo.util.MyConst;

public class OrderDao {
	private QueryRunner run;

	public OrderDao() {
		run = new QueryRunner(DBUtil.getDataSource());
	}

	public List<COrder> getPageCOrders(int currPage, String str) {
		String sql = "select*from COrder where orderDesc like '%" + str + "%' or firmName like '%" + str + "%' limit "
				+ (currPage - 1) * MyConst.PAGE_NUM + "," + MyConst.PAGE_NUM;
		List<COrder> res = new ArrayList<>();
		try {
			res = run.query(sql, new BeanListHandler<>(COrder.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public int addOneCOrder(COrder c) {
		String sql = "insert into COrder(orderDate,orderMoney,orderDesc,orderState,firmName) values(?,?,?,?,?)";
		Object[] param = { c.getOrderDate(), c.getOrderMoney(), c.getOrderDesc(), c.getOrderState(), c.getFirmName() };
		try {
			return run.update(sql, param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int updateOneCOrder(int orderId, COrder c) {
		String sql = "update COrder set orderDate=?,orderMoney=?,orderDesc=? ,orderState=?,firmName=? where orderId=?";
		Object[] params = { c.getOrderDate(), c.getOrderMoney(), c.getOrderDesc(), c.getOrderState(), c.getFirmName(),
				orderId };
		try {
			return run.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int delOneCOrder(int orderId) {
		String sql = "delete from COrder where orderId=?";
		Object[] param = { orderId };
		try {
			return run.update(sql, param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
