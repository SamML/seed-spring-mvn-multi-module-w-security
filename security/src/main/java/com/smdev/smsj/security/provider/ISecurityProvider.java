package com.smdev.smsj.security.provider;

import java.util.List;
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
	 * @throws RequiredFieldNotGivenException 
	 */
	UserDto createUserIfNotExists(UserDto userdto) throws AlreadyExistsException, RequiredFieldNotGivenException;

	/**
	 * @param userdto
	 * @param rolesdto
	 * @return
	 * @throws AlreadyExistsException
	 * @throws RequiredFieldNotGivenException 
	 */
	UserDto createUserIfNotExists(UserDto userdto, Set<RoleDto> rolesdto) throws AlreadyExistsException, RequiredFieldNotGivenException;

	/**
	 * @param roledto
	 * @return
	 * @throws AlreadyExistsException
	 * @throws RequiredFieldNotGivenException 
	 */
	RoleDto createRoleIfNotExists(RoleDto roledto) throws AlreadyExistsException, RequiredFieldNotGivenException;

	/**
	 * @param roledto
	 * @param permissionsdto
	 * @return
	 * @throws AlreadyExistsException
	 * @throws RequiredFieldNotGivenException 
	 */
	RoleDto createRoleIfNotExists(RoleDto roledto, Set<PermissionDto> permissionsdto) throws AlreadyExistsException, RequiredFieldNotGivenException;

	/**
	 * @param permissiondto
	 * @return
	 * @throws AlreadyExistsException
	 */
	PermissionDto createPermissionIfNotExists(PermissionDto permissiondto) throws AlreadyExistsException;

	/**
	 * @return
	 */
	List<UserDto> listAllUsers();
}
