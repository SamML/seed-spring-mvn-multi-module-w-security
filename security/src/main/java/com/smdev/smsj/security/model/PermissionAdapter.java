package com.smdev.smsj.security.model;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;

import com.smdev.smsj.security.database.entities.Permission;

/**
 * @author sm, in 2018
 *
 * |> PermissionAdapter ~~ [com.smdev.smsj.security.model]
 * 
 */
public class PermissionAdapter implements GrantedAuthority, Serializable {

	
	private static final long serialVersionUID = 5958816813450268019L;
	private Permission permission;
	
	public PermissionAdapter(Permission permission) {
		this.permission = permission;
	}
	@Override
	public String getAuthority() {
		return permission.getName();
	}
	
	

}
