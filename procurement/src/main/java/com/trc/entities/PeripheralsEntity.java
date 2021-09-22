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
	
	//Constructors	
	
	@Override
	public String toString()
	{
		return "SettingsEntity[itemid="+ itemid +",description="+ description +",peripheralNum="+ peripheralNum +",assetId="+ assetId +",kluch="+ kluch +",notes="+ notes +",assetNumber="+ assetNumber +"]";				
		
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

	public void setAssetId(String assetId) 
	{
		this.assetId=assetId;
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
	
	
	
}
