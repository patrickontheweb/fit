package com.patrickontheweb.fit.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.patrickontheweb.fit.model.user.User;
import com.patrickontheweb.fit.repository.user.UserRepository;

@Service
@Transactional
public class UserService {

	private UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public List<User> fetchAll() {
		return userRepository.findAll();
	}
	
	public Optional<User> get(Long id) {
		return userRepository.findById(id);
	}
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	public User deleteUser(User user) {
		userRepository.delete(user);
		return user;
	}
}