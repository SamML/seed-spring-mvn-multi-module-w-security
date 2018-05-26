package com.smdev.smsj.security.endpoint;

import java.util.Set;

import com.smdev.smsj.security.dto.RoleDto;
import com.smdev.smsj.security.dto.UserDto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sm, in 2018
 *
 * |> EPUserData ~~ [com.smdev.smsj.security.endpoint]
 * 
 */
public class EPUserData {

	@Getter @Setter
	private UserDto userdto;
	@Getter @Setter
	private Set<RoleDto> rolesdto;
}
