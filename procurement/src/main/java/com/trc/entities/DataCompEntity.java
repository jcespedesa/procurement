package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="datacomps")
public class DataCompEntity 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long dataid;
	
	@Column(name="date")
	private String date;
	
	@Column(name="realdate")
	private String realDate;
	
	@Column(name="hmis")
	private String hmis;
	
	@Column(name="bedlist")
	private String bedList;
	
	@Column(name="hmax")
	private String hmax;
	
	@Override
	public String toString()
	{
		return "DataCompEntity[dataid="+ dataid +",date="+ date +",realDate="+ realDate +",hmis="+ hmis +",bedList="+ bedList +",hmax="+ hmax +"]";				
		
	}

	public Long getDataid() 
	{
		return dataid;
	}

	public void setDataid(Long dataid) 
	{
		this.dataid=dataid;
	}

	public String getDate() 
	{
		return date;
	}

	public void setDate(String date) 
	{
		this.date=date;
	}
	
	

	public String getRealDate() 
	{
		return realDate;
	}

	public void setRealDate(String realDate) 
	{
		this.realDate=realDate;
	}

	public String getHmis() 
	{
		return hmis;
	}

	public void setHmis(String hmis) 
	{
		this.hmis=hmis;
	}

	public String getBedList() 
	{
		return bedList;
	}

	public void setBedList(String bedList) 
	{
		this.bedList=bedList;
	}

	public String getHmax() 
	{
		return hmax;
	}

	public void setHmax(String hmax) 
	{
		this.hmax=hmax;
	}
	
	
	
}
