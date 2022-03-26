package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.ProvidersEntity;
import com.trc.repositories.ProvidersRepository;
import com.trc.repositories.SitesRepository;


@Service
public class ProvidersService 
{
	@Autowired
	ProvidersRepository repository;
	
	@Autowired
	SitesRepository repositorySites;
	
	public List<ProvidersEntity> getAllProviders()
	{
		List<ProvidersEntity> result=(List<ProvidersEntity>) repository.findAll();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<ProvidersEntity>();
		
	}
	
	public List<ProvidersEntity> getAllProvidersListByCode()
	{
		List<ProvidersEntity> result=repository.getAllListByCode();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<ProvidersEntity>();
		
	}
	
	public List<ProvidersEntity> getAllProvidersListByName()
	{
		List<ProvidersEntity> result=repository.getAllListByName();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<ProvidersEntity>();
		
	}
	
	
	public ProvidersEntity getProviderById(Long id) throws RecordNotFoundException
	{
		Optional<ProvidersEntity> provider=repository.findById(id);
		
		if(provider.isPresent())
			return provider.get();
		else
			throw new RecordNotFoundException("No record exist for given id");
	}
	
	public ProvidersEntity createOrUpdate(ProvidersEntity entity)
	{
		if(entity.getProviderid()==null)
		{
			entity=repository.save(entity);
			
			return entity;
		}
		else
		{
			Optional<ProvidersEntity> provider=repository.findById(entity.getProviderid());
			
			if(provider.isPresent())
			{
				
				ProvidersEntity newEntity=provider.get();
				
				newEntity.setCode(entity.getCode());
				newEntity.setPname(entity.getPname());
				newEntity.setAccount(entity.getAccount());
				newEntity.setSiteNum(entity.getSiteNum());
				
				newEntity.setSite(entity.getSite());
				newEntity.setNotes(entity.getNotes());
				
				newEntity.setActive(entity.getActive());
				
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
	
	public void deleteProviderById(Long id) throws RecordNotFoundException
	{
		Optional<ProvidersEntity> provider=repository.findById(id);
		
		if(provider.isPresent())
			repository.deleteById(id);
		else
			throw new RecordNotFoundException("No Provider record exist for given id");
		
		
	}
	
	public String getSiteBySiteNumber(String siteNumber)
	{
		String site=repositorySites.getLocationName(siteNumber);
		
		return site;
		
	}
	
	public void updateSite(String site, Long id)
	{
		repository.updateLocationName(id,site);
		
				
	}
	

}
