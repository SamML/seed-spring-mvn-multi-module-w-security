package com.smdev.smsj.core.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
 *         |> CoreDatasourceConfig ~~ [com.smdev.smsj.core.config]
 * 
 */
/* 
 * List of properties to add: http://docs.jboss.org/hibernate/orm/4.2/manual/en-US/html/ch03.html
 * */
@Configuration
@PropertySource({ "classpath:coredb.properties" })
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.smdev.smsj.core.database.dao", entityManagerFactoryRef = "coreEntityManager", transactionManagerRef = "coreTransactionManager")
public class CoreDatasourceConfig {

	@Autowired
	private Environment env;

	@Primary
	@Bean(name = "coreEntityManager")
	public LocalContainerEntityManagerFactoryBean coreEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(coreDataSource());
		String[] scanpack = { "com.smdev.smsj.core.database.entities" };
		em.setPackagesToScan(scanpack);
		em.setPersistenceUnitName("coreDbPersistanceUnit");

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("coredb.jpa.properties.hibernate.hbm2ddl-auto"));
		properties.put("hibernate.dialect", env.getProperty("coredb.jpa.properties.hibernate.dialect"));
		properties.put("hibernate.show-sql", env.getProperty("coredb.jpa.show-sql"));
		em.setJpaPropertyMap(properties);
		return em;
	}

	@Primary
	@Bean(name = "coreDataSource")
	public DataSource coreDataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("coredb.datasource.driver-class-name"));
		dataSource.setUrl(env.getProperty("coredb.datasource.url"));
		dataSource.setUsername(env.getProperty("coredb.datasource.username"));
		dataSource.setPassword(env.getProperty("coredb.datasource.password"));

		return dataSource;
	}

	@Primary
	@Bean(name = "coreTransactionManager")
	public PlatformTransactionManager coreTransactionManager() {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(coreEntityManager().getObject());
		return transactionManager;
	}

}
