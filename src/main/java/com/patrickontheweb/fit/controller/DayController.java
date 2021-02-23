package com.patrickontheweb.fit.controller;

import java.util.Date;
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

@RestController
public class DayController {
	
	public static final String PATH = "/day";
	
	private DayService dayService;
	
	@Autowired
	public DayController(DayService dayService) {
		this.dayService = dayService;
	}
	
	@PostMapping(PATH)
	public ResponseEntity<Day> createDay(@RequestBody Day newDay) {
		User user = SecurityUtils.getCurrentUser().get();
		newDay.setUser(user);
		if(newDay.getDate() == null) {
			newDay.setDate(new Date());
		}
		Day day = dayService.saveDay(newDay);
		return ResponseEntity.ok(day);
	}
	
	@GetMapping(PATH + "/{id}") 
	public ResponseEntity<Day> getDay(@PathVariable("id") Long id) {
		Day day = dayService.get(id).get();
		checkAccessToDay(day);
		return ResponseEntity.ok(day);
	}
	
	@PutMapping(PATH + "/{id}")
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
	
	@DeleteMapping(PATH + "/{id}") 
	public ResponseEntity<Day> deleteDay(@PathVariable("id") Long id) {
		Day day = dayService.get(id).get();
		checkAccessToDay(day);
		dayService.deleteDay(day);
		return ResponseEntity.ok(day);
	}
	
	private Day checkAccessToDay(Day day) {
		if(!SecurityUtils.isAdmin() && !SecurityUtils.isCurrentUser(day.getUser())) {
			throw new NoSuchElementException("Either that day doesn't exist or you don't have access to it");
		}
		return day;
	}
}