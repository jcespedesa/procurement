package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.SectionsEntity;
import com.trc.repositories.SectionsRepository;

@Service
public class SectionsService 
{
	@Autowired
	SectionsRepository repository;
	
	public List<SectionsEntity> getAllSections()
	{
		List<SectionsEntity> result=(List<SectionsEntity>) repository.findAll();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<SectionsEntity>();
		
	}
	
	public List<SectionsEntity> getAllActives()
	{
		List<SectionsEntity> result=(List<SectionsEntity>) repository.getAllActives();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<SectionsEntity>();
		
	}
	
	public List<SectionsEntity> getAllByName()
	{
		List<SectionsEntity> result=repository.getAllByName();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<SectionsEntity>();
		
	}
	
	public SectionsEntity getSectionById(Long id) throws RecordNotFoundException
	{
		System.out.println("received id is: "+ id);
		
		Optional<SectionsEntity> section=repository.findById(id);
		
		if(section.isPresent())
			return section.get();
		else
			throw new RecordNotFoundException("No record exist for given id");
	}
	
	public SectionsEntity createOrUpdate(SectionsEntity entity)
	{
		if(entity.getSectionid()==null)
		{
			entity=repository.save(entity);
			
			return entity;
		}
		else
		{
			Optional<SectionsEntity> section=repository.findById(entity.getSectionid());
			
			if(section.isPresent())
			{
				
				SectionsEntity newEntity=section.get();
				
				newEntity.setSectionName(entity.getSectionName());
				newEntity.setSectionNumber(entity.getSectionNumber());
								
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
	
	public void deleteSectionById(Long id) throws RecordNotFoundException
	{
		Optional<SectionsEntity> section=repository.findById(id);
		
		if(section.isPresent())
			repository.deleteById(id);
		else
			throw new RecordNotFoundException("No Section record exist for given id");
		
		
	}
	
	public String getSectionByNumber(String sectionNumber)
	{
		String section=repository.getSectionName(sectionNumber);
		
		if(section==null)
			return "";
		else
			return section;
		
	}
	
	public int findDuplicates(String sectionNumber)
	{
		int priznakDuplicate=0;
		
		priznakDuplicate=repository.findSectionDuplicity(sectionNumber);
		
		return priznakDuplicate;
	}
}
