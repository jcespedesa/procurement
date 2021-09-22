package com.trc.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.ReceiptsEntity;
import com.trc.repositories.ReceiptsRepository;


@Service
public class ReceiptsService 
{
	@Autowired
	ReceiptsRepository repository;
	
	public List<ReceiptsEntity> getAllReceipts()
	{
		List<ReceiptsEntity> result=(List<ReceiptsEntity>) repository.findAll();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<ReceiptsEntity>();
		
	}
	
	public ReceiptsEntity create(ReceiptsEntity entity)
	{
		//Saving the new record
		entity=repository.save(entity);
		
		return entity;
	}
}
