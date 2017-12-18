package com.jimo.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class DBUtil {
	private static DataSource ds = null;
	static {
		try {
			Properties ppt = new Properties();
			InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
			ppt.load(in);
			ds = BasicDataSourceFactory.createDataSource(ppt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DataSource getDataSource() {
		return ds;
	}

	public static Connection getCon() throws SQLException {
		return ds.getConnection();
	}
}
