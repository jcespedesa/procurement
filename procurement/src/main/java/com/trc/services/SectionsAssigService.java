package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.SectionAssigEntity;
import com.trc.repositories.SectionAssigRepository;


@Service
public class SectionsAssigService 
{
	@Autowired
	SectionAssigRepository repository;

	public List<SectionAssigEntity> getAllSectionsAssig()
	{
		List<SectionAssigEntity> result=repository.findAll();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<SectionAssigEntity>();
		
	}
	
	public List<SectionAssigEntity> getAssigById(String userId)
	{
		List<SectionAssigEntity> result=repository.getAllAssigById(userId);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<SectionAssigEntity>();
		
	}
	
		
	public SectionAssigEntity getSectionAssigById(Long id) throws RecordNotFoundException
	{
		Optional<SectionAssigEntity> section=repository.findById(id);
		
		if(section.isPresent())
			return section.get();
		else
			throw new RecordNotFoundException("No record exist for given id");
	}
	
	
	public SectionAssigEntity createAssig(SectionAssigEntity entity)
	{
		//System.out.println(entity);
				
			
		entity=repository.save(entity);
				
		
		return entity;
				
		
	}
	
	public void deleteSectionAssigById(Long id) throws RecordNotFoundException
	{
		Optional<SectionAssigEntity> section=repository.findById(id);
		
		if(section.isPresent())
			repository.deleteById(id);
		else
			throw new RecordNotFoundException("No Section record exist for given id");
		
		
	}
	
	public int findDuplicates(String userId, String sectionNumber)
	{
		int priznakDuplicate=0;
		
		priznakDuplicate=repository.findAssigDuplicity(userId,sectionNumber);
		
		
		return priznakDuplicate;
	}

	public void deleteSectionAssigByNumber(String sectionNumber) throws RecordNotFoundException
	{
		
		repository.deleteBySectionNumber(sectionNumber);
		
	}
	
	
}
