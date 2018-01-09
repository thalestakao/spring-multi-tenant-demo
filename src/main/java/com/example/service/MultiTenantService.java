package com.example.service;

import javax.sql.DataSource;

import com.example.domain.Tenant;


public interface MultiTenantService {
	
	public DataSource criarDataSource(Tenant con);

}
