package com.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultitenantDataSource extends AbstractRoutingDataSource {

	// private Map<Object, Object> targetDataSources;

	@Override
	protected Object determineCurrentLookupKey() {
		return TenantContext.getCurrentTenant();
	}

	

	// public Connection getConnection() throws SQLException {
	// return determineTargetDataSource().getConnection();
	// }
	//
	// public Map<Object, Object> getTargetDataSources() {
	// return targetDataSources;
	// }
	//
	// public void setTargetDataSources(Map<Object, Object> targetDataSources) {
	// this.targetDataSources = targetDataSources;
	// }

}
