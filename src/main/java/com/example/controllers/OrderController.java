package com.example.controllers;

import com.example.domain.Tenant;
import com.example.domain.TenantRepository;
import com.example.domain.Order;
import com.example.domain.OrderRepository;
import com.example.service.MultiTenantService;
import com.example.MultitenantDataSource;
import com.example.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.MapDataSourceLookup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

@RestController
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TenantRepository connectionRepository;
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private MultiTenantService tenantService;
    
    
    @RequestMapping(path = "/orders", method= RequestMethod.POST)
    public ResponseEntity<?> createSampleOrder(@RequestHeader("X-TenantID") String tenantName) {
//    	System.out.println("Antes:" + TenantContext.getCurrentTenant().toString());
        TenantContext.setCurrentTenant(tenantName);
    	System.out.println("Depois:" + TenantContext.getCurrentTenant().toString());
        Order newOrder = new Order(new Date(System.currentTimeMillis()));
        orderRepository.save(newOrder);

        return ResponseEntity.ok(newOrder);
    }
    
    @RequestMapping(path="/oi", method=RequestMethod.GET)
    public String teste() {
		AbstractRoutingDataSource mds = (AbstractRoutingDataSource) dataSource;
    	if(mds != null)
    		System.out.println(mds.hashCode());
		return "ola mundo";
    }
    
    @RequestMapping(path="/tenant", method=RequestMethod.GET)
    public ResponseEntity<?> criarTenant() {
    	
    	Tenant con = new Tenant();
//    	con.setId(1L);
    	con.setIdTenant("tenant_1");
    	con.setUserName("root");
    	con.setPassword("thales3321");
    	con.setUrl("jdbc:mysql://localhost:3306/tenant_1");
    	
    	Tenant c = connectionRepository.save(con);
    	tenantService.criarDataSource(c);
    	

    	return ResponseEntity.ok(c);
    }
    
    
    @RequestMapping(path="/tenant2", method=RequestMethod.GET)
    public ResponseEntity<?> criarTenant2() {
    	
    	Tenant con = new Tenant();
//    	con.setId(2L);
    	con.setIdTenant("tenant_2");
    	con.setUserName("root");
    	con.setPassword("thales3321");
    	con.setUrl("jdbc:mysql://localhost:3306/tenant_2");
    	
    	Tenant c = connectionRepository.save(con);
    	tenantService.criarDataSource(c);
    	

    	return ResponseEntity.ok(c);
    }
    

    
}
