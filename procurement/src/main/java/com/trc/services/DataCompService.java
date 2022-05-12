package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.trc.entities.DataCompEntity;
import com.trc.repositories.DataCompRepository;

@Service
public class DataCompService 
{
	@Autowired
	DataCompRepository repository;
	
	public List<DataCompEntity> getAllDataComp()
	{
		List<DataCompEntity> result=(List<DataCompEntity>) repository.findAll();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<DataCompEntity>();
		
	}
	
	public List<DataCompEntity> getAllByDate()
	{
		List<DataCompEntity> result=(List<DataCompEntity>) repository.getByDate();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<DataCompEntity>();
		
	}
				
		
	public DataCompEntity getDataCompById(Long id) throws RecordNotFoundException
	{
		Optional<DataCompEntity> dataComp=repository.findById(id);
		
		if(dataComp.isPresent())
			return dataComp.get();
		else
			throw new RecordNotFoundException("No record exist for given id");
	}
	
	public DataCompEntity createOrUpdate(DataCompEntity entity)
	{
		if(entity.getDataid()==null)
		{
			entity.setRealDate(entity.getDate());
			
			entity=repository.save(entity);
			
			return entity;
		}
		else
		{
			Optional<DataCompEntity> dataComp=repository.findById(entity.getDataid());
			
			if(dataComp.isPresent())
			{
				
				DataCompEntity newEntity=dataComp.get();
				
				newEntity.setDate(entity.getDate());
				newEntity.setRealDate(entity.getDate());
				newEntity.setHmis(entity.getHmis());
				newEntity.setBedList(entity.getBedList());
				newEntity.setHmax(entity.getHmax());
				
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
	
	public void deleteDataCompById(Long id) throws RecordNotFoundException
	{
		Optional<DataCompEntity> dataComp=repository.findById(id);
		
		if(dataComp.isPresent())
			repository.deleteById(id);
		else
			throw new RecordNotFoundException("No DataComp record exist for given id");
		
		
	}
	
		
	public int findDuplicates(String date)
	{
		int priznakDuplicate=0;
		
		priznakDuplicate=repository.findDataCompDuplicity(date);
		
		return priznakDuplicate;
	}

	public DataCompEntity getLastRecord() 
	{
		DataCompEntity lastRecord=repository.getLastRecord(PageRequest.of(0,1));
						
		return lastRecord;
	}
	
	
}
