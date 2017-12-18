package com.jimo.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.jimo.model.Firm;
import com.jimo.util.DBUtil;
import com.jimo.util.MyConst;

public class FirmDao {
	private QueryRunner run;

	public FirmDao() {
		run = new QueryRunner(DBUtil.getDataSource());
	}

	public List<Firm> getPageFirms(int currPage, String str) {
		String sql = "select*from Firm where firmName like '%" + str + "%' or firmDesc like '%" + str + "%' limit "
				+ (currPage - 1) * MyConst.PAGE_NUM + "," + MyConst.PAGE_NUM;
		List<Firm> res = new ArrayList<>();
		try {
			res = run.query(sql, new BeanListHandler<>(Firm.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public int addOneFirm(Firm f) {
		String sql = "insert into Firm values(?,?,?)";
		Object[] param = { f.getFirmName(), f.getFirmDesc(), f.getFirmTel() };
		try {
			return run.update(sql, param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int updateOneFirm(String beforeName, Firm f) {
		String sql = "update Firm set firmName=?,firmTel=?,firmDesc=? where firmName=?";
		Object[] params = { f.getFirmName(), f.getFirmTel(), f.getFirmDesc(), beforeName };
		try {
			return run.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int delOneFirm(String name) {
		String sql = "delete from Firm where firmName=?";
		Object[] param = { name };
		try {
			return run.update(sql, param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
