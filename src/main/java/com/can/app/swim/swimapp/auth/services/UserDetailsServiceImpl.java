package com.can.app.swim.swimapp.auth.services;

import com.can.app.swim.swimapp.entity.User;
import com.can.app.swim.swimapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user$ = userRepository.findByUsername(username);
		if(user$.isPresent())
		{
			return UserDetailsImpl.build(user$.get());
		}
		else
		{
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		}
	}
}
