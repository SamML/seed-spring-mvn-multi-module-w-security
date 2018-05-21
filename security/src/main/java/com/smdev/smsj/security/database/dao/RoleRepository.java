package com.smdev.smsj.security.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smdev.smsj.security.database.entities.Role;

/**
 * @author sm, in 2018
 *
 * |> RoleRepository ~~ [com.smdev.smsj.security.database.dao]
 * 
 */
public interface RoleRepository extends JpaRepository<Role, String>{

}
