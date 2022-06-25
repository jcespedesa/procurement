package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="assigprojects")
public class ProjectAssigEntity 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long projectid;
	
	@Column(name="assigprojectnumber")
	private String assigProjectNumber;
	
	@Column(name="assigproject")
	private String assigProject;
	
	@Column(name="userid")
	private String userId;
	
	@Column(name="username")
	private String username;
	
	
	@Override
	public String toString()
	{
		return "SectionAssigEntity[projectid="+ projectid +",userId="+ userId +",assigProjectNumber="+ assigProjectNumber +",assigProject="+ assigProject +",username="+ username +"]";				
		
	}


	public Long getProjectid() 
	{
		return projectid;
	}


	public void setProjectid(Long projectid) 
	{
		this.projectid=projectid;
	}


	public String getAssigProjectNumber() 
	{
		return assigProjectNumber;
	}


	public void setAssigProjectNumber(String assigProjectNumber) 
	{
		this.assigProjectNumber=assigProjectNumber;
	}


	public String getAssigProject() 
	{
		return assigProject;
	}


	public void setAssigProject(String assigProject) 
	{
		this.assigProject=assigProject;
	}


	public String getUserId() 
	{
		return userId;
	}


	public void setUserId(String userId) 
	{
		this.userId=userId;
	}


	public String getUsername() 
	{
		return username;
	}


	public void setUsername(String username) 
	{
		this.username=username;
	}
	
	
}
