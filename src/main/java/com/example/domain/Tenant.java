package com.example.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

//@Entity
//@Table
@Document
public class Tenant {
	
	@Id
	private String Id;
	
	private String userName;
	private String password;
	private String url;
	private String idTenant;
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIdTenant() {
		return idTenant;
	}
	public void setIdTenant(String idTenant) {
		this.idTenant = idTenant;
	}
	
		
	
}
