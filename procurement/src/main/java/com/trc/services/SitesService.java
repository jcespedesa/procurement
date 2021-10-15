package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.SitesEntity;
import com.trc.repositories.SitesRepository;

@Service
public class SitesService 
{
	@Autowired
	SitesRepository repository;
	
	public List<SitesEntity> getAllSites()
	{
		List<SitesEntity> result=(List<SitesEntity>) repository.findAll();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<SitesEntity>();
		
	}
	
	
	public List<SitesEntity> getAllHHSsites()
	{
		List<SitesEntity> result=(List<SitesEntity>) repository.findAllHHSsites();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<SitesEntity>();
		
	}
	
		
	public List<SitesEntity> getAllByName()
	{
		List<SitesEntity> result=(List<SitesEntity>) repository.getAllByName();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<SitesEntity>();
		
	}
	
	public SitesEntity getSiteById(Long id) throws RecordNotFoundException
	{
		Optional<SitesEntity> site=repository.findById(id);
		
		if(site.isPresent())
			return site.get();
		else
			throw new RecordNotFoundException("No record exist for given id");
	}
	
	public SitesEntity createOrUpdate(SitesEntity entity)
	{
		if(entity.getSiteid()==null)
		{
			entity=repository.save(entity);
			
			return entity;
		}
		else
		{
			Optional<SitesEntity> site=repository.findById(entity.getSiteid());
			
			if(site.isPresent())
			{
				
				SitesEntity newEntity=site.get();
				
				newEntity.setSiteNumber(entity.getSiteNumber());
				newEntity.setSname(entity.getSname());
				newEntity.setAddress(entity.getAddress());
				newEntity.setPhone(entity.getPhone());
				
				newEntity.setProvider1(entity.getProvider1());
				newEntity.setAccount1(entity.getAccount1());
				
				newEntity.setProvider2(entity.getProvider2());
				newEntity.setAccount2(entity.getAccount2());
				
				newEntity.setProvider3(entity.getProvider3());
				newEntity.setAccount3(entity.getAccount3());
				
				newEntity.setProvider4(entity.getProvider4());
				newEntity.setAccount4(entity.getAccount4());
				
				newEntity.setActive(entity.getActive());
				newEntity.setDivision(entity.getDivision());
				
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
	
	public void deleteSiteById(Long id) throws RecordNotFoundException
	{
		Optional<SitesEntity> site=repository.findById(id);
		
		if(site.isPresent())
			repository.deleteById(id);
		else
			throw new RecordNotFoundException("No Site record exist for given id");
		
		
	}
	
	public String getSiteByNumber(String siteNumber)
	{
		String site=repository.getLocationName(siteNumber);
		
		if(site==null)
			return "";
		else
			return site;
		
	}
	
	public List<SitesEntity> searchByDivision(String stringSearch)
	{
		List<SitesEntity> result=(List<SitesEntity>) repository.searchByDivision(stringSearch);
		
		return result;
		
	}
	
}
