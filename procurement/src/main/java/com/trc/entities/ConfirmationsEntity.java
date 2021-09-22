package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="confirmations")
public class ConfirmationsEntity 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long confirmid;
	
	@Column(name="type")
	private String type;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="author")
	private String author;
	
	@Column(name="datecreation")
	private String dateCreation;
	
	@Column(name="kluch")
	private String kluch;
	
	@Column(name="email")
	private String email;
	
	
	@Override
	public String toString()
	{
		return "ConfirmationsEntity[confirmid="+ confirmid +",type="+ type +",notes="+ notes +",author="+ author +",dateCreation="+ dateCreation +",kluch="+ kluch +",email="+ email +"]";				
		
	}


	public Long getConfirmid() 
	{
		return confirmid;
	}


	public void setConfirmid(Long confirmid) 
	{
		this.confirmid=confirmid;
	}


	public String getType() 
	{
		return type;
	}


	public void setType(String type) 
	{
		this.type=type;
	}


	public String getNotes() 
	{
		return notes;
	}


	public void setNotes(String notes) 
	{
		this.notes=notes;
	}


	public String getAuthor() 
	{
		return author;
	}


	public void setAuthor(String author) 
	{
		this.author=author;
	}


	public String getDateCreation() 
	{
		return dateCreation;
	}


	public void setDateCreation(String dateCreation) 
	{
		this.dateCreation=dateCreation;
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
		
	
}
