package com.example;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DataSourceMap {

//	private static final Logger logger = LoggerFactory.getLogger(DataSourceMap.class);

	private Map<Object, Object> dataSourceMap = new ConcurrentHashMap<>();

	public void addDataSource(String session, DataSource dataSource) {
		this.dataSourceMap.put(session, dataSource);
	}

	public Map<Object, Object> getDataSourceMap() {
        return dataSourceMap;
    }
}