package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.ProjectAssigEntity;
import com.trc.repositories.ProjectAssigRepository;


@Service
public class ProjectsAssigService 
{
	@Autowired
	ProjectAssigRepository repository;

	public List<ProjectAssigEntity> getAllProjectsAssig()
	{
		List<ProjectAssigEntity> result=repository.findAll();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<ProjectAssigEntity>();
		
	}
	
	public List<ProjectAssigEntity> getAssigById(String userId)
	{
		List<ProjectAssigEntity> result=repository.getAllAssigById(userId);
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<ProjectAssigEntity>();
		
	}
	
		
	public ProjectAssigEntity getProjectAssigById(Long id) throws RecordNotFoundException
	{
		Optional<ProjectAssigEntity> project=repository.findById(id);
		
		if(project.isPresent())
			return project.get();
		else
			throw new RecordNotFoundException("No record exist for given id");
	}
	
	
	public ProjectAssigEntity createAssig(ProjectAssigEntity entity)
	{
		//System.out.println(entity);
				
			
		entity=repository.save(entity);
				
		
		return entity;
				
		
	}
	
	public void deleteProjectAssigById(Long id) throws RecordNotFoundException
	{
		Optional<ProjectAssigEntity> project=repository.findById(id);
		
		if(project.isPresent())
			repository.deleteById(id);
		else
			throw new RecordNotFoundException("No Section record exist for given id");
		
		
	}
	
	public int findDuplicates(String userId, String projectNumber)
	{
		int priznakDuplicate=0;
		
		priznakDuplicate=repository.findAssigDuplicity(userId,projectNumber);
		
		
		return priznakDuplicate;
	}

	public void deleteProjectAssigByNumber(String projectNumber) throws RecordNotFoundException
	{
		
		repository.deleteByProjectNumber(projectNumber);
		
	}

	
}
