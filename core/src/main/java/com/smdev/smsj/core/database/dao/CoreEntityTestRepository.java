package com.smdev.smsj.core.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smdev.smsj.core.database.entities.CoreEntityTest;

/**
 * @author sm, in 2018
 *
 * |> CoreEntityTestRepository ~~ [com.smdev.smsj.core.database.dao]
 * 
 */
public interface CoreEntityTestRepository extends JpaRepository<CoreEntityTest, String>{

}
