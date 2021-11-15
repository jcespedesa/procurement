package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bedlistshmis")
public class BedListsEntityHMIS 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long itemid;
	
	@Column(name="floor")
	private String floor;
	
	@Column(name="room")
	private String room;
	
	@Column(name="bed")
	private String bed;
	
	@Column(name="cname")
	private String cname;
	
	@Column(name="groupid")
	private String groupId;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="dob")
	private String dob;
	
	@Column(name="ssn")
	private String ssn;
	
	@Column(name="checkedin")
	private String checkedIn;
	
	@Column(name="checkedout")
	private String checkedOut;
	
	@Column(name="status")
	private String status;
	
	@Column(name="screeningdate")
	private String screeningDate;
	
	@Column(name="issac")
	private String issac;
	
	@Column(name="kluch")
	private String kluch;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="hhscname")
	private String hhsCname;
	
	@Override
	public String toString()
	{
		return "BedListsEntity[itemid="+ itemid +",floor="+ floor +",room="+ room +",bed="+ bed +",cname="+ cname +",groupId="+ groupId +",gender="+ gender +",dob="+ dob +",ssn="+ ssn +",checkedId="+ checkedIn +",checkedOut="+ checkedOut +",status="+ status +",screeningDate="+ screeningDate +",issac="+ issac +",kluch="+ kluch +",notes+"+ notes +",hhsCname="+ hhsCname +"]";				
		
	}
	
	public Long getItemid() 
	{
		return itemid;
	}

	public void setItemid(Long itemid) 
	{
		this.itemid=itemid;
	}

	public String getFloor() 
	{
		return floor;
	}

	public void setFloor(String floor) 
	{
		this.floor=floor;
	}

	public String getRoom() 
	{
		return room;
	}

	public void setRoom(String room) 
	{
		this.room=room;
	}

	public String getBed() 
	{
		return bed;
	}

	public void setBed(String bed) 
	{
		this.bed=bed;
	}

	public String getCname() 
	{
		return cname;
	}

	public void setCname(String cname) 
	{
		this.cname=cname;
	}

	public String getGroupId() 
	{
		return groupId;
	}

	public void setGroupId(String groupId) 
	{
		this.groupId=groupId;
	}

	public String getGender() 
	{
		return gender;
	}

	public void setGender(String gender) 
	{
		this.gender=gender;
	}

	public String getDob() 
	{
		return dob;
	}

	public void setDob(String dob) 
	{
		this.dob=dob;
	}

	public String getSsn() 
	{
		return ssn;
	}

	public void setSsn(String ssn) 
	{
		this.ssn=ssn;
	}

	public String getCheckedIn() 
	{
		return checkedIn;
	}

	public void setCheckedIn(String checkedIn) 
	{
		this.checkedIn=checkedIn;
	}

	public String getCheckedOut() 
	{
		return checkedOut;
	}

	public void setCheckedOut(String checkedOut) 
	{
		this.checkedOut=checkedOut;
	}

	public String getStatus() 
	{
		return status;
	}

	public void setStatus(String status) 
	{
		this.status=status;
	}

	public String getScreeningDate() 
	{
		return screeningDate;
	}

	public void setScreeningDate(String screeningDate) 
	{
		this.screeningDate=screeningDate;
	}

	public String getIssac() 
	{
		return issac;
	}

	public void setIssac(String issac) 
	{
		this.issac=issac;
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

	public String getHhsCname() 
	{
		return hhsCname;
	}

	public void setHhsCname(String hhsCname) 
	{
		this.hhsCname = hhsCname;
	}
	
	
}
