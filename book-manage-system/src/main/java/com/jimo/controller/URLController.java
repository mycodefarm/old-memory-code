package com.jimo.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jimo.model.User;
import com.jimo.service.UserService;
import com.jimo.util.MyUtils;

@Controller
public class URLController {
	@Autowired
	private UserService us;
	// @Autowired
	// private PowerService ps;

	@RequestMapping("/")
	public String login(Model model) {
		model.addAttribute("dates", us.getLoginDate());
		return "login";
	}

	@RequestMapping("/index")
	public String index(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		model.addAttribute("username", user.getUsername());
		model.addAttribute("loginTime", new Date());
		model.addAttribute("serverTime", MyUtils.getDateTime());
		return "user/index";
	}

	@RequestMapping("/manage_power")
	public String managePower(Model model) {
		// model.addAttribute("users", ps.getAllUser());
		return "user/manage_power";
	}

	@RequestMapping("/bianwu")
	public String bianwu() {
		return "user/bianwu";
	}

	@RequestMapping("/cuyun")
	public String cuyun() {
		return "user/cuyun";
	}

	@RequestMapping("/chuban")
	public String chuban() {
		return "user/chuban";
	}

	@RequestMapping("/book1")
	public String book1() {
		return "book/book1";
	}

	@RequestMapping("/book2")
	public String book2() {
		return "book/book2";
	}

	@RequestMapping("/book3")
	public String book3() {
		return "book/book3";
	}

	@RequestMapping("/bjs1")
	public String bjs1() {
		return "book/bjs1";
	}

	@RequestMapping("/bjs2")
	public String bjs2() {
		return "book/bjs2";
	}

	@RequestMapping("/bjs3")
	public String bjs3() {
		return "book/bjs3";
	}

	@RequestMapping("/bookType")
	public String bookType() {
		return "book/bookType";
	}

	@RequestMapping("/bookManage")
	public String bookManage() {
		return "book/bookManage";
	}

	@RequestMapping("/bookSearch")
	public String bookSearch() {
		return "book/bookSearch";
	}

	///// doc
	@RequestMapping("/bookStorage")
	public String bookStorage() {
		return "doc/bookStorage";
	}

	@RequestMapping("/bookEnter")
	public String bookEnter() {
		return "doc/bookEnter";
	}

	@RequestMapping("/bookChart")
	public String bookChart() {
		return "doc/bookChart";
	}
}
