package com.smdev.smsj.security.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smdev.smsj.security.database.entities.Permission;

/**
 * @author sm, in 2018
 *
 * |> PermissionRepository ~~ [com.smdev.smsj.security.database.dao]
 * 
 */
public interface PermissionRepository extends JpaRepository<Permission, String> {

}
