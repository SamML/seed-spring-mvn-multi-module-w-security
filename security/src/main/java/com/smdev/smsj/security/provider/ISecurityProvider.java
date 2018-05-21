package com.smdev.smsj.security.provider;

import com.smdev.smsj.security.dto.PermissionDto;
import com.smdev.smsj.security.dto.RoleDto;
import com.smdev.smsj.security.dto.UserDto;

/**
 * @author sm, in 2018
 *
 * |> ISecurityProvider ~~ [com.smdev.smsj.security.provider]
 * 
 */
public interface ISecurityProvider {
	
	public UserDto createUserIfNotExists(UserDto userdto);
	
	public RoleDto createRoleIfNotExists(RoleDto roledto);
	
	public PermissionDto createPermissionIfNotExists(PermissionDto permissiondto);
}
