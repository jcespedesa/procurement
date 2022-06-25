package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="projects")
public class ProjectsEntity 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long projectid;
	
	@Column(name="projectnumber")
	private String projectNumber;
	
	@Column(name="project")
	private String project;
	
	@Column(name="department")
	private String department;
	
	@Column(name="active")
	private String active;
	
	@Column(name="sitenumber1")
	private String siteNumber1;
	
	@Column(name="sitenumber2")
	private String siteNumber2;
	
	@Column(name="buffer1")
	private String buffer1;
	
	@Column(name="buffer2")
	private String buffer2;
	
	@Column(name="hhsdivision")
	private String hhsDivision;
	
	@Column(name="udelnybes")
	private String udelnyBes;
	
	@Column(name="tcpgraph")
	private String tcpGraph;
	
	//Constructors
		
	@Override
	public String toString()
	{
		return "ProjectsEntity[projectid="+ projectid +",projectNumber="+ projectNumber +",project="+ project +",department="+ department +",active="+ active +",siteNumber1="+ siteNumber1 +",siteNumber2="+ siteNumber2 +",buffer1="+ buffer1 +",buffer2="+ buffer2 +",hhsDivision="+ hhsDivision +",udelnyBes="+ udelnyBes+",tcpGraph="+ tcpGraph +"]";				
		
	}
	
	//Setters and getters

	public Long getProjectid() 
	{
		return projectid;
	}

	public void setProjectid(Long projectid) 
	{
		this.projectid=projectid;
	}

	public String getProject() 
	{
		return project;
	}

	public void setProject(String project) 
	{
		this.project=project;
	}

	
	public String getProjectNumber() 
	{
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) 
	{
		this.projectNumber = projectNumber;
	}

	public String getDepartment() 
	{
		return department;
	}

	public void setDepartment(String department) 
	{
		this.department=department;
	}

	public String getActive() 
	{
		return active;
	}

	public void setActive(String active) 
	{
		this.active=active;
	}

	public String getSiteNumber1() 
	{
		return siteNumber1;
	}

	public void setSiteNumber1(String siteNumber1) 
	{
		this.siteNumber1=siteNumber1;
	}

	public String getSiteNumber2() 
	{
		return siteNumber2;
	}

	public void setSiteNumber2(String siteNumber2) 
	{
		this.siteNumber2=siteNumber2;
	}

	public String getBuffer1() 
	{
		return buffer1;
	}

	public String getHhsDivision() 
	{
		return hhsDivision;
	}

	public void setHhsDivision(String hhsDivision) 
	{
		this.hhsDivision=hhsDivision;
	}

	public void setBuffer1(String buffer1) 
	{
		this.buffer1=buffer1;
	}

	public String getBuffer2() 
	{
		return buffer2;
	}

	public void setBuffer2(String buffer2) 
	{
		this.buffer2=buffer2;
	}

	public String getUdelnyBes() 
	{
		return udelnyBes;
	}

	public void setUdelnyBes(String udelnyBes) 
	{
		this.udelnyBes=udelnyBes;
	}

	public String getTcpGraph() 
	{
		return tcpGraph;
	}

	public void setTcpGraph(String tcpGraph) 
	{
		this.tcpGraph=tcpGraph;
	}
	
	

}
