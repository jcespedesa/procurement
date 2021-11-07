package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="testsites")
public class TestSitesEntity 
{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long itemid;
	
	@Column(name="sname")
	private String sname;
	
	@Column(name="address")
	private String address;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="active")
	private String active;
	
	@Column(name="division")
	private String division;
	
	@Override
	public String toString()
	{
		return "TestSitesEntity[itemid="+ itemid +",sname="+ sname +",address="+ address +",phone="+ phone +",active="+ active +",division="+ division +"]";				
		
	}

	public Long getItemid() 
	{
		return itemid;
	}

	public void setItemid(Long itemid) 
	{
		this.itemid = itemid;
	}

	public String getSname() 
	{
		return sname;
	}

	public void setSname(String sname) 
	{
		this.sname=sname;
	}

	public String getAddress() 
	{
		return address;
	}

	public void setAddress(String address) 
	{
		this.address=address;
	}

	public String getPhone() 
	{
		return phone;
	}

	public void setPhone(String phone) 
	{
		this.phone=phone;
	}

	public String getActive() 
	{
		return active;
	}

	public void setActive(String active) 
	{
		this.active=active;
	}

	public String getDivision() 
	{
		return division;
	}

	public void setDivision(String division) 
	{
		this.division=division;
	}

	
}
