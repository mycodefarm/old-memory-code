package com.jimo.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.jimo.model.Admin;
import com.jimo.util.DBUtil;
import com.jimo.util.MyConst;

public class AdminDao {
	private QueryRunner run;

	public AdminDao() {
		run = new QueryRunner(DBUtil.getDataSource());
	}

	public boolean login(String name, String pass) {
		String sql = "select*from Admins where username='" + name + "'";
		try {
			Admin admin = run.query(sql, new BeanHandler<>(Admin.class));
			if (null != admin && admin.getPassword().equals(pass)) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public int updatePassword(String newPass, String oldPass) {
		String sql1 = "select * from Admins where username='" + MyConst.USER_NAME + "'";
		String sql = "update Admins set password=? where username=?";
		Object[] param = { newPass, MyConst.USER_NAME };
		try {
			Admin ad = run.query(sql1, new BeanHandler<>(Admin.class));
			if (!ad.getPassword().equals(oldPass)) {
				return 0;
			}
			return run.update(sql, param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
