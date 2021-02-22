package com.patrickontheweb.fit.security;

import java.util.Objects;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.patrickontheweb.fit.model.user.User;

public class SecurityUtils {
	
	public static final String ROLE_ADMIN = "ADMIN";

	public static Optional<User> getCurrentUser() {
		User user = null;
		
		Authentication authentication = getAuthentication();
		
		if(authentication.isAuthenticated()) {
			user = AppUserDetails.class.cast(authentication.getPrincipal()).getUser();
		}
		
		return Optional.ofNullable(user);
	}
	
	public static boolean isAdmin() {
		return getAuthentication().getAuthorities().stream()
				.filter(a -> a instanceof GrantedAuthority)
				.map(a -> GrantedAuthority.class.cast(a))
				.anyMatch(ga -> ROLE_ADMIN.equals(ga.getAuthority()));
	}
	
	public static boolean isCurrentUser(User user) {
		return Objects.equals(getCurrentUser().get(), user);
	}
	
	private static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
