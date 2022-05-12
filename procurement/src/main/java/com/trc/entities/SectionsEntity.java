package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sections")
public class SectionsEntity 
{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long sectionid;
	
	@Column(name="sectionnumber")
	private String sectionNumber;
	
	@Column(name="sectionname")
	private String sectionName;
	
	@Column(name="active")
	private String active;
	
	@Override
	public String toString()
	{
		return "SectionsEntity[sectionid="+ sectionid +",sectionName="+ sectionName +",sectionNumber="+ sectionNumber +",active="+ active +"]";				
		
	}

	public Long getSectionid() 
	{
		return sectionid;
	}

	public void setSectionid(Long sectionid) 
	{
		this.sectionid=sectionid;
	}

	public String getSectionNumber() 
	{
		return sectionNumber;
	}

	public void setSectionNumber(String sectionNumber) 
	{
		this.sectionNumber=sectionNumber;
	}

	public String getSectionName() 
	{
		return sectionName;
	}

	public void setSectionName(String sectionName) 
	{
		this.sectionName=sectionName;
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
