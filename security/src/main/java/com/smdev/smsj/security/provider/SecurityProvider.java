package com.smdev.smsj.security.provider;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smdev.smsj.security.database.dao.PermissionRepository;
import com.smdev.smsj.security.database.dao.RoleRepository;
import com.smdev.smsj.security.database.dao.UserRepository;
import com.smdev.smsj.security.database.entities.Permission;
import com.smdev.smsj.security.database.entities.Role;
import com.smdev.smsj.security.database.entities.User;
import com.smdev.smsj.security.dto.PermissionDto;
import com.smdev.smsj.security.dto.RoleDto;
import com.smdev.smsj.security.dto.UserDto;

/**
 * @author sm, in 2018
 *
 *         |> SecurityProvider ~~ [com.smdev.smsj.security.provider]
 * 
 */
@Component
public class SecurityProvider implements ISecurityProvider {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PermissionRepository permissionRepository;
	private final ModelMapper modelMapper;

	private static final String ROLE_PREFIX = "ROLE_";
	private static final String DEFAULT_ROLE = "DEFAULT";

	@Autowired
	public SecurityProvider(UserRepository userRepository, RoleRepository roleRepository,
			PermissionRepository permissionRepository, ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.permissionRepository = permissionRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public UserDto createUserIfNotExists(UserDto userdto)
			throws AlreadyExistsException, RequiredFieldNotGivenException {

		// Check if DTO has required fields
		if (userdto.getUsername() == null || userdto.getUsername().isEmpty()) {
			throw new RequiredFieldNotGivenException("username");
		}
		if (userdto.getPassword() == null || userdto.getPassword().isEmpty()) {
			throw new RequiredFieldNotGivenException("password");
		}
		if (userdto.getEmail() == null || userdto.getEmail().isEmpty()) {
			throw new RequiredFieldNotGivenException("email");
		}

		// Check if already exists
		if (userRepository.findOneByUsernameOrEmail(userdto.getUsername(), userdto.getEmail()).isPresent()) {
			// Exists one
			throw new AlreadyExistsException(UserDto.class);

		}
		// Set default roles
		Set<Role> defaultroles = new HashSet<Role>(Arrays.asList(new Role(DEFAULT_ROLE,
				new HashSet<Permission>(Arrays.asList(new Permission(ROLE_PREFIX + DEFAULT_ROLE))))));

		User newuser = new User(userdto.getUsername(), userdto.getPassword(), defaultroles);
		userRepository.save(newuser);

		return userdto;
	}

	@Override
	public UserDto createUserIfNotExists(UserDto userdto, Set<RoleDto> rolesdto)
			throws AlreadyExistsException, RequiredFieldNotGivenException {

		// Check if DTO has required fields
		if (userdto.getUsername() == null || userdto.getUsername().isEmpty()) {
			throw new RequiredFieldNotGivenException("username");
		}
		if (userdto.getPassword() == null || userdto.getPassword().isEmpty()) {
			throw new RequiredFieldNotGivenException("password");
		}
		if (userdto.getEmail() == null || userdto.getEmail().isEmpty()) {
			throw new RequiredFieldNotGivenException("email");
		}

		// Check if already exists
		if (userRepository.findOneByUsernameOrEmail(userdto.getUsername(), userdto.getEmail()).isPresent()) {
			// Exists one
			throw new AlreadyExistsException(UserDto.class);

		}
		// Add passed roles
		Set<Role> roles = new HashSet<Role>();
		for (RoleDto rdto : rolesdto) {
			roles.add(new Role(rdto.getName()));
		}

		// Save user
		User newuser = new User(userdto.getUsername(), userdto.getPassword(), roles);

		userRepository.save(newuser);
		return userdto;
	}

	@Override
	public RoleDto createRoleIfNotExists(RoleDto roledto)
			throws AlreadyExistsException, RequiredFieldNotGivenException {

		// Check if DTO has required fields
		if (roledto.getName() == null || roledto.getName().isEmpty()) {
			throw new RequiredFieldNotGivenException("rolename");
		}

		// Check if already exists
		if (roleRepository.findOneByName(roledto.getName()).isPresent()) {
			throw new AlreadyExistsException(RoleDto.class);
		}

		// Add void wrapper of permissions
		Set<Permission> defaultpermissions = new HashSet<Permission>();
		Role newrole = modelMapper.map(roledto, Role.class);
		newrole.setPermissions(defaultpermissions);

		// Save role
		roleRepository.save(newrole);
		return roledto;
	}

	@Override
	public RoleDto createRoleIfNotExists(RoleDto roledto, Set<PermissionDto> permissionsdto)
			throws AlreadyExistsException, RequiredFieldNotGivenException {

		// Check if DTO has required fields
		if (roledto.getName() == null || roledto.getName().isEmpty()) {
			throw new RequiredFieldNotGivenException("rolename");
		}
		// Check if already exists
		if (roleRepository.findOneByName(roledto.getName()).isPresent()) {
			throw new AlreadyExistsException(RoleDto.class);
		}

		// Add passed permissions
		Role newrole = modelMapper.map(roledto, Role.class);
		Set<Permission> permissions = new HashSet<Permission>();
		for (PermissionDto pdto : permissionsdto) {

			permissions.add(modelMapper.map(pdto, Permission.class));
		}
		newrole.setPermissions(permissions);

		// Save role
		roleRepository.save(newrole);
		return roledto;
	}

	@Override
	public PermissionDto createPermissionIfNotExists(PermissionDto permissiondto) throws AlreadyExistsException {
		
		// Check if DTO has required fields
		if (permissionRepository.findOneByName(permissiondto.getName()).isPresent()) {
			throw new AlreadyExistsException(PermissionDto.class);
		}
		// Check if already exists
		if (roleRepository.findOneByName(permissiondto.getName()).isPresent()) {
			throw new AlreadyExistsException(PermissionDto.class);
			}
		
		//Save permission
		roleRepository.save(modelMapper.map(permissiondto, Role.class));
		return permissiondto;
	}

	@Override
	public List<UserDto> listAllUsers() {

		return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());
	}

}
