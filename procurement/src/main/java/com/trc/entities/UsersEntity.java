package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="secure")
public class UsersEntity 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long userid;
	
	@Column(name="username")
	private String username;
	
	@Column(name="role")
	private String role;
	
	@Column(name="password")
	private String password;
	
		
	@Column(name="domain")
	private String domain;
	
	@Column(name="active")
	private String active;
	
	@Override
	public String toString()
	{
		return "UsersEntity[userid="+ userid +",username="+ username +",role="+ role +",password="+ password +",domain="+ domain +",active="+ active +"]";				
		
	}

	public Long getUserid() 
	{
		return userid;
	}

	public void setUserid(Long userid) 
	{
		this.userid=userid;
	}

	public String getUsername() 
	{
		return username;
	}

	public void setUsername(String username) 
	{
		this.username=username;
	}

	public String getRole() 
	{
		return role;
	}

	public void setRole(String role) 
	{
		this.role=role;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password=password;
	}

	
	public String getDomain() 
	{
		return domain;
	}

	public void setDomain(String domain) 
	{
		this.domain=domain;
	}

	public String getActive() 
	{
		return active;
	}

	public void setActive(String active) 
	{
		this.active=active;
	}
	
	
}
