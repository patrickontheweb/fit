package com.patrickontheweb.fit.controller;

import java.util.Date;
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

import com.patrickontheweb.fit.model.day.Day;
import com.patrickontheweb.fit.model.user.User;
import com.patrickontheweb.fit.security.SecurityUtils;
import com.patrickontheweb.fit.service.day.DayService;
import com.patrickontheweb.fit.service.user.UserService;

@RestController
public class AppController {
	
	private UserService userService;
	private DayService dayService;
	
	@Autowired
	public AppController(UserService userService, DayService dayService) {
		this.userService = userService;
		this.dayService = dayService;
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
	public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
		User user = userService.get(id).get();
		checkAccessToUser(user);
		return ResponseEntity.ok(user);
	}
	
	@PutMapping("/user/{id}")
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
	
	@DeleteMapping("/user/{id}") 
	public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
		User user = userService.get(id).get();
		checkAccessToUser(user);
		userService.deleteUser(user);
		return ResponseEntity.ok(user);
	}
	
	// Day endpoints -- maybe split these out into their own controller
	
	@PostMapping("/day")
	public ResponseEntity<Day> createDay(@RequestBody Day newDay) {
		User user = SecurityUtils.getCurrentUser().get();
		newDay.setUser(user);
		if(newDay.getDate() == null) {
			newDay.setDate(new Date());
		}
		Day day = dayService.saveDay(newDay);
		return ResponseEntity.ok(day);
	}
	
	@GetMapping("/day/{id}") 
	public ResponseEntity<Day> getDay(@PathVariable("id") Long id) {
		Day day = dayService.get(id).get();
		checkAccessToDay(day);
		return ResponseEntity.ok(day);
	}
	
	@PutMapping("/day/{id}")
	public ResponseEntity<Day> updateDay(@PathVariable("id") Long id, @RequestBody Day incomingDay) {
		Day day = dayService.get(id).get();
		checkAccessToDay(day);
		day.setDate(incomingDay.getDate());
		day.setWeight(incomingDay.getWeight());
		day.setSleepHours(incomingDay.getSleepHours());
		day.setComment(incomingDay.getComment());
		day = dayService.saveDay(day);
		return ResponseEntity.ok(day);
	}
	
	@DeleteMapping("/day/{id}") 
	public ResponseEntity<Day> deleteDay(@PathVariable("id") Long id) {
		Day day = dayService.get(id).get();
		checkAccessToDay(day);
		dayService.deleteDay(day);
		return ResponseEntity.ok(day);
	}
	
	private User checkAccessToUser(User user) {
		if(!SecurityUtils.isAdmin() && !SecurityUtils.isCurrentUser(user)) {
			throw new NoSuchElementException("Either that user doesn't exist or you don't have access to it");
		}
		return user;
	}
	
	private Day checkAccessToDay(Day day) {
		if(!SecurityUtils.isAdmin() && !SecurityUtils.isCurrentUser(day.getUser())) {
			throw new NoSuchElementException("Either that day doesn't exist or you don't have access to it");
		}
		return day;
	}
}