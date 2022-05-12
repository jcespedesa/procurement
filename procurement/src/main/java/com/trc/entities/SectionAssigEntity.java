package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="assigsections")
public class SectionAssigEntity 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long sectionid;
	
	@Column(name="assigsectionnumber")
	private String assigSectionNumber;
	
	@Column(name="userid")
	private String userId;
	
	@Column(name="username")
	private String username;
	
	
	@Override
	public String toString()
	{
		return "SectionAssigEntity[sectionid="+ sectionid +",userId="+ userId +",assigSectionNumber="+ assigSectionNumber +",username="+ username +"]";				
		
	}


	public Long getSectionid() 
	{
		return sectionid;
	}


	public void setSectionid(Long sectionid) 
	{
		this.sectionid=sectionid;
	}


	public String getAssigSectionNumber() 
	{
		return assigSectionNumber;
	}


	public void setAssigSectionNumber(String assigSectionNumber) 
	{
		this.assigSectionNumber=assigSectionNumber;
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
