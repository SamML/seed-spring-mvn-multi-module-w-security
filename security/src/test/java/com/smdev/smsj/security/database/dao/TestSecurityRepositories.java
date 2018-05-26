package com.smdev.smsj.security.database.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.h2.message.DbException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.smdev.smsj.security.config.SecurityDatasourceConfig;
import com.smdev.smsj.security.database.entities.Permission;
import com.smdev.smsj.security.database.entities.Role;
import com.smdev.smsj.security.database.entities.User;

/**
 * @author sm, in 2018
 *
 *         |> TestUserRepository ~~ [com.smdev.smsj.security.database.dao]
 * 
 */

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { SecurityDatasourceConfig.class,
		UserRepository.class, RoleRepository.class, PermissionRepository.class, User.class, Role.class,
		Permission.class })
// @ActiveProfiles("test")
@SpringBootTest
@Transactional
@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSecurityRepositories {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	// @PersistenceContext private EntityManager em;

	@Before
	@Autowired
	public void setUp() {

	}

	@Test
	public void it_can_find_after_save_user_with_role_and_permissions() {
		User newuser = new User("ThisWillBeUsername", "password",
				new HashSet<Role>(Arrays.asList(
						new Role("ADMIN", new HashSet<Permission>(Arrays.asList(new Permission("WRITE_FILES")))))),
				"asd@asd.com");
		userRepository.save(newuser);
		List<User> users = userRepository.findAll();
		assertEquals(users.size(), 1);
		assertEquals(users.get(0).getUsername(), "ThisWillBeUsername");
		assertEquals(users.get(0).getPassword(), "password");

		assertEquals(users.get(0).getEmail(), "asd@asd.com");

		assertEquals(users.get(0).getRoles().size(), 1);
		List<Role> roles = roleRepository.findAll();
		assertEquals(roles.get(0).getName(), "ADMIN");
		assertEquals(roles.get(0).getPermissions().size(), 1);
		List<Permission> permissions = permissionRepository.findAll();
		assertEquals(permissions.get(0).getName(), "WRITE_FILES");

	}

	@Test
	public void it_can_delete_user_after_save_it() {
		User newuser = new User("ThisWillBeUsername", "password",
				new HashSet<Role>(Arrays.asList(
						new Role("ADMIN", new HashSet<Permission>(Arrays.asList(new Permission("WRITE_FILES")))))),
				"asd@asd.com");
		userRepository.save(newuser);
		List<User> users = userRepository.findAll();
		assertEquals(users.size(), 1);
		userRepository.delete(users.get(0));
		users = userRepository.findAll();
		assertEquals(users.size(), 0);

	}

	@Test
	public void it_can_update_after_save_user() {
		User newuser = new User("ThisWillBeUsername", "password",
				new HashSet<Role>(Arrays.asList(
						new Role("ADMIN", new HashSet<Permission>(Arrays.asList(new Permission("WRITE_FILES")))))),
				"asd@asd.com");
		userRepository.save(newuser);
		newuser.setEmail("nuevoemail@email.com");
		userRepository.save(newuser);
		assertEquals(userRepository.findAll().size(), 1);
		assertEquals(userRepository.findAll().get(0).getEmail(), "nuevoemail@email.com");
	}

	@Test(expected=NoSuchElementException.class)
	public void it_return_exception_when_not_found_and_get() {

		Optional<User> found = userRepository.findOneByUsername("Doesntexists");

		assertEquals(userRepository.findOneByUsername("Doesntexists").isPresent(), false);
		
		//Get of not present -> will throw NoSuchElementException
		found.get();

	}

}
