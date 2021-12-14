package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="viServices")
public class ViServices 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long serviceid;
	
	@Column(name="hmisid")
	private String hmisId;
	
	@Column(name="counter")
	private int counter;
	
	@Column(name="kluch")
	private String kluch;
	
	//Constructors
	
	@Override
	public String toString()
	{
		return "VispdatsEntity[serviceid="+ serviceid +",hmisId="+ hmisId +",counter="+ counter +",kluch="+ kluch +"]";				
			
	}

	public Long getServiceid() 
	{
		return serviceid;
	}

	public void setServiceid(Long serviceid) 
	{
		this.serviceid=serviceid;
	}

	public String getHmisId() 
	{
		return hmisId;
	}

	public void setHmisId(String hmisId) 
	{
		this.hmisId=hmisId;
	}

	public int getCounter() 
	{
		return counter;
	}

	public void setCounter(int counter) 
	{
		this.counter=counter;
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
