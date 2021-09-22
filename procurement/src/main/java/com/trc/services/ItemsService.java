package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.ItemsEntity;
import com.trc.repositories.ItemsRepository;

@Service
public class ItemsService 
{
	@Autowired
	ItemsRepository repository;
	
	public List<ItemsEntity> getAllItems()
	{
		List<ItemsEntity> result=(List<ItemsEntity>) repository.findAll();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<ItemsEntity>();
		
	}
	
	public List<ItemsEntity> getAllItemsListDesc()
	{
		List<ItemsEntity> result=(List<ItemsEntity>) repository.getAllDesc();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<ItemsEntity>();
		
	}
	
	public List<ItemsEntity> getAllMainItems()
	{
		List<ItemsEntity> result=(List<ItemsEntity>) repository.getAllMain();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<ItemsEntity>();
		
	}
	
	public List<ItemsEntity> getAllPeripherals()
	{
		List<ItemsEntity> result=(List<ItemsEntity>) repository.getAllPeripherals();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<ItemsEntity>();
		
	}
	
	public List<ItemsEntity> getAllPeripheralsHHS()
	{
		List<ItemsEntity> result=(List<ItemsEntity>) repository.getAllPeripheralsHHS();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<ItemsEntity>();
		
	}
	
	
	public ItemsEntity getItemById(Long id) throws RecordNotFoundException
	{
		Optional<ItemsEntity> item=repository.findById(id);
		
		if(item.isPresent())
			return item.get();
		else
			throw new RecordNotFoundException("No record exist for given id");
	}
	
	public ItemsEntity createOrUpdate(ItemsEntity entity)
	{
		if(entity.getItemid()==null)
		{
			entity=repository.save(entity);
			
			return entity;
		}
		else
		{
			Optional<ItemsEntity> item=repository.findById(entity.getItemid());
			
			if(item.isPresent())
			{
				
				ItemsEntity newEntity=item.get();
				
				newEntity.setItem(entity.getItem());
				newEntity.setNotes(entity.getNotes());
				newEntity.setKlass(entity.getKlass());
				newEntity.setHhsForm(entity.getHhsForm());
				
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
	
	public void deleteItemById(Long id) throws RecordNotFoundException
	{
		Optional<ItemsEntity> item=repository.findById(id);
		
		if(item.isPresent())
			repository.deleteById(id);
		else
			throw new RecordNotFoundException("No Item record exist for given id");
		
		
	}
	
	public String getItemDescById(Long id) throws RecordNotFoundException
	{
		String description=repository.getItemDescById(id);
		
		return description;
	}
	
	public Long getIdByDesc(String description)
	{
		Long itemId=null;
		
		ItemsEntity item=repository.getByDescription(description);
		
		itemId=item.getItemid();
		
		return itemId;
	}
	
	
	public ItemsEntity getItemByDesc(String description)
	{
				
		ItemsEntity item=repository.getByDescription(description);
		
				
		return item;
	}
	
}
