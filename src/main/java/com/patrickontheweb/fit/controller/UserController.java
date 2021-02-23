package com.patrickontheweb.fit.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.patrickontheweb.fit.model.user.User;
import com.patrickontheweb.fit.security.SecurityUtils;
import com.patrickontheweb.fit.service.user.UserService;

@RestController
public class UserController {
	
	public static final String PATH = "/user";
	
	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping(PATH)
	public ResponseEntity<List<User>> fetchUsers() {
		List<User> users = userService.fetchAll();
		return ResponseEntity.ok(users);
	}
	
	@PostMapping(PATH)
	public ResponseEntity<User> createUser(@RequestBody User newUser) {
		User user = userService.saveUser(newUser);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping(PATH + "/{id}") 
	public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
		User user = userService.get(id).get();
		checkAccessToUser(user);
		return ResponseEntity.ok(user);
	}
	
	@PutMapping(PATH + "/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User incomingUser) {
		User user = userService.get(id).get();
		checkAccessToUser(user);
		user.setUsername(incomingUser.getUsername());
		user.setEmail(incomingUser.getEmail());
		user.setFirstName(incomingUser.getFirstName());
		user.setLastName(incomingUser.getLastName());
		user = userService.saveUser(user);
		return ResponseEntity.ok(user);
	}
	
	@DeleteMapping(PATH + "/{id}") 
	public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
		User user = userService.get(id).get();
		checkAccessToUser(user);
		userService.deleteUser(user);
		return ResponseEntity.ok(user);
	}
	
	private User checkAccessToUser(User user) {
		if(!SecurityUtils.isAdmin() && !SecurityUtils.isCurrentUser(user)) {
			throw new NoSuchElementException("Either that user doesn't exist or you don't have access to it");
		}
		return user;
	}
}