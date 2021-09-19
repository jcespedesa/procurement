package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="periodicals")
public class PeriodicalsEntity 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long periodicalid;
	
	@Column(name="description")
	private String description;
	
	@Column(name="provider")
	private String provider;
		
	@Column(name="amount")
	private String amount;
	
	@Column(name="locationnumber")
	private String locationNumber;
	
	@Column(name="location")
	private String location;
	
	@Column(name="ponum")
	private String ponum;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="provsite")
	private String provSite;
	
	@Column(name="provuser")
	private String provUser;
	
	@Column(name="provpass")
	private String provPass;
	
	@Column(name="status")
	private String status;
	
	@Column(name="account")
	private String account;
	
	@Column(name="frequency")
	private String frequency;
	
	@Column(name="strobe")
	private String strobe;
	
	@Column(name="type")
	private String type;
	
	//Constructors
	
	@Override
	public String toString()
	{
		return "PeriodicalsEntity[periodicalid="+ periodicalid +",description="+ description +",provider="+ provider +",amount="+ amount +",location="+ location +",locationNumber="+ locationNumber +",ponum="+ ponum +",notes="+ notes +",provSite="+ provSite +",provUser="+ provUser +",provPass="+ provPass +",status="+ status +",account="+ account +",frequency="+ frequency +",strobe="+ strobe +",type="+ type +"]";				
		
	}


	//Getters and setters
	

	public String getType() 
	{
		return type;
	}


	public void setType(String type) 
	{
		this.type=type;
	}


	public Long getPeriodicalid() 
	{
		return periodicalid;
	}



	public void setPeriodicalid(Long periodicalid) 
	{
		this.periodicalid=periodicalid;
	}



	public String getDescription() 
	{
		return description;
	}



	public void setDescription(String description) 
	{
		this.description=description;
	}



	public String getProvider() 
	{
		return provider;
	}



	public void setProvider(String provider) 
	{
		this.provider=provider;
	}


	public String getAmount() 
	{
		return amount;
	}



	public void setAmount(String amount) 
	{
		this.amount=amount;
	}



	public String getLocationNumber() 
	{
		return locationNumber;
	}



	public void setLocationNumber(String locationNumber) 
	{
		this.locationNumber=locationNumber;
	}



	public String getLocation() 
	{
		return location;
	}



	public void setLocation(String location) 
	{
		this.location=location;
	}



	public String getPonum() 
	{
		return ponum;
	}



	public void setPonum(String ponum) 
	{
		this.ponum=ponum;
	}



	public String getNotes() 
	{
		return notes;
	}



	public void setNotes(String notes) 
	{
		this.notes=notes;
	}



	public String getProvSite() 
	{
		return provSite;
	}



	public void setProvSite(String provSite) 
	{
		this.provSite=provSite;
	}



	public String getProvUser() 
	{
		return provUser;
	}



	public void setProvUser(String provUser) 
	{
		this.provUser=provUser;
	}



	public String getProvPass() 
	{
		return provPass;
	}



	public void setProvPass(String provPass) 
	{
		this.provPass=provPass;
	}


	public String getStatus() 
	{
		return status;
	}


	public void setStatus(String status) 
	{
		this.status=status;
	}


	public String getAccount() 
	{
		return account;
	}


	public void setAccount(String account) 
	{
		this.account=account;
	}


	public String getFrequency() 
	{
		return frequency;
	}


	public void setFrequency(String frequency) 
	{
		this.frequency=frequency;
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
