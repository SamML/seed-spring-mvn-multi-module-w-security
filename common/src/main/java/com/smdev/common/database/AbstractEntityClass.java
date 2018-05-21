package com.smdev.common.database;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sm, in 2018
 *
 * |> AbstractEntityClass ~~ [com.smdev.common.database]
 * 
 */
@MappedSuperclass
public abstract class AbstractEntityClass implements IEntityObject{

	@Id
	@GeneratedValue(generator = "uuid2-gen")
	@GenericGenerator(name = "uuid2-gen", strategy = "uuid2")
	@Basic(optional = false)
	@Column(name = "id")
	private String id;

	@Version
	@Getter
	@Setter
	private Integer version;

	@Getter
	private Date dateCreated;
	@Getter
	private Date lastUpdated;

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@PreUpdate
	@PrePersist
	public void updateTimeStamps() {
		LocalDate ldate = LocalDate.now();

		if (dateCreated == null) {
			dateCreated = Date.from(ldate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		}
		ldate = LocalDate.now();
		lastUpdated = Date.from(ldate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	
}
