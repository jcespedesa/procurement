package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="peripherals")
public class PeripheralsEntity 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long itemid;
	
		
	@Column(name="description")
	private String description;
	
	@Column(name="peripheralnum")
	private String peripheralNum;
	
	@Column(name="assetid")
	private String assetId;
	
	@Column(name="kluch")
	private String kluch;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="assetnumber")
	private String assetNumber;
	
	@Column(name="datepurchased")
	private String datePurchased;
	
	@Column(name="realdatepurchased")
	private String realDatePurchased;
	
	@Column(name="age")
	private String age;
	
	@Column(name="status")
	private String status;
	
	@Column(name="active")
	private String active;
	
	//Constructors	
	
	@Override
	public String toString()
	{
		return "SettingsEntity[itemid="+ itemid +",description="+ description +",peripheralNum="+ peripheralNum +",assetId="+ assetId +",kluch="+ kluch +",notes="+ notes +",assetNumber="+ assetNumber +",datePurchased="+ datePurchased +",realDatePurchased="+ realDatePurchased +",age="+ age +",active="+ active +",status="+ status +"]";				
		
	}

	public Long getItemid() 
	{
		return itemid;
	}

	public void setItemid(Long itemid) 
	{
		this.itemid=itemid;
	}

	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description) 
	{
		this.description=description;
	}

	public String getPeripheralNum() 
	{
		return peripheralNum;
	}

	public void setPeripheralNum(String peripheralNum) 
	{
		this.peripheralNum=peripheralNum;
	}

	public String getAssetId() 
	{
		return assetId;
	}

	public void setAssetId(String assetId2) 
	{
		this.assetId=assetId2;
	}

	public String getKluch() 
	{
		return kluch;
	}

	public void setKluch(String kluch) 
	{
		this.kluch=kluch;
	}

	public String getNotes() 
	{
		return notes;
	}

	public void setNotes(String notes) 
	{
		this.notes=notes;
	}

	public String getAssetNumber() 
	{
		return assetNumber;
	}

	public void setAssetNumber(String assetNumber) 
	{
		this.assetNumber=assetNumber;
	}

	public String getDatePurchased() 
	{
		return datePurchased;
	}

	public void setDatePurchased(String datePurchased) 
	{
		this.datePurchased=datePurchased;
	}

	public String getRealDatePurchased() 
	{
		return realDatePurchased;
	}

	public void setRealDatePurchased(String realDatePurchased) 
	{
		this.realDatePurchased=realDatePurchased;
	}

	public String getAge() 
	{
		return age;
	}

	public void setAge(String age) 
	{
		this.age=age;
	}

	public String getStatus() 
	{
		return status;
	}

	public void setStatus(String status) 
	{
		this.status=status;
	}

	public String getActive() 
	{
		return active;
	}

	public void setActive(String active) 
	{
		this.active=active;
	}
		
	
}
