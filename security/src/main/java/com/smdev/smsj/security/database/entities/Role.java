package com.smdev.smsj.security.database.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.smdev.common.database.AbstractEntityClass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author sm, in 2018
 *
 * |> Role ~~ [com.smdev.smsj.security.database.entities]
 * 
 */
@Entity(name = "ROLE")
@Table(name = "roles")
@NoArgsConstructor
public class Role extends AbstractEntityClass {

	@Getter
	@Setter
	private String rolename;

	@ManyToMany(mappedBy = "roles")
	private Set<User> users;
	@ManyToMany
	@JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
	@Getter
	private Set<Permission> permissions;
	
	public Role(String rolename) {
		this.rolename = rolename;
	}
}
