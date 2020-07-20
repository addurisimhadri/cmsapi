package com.sim.wicmsapi.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sim.wicmsapi.dao.UserRepository;
import com.sim.wicmsapi.entity.User;
import com.sim.wicmsapi.vo.LoginRequest;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;
  
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}	
	public User save(LoginRequest login) {
		User user = new User();
		user.setUsername(login.getUsername());
		user.setPassword(bcryptEncoder.encode(login.getPassword()));
		return userRepository.save(user);
	}
	
	public User findByUsername(String username) {		
		return userRepository.findByUsername(username);
	}
	
}