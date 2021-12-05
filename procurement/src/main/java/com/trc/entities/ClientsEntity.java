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
	
	@Column(name="realcompletiontime")
	private String realCompletionTime;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="dateseconddose")
	private String dateSecondDose;
	
	@Column(name="realdateseconddose")
	private String realDateSecondDose;
	
	@Column(name="datecreation")
	private String dateCreation;
	
	@Column(name="realdatecreation")
	private String realDateCreation;
	
	@Column(name="type")
	private String type;
	
	@Column(name="hmisnum")
	private String hmisNum;
	
	@Override
	public String toString()
	{
		return "ClientsEntity[clientid="+ clientid +",cname="+ cname +",division="+ division +",program="+ program +",projectNumber="+ projectNumber +",hhsDivision="+ hhsDivision +",email="+ email +",active="+ active +",vacStatus="+ vacStatus +",completionTime="+ completionTime +",realCompletionTime="+ realCompletionTime +",notes="+ notes +",dateSecondDose="+ dateSecondDose +",realDateSecondDose="+ realDateSecondDose +",type="+ type +",dateCreation="+ dateCreation +",realDateCreation="+ realDateCreation +",hmisNum="+ hmisNum +"]";				
		
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


	public String getDateCreation() 
	{
		return dateCreation;
	}


	public void setDateCreation(String dateCreation) 
	{
		this.dateCreation=dateCreation;
	}


	public String getType() 
	{
		return type;
	}


	public void setType(String type) 
	{
		this.type=type;
	}


	public String getHmisNum() 
	{
		return hmisNum;
	}


	public void setHmisNum(String hmisNum) 
	{
		this.hmisNum=hmisNum;
	}


	public String getRealCompletionTime() 
	{
		return realCompletionTime;
	}


	public void setRealCompletionTime(String realCompletionTime) 
	{
		this.realCompletionTime=realCompletionTime;
	}


	public String getRealDateSecondDose() 
	{
		return realDateSecondDose;
	}


	public void setRealDateSecondDose(String realDateSecondDose) 
	{
		this.realDateSecondDose=realDateSecondDose;
	}


	public String getRealDateCreation() 
	{
		return realDateCreation;
	}


	public void setRealDateCreation(String realDateCreation) 
	{
		this.realDateCreation=realDateCreation;
	}
	
	
	
}
