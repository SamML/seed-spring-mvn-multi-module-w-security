package com.smdev.smsj.security.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.smdev.smsj.security.database.dao.PermissionRepository;
import com.smdev.smsj.security.database.dao.RoleRepository;
import com.smdev.smsj.security.database.dao.UserRepository;
import com.smdev.smsj.security.database.entities.Permission;
import com.smdev.smsj.security.database.entities.Role;
import com.smdev.smsj.security.database.entities.User;
import com.smdev.smsj.security.dto.UserDto;

/**
 * @author sm, in 2018
 *
 *         |> TestSecurityProvider ~~ [com.smdev.smsj.security.provider]
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class TestSecurityProvider {

	private SecurityProvider securityProvider;

	@Mock
	private UserRepository userRepository;
	@Mock
	private RoleRepository roleRepository;
	@Mock
	private PermissionRepository permissionRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.securityProvider = new SecurityProvider(userRepository, roleRepository, permissionRepository, modelMapper);
		this.modelMapper = new ModelMapper();
	}

	/**
	 * Test method for
	 * {@link com.smdev.smsj.security.provider.SecurityProvider#createUserIfNotExists(com.smdev.smsj.security.dto.UserDto)}.
	 */
	@Test
	public void method_create_user_if_not_exists() {
		// set up data - mock
		Set<Role> roles = new HashSet<Role>(
				Arrays.asList(new Role("ADMIN", new HashSet<Permission>(Arrays.asList(new Permission("READ_FILES")))),
						new Role("USER")));
		Optional<User> oneuser = Optional.of(new User("User1", "password", roles, "user1@email.com"));

		// not fully filled UserDto will return RequiredFieldNotGivenException
		// ("username")
		when(userRepository.findOneByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.empty());
		UserDto userdto = new UserDto();
		Throwable throwable = catchThrowable(() -> securityProvider.createUserIfNotExists(userdto));
		assertThat(throwable).isNotNull().isInstanceOf(RequiredFieldNotGivenException.class);

		userdto.setUsername("User2");
		userdto.setPassword("pass");
		userdto.setEmail("user2@email.com");
		// Try to create when exists will return AlreadyExistsException
		when(userRepository.findOneByUsernameOrEmail(anyString(), anyString())).thenReturn(oneuser);
		UserDto oneuserdto = modelMapper.map(oneuser, UserDto.class);
		throwable = catchThrowable(
				() -> securityProvider.createUserIfNotExists(oneuserdto));
		assertThat(throwable).isNotNull().isInstanceOf(AlreadyExistsException.class);

		// Create user
		when(userRepository.findOneByUsernameOrEmail(anyString(), anyString())).thenReturn(oneuser);
		Assertions.assertThatCode(() -> securityProvider.createUserIfNotExists(userdto)).doesNotThrowAnyException();

	}

	
	
	
}
