package com.smdev.smsj.security.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smdev.smsj.security.database.entities.SecEntityTest;

/**
 * @author sm, in 2018
 *
 * |> SecEntityTestRepository ~~ [com.smdev.smsj.security.database.dao]
 * 
 */
public interface SecEntityTestRepository extends JpaRepository<SecEntityTest, String>{

}
