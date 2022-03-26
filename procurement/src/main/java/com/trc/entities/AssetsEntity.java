package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="assets")
public class AssetsEntity 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long assetid;
	
	@Column(name="item")
	private String item;
	
	@Column(name="assetnumber")
	private String assetNumber;
	
	@Column(name="maker")
	private String maker;
	
	@Column(name="model")
	private String model;
	
	@Column(name="datepurchased")
	private String datePurchased;
	
	@Column(name="clientid")
	private String clientId;
	
	@Column(name="username")
	private String username;
	
	@Column(name="title")
	private String title;
	
	@Column(name="empstatus")
	private String empStatus;
	
	@Column(name="division")
	private String division;
	
	@Column(name="site")
	private String site;
	
	@Column(name="active")
	private String active;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="realdatepurchased")
	private String realDatePurchased;
	
	@Column(name="project")
	private String project;
	
	@Column(name="strobe")
	private String strobe;
	
	@Column(name="authorid")
	private String authorId;
	
	@Column(name="author")
	private String author;
	
	@Column(name="authoremail")
	private String authorEmail;
	
	@Column(name="kluch")
	private String kluch;
	
	@Column(name="email")
	private String email;
	
	@Column(name="program")
	private String program;
	
	@Column(name="klass")
	private String klass;
	
	@Column(name="datecreation",updatable=false, insertable=false)
	private String dateCreation;
	
	@Column(name="age")
	private String age;
	
	@Column(name="status")
	private String status;

	@Override
	public String toString()
	{
		return "AssetsEntity[assetid="+ assetid +",item="+ item +",assetNumber="+ assetNumber +",maker="+ maker +",model="+ model +",datePurchased="+ datePurchased +",username="+ username +",title="+ title +",empStatus="+ empStatus +",division="+ division +",site="+ site +",active="+ active +",notes="+ notes +",realDatePurchased="+ realDatePurchased +",project="+ project +",strobe="+ strobe +",author="+ author +",authorEmail="+ authorEmail +",kluch="+ kluch +",email="+ email +",program="+ program +",klass="+ klass +",dateCreation="+ dateCreation +",age="+ age +",status="+ status +",clientId="+ clientId +",authorId="+ authorId +"]";				
		
	}
	
	
	public Long getAssetid() 
	{
		return assetid;
	}

	public void setAssetid(Long assetid) 
	{
		this.assetid=assetid;
	}

	public String getItem() 
	{
		return item;
	}

	public void setItem(String item) 
	{
		this.item=item;
	}

	public String getAssetNumber() 
	{
		return assetNumber;
	}

	public void setAssetNumber(String assetNumber) 
	{
		this.assetNumber=assetNumber;
	}

	public String getMaker() 
	{
		return maker;
	}

	public void setMaker(String maker) {
		this.maker=maker;
	}

	public String getModel() 
	{
		return model;
	}

	public void setModel(String model) 
	{
		this.model=model;
	}

	public String getDatePurchased() 
	{
		return datePurchased;
	}

	public void setDatePurchased(String datePurchased) 
	{
		this.datePurchased=datePurchased;
	}

	public String getUsername() 
	{
		return username;
	}

	public void setUsername(String username) {
		this.username=username;
	}

	public String getTitle() 
	{
		return title;
	}

	public void setTitle(String title) 
	{
		this.title=title;
	}

	public String getDivision() 
	{
		return division;
	}

	public void setDivision(String division) 
	{
		this.division=division;
	}

	public String getSite() 
	{
		return site;
	}

	public void setSite(String site) 
	{
		this.site=site;
	}

	public String getActive() 
	{
		return active;
	}

	public void setActive(String active) 
	{
		this.active=active;
	}

	public String getNotes() 
	{
		return notes;
	}

	public void setNotes(String notes) 
	{
		this.notes=notes;
	}

	public String getRealDatePurchased() 
	{
		return realDatePurchased;
	}

	public void setRealDatePurchased(String realDatePurchased) 
	{
		this.realDatePurchased=realDatePurchased;
	}

	public String getProject() 
	{
		return project;
	}

	public void setProject(String project) 
	{
		this.project=project;
	}

	public String getStrobe() 
	{
		return strobe;
	}

	public void setStrobe(String strobe) 
	{
		this.strobe=strobe;
	}

	public String getAuthor() 
	{
		return author;
	}

	public void setAuthor(String author) 
	{
		this.author=author;
	}

	public String getKluch() 
	{
		return kluch;
	}

	public void setKluch(String kluch) 
	{
		this.kluch=kluch;
	}

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email=email;
	}

	public String getProgram() 
	{
		return program;
	}

	public void setProgram(String program) 
	{
		this.program=program;
	}

	public String getKlass() 
	{
		return klass;
	}

	public void setKlass(String klass) 
	{
		this.klass=klass;
	}

	public String getAuthorEmail() 
	{
		return authorEmail;
	}

	public void setAuthorEmail(String authorEmail) 
	{
		this.authorEmail=authorEmail;
	}

	public String getEmpStatus() 
	{
		return empStatus;
	}

	public void setEmpStatus(String empStatus) 
	{
		this.empStatus=empStatus;
	}

	public String getDateCreation() 
	{
		return dateCreation;
	}

	public void setDateCreation(String dateCreation) 
	{
		this.dateCreation=dateCreation;
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


	public String getClientId() 
	{
		return clientId;
	}


	public void setClientId(String clientId) 
	{
		this.clientId=clientId;
	}


	public String getAuthorId() 
	{
		return authorId;
	}


	public void setAuthorId(String authorId) 
	{
		this.authorId=authorId;
	}
	
	
		
}
