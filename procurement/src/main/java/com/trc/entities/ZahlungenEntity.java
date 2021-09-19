package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="zahlungen")
public class ZahlungenEntity 
{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long zahlungenid;
	
	@Column(name="month")
	private int month;
	
	@Column(name="year")
	private int year;
		
	@Column(name="providerid")
	private String providerId;
	
	@Column(name="amount")
	private String amount;
	
	@Column(name="sitenumber")
	private String siteNumber;
	
	@Column(name="type")
	private String type;
	
	@Column(name="datesent")
	private String dateSent;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="invoicenum")
	private String invoiceNum;
	
	@Column(name="description")
	private String description;
	
	@Column(name="account")
	private String account;

	@Column(name="ponum")
	private String poNum;
	
	@Column(name="frequency")
	private String frequency;
	
	@Column(name="reqnumber")
	private String reqNumber;
	
	@Column(name="invoicedownloaded")
	private String invoiceDownloaded;
	
	@Column(name="voucher")
	private String voucher;
	
	@Column(name="checkcut")
	private String checkCut;
		
	//Constructors
	
	@Override
	public String toString()
	{
		return "ZahlungenEntity[zahlungenlid="+ zahlungenid +",month="+ month +",year="+ year +",amount="+ amount +",providerId="+ providerId +",siteNumber="+ siteNumber +",type="+ type +",notes="+ notes +",dateSent="+ dateSent +",invoiceNum="+ invoiceNum +",dascription="+ description +",account="+ account +",poNum="+ poNum +",frequency="+ frequency +",reqNumber="+ reqNumber +",invoiceDownloaded="+ invoiceDownloaded +",voucher="+ voucher +",checkCut="+ checkCut +"]";				
		
	}
	//Getters and setters


	public Long getZahlungenid() 
	{
		return zahlungenid;
	}


	public void setZahlungenid(Long zahlungenid) 
	{
		this.zahlungenid=zahlungenid;
	}


	public String getProviderId() 
	{
		return providerId;
	}


	public void setProviderId(String providerId) 
	{
		this.providerId=providerId;
	}


	public String getAmount() 
	{
		return amount;
	}


	public void setAmount(String amount) 
	{
		this.amount=amount;
	}


	public String getSiteNumber() 
	{
		return siteNumber;
	}


	public void setSiteNumber(String siteNumber) 
	{
		this.siteNumber=siteNumber;
	}


	public String getType() 
	{
		return type;
	}


	public void setType(String type) 
	{
		this.type=type;
	}


	public String getDateSent() 
	{
		return dateSent;
	}


	public void setDateSent(String dateSent) 
	{
		this.dateSent=dateSent;
	}


	public String getNotes() 
	{
		return notes;
	}


	public void setNotes(String notes) 
	{
		this.notes=notes;
	}


	public String getInvoiceNum() 
	{
		return invoiceNum;
	}


	public void setInvoiceNum(String invoiceNum) 
	{
		this.invoiceNum=invoiceNum;
	}


	public int getMonth() 
	{
		return month;
	}


	public void setMonth(int month) 
	{
		this.month=month;
	}


	public int getYear() 
	{
		return year;
	}


	public void setYear(int year) 
	{
		this.year=year;
	}


	public String getDescription() 
	{
		return description;
	}


	public void setDescription(String description) 
	{
		this.description=description;
	}


	public String getAccount() 
	{
		return account;
	}


	public void setAccount(String account) 
	{
		this.account=account;
	}


	public String getPoNum() 
	{
		return poNum;
	}


	public void setPoNum(String poNum) 
	{
		this.poNum=poNum;
	}


	public String getFrequency() 
	{
		return frequency;
	}


	public void setFrequency(String frequency) 
	{
		this.frequency=frequency;
	}


	public String getReqNumber() 
	{
		return reqNumber;
	}


	public void setReqNumber(String reqNumber) 
	{
		this.reqNumber=reqNumber;
	}


	public String getInvoiceDownloaded() 
	{
		return invoiceDownloaded;
	}


	public void setInvoiceDownloaded(String invoiceDownloaded) 
	{
		this.invoiceDownloaded=invoiceDownloaded;
	}


	public String getVoucher() 
	{
		return voucher;
	}


	public void setVoucher(String voucher) 
	{
		this.voucher=voucher;
	}


	public String getCheckCut() 
	{
		return checkCut;
	}


	public void setCheckCut(String checkCut) 
	{
		this.checkCut=checkCut;
	}
	
	
		
}
