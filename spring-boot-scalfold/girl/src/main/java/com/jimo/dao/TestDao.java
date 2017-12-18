package com.jimo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestDao {

	@Autowired
	JdbcTemplate jt;

	public void getMen() {
		String sql = "select count(*) from booksIn_2135";
		System.out.println(jt.queryForObject(sql, Integer.class));
	}
}
