package com.smdev.smsj.security.provider;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
	public UserDto createUserIfNotExists(UserDto userdto) throws AlreadyExistsException {

		if (userRepository.findOneByUsernameOrEmail(userdto.getUsername(), userdto.getEmail()).isPresent()) {
			// Exists one
			throw new AlreadyExistsException(UserDto.class);

		}
		Set<Role> defaultroles = new HashSet<Role>(Arrays
				.asList(new Role(DEFAULT_ROLE, new HashSet<Permission>(Arrays.asList(new Permission(ROLE_PREFIX+DEFAULT_ROLE))))));
		User newuser = modelMapper.map(userdto, User.class);
		newuser.setRoles(defaultroles);
		userRepository.save(newuser);
		return userdto;
	}

	@Override
	public UserDto createUserIfNotExists(UserDto userdto, Set<RoleDto> rolesdto) throws AlreadyExistsException {

		if (userRepository.findOneByUsernameOrEmail(userdto.getUsername(), userdto.getEmail()).isPresent()) {
			// Exists one
			throw new AlreadyExistsException(UserDto.class);

		}
		User newuser = modelMapper.map(userdto, User.class);
		Set<Role> roles = new HashSet<Role>();
		for (RoleDto rdto : rolesdto) {
			roles.add(modelMapper.map(rdto, Role.class));
		}
		newuser.setRoles(roles);

		userRepository.save(newuser);
		return userdto;
	}

	@Override
	public RoleDto createRoleIfNotExists(RoleDto roledto) throws AlreadyExistsException {
		if (roleRepository.findOneByName(roledto.getName()).isPresent()) {
			throw new AlreadyExistsException(RoleDto.class);
		}

		Set<Permission> defaultpermissions = new HashSet<Permission>();
		Role newrole = modelMapper.map(roledto, Role.class);
		newrole.setPermissions(defaultpermissions);
		roleRepository.save(newrole);
		return roledto;
	}

	@Override
	public RoleDto createRoleIfNotExists(RoleDto roledto, Set<PermissionDto> permissionsdto)
			throws AlreadyExistsException {
		if (roleRepository.findOneByName(roledto.getName()).isPresent()) {
			throw new AlreadyExistsException(RoleDto.class);
		}
		Role newrole = modelMapper.map(roledto, Role.class);
		Set<Permission> permissions = new HashSet<Permission>();
		for (PermissionDto pdto : permissionsdto) {
			// createPermissionIfNotExists(pdto);
			permissions.add(modelMapper.map(pdto, Permission.class));
		}
		newrole.setPermissions(permissions);
		roleRepository.save(newrole);
		return roledto;
	}

	@Override
	public PermissionDto createPermissionIfNotExists(PermissionDto permissiondto) throws AlreadyExistsException {
		if (permissionRepository.findOneByName(permissiondto.getName()).isPresent()) {
			throw new AlreadyExistsException(PermissionDto.class);
		}
		roleRepository.save(modelMapper.map(permissiondto, Role.class));
		return permissiondto;
	}

}
