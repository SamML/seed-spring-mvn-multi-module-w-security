package com.smdev.smsj.security.database.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smdev.smsj.security.database.entities.Role;

/**
 * @author sm, in 2018
 *
 * |> RoleRepository ~~ [com.smdev.smsj.security.database.dao]
 * 
 */
@Repository("RoleRepository")
public interface RoleRepository extends JpaRepository<Role, String>{
	Optional<Role> findOneByName(String name);
}
