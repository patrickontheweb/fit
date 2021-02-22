package com.patrickontheweb.fit.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.patrickontheweb.fit.model.user.User;
import com.patrickontheweb.fit.repository.user.UserRepository;
import com.patrickontheweb.fit.security.AppUserDetails;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	 
    private UserRepository userRepository;
    
    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
    	this.userRepository = userRepository;
	}
     
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
         
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User not found: %s", username));
        }
         
        return new AppUserDetails(user);
    }
 
}
