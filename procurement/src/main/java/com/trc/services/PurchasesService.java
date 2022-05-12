package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.PurchasesEntity;
import com.trc.repositories.PurchasesRepository;

@Service
public class PurchasesService 
{

	@Autowired
	PurchasesRepository repository;
	
	public List<PurchasesEntity> getAllByDate()
	{
		List<PurchasesEntity> result=repository.getAllByDate();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<PurchasesEntity>();
		
	}
	
	public PurchasesEntity getPurchaseById(Long id) throws RecordNotFoundException
	{
		//System.out.println("received id is: "+ id);
		
		Optional<PurchasesEntity> request=repository.findById(id);
		
		if(request.isPresent())
			return request.get();
		else
			throw new RecordNotFoundException("No record exist for given id");
	}
	
	public void deletePurchaseById(Long id) throws RecordNotFoundException
	{
		Optional<PurchasesEntity> request=repository.findById(id);
		
		if(request.isPresent())
			repository.deleteById(id);
		else
			throw new RecordNotFoundException("No Request record exist for given id");
		
		
	}
	
	public void retrievePurchaseById(Long id) throws RecordNotFoundException
	{
		Optional<PurchasesEntity> request=repository.findById(id);
		
		if(request.isPresent())
			repository.retrieveById(id);
		else
			throw new RecordNotFoundException("No Request record exist for given id");
		
		
	}
	
	public List<Integer> getArrayIntegers(int maxNum) 
	{
		List<Integer> quantities=new ArrayList<Integer>();
		
		int i=0;
		
		for(i=1;i<=maxNum;i++)
		{
			quantities.add(i);
		}
		
		return quantities;
		
	}
	
	public PurchasesEntity createOrUpdate(PurchasesEntity entity,String requestorId)
	{
		if(entity.getReqid()==null)
		{
			//Preparing to retrieve quote ID
			String reqId=null;
			String seed=null;
			String dateRequest=null;
			
			Long id;
			
			//Saving the new record
			entity=repository.save(entity);
			
			seed=entity.getStrobe();
			
			reqId=repository.getRequestId(seed);
			
			id=Long.parseLong(reqId);
			
			//Updating special fields (request date,requester ID and status)
			dateRequest=entity.getReqDate();
			repository.updateRealDateReq(id,dateRequest,requestorId);
			
			return entity;
		}
		else
		{
			Optional<PurchasesEntity> request=repository.findById(entity.getReqid());
			
			if(request.isPresent())
			{
				
				PurchasesEntity newEntity=request.get();
				
				newEntity.setReqDate(entity.getReqDate());
				newEntity.setRealReqDate(entity.getReqDate());
				
				newEntity.setItemNumber(entity.getItemNumber());
				newEntity.setQuantity(entity.getQuantity());
				newEntity.setProjectNumber(entity.getProjectNumber());
								
				newEntity.setStatus("new");
				
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
}
