package com.patrickontheweb.fit.service.day;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.patrickontheweb.fit.model.day.Day;
import com.patrickontheweb.fit.repository.day.DayRepository;

@Service
@Transactional
public class DayService {

	private DayRepository dayRepository;
	
	@Autowired
	public DayService(DayRepository dayRepository) {
		this.dayRepository = dayRepository;
	}
	
	public List<Day> fetchAll() {
		return dayRepository.findAll();
	}
	
	public Optional<Day> get(Long id) {
		return dayRepository.findById(id);
	}
	
	public Day saveDay(Day day) {
		return dayRepository.save(day);
	}
	
	public Day deleteDay(Day day) {
		dayRepository.delete(day);
		return day;
	}
}