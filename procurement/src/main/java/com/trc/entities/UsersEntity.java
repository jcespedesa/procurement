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
	
	@Column(name="email")
	private String email;
	
	@Column(name="role")
	private String role;
	
	@Column(name="password")
	private String password;
			
	@Column(name="domain")
	private String domain;
	
	@Column(name="active")
	private String active;
	
	@Column(name="priznakitapprover")
	private String priznakITapprover;
	
	@Column(name="priznakfoapprover")
	private String priznakFOapprover;
	
	@Column(name="priznakppc")
	private String priznakPpc;
	
	@Column(name="assignedrole")
	private String assignedRole;
	
	@Override
	public String toString()
	{
		return "UsersEntity[userid="+ userid +",username="+ username +",email="+ email +",role="+ role +",password="+ password +",domain="+ domain +",active="+ active +",priznakITapprover="+ priznakITapprover +",priznakFOapprover="+ priznakFOapprover +",priznakPpc="+ priznakPpc +",assignedRole="+ assignedRole +"]";				
		
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

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email=email;
	}

	public String getPriznakITapprover() 
	{
		return priznakITapprover;
	}

	public void setPriznakITapprover(String priznakITapprover) 
	{
		this.priznakITapprover=priznakITapprover;
	}

	public String getPriznakFOapprover() 
	{
		return priznakFOapprover;
	}

	public void setPriznakFOapprover(String priznakFOapprover) 
	{
		this.priznakFOapprover=priznakFOapprover;
	}

	public String getPriznakPpc() 
	{
		return priznakPpc;
	}

	public void setPriznakPpc(String priznakPpc) 
	{
		this.priznakPpc=priznakPpc;
	}

	public String getAssignedRole() 
	{
		return assignedRole;
	}

	public void setAssignedRole(String assignedRole) 
	{
		this.assignedRole=assignedRole;
	}
	
}
