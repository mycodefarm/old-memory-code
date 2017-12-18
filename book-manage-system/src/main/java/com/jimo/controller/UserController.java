package com.jimo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jimo.model.PowerResult;
import com.jimo.model.User;
import com.jimo.service.PowerService;
import com.jimo.service.UserService;
import com.jimo.util.Result;
import com.jimo.util.ResultCode;
import com.jimo.util.ResultHelper;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService us;
	@Autowired
	private PowerService ps;

	@PostMapping("/login")
	public Result login(@RequestParam("name") String name, @RequestParam("pass") String pass,
			@RequestParam("date") String date, HttpSession session) {
		Result re = us.checkLogin(name, pass, date);
		if (re.getCode() == ResultCode.OK.getErrcode()) {
			// 存入session
			session.setAttribute("user", re.getData());
		}
		return re;
	}

	@PostMapping("/getAllUser")
	public Result getAllUser() {
		return ResultHelper.success(ps.getAllUser());
	}

	@PostMapping("/deleteUser")
	public Result deleteUser(@RequestParam("jusername") String jusername) {
		return ps.deleteUser(jusername);
	}

	@PostMapping("/saveUser")
	public Result saveUser(@RequestParam("username") String name, @RequestParam("password") String pass) {
		User u = new User();
		u.setUsername(name);
		u.setPassword(pass);
		return ps.saveUser(u);
	}

	@PostMapping("/addPower")
	public Result addPower(@RequestParam("userId") String id, @RequestParam("pids[]") String[] pids) {
		return ps.addPower(id, pids);
	}

	@PostMapping("/removePower")
	public Result removePower(@RequestParam("userId") String id, @RequestParam("pids[]") String[] pids) {
		return ps.removePower(id, pids);
	}

	@PostMapping("/getUserPowers")
	public Result getUserPowers(@RequestParam("userId") String id) {
		PowerResult pr = new PowerResult();
		pr.setHavedPower(ps.getUserPowers(id, 1));
		pr.setNotHavedPower(ps.getUserPowers(id, 2));
		return ResultHelper.success(pr);
	}

	/**
	 * 取得用户的菜单，其实调用权限接口就行了
	 * 
	 * @param session
	 * @return
	 */
	@PostMapping("/getUserMenu")
	public Result getUserMenu(HttpSession session) {
		User user = (User) session.getAttribute("user");
		return ResultHelper.success(ps.getUserPowers(user.getId(), 1));
	}
}
