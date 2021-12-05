package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tempusers")
public class TempUsersEntity 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long itemid;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="role")
	private String role;
	
	@Column(name="active")
	private String active;
	
	@Override
	public String toString()
	{
		return "TempUsersEntity[itemid="+ itemid +",email="+ email +",password="+ password +",role="+ role +",active="+ active +"]";				
		
	}

	public Long getItemid() 
	{
		return itemid;
	}

	public void setItemid(Long itemid) 
	{
		this.itemid=itemid;
	}

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email=email;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password=password;
	}

	public String getRole() 
	{
		return role;
	}

	public void setRole(String role) 
	{
		this.role=role;
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
