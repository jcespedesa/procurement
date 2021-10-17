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
	
	@Column(name="datereceipt")
	private String dateReceipt;
	
	@Column(name="signedby")
	private String signedBy;
	
	@Column(name="semail")
	private String semail;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="kluch")
	private String kluch;
	
	
	@Override
	public String toString()
	{
		return "ReceiptsEntity[receiptid="+ receiptid +",description="+ description +",dateReceipt="+ dateReceipt +",signedBy="+ signedBy +",semail="+ semail +",notes="+ notes +",kluch="+ kluch +"]";				
		
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


	public String getDateReceipt() 
	{
		return dateReceipt;
	}


	public void setDateReceipt(String dateReceipt) 
	{
		this.dateReceipt=dateReceipt;
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


	public String getKluch() 
	{
		return kluch;
	}


	public void setKluch(String kluch) 
	{
		this.kluch=kluch;
	}

			
}
