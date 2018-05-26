package com.smdev.smsj.security.database.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smdev.smsj.security.database.entities.User;

/**
 * @author sm, in 2018
 *
 * |> UserRepository ~~ [com.smdev.smsj.security.database.dao]
 * 
 */
@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, String>{
	@Query("SELECT u FROM USER u WHERE u.username=:username")
	Optional<User> findOneByUsername(@Param("username") String username);
	
	@Query("SELECT u FROM USER u WHERE u.username=:username OR u.email =:email")
	Optional<User> findOneByUsernameOrEmail(@Param("username") String username, @Param("email")String email);
	
	@Query("SELECT u FROM USER u")
	List<User> findAll();
}
