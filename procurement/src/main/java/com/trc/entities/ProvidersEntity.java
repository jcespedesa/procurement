package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="providers")
public class ProvidersEntity 
{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long providerid;
	
	@Column(name="code")
	private String code;
	
	@Column(name="pname")
	private String pname;
	
	@Column(name="account")
	private String account;
	
	@Column(name="sitenum")
	private String siteNum;
	
	@Column(name="site")
	private String site;
	
	@Column(name="notes")
	private String notes;
			
	@Column(name="active")
	private String active;
	
	@Override
	public String toString()
	{
		return "ProvidersEntity[providerid="+ providerid +",code="+ code +",pname="+ pname +",account="+ account +",siteNum="+ siteNum +",site="+ site +",notes="+ notes +",active="+ active +"]";				
		
	}

	public Long getProviderid() 
	{
		return providerid;
	}

	public void setProviderid(Long providerid) 
	{
		this.providerid=providerid;
	}

	public String getCode() 
	{
		return code;
	}

	public void setCode(String code) 
	{
		this.code=code;
	}

	public String getPname() 
	{
		return pname;
	}

	public void setPname(String pname) 
	{
		this.pname=pname;
	}

	public String getAccount() 
	{
		return account;
	}

	public void setAccount(String account) 
	{
		this.account=account;
	}

	public String getSiteNum() 
	{
		return siteNum;
	}

	public void setSiteNum(String siteNum) 
	{
		this.siteNum=siteNum;
	}

	public String getSite() 
	{
		return site;
	}

	public void setSite(String site) 
	{
		this.site=site;
	}

	public String getNotes() 
	{
		return notes;
	}

	public void setNotes(String notes) 
	{
		this.notes=notes;
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
