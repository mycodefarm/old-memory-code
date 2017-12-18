package com.jimo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jimo.model.User;

@Controller
public class URLController {
	@RequestMapping(value = { "/", "/login" })
	public String login(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user != null) {
			return "index";
		}
		return "login";
	}

	@RequestMapping("/index")
	public String index() {
		return "index";
	}
}
