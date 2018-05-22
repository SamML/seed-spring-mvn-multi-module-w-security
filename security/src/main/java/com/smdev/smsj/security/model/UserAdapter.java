package com.smdev.smsj.security.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smdev.smsj.security.database.entities.Permission;
import com.smdev.smsj.security.database.entities.Role;
import com.smdev.smsj.security.database.entities.User;

/**
 * @author sm, in 2018
 *
 *         |> UserAdapter ~~ [com.smdev.smsj.security.model]
 * 
 */
public class UserAdapter implements UserDetails, Serializable {

	private static final long serialVersionUID = -6395683343329167936L;
	private static final String ROLE_PREFIX = "ROLE_";
	private User user;

	public UserAdapter (User user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (Role r : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + r.getName()));
			if (r.getPermissions().size() > 0) {
				for (Permission p : r.getPermissions()) {
					authorities.add(new SimpleGrantedAuthority(p.getName()));
				}
			}
			

		}

		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
