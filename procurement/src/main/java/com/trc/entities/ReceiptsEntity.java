package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="receipts")
public class ReceiptsEntity 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long receiptid;
	
	@Column(name="description")
	private String description;
	
	@Column(name="assetid")
	private String assetId;
	
	@Column(name="assetname")
	private String assetName;
	
	@Column(name="datereceipt")
	private String dateReceipt;
	
	@Column(name="cname")
	private String cname;
	
	@Column(name="email")
	private String email;
	
	@Column(name="title")
	private String title;
	
	@Column(name="empstatus")
	private String empStatus;
	
	@Column(name="program")
	private String program;
	
	@Column(name="projectnum")
	private String projectNum;
	
	@Column(name="signedby")
	private String signedBy;
	
	@Column(name="semail")
	private String semail;
	
	@Column(name="notes")
	private String notes;
	
	
	@Override
	public String toString()
	{
		return "ReceiptsEntity[receiptid="+ receiptid +",description="+ description +",assetId="+ assetId +",assetName="+ assetName +",dateReceipt="+ dateReceipt +",cname="+ cname +",email="+ email +",title="+ title +",empStatus="+ empStatus +",program="+ program +",projectNum="+ projectNum +",sgnedBy="+ signedBy +",semail="+ semail +",notes="+ notes +"]";				
		
	}


	public Long getReceiptid() 
	{
		return receiptid;
	}


	public void setReceiptid(Long receiptid) 
	{
		this.receiptid=receiptid;
	}


	public String getDescription() 
	{
		return description;
	}


	public void setDescription(String description) 
	{
		this.description=description;
	}


	public String getAssetId() 
	{
		return assetId;
	}


	public void setAssetId(String assetId) 
	{
		this.assetId=assetId;
	}


	public String getDateReceipt() 
	{
		return dateReceipt;
	}


	public void setDateReceipt(String dateReceipt) 
	{
		this.dateReceipt=dateReceipt;
	}


	public String getCname() 
	{
		return cname;
	}


	public void setCname(String cname) 
	{
		this.cname=cname;
	}


	public String getEmail() 
	{
		return email;
	}


	public void setEmail(String email) 
	{
		this.email=email;
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


	public String getProgram() 
	{
		return program;
	}


	public void setProgram(String program) 
	{
		this.program=program;
	}


	public String getProjectNum() 
	{
		return projectNum;
	}


	public void setProjectNum(String projectNum) 
	{
		this.projectNum=projectNum;
	}


	public String getSignedBy() 
	{
		return signedBy;
	}


	public void setSignedBy(String signedBy) 
	{
		this.signedBy=signedBy;
	}


	public String getSemail() 
	{
		return semail;
	}


	public void setSemail(String semail) 
	{
		this.semail=semail;
	}


	public String getNotes() 
	{
		return notes;
	}


	public void setNotes(String notes) 
	{
		this.notes=notes;
	}


	public String getAssetName() 
	{
		return assetName;
	}


	public void setAssetName(String assetName) 
	{
		this.assetName=assetName;
	}

		
}
