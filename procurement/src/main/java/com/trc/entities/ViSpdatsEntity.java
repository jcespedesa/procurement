package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="viSpdats")
public class ViSpdatsEntity 
{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long clientid;
	
	@Column(name="hmisid")
	private String hmisId;
	
	@Column(name="cname")
	private String cname;
	
	@Column(name="counter")
	private int counter;
	
	@Column(name="kluch")
	private String kluch;
	
	//Constructors
	
	@Override
	public String toString()
	{
		return "VispdatsEntity[clientid="+ clientid +",hmisId="+ hmisId +",cname="+ cname +",counter="+ counter +",kluch="+ kluch +"]";				
			
	}

	public Long getClientid() 
	{
		return clientid;
	}

	public void setClientid(Long clientid) 
	{
		this.clientid=clientid;
	}

	public String getHmisId() 
	{
		return hmisId;
	}

	public void setHmisId(String hmisId) 
	{
		this.hmisId=hmisId;
	}

	public String getCname() 
	{
		return cname;
	}

	public void setCname(String cname) 
	{
		this.cname=cname;
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
