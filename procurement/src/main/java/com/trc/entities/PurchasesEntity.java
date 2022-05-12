package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="purchases")
public class PurchasesEntity 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long reqid;
	
	@Column(name="reqdate")
	private String reqDate;
	
	@Column(name="realreqdate")
	private String realReqDate;
	
	@Column(name="requestorid")
	private String requestorId;
		
	@Column(name="requestorname")
	private String requestorName;
	
	@Column(name="itemnumber")
	private String itemNumber;
	
	@Column(name="itemname")
	private String itemName;
	
	@Column(name="quantity")
	private String quantity;
	
	@Column(name="projectnumber")
	private String projectNumber;
	
	@Column(name="projectname")
	private String projectName;
			
	@Column(name="notes")
	private String notes;
	
		
	@Column(name="itapproverid")
	private String itApproverId;
	
	@Column(name="itapprovername")
	private String itApproverName;
	
	@Column(name="itdateapproval")
	private String itDateApproval;
	
	@Column(name="realitdateapproval")
	private String realItDateApproval;
	
	@Column(name="itnotes")
	private String itNotes;
	
	@Column(name="foapproverid")
	private String foApproverId;
	
	@Column(name="foapprovername")
	private String foApproverName;
	
	@Column(name="fodateapproval")
	private String foDateApproval;
	
	@Column(name="realfodateapproval")
	private String realFoDateApproval;
	
	@Column(name="fonotes")
	private String foNotes;
	
	@Column(name="divisionnumber")
	private String divisionNumber;
	
	@Column(name="status")
	private String status;
	
	@Column(name="observation")
	private String observation;
	
	@Column(name="ppcid")
	private String ppcId;
	
	@Column(name="ppcname")
	private String ppcName;
	
	@Column(name="dateprocessed")
	private String dateProcessed;
	
	@Column(name="realdateprocessed")
	private String realDateProcessed;
	
	@Column(name="ppcnotes")
	private String ppcNotes;
	
	
	@Column(name="dateCreation",updatable=false, insertable=false)
	private String dateCreation;	
	
	@Column(name="strobe")
	private String strobe;
	
	
	@Override
	public String toString()
	{
		return "PurchasesEntity[reqid="+ reqid +",reqDate="+ reqDate +",realReqDate="+ realReqDate +",requestorId="+ requestorId +",requestorName="+ requestorName +","
				+ "itemNumber="+ itemNumber +",itemName="+ itemName +",projectNumber="+ projectNumber +",projectName="+ projectName +",quantity="+ quantity +",notes="+ notes +","
						+ "itApproverId="+ itApproverId +",itApproverName="+ itApproverName +",itDateApproval="+ itDateApproval +",realItDateApproval="+ realItDateApproval +",itNotes="+ itNotes +","
								+ "foApproverId="+ foApproverId +",foApproverName="+ foApproverName +",foDateApproval="+ foDateApproval +",foNotes="+ foNotes +",divisionNumber="+ divisionNumber +","
										+ "status="+ status +",observation="+ observation +",ppcId="+ ppcId +",ppcName="+ ppcName +",dateProcessed="+ dateProcessed +",realDateProcessed="+ realDateProcessed +",ppcNotes="+ ppcNotes +",strobe="+ strobe +"]";				
		
	}


	public Long getReqid() 
	{
		return reqid;
	}


	public void setReqid(Long reqid) 
	{
		this.reqid=reqid;
	}


	public String getReqDate() 
	{
		return reqDate;
	}


	public void setReqDate(String reqDate) 
	{
		this.reqDate=reqDate;
	}


	public String getRealReqDate() 
	{
		return realReqDate;
	}


	public void setRealReqDate(String realReqDate) 
	{
		this.realReqDate=realReqDate;
	}


	public String getRequestorId() 
	{
		return requestorId;
	}


	public void setRequestorId(String requestorId) 
	{
		this.requestorId=requestorId;
	}


	public String getRequestorName() 
	{
		return requestorName;
	}


	public void setRequestorName(String requestorName) 
	{
		this.requestorName=requestorName;
	}


	public String getItemNumber() 
	{
		return itemNumber;
	}


	public void setItemNumber(String itemNumber) 
	{
		this.itemNumber=itemNumber;
	}


	public String getItemName() 
	{
		return itemName;
	}


	public void setItemName(String itemName) 
	{
		this.itemName=itemName;
	}


	public String getProjectNumber() 
	{
		return projectNumber;
	}


	public void setProjectNumber(String projectNumber) 
	{
		this.projectNumber=projectNumber;
	}


	public String getProjectName() 
	{
		return projectName;
	}


	public void setProjectName(String projectName) 
	{
		this.projectName=projectName;
	}

	
	
	public String getNotes() 
	{
		return notes;
	}


	public void setNotes(String notes) 
	{
		this.notes=notes;
	}


	public String getItApproverId() 
	{
		return itApproverId;
	}


	public void setItApproverId(String itApproverId) 
	{
		this.itApproverId=itApproverId;
	}


	public String getItApproverName() 
	{
		return itApproverName;
	}


	public void setItApproverName(String itApproverName) 
	{
		this.itApproverName=itApproverName;
	}


	public String getItDateApproval() 
	{
		return itDateApproval;
	}


	public void setItDateApproval(String itDateApproval) 
	{
		this.itDateApproval=itDateApproval;
	}


	public String getRealItDateApproval() 
	{
		return realItDateApproval;
	}


	public void setRealItDateApproval(String realItDateApproval) 
	{
		this.realItDateApproval=realItDateApproval;
	}


	public String getItNotes() 
	{
		return itNotes;
	}


	public void setItNotes(String itNotes) 
	{
		this.itNotes=itNotes;
	}


	public String getFoApproverId() 
	{
		return foApproverId;
	}


	public void setFoApproverId(String foApproverId) 
	{
		this.foApproverId=foApproverId;
	}


	public String getFoApproverName() 
	{
		return foApproverName;
	}


	public void setFoApproverName(String foApproverName) 
	{
		this.foApproverName=foApproverName;
	}


	public String getFoDateApproval() 
	{
		return foDateApproval;
	}


	public void setFoDateApproval(String foDateApproval) 
	{
		this.foDateApproval=foDateApproval;
	}


	public String getRealFoDateApproval() 
	{
		return realFoDateApproval;
	}


	public void setRealFoDateApproval(String realFoDateApproval) 
	{
		this.realFoDateApproval=realFoDateApproval;
	}


	public String getFoNotes() 
	{
		return foNotes;
	}


	public void setFoNotes(String foNotes) 
	{
		this.foNotes=foNotes;
	}


	public String getDivisionNumber() 
	{
		return divisionNumber;
	}


	public void setDivisionNumber(String divisionNumber) 
	{
		this.divisionNumber=divisionNumber;
	}


	public String getStatus() 
	{
		return status;
	}


	public void setStatus(String status) 
	{
		this.status=status;
	}


	public String getObservation() 
	{
		return observation;
	}


	public void setObservation(String observation) 
	{
		this.observation=observation;
	}


	public String getPpcId() 
	{
		return ppcId;
	}


	public void setPpcId(String ppcId) 
	{
		this.ppcId=ppcId;
	}


	public String getPpcName() 
	{
		return ppcName;
	}


	public void setPpcName(String ppcName) 
	{
		this.ppcName=ppcName;
	}


	public String getDateProcessed() 
	{
		return dateProcessed;
	}


	public void setDateProcessed(String dateProcessed) 
	{
		this.dateProcessed=dateProcessed;
	}


	public String getRealDateProcessed() 
	{
		return realDateProcessed;
	}


	public void setRealDateProcessed(String realDateProcessed) 
	{
		this.realDateProcessed=realDateProcessed;
	}


	public String getPpcNotes() 
	{
		return ppcNotes;
	}


	public void setPpcNotes(String ppcNotes) 
	{
		this.ppcNotes=ppcNotes;
	}


	public String getQuantity() 
	{
		return quantity;
	}


	public void setQuantity(String quantity) 
	{
		this.quantity=quantity;
	}


	public String getStrobe() 
	{
		return strobe;
	}


	public void setStrobe(String strobe) 
	{
		this.strobe=strobe;
	}
	
	
}
