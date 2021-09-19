package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="phones911Locations")
public class PhoneAddress 
{
	@Id
	@GeneratedValue
	private Long phoneid;
	
	@Column(name="phonenumber")
	private String phoneNumber;
	
	@Column(name="streetnum")
	private String streetNum;
	
	@Column(name="suitenum")
	private String suiteNum;
	
	@Column(name="streetname")
	private String streetName;
	
	@Column(name="city")
	private String city;
	
	@Column(name="state")
	private String state;
	
	@Column(name="zip")
	private String zip;
	
	@Column(name="vonagekey")
	private String vonageKey;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="month")
	private String month;
	
	@Column(name="year")
	private String year;
	
	@Column(name="customer")
	private String customer;
	
	@Column(name="errorcode")
	private String errorCode;
	
	@Column(name="status")
	private String status;
	
	@Column(name="sitenumber")
	private String siteNumber;
	
	@Column(name="site")
	private String site;
	
	@Column(name="extension")
	private String extension;
	
	@Column(name="klass")
	private String klass;
	
	//constructors
	
	public PhoneAddress()
	{
		
	}
	
	public PhoneAddress(Long phoneid, String phoneNumber, String streetNum, String suiteNum, String streetName, String city, String state, String zip, String vonageKey, String notes, String month, String year, String errorCode, String customer, String status, String siteNumber, String site,String extension, String klass) 
	{
		super();
		
		this.phoneid=phoneid;
		this.phoneNumber=phoneNumber;
		this.streetNum=streetNum;
		this.suiteNum=suiteNum;
		this.streetName=streetName;
		this.city=city;
		this.state=state;
		this.zip=zip;
		this.vonageKey=vonageKey;
		this.notes=notes;
		this.month=month;
		this.year=year;
		this.customer=customer;
		this.errorCode=errorCode;
		this.status=status;
		this.siteNumber=siteNumber;
		this.site=site;
		this.extension=extension;
		this.klass=klass;
	}

	//Setters and getters
	
	public Long getPhoneid() 
	{
		return phoneid;
	}

	public void setPhoneid(Long phoneid) 
	{
		this.phoneid=phoneid;
	}

	public String getPhoneNumber() 
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) 
	{
		this.phoneNumber=phoneNumber;
	}

	public String getStreetNum() 
	{
		return streetNum;
	}

	public void setStreetNum(String streetNum)
	{
		this.streetNum=streetNum;
	}

	public String getSuiteNum() 
	{
		return suiteNum;
	}

	public void setSuiteNum(String suiteNum) 
	{
		this.suiteNum=suiteNum;
	}

	public String getStreetName() 
	{
		return streetName;
	}

	public void setStreetName(String streetName) 
	{
		this.streetName=streetName;
	}

	public String getCity() 
	{
		return city;
	}

	public void setCity(String city) 
	{
		this.city=city;
	}

	public String getState() 
	{
		return state;
	}

	public void setState(String state) 
	{
		this.state=state;
	}

	public String getZip() 
	{
		return zip;
	}

	public void setZip(String zip) 
	{
		this.zip=zip;
	}

	public String getVonageKey() 
	{
		return vonageKey;
	}

	public void setVonageKey(String vonageKey) 
	{
		this.vonageKey=vonageKey;
	}

	public String getNotes() 
	{
		return notes;
	}

	public void setNotes(String notes) 
	{
		this.notes=notes;
	}

	public String getMonth() 
	{
		return month;
	}

	public void setMonth(String month) 
	{
		this.month=month;
	}

	public String getYear() 
	{
		return year;
	}

	public void setYear(String year) 
	{
		this.year=year;
	}

	public String getCustomer() 
	{
		return customer;
	}

	public void setCustomer(String customer) 
	{
		this.customer=customer;
	}

	public String getErrorCode() 
	{
		return errorCode;
	}

	public void setErrorCode(String errorCode) 
	{
		this.errorCode=errorCode;
	}

	public String getStatus() 
	{
		return status;
	}

	public void setStatus(String status) 
	{
		this.status=status;
	}

	public String getSiteNumber() 
	{
		return siteNumber;
	}

	public void setSiteNumber(String siteNumber) 
	{
		this.siteNumber=siteNumber;
	}

	public String getSite() 
	{
		return site;
	}

	public void setSite(String site) 
	{
		this.site=site;
	}

	public String getExtension() 
	{
		return extension;
	}

	public void setExtension(String extension) 
	{
		this.extension=extension;
	}

	public String getKlass() 
	{
		return klass;
	}

	public void setKlass(String klass) 
	{
		this.klass=klass;
	}
	
	
}
