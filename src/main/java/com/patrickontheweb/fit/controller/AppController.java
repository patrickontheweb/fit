package com.patrickontheweb.fit.controller;

import java.util.List;

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
import com.patrickontheweb.fit.service.user.UserService;

@RestController
public class AppController {
	
	private UserService userService;
	
	@Autowired
	public AppController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/")
	public String index() {
		return "Fit is up";
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<User>> fetchUsers() {
		List<User> users = userService.fetchAll();
		return ResponseEntity.ok(users);
	}
	
	@PostMapping("/user")
	public ResponseEntity<User> createUser(@RequestBody User newUser) {
		User user = userService.saveUser(newUser);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/user/{id}") 
	public ResponseEntity<User> fetchUser(@PathVariable("id") Long id) {
		User user = userService.get(id).get();
		return ResponseEntity.ok(user);
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User incomingUser) {
		User user = userService.get(id).get();
		user.setUsername(incomingUser.getUsername());
		user.setEmail(incomingUser.getEmail());
		user.setFirstName(incomingUser.getFirstName());
		user.setLastName(incomingUser.getLastName());
		user = userService.saveUser(user);
		return ResponseEntity.ok(user);
	}
	
	@DeleteMapping("/user/{id}") 
	public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
		User user = userService.get(id).get();
		userService.deleteUser(user);
		return ResponseEntity.ok(user);
	}
}