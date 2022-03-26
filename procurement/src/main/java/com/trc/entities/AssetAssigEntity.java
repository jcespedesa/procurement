package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="assetsreassignations")
public class AssetAssigEntity 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long reassigid;
	
	@Column(name="assetid")
	private String assetId;
	
	@Column(name="assetnumber")
	private String assetNumber;
	
	@Column(name="assigid")
	private String assigId;
	
	@Column(name="assigname")
	private String assigName;
	
	@Column(name="assigemail")
	private String assigEmail;
	
	@Column(name="assigempstatus")
	private String assigEmpStatus;
	
	@Column(name="assigproject")
	private String assigProject;
	
	@Column(name="assigtitle")
	private String assigTitle;
	
	@Column(name="newassigid")
	private String newAssigId;
	
	@Column(name="newassigname")
	private String newAssigName;
	
	@Column(name="newassigemail")
	private String newAssigEmail;
	
	@Column(name="newassigempstatus")
	private String newAssigEmpStatus;
	
	@Column(name="newassigproject")
	private String newAssigProject;
	
	@Column(name="newassigtitle")
	private String newAssigTitle;
	
	@Column(name="datereassignation",updatable=false, insertable=false)
	private String dateReassignation;
	
	@Column(name="reassignedby")
	private String reassignedBy;
	
	@Column(name="emailreassigner")
	private String emailReassigner;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="kluch")
	private String kluch;
	
	@Override
	public String toString()
	{
		return "AssetsAssigEntity[reassigid="+ reassigid +",assetId="+ assetId +",assetNumber="+ assetNumber +",assigName="+ assigName +",assigEmail="+ assigEmail +",assigEmpStatus="+ assigEmpStatus +",assigProject="+ assigProject +",assigTitle="+ assigTitle +",newAssigName="+ newAssigName +",newAssigEmail="+ newAssigEmail +",newAssigEmpStatus="+ newAssigEmpStatus +",newAssigProject="+ newAssigProject +",newAssigTitle="+ newAssigTitle +",dateReassignation="+ dateReassignation +",reassignedBy="+ reassignedBy +",emailReassigner="+ emailReassigner +",notes="+ notes +",kluch="+ kluch +",assigId="+ assigId +",newAssigId="+ newAssigId +"]";				
		
	}

	public Long getReassigid() 
	{
		return reassigid;
	}

	public void setReassigid(Long reassigid) 
	{
		this.reassigid=reassigid;
	}

	public String getAssetId() 
	{
		return assetId;
	}

	public void setAssetId(String assetId) 
	{
		this.assetId=assetId;
	}

	public String getAssetNumber() 
	{
		return assetNumber;
	}

	public void setAssetNumber(String assetNumber) 
	{
		this.assetNumber=assetNumber;
	}

	public String getAssigName() 
	{
		return assigName;
	}

	public void setAssigName(String assigName) 
	{
		this.assigName=assigName;
	}

	public String getAssigEmail() 
	{
		return assigEmail;
	}

	public void setAssigEmail(String assigEmail) 
	{
		this.assigEmail=assigEmail;
	}

	public String getAssigEmpStatus() 
	{
		return assigEmpStatus;
	}

	public void setAssigEmpStatus(String assigEmpStatus) 
	{
		this.assigEmpStatus=assigEmpStatus;
	}

	public String getAssigProject() 
	{
		return assigProject;
	}

	public void setAssigProject(String assigProject) 
	{
		this.assigProject=assigProject;
	}

	public String getAssigTitle() 
	{
		return assigTitle;
	}

	public void setAssigTitle(String assigTitle) 
	{
		this.assigTitle=assigTitle;
	}

	public String getNewAssigName() 
	{
		return newAssigName;
	}

	public void setNewAssigName(String newAssigName) 
	{
		this.newAssigName=newAssigName;
	}

	public String getNewAssigEmail() 
	{
		return newAssigEmail;
	}

	public void setNewAssigEmail(String newAssigEmail) 
	{
		this.newAssigEmail=newAssigEmail;
	}

	public String getNewAssigEmpStatus() 
	{
		return newAssigEmpStatus;
	}

	public void setNewAssigEmpStatus(String newAssigEmpStatus) 
	{
		this.newAssigEmpStatus=newAssigEmpStatus;
	}

	public String getNewAssigProject() 
	{
		return newAssigProject;
	}

	public void setNewAssigProject(String newAssigProject) 
	{
		this.newAssigProject=newAssigProject;
	}

	public String getNewAssigTitle() 
	{
		return newAssigTitle;
	}

	public void setNewAssigTitle(String newAssigTitle) 
	{
		this.newAssigTitle=newAssigTitle;
	}

	public String getDateReassignation() 
	{
		return dateReassignation;
	}

	public void setDateReassignation(String dateReassignation) 
	{
		this.dateReassignation=dateReassignation;
	}

	public String getReassignedBy() 
	{
		return reassignedBy;
	}

	public void setReassignedBy(String reassignedBy) 
	{
		this.reassignedBy=reassignedBy;
	}

	public String getEmailReassigner() 
	{
		return emailReassigner;
	}

	public void setEmailReassigner(String emailReassigner) 
	{
		this.emailReassigner=emailReassigner;
	}

	public String getNotes() 
	{
		return notes;
	}

	public void setNotes(String notes) 
	{
		this.notes=notes;
	}

	public String getKluch() 
	{
		return kluch;
	}

	public void setKluch(String kluch) 
	{
		this.kluch=kluch;
	}

	public String getAssigId() 
	{
		return assigId;
	}

	public void setAssigId(String assigId) 
	{
		this.assigId=assigId;
	}

	public String getNewAssigId() 
	{
		return newAssigId;
	}

	public void setNewAssigId(String newAssigId) 
	{
		this.newAssigId=newAssigId;
	}
			
}
