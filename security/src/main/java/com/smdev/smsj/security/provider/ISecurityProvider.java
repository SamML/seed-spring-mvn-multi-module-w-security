package com.smdev.smsj.security.provider;

import java.util.Set;

import com.smdev.smsj.security.dto.PermissionDto;
import com.smdev.smsj.security.dto.RoleDto;
import com.smdev.smsj.security.dto.UserDto;

/**
 * @author sm, in 2018
 *
 *         |> ISecurityProvider ~~ [com.smdev.smsj.security.provider]
 * 
 */
public interface ISecurityProvider {

	/**
	 * @param userdto
	 * @return
	 * @throws AlreadyExistsException
	 */
	UserDto createUserIfNotExists(UserDto userdto) throws AlreadyExistsException;

	/**
	 * @param userdto
	 * @param rolesdto
	 * @return
	 * @throws AlreadyExistsException
	 */
	UserDto createUserIfNotExists(UserDto userdto, Set<RoleDto> rolesdto) throws AlreadyExistsException;

	/**
	 * @param roledto
	 * @return
	 * @throws AlreadyExistsException
	 */
	RoleDto createRoleIfNotExists(RoleDto roledto) throws AlreadyExistsException;

	/**
	 * @param roledto
	 * @param permissionsdto
	 * @return
	 * @throws AlreadyExistsException
	 */
	RoleDto createRoleIfNotExists(RoleDto roledto, Set<PermissionDto> permissionsdto) throws AlreadyExistsException;

	/**
	 * @param permissiondto
	 * @return
	 * @throws AlreadyExistsException
	 */
	PermissionDto createPermissionIfNotExists(PermissionDto permissiondto) throws AlreadyExistsException;
}
