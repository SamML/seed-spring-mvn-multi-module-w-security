package com.smdev.smsj.security.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author sm, in 2018
 *
 *         |> SecurityDatasourceConfig ~~ [com.smdev.smsj.security.config]
 * 
 */
/* 
 * List of properties to add: http://docs.jboss.org/hibernate/orm/4.2/manual/en-US/html/ch03.html
 * */
@Configuration
@PropertySource({ "classpath:secdb.properties" })
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.smdev.smsj.security.database.dao", entityManagerFactoryRef = "securityEntityManager", transactionManagerRef = "securityTransactionManager")
public class SecurityDatasourceConfig {

	@Autowired
	private Environment env;

	@Bean(name = "securityEntityManager")
	public LocalContainerEntityManagerFactoryBean securityEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(securityDataSource());
		String[] scanpack = { "com.smdev.smsj.security.database.entities" };
		em.setPackagesToScan(scanpack);
		em.setPersistenceUnitName("securityDbPersistanceUnit");

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("secdb.jpa.properties.hibernate.hbm2ddl-auto"));
		properties.put("hibernate.dialect", env.getProperty("secdb.jpa.properties.hibernate.dialect"));
		properties.put("hibernate.show-sql", env.getProperty("secdb.jpa.show-sql"));
		em.setJpaPropertyMap(properties);
		return em;
	}

	@Bean(name = "securityDataSource")
	public DataSource securityDataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("secdb.datasource.driver-class-name"));
		dataSource.setUrl(env.getProperty("secdb.datasource.url"));
		dataSource.setUsername(env.getProperty("secdb.datasource.username"));
		dataSource.setPassword(env.getProperty("secdb.datasource.password"));

		return dataSource;
	}

	@Bean(name = "securityTransactionManager")
	public PlatformTransactionManager securityTransactionManager() {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(securityEntityManager().getObject());
		return transactionManager;
	}

}
