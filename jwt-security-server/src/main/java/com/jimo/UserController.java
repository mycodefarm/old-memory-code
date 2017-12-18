package com.jimo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	@RequestMapping(value = { "/"})
	public String login() {
		return "hello login to my web";
	}

	@GetMapping("/users")
	public String getUsers() {
		return "{\"users\":[{\"firstname\":\"Richard\", \"lastname\":\"Feynman\"},"
				+ "{\"firstname\":\"Marie\",\"lastname\":\"Curie\"}]}";
	}
}
