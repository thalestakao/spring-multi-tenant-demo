package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.example.domain.Tenant;
import com.example.domain.TenantRepository;

import javax.sql.DataSource;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;

@Configuration
public class MultitenantConfiguration {

	@Autowired
	private DataSourceProperties properties;

	@Autowired
	private DataSourceMap dataSourceMap;

	/**
	 * Defines the data source for the application
	 * 
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {

		// Create the final multi-tenant source.
		// It needs a default database to connect to.
		// Make sure that the default database is actually an empty tenant database.
		// Don't use that for a regular tenant if you want things to be safe!
		MultitenantDataSource dataSource = new MultitenantDataSource();
		dataSource.setDefaultTargetDataSource(defaultDataSource());
		dataSource.setTargetDataSources(dataSourceMap.getDataSourceMap());

		// Call this to finalize the initialization of the data source.
		dataSource.afterPropertiesSet();

		return dataSource;
	}

	/**
	 * Creates the default data source for the application
	 * 
	 * @return
	 */
	private DataSource defaultDataSource() {
		DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader())
				.driverClassName(properties.getDriverClassName()).url(properties.getUrl())
				.username(properties.getUsername()).password(properties.getPassword());

		if (properties.getType() != null) {
			dataSourceBuilder.type(properties.getType());
		}

		return dataSourceBuilder.build();
	}

}
