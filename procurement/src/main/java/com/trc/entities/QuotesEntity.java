package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="quotes")
public class QuotesEntity 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long quoteid;
	
	@Column(name="number")
	private String number;
	
	@Column(name="details")
	private String details;
	
	@Column(name="datequote")
	private String dateQuote;
		
	@Column(name="reqnumber")
	private String reqNumber;
	
	@Column(name="requester")
	private String requester;
	
	@Column(name="projectnum")
	private String projectNum;
	
	@Column(name="project")
	private String project;
			
	@Column(name="amount")
	private double amount;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="realdatequote")
	private String realDateQuote;
	
	@Column(name="strobe")
	private String strobe;
	
	@Column(name="quotesentto")
	private String quoteSentTo;
	
	@Column(name="datequotesent")
	private String dateQuoteSent;
	
	@Column(name="realdatequotesent")
	private String realDateQuoteSent;
	
	@Column(name="item")
	private String item;
	
	
	@Override
	public String toString()
	{
		return "QuotesEntity[quoteid="+ quoteid +",number="+ number +",details="+ details +",dateQuote="+ dateQuote +",reqNumber="+ reqNumber +",requester="+ requester +",projectNum="+ projectNum +",amount="+ amount +",notes="+ notes +",realDateQuote="+ dateQuote +",strobe="+ strobe +",quoteSentTo="+ quoteSentTo +",dateQuoteSent="+ dateQuoteSent +",realDateQuoteSent="+ dateQuoteSent +",item="+ item +"]";				
		
	}

	//Getters and setters
	
	public Long getQuoteid() 
	{
		return quoteid;
	}

	public void setQuoteid(Long quoteid) 
	{
		this.quoteid=quoteid;
	}

	public String getNumber() 
	{
		return number;
	}

	public void setNumber(String number) 
	{
		this.number=number;
	}

	public String getDetails() 
	{
		return details;
	}

	public void setDetails(String details) 
	{
		this.details=details;
	}
	
	public String getDateQuote() 
	{
		return dateQuote;
	}

	public void setDateQuote(String dateQuote) 
	{
		this.dateQuote=dateQuote;
	}

	public String getReqNumber() 
	{
		return reqNumber;
	}

	public void setReqNumber(String reqNumber) 
	{
		this.reqNumber=reqNumber;
	}

	public String getRequester() 
	{
		return requester;
	}

	public void setRequester(String requester) 
	{
		this.requester=requester;
	}

	public String getProjectNum() 
	{
		return projectNum;
	}

	public void setProjectNum(String projectNum) 
	{
		this.projectNum=projectNum;
	}

	public String getProject() 
	{
		return project;
	}

	public void setProject(String project) 
	{
		this.project=project;
	}

	public double getAmount() 
	{
		return amount;
	}

	public void setAmount(double amount) 
	{
		this.amount=amount;
	}
	
	public String getNotes() 
	{
		return notes;
	}

	public void setNotes(String notes) 
	{
		this.notes=notes;
	}

	public String getRealDateQuote() 
	{
		return realDateQuote;
	}

	public void setRealDateQuote(String realDateQuote) 
	{
		this.realDateQuote=realDateQuote;
	}

	public String getStrobe() 
	{
		return strobe;
	}

	public void setStrobe(String strobe) 
	{
		this.strobe=strobe;
	}

	public String getQuoteSentTo() 
	{
		return quoteSentTo;
	}

	public void setQuoteSentTo(String quoteSentTo) 
	{
		this.quoteSentTo=quoteSentTo;
	}

	public String getDateQuoteSent() 
	{
		return dateQuoteSent;
	}

	public void setDateQuoteSent(String dateQuoteSent) 
	{
		this.dateQuoteSent=dateQuoteSent;
	}

	public String getRealDateQuoteSent() 
	{
		return realDateQuoteSent;
	}

	public void setRealDateQuoteSent(String realDateQuoteSent) 
	{
		this.realDateQuoteSent=realDateQuoteSent;
	}

	public String getItem() 
	{
		return item;
	}

	public void setItem(String item) 
	{
		this.item=item;
	}
	
	
	
}
