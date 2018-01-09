package com.example;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import com.example.domain.Tenant;
import com.example.domain.TenantRepository;

@Component
public class TenantsInitialize {
	
	@Autowired
	private DataSourceProperties properties;

	@Autowired
	private DataSourceMap dataSourceMap;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private TenantRepository connectionRepository;

	
	@PostConstruct
	public void loadTenants() {
		List<Tenant> cons = (List<Tenant>) connectionRepository.findAll();
		DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader());

		for (Tenant con : cons) {
			dataSourceBuilder.driverClassName(properties.getDriverClassName()).url(con.getUrl())
					.username(con.getUserName()).password(con.getPassword());
			if (properties.getType() != null) {
				dataSourceBuilder.type(properties.getType());
			}
			System.out.println(con.getIdTenant());
			dataSourceMap.addDataSource(con.getIdTenant(), dataSourceBuilder.build());
			((AbstractRoutingDataSource) dataSource).setTargetDataSources(dataSourceMap.getDataSourceMap());
			((AbstractRoutingDataSource) dataSource).afterPropertiesSet();
		}
	}
}
