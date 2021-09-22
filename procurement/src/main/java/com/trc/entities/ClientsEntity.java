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
	
	@Column(name="division")
	private String division;
	
	@Column(name="program")
	private String program;
	
	@Column(name="projectnumber")
	private String projectNumber;
	
	@Column(name="hhsdivision")
	private String hhsDivision;
	
	@Column(name="email")
	private String email;
	
	@Column(name="active")
	private String active;
	
	@Column(name="vacstatus")
	private String vacStatus;
	
	@Column(name="completiontime")
	private String completionTime;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="dateseconddose")
	private String dateSecondDose;
	
	
	@Override
	public String toString()
	{
		return "ClientsEntity[clientid="+ clientid +",cname="+ cname +",division="+ division +",program="+ program +",projectNumber="+ projectNumber +",hhsDivision="+ hhsDivision +",email="+ email +",active="+ active +",vacStatus="+ vacStatus +",completionTime="+ completionTime +",notes="+ notes +",dateSecondDose="+ dateSecondDose +"]";				
		
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


	public String getProgram() 
	{
		return program;
	}


	public void setProgram(String program) 
	{
		this.program=program;
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


	public String getCompletionTime() 
	{
		return completionTime;
	}


	public void setCompletionTime(String completionTime) 
	{
		this.completionTime=completionTime;
	}


	public String getNotes() 
	{
		return notes;
	}


	public void setNotes(String notes) 
	{
		this.notes=notes;
	}


	public String getDateSecondDose() 
	{
		return dateSecondDose;
	}


	public void setDateSecondDose(String dateSecondDose) 
	{
		this.dateSecondDose=dateSecondDose;
	}
	
	
	
}
