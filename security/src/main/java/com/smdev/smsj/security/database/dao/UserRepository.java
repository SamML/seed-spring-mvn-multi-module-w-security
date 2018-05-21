package com.smdev.smsj.security.database.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smdev.smsj.security.database.entities.User;

/**
 * @author sm, in 2018
 *
 * |> UserRepository ~~ [com.smdev.smsj.security.database.dao]
 * 
 */
public interface UserRepository extends JpaRepository<User, String>{
	Optional<User> findOneByUsername(String username);
}
