package com.jimo.util;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ModelUtil {
	@Autowired
	private JdbcTemplate jt;

	public void generateBean(String tableName) {
		String sql = "select name from sys.columns where object_id=object_id(?)";
		List<String> cols = null;
		StringBuilder bean = new StringBuilder();
		try {
			cols = jt.queryForList(sql, new Object[] { tableName }, String.class);
			for (String col : cols) {
				bean.append("private String ").append(col).append(";").append('\n');
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(bean.toString());
	}

	public void generateBeanByMysql(String tableName) {
		String sql = "select COLUMN_NAME,COLUMN_COMMENT from information_schema.COLUMNS where table_name=?";
		List<Map<String, Object>> cols = null;
		StringBuilder bean = new StringBuilder();
		try {
			cols = jt.queryForList(sql, tableName);
			for (Map<String, Object> col : cols) {
				bean.append("private String ").append(col.get("COLUMN_NAME")).append(";//")
						.append(col.get("COLUMN_COMMENT")).append('\n');
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(bean.toString());
	}
}
