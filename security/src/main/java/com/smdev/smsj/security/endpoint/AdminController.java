package com.smdev.smsj.security.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smdev.smsj.security.dto.UserDto;
import com.smdev.smsj.security.provider.AlreadyExistsException;
import com.smdev.smsj.security.provider.RequiredFieldNotGivenException;
import com.smdev.smsj.security.provider.SecurityProvider;

/**
 * @author sm, in 2018
 *
 *         |> AdminController ~~ [com.smdev.smsj.security.endpoint]
 * 
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

	private final SecurityProvider securityProvider;

	@Autowired
	public AdminController(SecurityProvider securityProvider) {
		this.securityProvider = securityProvider;
	}

	@GetMapping(path = "/users")
	public ResponseEntity<List<UserDto>> listUsers() {
		return new ResponseEntity<List<UserDto>>(securityProvider.listAllUsers(), HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<?> addUser(@RequestBody EPUserData userdata) {
		try {
			return new ResponseEntity<UserDto> (securityProvider.createUserIfNotExists(userdata.getUserdto(), userdata.getRolesdto()), HttpStatus.OK);
		} catch (AlreadyExistsException e) {
			return new ResponseEntity<EPUserData> (userdata, HttpStatus.IM_USED);
		} catch (RequiredFieldNotGivenException e) {
			return new ResponseEntity<EPUserData> (userdata, HttpStatus.PRECONDITION_REQUIRED);
		}
	}

}
