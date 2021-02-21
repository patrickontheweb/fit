package com.patrickontheweb.fit.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patrickontheweb.fit.model.User;
import com.patrickontheweb.fit.repository.user.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public List<User> fetchAll() {
		return userRepository.findAll();
	}
	
	public Optional<User> get(Long id) {
		return userRepository.findById(id);
	}
}
