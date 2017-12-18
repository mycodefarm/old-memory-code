package com.jimo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jimo.model.Contact;
import com.jimo.model.User;
import com.jimo.service.ContactService;
import com.jimo.util.Result;

@RestController
@RequestMapping("/contact")
public class ContactController {
	@Autowired
	private ContactService cs;

	@PostMapping("/getContacts")
	public Result getContacts(String search, Integer page, HttpSession session) {
		return cs.getPageContacts(search, page - 1, (User) session.getAttribute("user"));
	}

	@PostMapping("/addContact")
	public Result addContact(Contact c, HttpSession session) {
		c.setUser((User) session.getAttribute("user"));
		return cs.addContact(c, true);
	}

	@PostMapping("/modifyContact")
	public Result modifyContact(Contact c, HttpSession session) {
		c.setUser((User) session.getAttribute("user"));
		return cs.addContact(c, false);
	}

	@PostMapping("/delContact")
	public Result delContact(Long id, HttpSession session) {
		return cs.delContact(id, (User) session.getAttribute("user"));
	}
}
