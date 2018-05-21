package com.smdev.smsj.security.database.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.smdev.common.database.AbstractEntityClass;

import lombok.NoArgsConstructor;

/**
 * @author sm, in 2018
 *
 * |> SecEntityTest ~~ [com.smdev.smsj.security.database.entities]
 * 
 */
@Entity(name = "SECENTITYTEST")
@Table(name = "secentitytests")
@NoArgsConstructor
public class SecEntityTest extends AbstractEntityClass{

}
