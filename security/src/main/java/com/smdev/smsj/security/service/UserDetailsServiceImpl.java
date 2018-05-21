package com.smdev.smsj.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smdev.smsj.security.database.dao.UserRepository;
import com.smdev.smsj.security.model.UserAdapter;

/**
 * @author sm, in 2018
 *
 *         |> UserDetailsServiceImpl ~~ [com.smdev.smsj.security.service]
 * 
 */
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (userRepository.findOneByUsername(username).isPresent()) {
			return new UserAdapter(userRepository.findOneByUsername(username).get());
		} else {
			throw new UsernameNotFoundException("Username not found");
		}
	}
}
