package com.jimo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jimo.dao.PowerDao;
import com.jimo.dao.UserDao;
import com.jimo.model.PowerData;
import com.jimo.model.Right;
import com.jimo.model.User;
import com.jimo.util.Result;
import com.jimo.util.ResultCode;
import com.jimo.util.ResultHelper;

@Service
public class PowerService {
	@Autowired
	private PowerDao pd;
	@Autowired
	private UserDao ud;

	public List<User> getAllUser() {
		return pd.getAllUser();
	}

	/**
	 * 删除一个用户，先判断是否存在，存在则删除用户及他的权限
	 * 
	 * @param name
	 * @return
	 */
	public Result deleteUser(String name) {
		User user = ud.getUser(name);
		if (user == null) {
			return ResultHelper.error(ResultCode.INVALID_USER);
		}
		int re = pd.deleteUser(user.getId());
		if (re <= 0) {
			return ResultHelper.error(ResultCode.SYSTEM_ERR.getErrcode(), "删除用户失败");
		}
		// 删除权限
		pd.deleteUserPower(user.getId());
		return ResultHelper.success();
	}

	/**
	 * 保存一个用户，先判断是否存在，存在则修改用户，否则作为新的用户
	 * 
	 * @param User
	 *            user
	 * @return
	 */
	public Result saveUser(User u) {
		User user = ud.getUser(u.getUsername());
		if (user == null) {
			// add
			int re = pd.addUser(u);
			if (re > 0) {
				return ResultHelper.success(ResultCode.ADD_OK);
			} else {
				return ResultHelper.error("添加用户失败");
			}
		}
		u.setId(user.getId());
		int re = pd.updateUser(u);
		if (re <= 0) {
			return ResultHelper.error("更新用户失败");
		}
		return ResultHelper.success(ResultCode.UPDATE_OK);
	}

	public Result addPower(String id, String[] pids) {
		if (pd.addPowers(id, pids) > 0) {
			return ResultHelper.success(ResultCode.ADD_OK);
		}
		return ResultHelper.error(ResultCode.NORMAL_ERROR);
	}

	public Result removePower(String id, String[] pids) {
		if (pd.removePowers(id, pids) > 0) {
			return ResultHelper.success(ResultCode.UPDATE_OK);
		}
		return ResultHelper.error(ResultCode.NORMAL_ERROR);
	}

	/**
	 * 
	 * @param id
	 * @param type:1:有的权限，2：没有的权限
	 * @return
	 */
	public List<PowerData> getUserPowers(String id, int type) {
		List<PowerData> re = new ArrayList<>();
		List<Right> rts = null;
		if (type == 1) {
			rts = pd.getUserPowers(id);
		} else {
			rts = pd.getUserNoPowers(id);
		}
		Set<String> set = new HashSet<>();
		for (Right r : rts) {
			set.add(r.getModule());
		}
		if (rts != null) {
			for (String r : set) {
				PowerData data = new PowerData();
				List<Right> rl = new ArrayList<>();
				for (Right rs : rts) {
					if (rs.getModule().equals(r)) {
						rl.add(rs);
					}
				}
				data.setRights(rl);
				data.setCount(rl.size());
				re.add(data);
			}
		}
		return re;
	}
}
