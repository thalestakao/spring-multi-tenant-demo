package com.example;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.example.domain.Tenant;
import com.example.domain.TenantRepository;

@SpringBootApplication
public class MultitenantDemoApplication implements CommandLineRunner {
	@Autowired
	private DataSourceProperties properties;

	@Autowired
	private DataSourceMap dataSourceMap;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private TenantRepository connectionRepository;


	public static void main(String[] args) {
		SpringApplication.run(MultitenantDemoApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("Executando");

		System.out.println("Haaa: " + properties.getUrl());

	}

//	@PostConstruct
//	public void loadTenants() {
//		List<Tenant> cons = (List<Tenant>) connectionRepository.findAll();
//		DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader());
//
//		for (Tenant con : cons) {
//			dataSourceBuilder.driverClassName(properties.getDriverClassName()).url(con.getUrl())
//					.username(con.getUserName()).password(con.getPassword());
//			if (properties.getType() != null) {
//				dataSourceBuilder.type(properties.getType());
//			}
//			System.out.println(con.getIdTenant());
//			dataSourceMap.addDataSource(con.getIdTenant(), dataSourceBuilder.build());
//			((AbstractRoutingDataSource) dataSource).setTargetDataSources(dataSourceMap.getDataSourceMap());
//
//		}
//	}
}
