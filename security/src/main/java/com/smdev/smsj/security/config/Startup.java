package com.smdev.smsj.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.smdev.smsj.security.dto.RoleDto;
import com.smdev.smsj.security.dto.UserDto;
import com.smdev.smsj.security.provider.AlreadyExistsException;
import com.smdev.smsj.security.provider.SecurityProvider;

/**
 * @author sm, in 2018
 *
 * |> Startup ~~ [com.smdev.smsj.security.config]
 * 
 */
@Component
public class Startup implements ApplicationRunner {

    private final SecurityProvider securityProvider;

    @Autowired
    public Startup(SecurityProvider securityProvider) {
        this.securityProvider = securityProvider;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
    	
    	UserDto oneuser = new UserDto();
    	oneuser.setUsername("Samuel");
    	oneuser.setPassword("Password");
    	oneuser.setEmail("samuel.maicas@gmail.com");
    	try {
    		securityProvider.createUserIfNotExists(oneuser);
    	}
    	catch (AlreadyExistsException e) 
    	{
    		System.err.println("No se ha inicializado el usuario porque ya existe");
    	}
    	
        
    }

}
