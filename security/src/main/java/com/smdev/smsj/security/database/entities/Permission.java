package com.smdev.smsj.security.database.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.smdev.common.database.AbstractEntityClass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author sm, in 2018
 *
 * |> Permission ~~ [com.smdev.smsj.security.database.entities]
 * 
 */
@Entity(name="PERMISSION")
@Table(name="permissions")
@NoArgsConstructor
public class Permission extends AbstractEntityClass{

	@Getter @Setter
	@Column(name="name")
	private String name;
	
	@ManyToMany (mappedBy="permissions")
	private Set<Role> roles;
	
	public Permission(String name) {
		this.name = name;
	}
	
}

