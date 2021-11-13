package com.trc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.BedListsEntityHHS;
import com.trc.repositories.BedListsRepositoryHHS;

@Service
public class ServicesService 
{

	@Autowired
	BedListsRepositoryHHS repository;
	
	public BedListsEntityHHS create(BedListsEntityHHS entity)
	{
		
		entity=repository.save(entity);
				
		return entity;
				
		
			
	}
	
}
