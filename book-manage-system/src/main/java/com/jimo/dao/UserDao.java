package com.jimo.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jimo.model.User;
import com.jimo.property.Argments;
import com.jimo.util.MyUtils;

@Repository
public class UserDao {
	@Autowired
	JdbcTemplate jt;
	@Autowired
	private Argments arg;

	RowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User.class);

	public User getUser(String name) {
		String sql = "select top 1 * from users where username=?";
		User user = null;
		try {
			user = jt.queryForObject(sql, new Object[] { name }, userRowMapper);
		} catch (Exception e) {
		}
		return user;
	}

	/**
	 * 更新用户失败次数
	 * 
	 * @param name
	 * @return
	 */
	public int updateUserFaultTime(String name) {
		String sql = "update users set fault_time=fault_time+1 where username=?";
		int re = 0;
		try {
			re = jt.update(sql, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	/**
	 * 重置失败次数
	 * 
	 * @param name
	 * @return
	 */
	public int resetUserFaultTime(String name) {
		String sql = "update users set fault_time=0 where username=?";
		int re = 0;
		try {
			re = jt.update(sql, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	/**
	 * 更新用户解锁的时间
	 * 
	 * @param name
	 * @return
	 */
	public int updateUserFaultLockDay(String name) {
		String sql = "update users set unlock_time=? where username=?";
		int re = 0;
		try {
			re = jt.update(sql, MyUtils.getDateTimeAfterDay(new Date(), arg.getLock_days()), name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	/**
	 * 重置lockDay
	 * 
	 * @param name
	 * @return
	 */
	public int resetUserFaultLockDay(String name) {
		String sql = "update users set unlock_time=null where username=?";
		int re = 0;
		try {
			re = jt.update(sql, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}
}
