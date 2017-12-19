package com.jimo.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jimo.dao.UserDao;
import com.jimo.exception.MyException;
import com.jimo.model.User;
import com.jimo.util.Result;
import com.jimo.util.ResultHelper;

@Service
public class UserService {

	@Autowired
	private UserDao ud;

	public Result login(User user, HttpSession session) {
		User u = ud.findByUsername(user.getUsername());
		if (u == null) {
			throw new MyException(ResultHelper.error("用户不存在"));
		}
		if (!u.getPassword().equals(user.getPassword())) {
			throw new MyException(ResultHelper.error("密码错误"));
		}
		session.setAttribute("user", u);
		return ResultHelper.success();
	}
}
