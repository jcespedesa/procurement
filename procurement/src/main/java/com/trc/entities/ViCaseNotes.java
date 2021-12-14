package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="viCaseNotes")
public class ViCaseNotes 
{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long casenoteid;
	
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
		return "VispdatsEntity[casenoteid="+ casenoteid +",hmisId="+ hmisId +",counter="+ counter +",kluch="+ kluch +"]";				
			
	}

	public Long getCasenoteid() 
	{
		return casenoteid;
	}

	public void setCasenoteid(Long casenoteid) 
	{
		this.casenoteid=casenoteid;
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
