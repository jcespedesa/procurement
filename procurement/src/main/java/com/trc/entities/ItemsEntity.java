package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="items")
public class ItemsEntity 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long itemid;
	
	@Column(name="item")
	private String item;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="klass")
	private String klass;
	
	@Column(name="hhsform")
	private String hhsForm;
	
	@Column(name="itemnumber")
	private String itemNumber;
		
	@Override
	public String toString()
	{
		return "ItemsEntity[itemid="+ itemid +",item="+ item +",notes="+ notes +",klass="+ klass +",hhsForm="+ hhsForm +",itemNumber="+ itemNumber +"]";				
		
	}

	//Setters and getters
	
	public Long getItemid() 
	{
		return itemid;
	}


	public void setItemid(Long itemid) 
	{
		this.itemid=itemid;
	}


	public String getItem() 
	{
		return item;
	}


	public void setItem(String item) 
	{
		this.item=item;
	}


	public String getNotes() 
	{
		return notes;
	}


	public void setNotes(String notes) 
	{
		this.notes=notes;
	}

	public String getKlass() 
	{
		return klass;
	}

	public void setKlass(String klass) 
	{
		this.klass=klass;
	}

	public String getHhsForm() 
	{
		return hhsForm;
	}

	public void setHhsForm(String hhsForm) 
	{
		this.hhsForm=hhsForm;
	}

	public String getItemNumber() 
	{
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) 
	{
		this.itemNumber=itemNumber;
	}
	
	
	
}
