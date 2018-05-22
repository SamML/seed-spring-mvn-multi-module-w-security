package com.smdev.smsj.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author sm, in 2018
 *
 * |> PasswordEncoderConfig ~~ [com.smdev.smsj.security.config]
 * 
 */
@Configuration
public class PasswordEncoderConfig {

	@Bean
	public PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder(11);
	}
}

