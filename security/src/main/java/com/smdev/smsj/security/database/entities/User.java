package com.smdev.smsj.security.database.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 *         |> User ~~ [com.smdev.smsj.security.database.entities]
 * 
 */
@Entity(name = "USER")
@Table(name = "users")
@NoArgsConstructor
public class User extends AbstractEntityClass {

	@Getter
	@Setter
	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Getter
	@Setter
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	@Getter
	@Setter
	@Column(name = "password", nullable = false)
	private String password;

	@Getter
	@Setter
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User(String username, String password, Set<Role> roles) {
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

}
