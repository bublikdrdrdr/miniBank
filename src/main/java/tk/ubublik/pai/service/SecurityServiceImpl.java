package tk.ubublik.pai.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tk.ubublik.pai.entity.Role;
import tk.ubublik.pai.entity.User;

@Service
public class SecurityServiceImpl implements SecurityService {

	@Override
	public boolean hasRole(Role role) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return  (authentication!=null && authentication.getAuthorities().contains(role.getGrantedAuthority()));
	}

	@Override
	public User getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() instanceof User) return (User) authentication.getPrincipal();
		return null;
	}
}
