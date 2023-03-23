package it.epicode.capstone.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.epicode.capstone.models.LoggedUserDetails;
import it.epicode.capstone.models.User;
import it.epicode.capstone.repositories.UserRepository;

@Service
public class LoggedUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository uR;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String x) throws UsernameNotFoundException {
		User u = uR.findUserByUsername(x)
				.orElseThrow(() -> new UsernameNotFoundException("No User with Username '" + x + "' was Found."));
		return LoggedUserDetails.build(u);
	}
}