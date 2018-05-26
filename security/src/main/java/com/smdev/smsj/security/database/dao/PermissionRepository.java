package com.smdev.smsj.security.database.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smdev.smsj.security.database.entities.Permission;

/**
 * @author sm, in 2018
 *
 * |> PermissionRepository ~~ [com.smdev.smsj.security.database.dao]
 * 
 */
@Repository("PermissionRepository")
public interface PermissionRepository extends JpaRepository<Permission, String> {
	Optional<Permission> findOneByName(String name);
}
