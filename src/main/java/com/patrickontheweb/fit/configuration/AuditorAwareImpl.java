package com.patrickontheweb.fit.configuration;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.patrickontheweb.fit.model.user.User;
import com.patrickontheweb.fit.security.SecurityUtils;

@Component
public class AuditorAwareImpl implements AuditorAware<User> {
	 
    @Override
    public Optional<User> getCurrentAuditor() {
        return SecurityUtils.getCurrentUser();
    }
}