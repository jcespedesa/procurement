package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.PeripheralsEntity;
import com.trc.repositories.PeripheralsRepository;


@Service
public class PeripheralsService 
{
	@Autowired
	PeripheralsRepository repository;
	
	public List<PeripheralsEntity> getAllPeripherals()
	{
		List<PeripheralsEntity> result=(List<PeripheralsEntity>) repository.findAll();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<PeripheralsEntity>();
		
	}
	
	public PeripheralsEntity getPeripheralById(Long id) throws RecordNotFoundException
	{
		Optional<PeripheralsEntity> peripheral=repository.findById(id);
		
		if(peripheral.isPresent())
			return peripheral.get();
		else
			throw new RecordNotFoundException("No record exist for given id");
	}
	
	public PeripheralsEntity createOrUpdate(PeripheralsEntity entity)
	{
		if(entity.getItemid()==null)
		{
			entity=repository.save(entity);
			
			return entity;
		}
		else
		{
			Optional<PeripheralsEntity> peripheral=repository.findById(entity.getItemid());
			
			if(peripheral.isPresent())
			{
				//Setting variables to update real date quote
				Long localId=entity.getItemid();
				String localDatePurchased=entity.getDatePurchased();
				
				
				PeripheralsEntity newEntity=peripheral.get();
				
				newEntity.setDescription(entity.getDescription());
				newEntity.setPeripheralNum(entity.getPeripheralNum());
				newEntity.setAssetId(entity.getAssetId());
				newEntity.setAssetNumber(entity.getAssetNumber ());
				newEntity.setKluch(entity.getKluch());
				newEntity.setNotes(entity.getNotes());
				newEntity.setDatePurchased(entity.getDatePurchased());
				newEntity.setAge(entity.getAge());
				newEntity.setStatus(entity.getStatus());
				newEntity.setActive(entity.getActive());
					
				newEntity=repository.save(newEntity);
				
				//Updating local date 
				repository.setDatePurchased(localId,localDatePurchased);
				
				return newEntity;
				
			}
			else
			{
				entity=repository.save(entity);
				
				return entity;
				
			}
			
		}
				
		
	}
	
	public void deletePeripheralById(Long id) throws RecordNotFoundException
	{
		Optional<PeripheralsEntity> peripheral=repository.findById(id);
		
		if(peripheral.isPresent())
			repository.deleteById(id);
		else
			throw new RecordNotFoundException("No Peripheral record exist for given id");
		
		
	}
	
	public List<PeripheralsEntity> getByKluch(String kluch)
	{
		List<PeripheralsEntity> result=repository.getAllByKluch(kluch);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<PeripheralsEntity>();
		
	}
	
	public List<PeripheralsEntity> getByAssetId(String assetId)
	{
		List<PeripheralsEntity> result=repository.getAllByAssetId(assetId);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<PeripheralsEntity>();
		
	}
	
	public List<PeripheralsEntity> getByProject(String project)
	{
		List<PeripheralsEntity> result=repository.getByProject(project);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<PeripheralsEntity>();
		
	}
	
		
}
