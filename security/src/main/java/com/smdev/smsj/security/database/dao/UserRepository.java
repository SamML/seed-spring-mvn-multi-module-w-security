package com.smdev.smsj.security.database.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.smdev.smsj.security.database.entities.User;

/**
 * @author sm, in 2018
 *
 * |> UserRepository ~~ [com.smdev.smsj.security.database.dao]
 * 
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, String>{
	Optional<User> findOneByUsername(String username);
	
	@Query("SELECT u from USER u WHERE u.username=:username OR u.email =:email")
	Optional<User> findOneByUsernameOrEmail(String username, String email);
}
