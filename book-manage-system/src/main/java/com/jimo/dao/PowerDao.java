package com.jimo.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jimo.model.Right;
import com.jimo.model.User;

@Repository
public class PowerDao {
	@Autowired
	private JdbcTemplate jt;

	RowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User.class);

	/**
	 * 取得所有用户
	 * 
	 * @return
	 */
	public List<User> getAllUser() {
		String sql = "select*from users";
		List<User> re = null;
		try {
			re = jt.query(sql, userRowMapper);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	/**
	 * 插入用户
	 * 
	 * @param user
	 * @return
	 */
	public int addUser(User user) {
		String sql = "insert into users values(?,?,0,null)";
		int re = 0;
		try {
			re = jt.update(sql, user.getUsername(), user.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	/**
	 * 更新用户
	 * 
	 * @param id
	 * @return
	 */
	public int updateUser(User u) {
		String sql = "update users  set username=?,password=? where id=?";
		int re = 0;
		try {
			re = jt.update(sql, u.getUsername(), u.getPassword(), u.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	public int deleteUser(String id) {
		String sql = "delete from users where id=?";
		int re = 0;
		try {
			re = jt.update(sql, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	/**
	 * 删除用户权限
	 * 
	 * @param id
	 * @return
	 */
	public int deleteUserPower(String id) {
		int re = 0;
		String sql = "delete from user_rights where user_id=?";
		try {
			re = jt.update(sql, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	/**
	 * 批处理添加权限
	 * 
	 * @param id
	 * @param pids
	 * @return
	 */
	public int addPowers(String id, String[] pids) {
		int[] re = new int[0];
		String sql = "insert into user_rights values(?,?)";
		try {
			re = jt.batchUpdate(sql, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, id);
					ps.setString(2, pids[i]);
				}

				@Override
				public int getBatchSize() {
					return pids.length;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re.length;
	}

	/**
	 * 批处理删除权限
	 * 
	 * @param id
	 * @param pids
	 * @return
	 */
	public int removePowers(String id, String[] pids) {
		int[] re = new int[0];
		String sql = "delete from user_rights where user_id=? and right_id=?";
		try {
			re = jt.batchUpdate(sql, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, id);
					ps.setString(2, pids[i]);
				}

				@Override
				public int getBatchSize() {
					return pids.length;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re.length;
	}

	/**
	 * 取得用户有的权限
	 * 
	 * @param id
	 * @return
	 */
	public List<Right> getUserPowers(String id) {
		String sql = "select * from rights where id in (select right_id from user_rights where user_id=?)";
		List<Right> re = null;
		try {
			new BeanPropertyRowMapper<>(Right.class);
			re = jt.query(sql, new Object[] { id }, new BeanPropertyRowMapper<>(Right.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}

	/**
	 * 取得用户没有的权限
	 * 
	 * @param id
	 * @return
	 */
	public List<Right> getUserNoPowers(String id) {
		String sql = "select * from rights where id not in (select right_id from user_rights where user_id=?)";
		List<Right> re = null;
		try {
			new BeanPropertyRowMapper<>(Right.class);
			re = jt.query(sql, new Object[] { id }, new BeanPropertyRowMapper<>(Right.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
	}
}
