package com.angularjsspring.crud.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.zaxxer.hikari.HikariDataSource;

public class JpaConfig {
	
	@Value("${datasource.crud.max-poolsize:10}")
	private int maxPoolSize;
	
	/**
	 * populate dataSource properties from application.properties file
	 */
	@Bean
	@ConfigurationProperties(prefix = "datasource.crud")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}
	
	/**
	 * configure hikariCP pooled data source
	 * 
	 * @return DataSource
	 */
	@Bean
	public DataSource dataSource() {
		DataSourceProperties dataSourceProperties = dataSourceProperties();
		HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder
				.create(dataSourceProperties.getClassLoader())
				.driverClassName(dataSourceProperties.getDriverClassName())
				.url(dataSourceProperties.getUrl())
				.username(dataSourceProperties.getUsername())
				.password(dataSourceProperties.getPassword())
				.type(HikariDataSource.class)
				.build();
		dataSource.setMaximumPoolSize(maxPoolSize);
		return dataSource;
	}
}
