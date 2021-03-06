package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.ClientsEntity;
import com.trc.repositories.ClientsRepository;

@Service
public class ClientsService 
{
	@Autowired
	ClientsRepository repository;
	
	
	public List<ClientsEntity> getAllClients()
	{
		List<ClientsEntity> result=(List<ClientsEntity>) repository.findAll();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<ClientsEntity>();
		
	}
	
	public List<ClientsEntity> getAllClientsAlphab()
	{
		List<ClientsEntity> result=repository.getAllAlphab();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<ClientsEntity>();
		
	}
	
	public List<ClientsEntity> getAllActives()
	{
		List<ClientsEntity> result=repository.getAllActives();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<ClientsEntity>();
		
	}
	
		
	public ClientsEntity getClientById(Long id) throws RecordNotFoundException
	{
		Optional<ClientsEntity> client=repository.findById(id);
		
		if(client.isPresent())
			return client.get();
		else
			throw new RecordNotFoundException("No record exist for given id");
	}
	
	
		
	public ClientsEntity createOrUpdate(ClientsEntity entity)
	{
		if(entity.getClientid()==null)
		{
			
			
			entity=repository.save(entity);
			
			return entity;
		}
		else
		{
			Optional<ClientsEntity> client=repository.findById(entity.getClientid());
			
			if(client.isPresent())
			{
				
				ClientsEntity newEntity=client.get();
				
				newEntity.setCname(entity.getCname());
				newEntity.setDivision(entity.getDivision());
				newEntity.setProjectNumber(entity.getProjectNumber());
				newEntity.setHhsDivision(entity.getHhsDivision());
				newEntity.setEmail(entity.getEmail());
				newEntity.setActive(entity.getActive());
				newEntity.setVacStatus(entity.getVacStatus());
				newEntity.setNotes(entity.getNotes());
				
				newEntity.setSiteNumber(entity.getSiteNumber());
				newEntity.setTitle(entity.getTitle());
				newEntity.setEmpStatus(entity.getEmpStatus());
								
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
	
	public void deleteClientById(Long id) throws RecordNotFoundException
	{
		Optional<ClientsEntity> client=repository.findById(id);
		
		if(client.isPresent())
			repository.deleteById(id);
		else
			throw new RecordNotFoundException("No client record exist for given id");
		
		
	}
	
	public List<ClientsEntity> searchClientsByName(String stringSearch)
	{
		List<ClientsEntity> result=repository.getClientsByName(stringSearch);
		
		return result;
		
	}
	
	
	public List<ClientsEntity> searchClientsByProgram(String stringSearch)
	{
		List<ClientsEntity> result=repository.getClientsByProgram(stringSearch);
		
		return result;
		
	}
	
	public List<ClientsEntity> viewVaxStatus(String division, String vacStatus)
	{
		List<ClientsEntity> result=repository.getClieByVacStatus(division,vacStatus);
		
		return result;
		
	}
	
	public int findDuplicates(String email)
	{
		int priznakDuplicate=0;
		
		priznakDuplicate=repository.findEmailDuplicity(email);
		
		
		return priznakDuplicate;
	}

	public String getCnameById(Long clientId) 
	{
		String cname=null;
		
		cname=repository.getCnameById(clientId);
				
		return cname;
	}
	
}
