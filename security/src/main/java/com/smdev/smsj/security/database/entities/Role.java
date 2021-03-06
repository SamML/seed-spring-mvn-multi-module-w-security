package com.smdev.smsj.security.database.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.smdev.common.database.AbstractEntityClass;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sm, in 2018
 *
 *         |> Role ~~ [com.smdev.smsj.security.database.entities]
 * 
 */
@Entity(name = "ROLE")
@Table(name = "roles")
public class Role extends AbstractEntityClass {

	@Getter
	@Setter
	private String name;

	@ManyToMany(mappedBy = "roles")
	private Set<User> users;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
	@Getter
	@Setter
	private Set<Permission> permissions;

	public Role() {
		this("");
	}
	
	public Role(String rolename) {
		this.name = rolename;
		this.permissions = new HashSet<Permission>();
	}

	/**
	 * @param string
	 * @param onepermissions
	 */
	public Role(String rolename, Set<Permission> permissions) {
		this.name = rolename;
		this.permissions = permissions;
	}
}
