package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.HhsDivisionsEntity;
import com.trc.repositories.HhsDivisionsRepository;

@Service
public class HhsDivisionsService 
{
	@Autowired
	HhsDivisionsRepository repository;
	
	public List<HhsDivisionsEntity> getAllDivisions()
	{
		List<HhsDivisionsEntity> result=(List<HhsDivisionsEntity>) repository.findAll();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<HhsDivisionsEntity>();
		
	}
	
	public List<HhsDivisionsEntity> getAllByName()
	{
		List<HhsDivisionsEntity> result=(List<HhsDivisionsEntity>) repository.getAllByName();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<HhsDivisionsEntity>();
		
	}
	
	public HhsDivisionsEntity getDivisionById(Long id) throws RecordNotFoundException
	{
		Optional<HhsDivisionsEntity> division=repository.findById(id);
		
		if(division.isPresent())
			return division.get();
		else
			throw new RecordNotFoundException("No record exist for given id");
	}
	
	public HhsDivisionsEntity createOrUpdate(HhsDivisionsEntity entity)
	{
		if(entity.getDivisionid()==null)
		{
			entity=repository.save(entity);
			
			return entity;
		}
		else
		{
			Optional<HhsDivisionsEntity> division=repository.findById(entity.getDivisionid());
			
			if(division.isPresent())
			{
				
				HhsDivisionsEntity newEntity=division.get();
				
				newEntity.setDivision(entity.getDivision());
				newEntity.setDivisionNumber(entity.getDivisionNumber());
				
				newEntity.setDirector(entity.getDirector());
				newEntity.setClientId(entity.getClientId());
								
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
	
	public void deleteDivisionById(Long id) throws RecordNotFoundException
	{
		Optional<HhsDivisionsEntity> division=repository.findById(id);
		
		if(division.isPresent())
			repository.deleteById(id);
		else
			throw new RecordNotFoundException("No Division record exist for given id");
		
		
	}
	
	public String getDivisionByNumber(String dnumber)
	{
		String division=repository.getDivisionName(dnumber);
		
		if(division==null)
			return "";
		else
			return division;
		
	}
}
