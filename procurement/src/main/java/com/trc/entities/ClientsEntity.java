package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="clients")
public class ClientsEntity 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long clientid;
	
	@Column(name="cname")
	private String cname;
	
	@Column(name="email")
	private String email;
	
	@Column(name="division")
	private String division;
	
	@Column(name="hhsdivision")
	private String hhsDivision;
	
	@Column(name="projectnumber")
	private String projectNumber;
	
	@Column(name="sitenumber")
	private String siteNumber;
	
	@Column(name="title")
	private String title;
	
	@Column(name="empstatus")
	private String empStatus;
	
	@Column(name="vacstatus")
	private String vacStatus;
	
	@Column(name="active")
	private String active;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="counter")
	private Integer counter;
	
		
	@Override
	public String toString()
	{
		return "ClientsEntity[clientid="+ clientid +",cname="+ cname +",division="+ division +",projectNumber="+ projectNumber +",hhsDivision="+ hhsDivision +",email="+ email +",active="+ active +",vacStatus="+ vacStatus +",siteNumber="+ siteNumber +",title="+ title +",empStatus="+ empStatus +",notes="+ notes +",counter="+ counter +"]";				
		
	}


	public Long getClientid() 
	{
		return clientid;
	}


	public void setClientid(Long clientid) 
	{
		this.clientid=clientid;
	}


	public String getCname() 
	{
		return cname;
	}


	public void setCname(String cname) 
	{
		this.cname=cname;
	}


	public String getDivision() 
	{
		return division;
	}


	public void setDivision(String division) 
	{
		this.division=division;
	}


	
	public String getProjectNumber() 
	{
		return projectNumber;
	}


	public void setProjectNumber(String projectNumber) 
	{
		this.projectNumber=projectNumber;
	}


	public String getHhsDivision() 
	{
		return hhsDivision;
	}


	public void setHhsDivision(String hhsDivision) 
	{
		this.hhsDivision=hhsDivision;
	}


	public String getEmail() 
	{
		return email;
	}


	public void setEmail(String email) 
	{
		this.email=email;
	}


	public String getActive() 
	{
		return active;
	}


	public void setActive(String active) 
	{
		this.active=active;
	}


	public String getVacStatus() 
	{
		return vacStatus;
	}


	public void setVacStatus(String vacStatus) 
	{
		this.vacStatus=vacStatus;
	}


	public String getNotes() 
	{
		return notes;
	}


	public void setNotes(String notes) 
	{
		this.notes=notes;
	}


	public String getSiteNumber() 
	{
		return siteNumber;
	}


	public void setSiteNumber(String siteNumber) 
	{
		this.siteNumber=siteNumber;
	}


	public String getTitle() 
	{
		return title;
	}


	public void setTitle(String title) 
	{
		this.title=title;
	}


	public String getEmpStatus() 
	{
		return empStatus;
	}


	public void setEmpStatus(String empStatus) 
	{
		this.empStatus=empStatus;
	}


	public int getCounter() 
	{
		return counter;
	}


	public void setCounter(int counter) 
	{
		this.counter=counter;
	}

	
}
