package com.patrickontheweb.fit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patrickontheweb.fit.model.User;
import com.patrickontheweb.fit.service.user.UserService;

@RestController
public class AppController {
	
	@Autowired
	private UserService userService;

	@RequestMapping("/")
	public String index() {
		return "Fit is up";
	}
	
	@RequestMapping("/user")
	public String fetchUsers() {
		List<User> users = userService.fetchAll();
		return users.toString();
	}
	
	@RequestMapping("/user/{id}") 
	public String fetchUser(@PathVariable("id") Long id) {
		User user = userService.get(id).get();
		return user.getUsername();
	}

}