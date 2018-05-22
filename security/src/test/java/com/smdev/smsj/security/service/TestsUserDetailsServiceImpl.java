package com.smdev.smsj.security.service;

import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;
import com.smdev.smsj.security.database.dao.UserRepository;
import com.smdev.smsj.security.database.entities.Permission;
import com.smdev.smsj.security.database.entities.Role;
import com.smdev.smsj.security.database.entities.User;

/**
 * @author sm, in 2018
 *
 *         |> UserDetailsServiceImplTests ~~ [security]
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class TestsUserDetailsServiceImpl {

	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Mock
	private UserRepository userRepository;

	@Before
	public void setUp() {
		this.userDetailsServiceImpl = new UserDetailsServiceImpl(userRepository);
	}

	@Test
	public void loadUserByUsernameTest() {
		// set up data
		Set<Permission> onepermissions = new HashSet<Permission>();
		Permission onepermission = new Permission("WRITE_FILES");
		onepermissions.add(onepermission);
		
		Set<Role> oneroles = new HashSet<Role>();
		Role roleone = new Role("ADMIN", onepermissions);
		Role roletwo = new Role("USER");
		oneroles.add(roleone);
		oneroles.add(roletwo);

		
		
		
		Optional<User> oneuser = Optional.of(new User("Juan", "pass", oneroles));

		when(userRepository.findOneByUsername(anyString())).thenReturn(oneuser);
		// call
		UserDetails result = userDetailsServiceImpl.loadUserByUsername(anyString());
		UserDetails compareto = org.springframework.security.core.userdetails.User.withUsername("Juan").password("pass")
				.roles("ADMIN","USER").authorities("WRITE_FILES").build();

		// check
		assertEquals(result.getUsername(), compareto.getUsername());
//		assertEquals(result.getAuthorities(), compareto.getAuthorities());
	}
}
