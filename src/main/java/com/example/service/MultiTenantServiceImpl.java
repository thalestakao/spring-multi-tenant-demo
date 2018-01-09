package com.example.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;

import com.example.DataSourceMap;
import com.example.MultitenantDataSource;
import com.example.domain.Tenant;

@Service
public class MultiTenantServiceImpl implements MultiTenantService {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
    private DataSourceProperties properties;
	
	@Autowired
	private DataSourceMap dataSourceMap;
	
//	@Bean
	@Override
	@ConfigurationProperties(
            prefix = "spring.datasource"
    )
	public DataSource criarDataSource(Tenant con) {
//		 Map<Object,Object> resolvedDataSources = new HashMap<>();
		 
         DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader());
         
         dataSourceBuilder.driverClassName(properties.getDriverClassName())
         .url(con.getUrl()).username(con.getUserName()).password(con.getPassword());
         
         dataSourceMap.addDataSource(con.getIdTenant(), dataSourceBuilder.build());
//         resolvedDataSources.put(con.getIdTenant(), dataSourceBuilder.build());
         
         
		((AbstractRoutingDataSource) dataSource).setDefaultTargetDataSource(defaultDataSource());
        ((AbstractRoutingDataSource) dataSource).setTargetDataSources(dataSourceMap.getDataSourceMap());
		System.out.println("Registrou o novo tenant: " + con.getIdTenant());
		((MultitenantDataSource) dataSource).afterPropertiesSet();
		return dataSource;
	}
	
	   /**
     * Creates the default data source for the application
     * @return
     */
    private DataSource defaultDataSource() {
        DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader())
                .driverClassName(properties.getDriverClassName())
                .url(properties.getUrl())
                .username(properties.getUsername())
                .password(properties.getPassword());

        if(properties.getType() != null) {
            dataSourceBuilder.type(properties.getType());
        }

        return dataSourceBuilder.build();
    }

}
