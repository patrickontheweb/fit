package com.patrickontheweb.fit.configuration;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.patrickontheweb.fit.model.user.User;
import com.patrickontheweb.fit.security.AppUserDetails;

@Component
public class AuditorAwareImpl implements AuditorAware<User> {
	 
    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserDetails userDetails = AppUserDetails.class.cast(authentication.getPrincipal());
        return Optional.ofNullable(userDetails.getUser());
    }
}