package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.PhoneAddress;
import com.trc.repositories.PhonesRepository;
import com.trc.repositories.SitesRepository;


@Service
public class PhoneServices 
{

	@Autowired
	PhonesRepository repository;
	
	@Autowired
	SitesRepository repositorySites;
	
	
	public List<PhoneAddress> getAllPhones()
	{
		List<PhoneAddress> result=(List<PhoneAddress>) repository.getAllByPhoneName();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<PhoneAddress>();
		
	}
	
		
	public PhoneAddress getPhoneById(Long id) throws RecordNotFoundException
	{
		Optional<PhoneAddress> phone=repository.findById(id);
		
		if(phone.isPresent())
			return phone.get();
		else
			throw new RecordNotFoundException("No record exist for given id");
	}
	
	public PhoneAddress createOrUpdate(PhoneAddress entity)
	{
		if(entity.getPhoneid()==null)
		{
			entity=repository.save(entity);
			
			return entity;
		}
		else
		{
			Optional<PhoneAddress> phone=repository.findById(entity.getPhoneid());
			
			if(phone.isPresent())
			{
				
				PhoneAddress newEntity=phone.get();
				
				newEntity.setPhoneNumber(entity.getPhoneNumber());
				newEntity.setStreetNum(entity.getStreetNum());
				newEntity.setSuiteNum(entity.getSuiteNum());
				newEntity.setStreetName(entity.getStreetName());
				newEntity.setCity(entity.getCity());
				newEntity.setState(entity.getState());
				newEntity.setZip(entity.getZip());
				newEntity.setVonageKey(entity.getVonageKey());
				
				newEntity.setNotes(entity.getNotes());
				newEntity.setStatus(entity.getStatus());
				newEntity.setSiteNumber(entity.getSiteNumber());
				
				newEntity.setExtension(entity.getExtension());
				newEntity.setKlass(entity.getKlass());
				
				newEntity=repository.save(newEntity);
				
				return newEntity;
				
			}
			else
			{
				entity=repository.save(entity);
				
				return entity;
				
			}
			
		}
				
		
	}
	
	public void deletePhoneById(Long id) throws RecordNotFoundException
	{
		Optional<PhoneAddress> phone=repository.findById(id);
		
		if(phone.isPresent())
			repository.deleteById(id);
		else
			throw new RecordNotFoundException("No Project record exist for given id");
		
		
	}
	
	public PhoneAddress getPhoneByPhoneNumber1(String phoneNumber)
	{
		PhoneAddress phone=repository.getByPhoneNumber(phoneNumber);
		
		return phone;
		
	}
	
	public List<PhoneAddress> getByPhoneNum(String stringSearch)
	{
		List<PhoneAddress> result=(List<PhoneAddress>) repository.getByPhoneNum(stringSearch);
		
		//System.out.println("Inside the service to search by string. Object is: "+ stringSearch);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<PhoneAddress>();
		
	}
	
	public List<PhoneAddress> getBySiteNum(String stringSearch)
	{
		List<PhoneAddress> result=(List<PhoneAddress>) repository.getBySiteNum(stringSearch);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<PhoneAddress>();
		
	}
	
	public List<String> findDistincStatuses()
	{
		List<String> result=(List<String>) repository.findDistStatuses();
		
	
		return result;
		
	}
	
	public List<String> findDistincKlasses()
	{
		List<String> result=(List<String>) repository.findDistKlasses();
		
	
		return result;
		
	}
	
	public List<PhoneAddress> getByStatus(String stringSearch)
	{
		List<PhoneAddress> result=(List<PhoneAddress>) repository.getByStatus(stringSearch);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<PhoneAddress>();
		
	}
	
	public List<PhoneAddress> getByKlass(String stringSearch)
	{
		List<PhoneAddress> result=(List<PhoneAddress>) repository.getByKlass(stringSearch);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<PhoneAddress>();
		
	}
	
	public List<PhoneAddress> getSiteNumKlass(String siteNum,String klass)
	{
		List<PhoneAddress> result=(List<PhoneAddress>) repository.getSiteNumKlass(siteNum,klass);
		
		
		//System.out.println("Selected site is: "+ siteNum +" and selected status was "+ klass);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<PhoneAddress>();
		
	}
	
	public String getSiteNameBySiteNum(String siteNum)
	{
		String siteName=(String) repositorySites.getLocationName(siteNum);
		
		//System.out.println("Selected site is: "+ siteNum);
		
		return siteName;
		
	}
	
	public List<PhoneAddress> getSiteNumStatus(String siteNum,String status)
	{
		List<PhoneAddress> result=(List<PhoneAddress>) repository.getSiteNumStatus(siteNum,status);
		
		
		//System.out.println("Selected site is: "+ siteNum +" and selected status was "+ status);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<PhoneAddress>();
		
	}
	
	
	public PhoneAddress getPhoneByExtension(String phoneNumber)
	{
		PhoneAddress phone=repository.getByPhoneNumber(phoneNumber);
		
		return phone;
		
	}
	
	public List<PhoneAddress> getByExtension(String stringSearch)
	{
		List<PhoneAddress> result=(List<PhoneAddress>) repository.getByExtension(stringSearch);
		
		//System.out.println("Inside the service to search by string. Object is: "+ stringSearch);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<PhoneAddress>();
		
	}
	
}
