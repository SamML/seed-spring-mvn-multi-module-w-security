package com.smdev.smsj.security.dto;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author sm, in 2018
 *
 *         |> UserDto ~~ [com.smdev.smsj.security.dto]
 * 
 */
@NoArgsConstructor
public class UserDto {
	@Getter
	@Setter
	private String username;

	@Getter
	@Setter
	private String email;
	
	@Getter
	@Setter
	private String password;
}
