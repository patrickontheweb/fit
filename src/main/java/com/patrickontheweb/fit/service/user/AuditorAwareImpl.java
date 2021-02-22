package com.patrickontheweb.fit.service.user;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.patrickontheweb.fit.model.user.User;
import com.patrickontheweb.fit.security.AppUserDetails;

public class AuditorAwareImpl implements AuditorAware<User> {
	 
    @Override
    public Optional<User> getCurrentAuditor() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
    	User user = AppUserDetails.class.cast(authentication.getPrincipal()).getUser();
    	return Optional.ofNullable(user);
    }
}