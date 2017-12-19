package com.jimo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jimo.dao.UserDao;
import com.jimo.model.User;
import com.jimo.property.Argments;
import com.jimo.util.MyUtils;
import com.jimo.util.Result;
import com.jimo.util.ResultCode;
import com.jimo.util.ResultHelper;

@Service
public class UserService {
	@Autowired
	private UserDao ud;
	@Autowired
	private Argments arg;

	/*
	 * 用户可以登录的之前日期
	 */
	public List<String> getLoginDate() {
		List<String> ds = new ArrayList<String>();
		Date d = new Date();
		for (int i = 0; i <= arg.getDays(); i++) {
			ds.add(MyUtils.getDateTimeAfterDay(d, -1 * i));
		}
		return ds;
	}

	public Result checkLogin(String name, String pass, String date) {
		User user = ud.getUser(name);
		if (user == null) {
			return ResultHelper.error(ResultCode.INVALID_USER);
		}

		if (user.getUnlock_time() != null && user.getUnlock_time().compareTo(MyUtils.getDateTime()) > 0) {
			return ResultHelper.error(ResultCode.USER_LOCK);
		}

		if (!user.getPassword().equals(pass)) {
			// 失败次数+1
			ud.updateUserFaultTime(name);
			if (user.getFault_time() + 1 >= arg.getNum()) {
				ud.updateUserFaultLockDay(name);
				ud.resetUserFaultTime(name);
				// back.setMsg("密码错误,账户已锁定");
			}
			return ResultHelper.error(ResultCode.INVALID_PASSWORD);
		}

		// 登录日期控制
		int day = MyUtils.getDaysBetweenNow(date);
		if (day < 0 || day > arg.getDays()) {
			return ResultHelper.error(ResultCode.OUT_OF_DATE.getErrcode(), "只能登录最近" + arg.getDays() + "天");
		}

		// 成功
		ud.resetUserFaultTime(name);
		ud.resetUserFaultLockDay(name);
		return ResultHelper.success(user);
	}

}
