package com.trc.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.PeriodicalsEntity;
import com.trc.repositories.PeriodicalsRepository;
import com.trc.repositories.SettingsRepository;
import com.trc.repositories.SitesRepository;

@Service
public class PeriodicalsService 
{
	@Autowired
	PeriodicalsRepository repository;
	
	@Autowired
	SitesRepository repositorySites;
	
	@Autowired
	SettingsRepository repositorySettings;
	
	public List<PeriodicalsEntity> getAllPeriodicals()
	{
		List<PeriodicalsEntity> result=(List<PeriodicalsEntity>) repository.findAll();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<PeriodicalsEntity>();
		
	}
	
	public List<PeriodicalsEntity> getAllByDescription()
	{
		List<PeriodicalsEntity> result=repository.getAllByDescription();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<PeriodicalsEntity>();
		
	}
	
	public List<PeriodicalsEntity> getAllActive()
	{
		List<PeriodicalsEntity> result=repository.getAllActives();
		
		if(result.size() > 0)
			return result;
		else
			return new ArrayList<PeriodicalsEntity>();
		
	}
	
	public PeriodicalsEntity getPeriodicalById(Long id) throws RecordNotFoundException
	{
		Optional<PeriodicalsEntity> periodical=repository.findById(id);
		
		if(periodical.isPresent())
			return periodical.get();
		else
			throw new RecordNotFoundException("No record exist for given id");
	}
	
	public PeriodicalsEntity createOrUpdate(PeriodicalsEntity entity)
	{
		String location=null;
		String locationNumber=null;
		
		
		String dirPath="";
		String uploadDirAux="";
		String dirTrail="periodicals/";
		
		
		//Retrieving number fields to be described
		locationNumber=entity.getLocationNumber();
		
		//Retrieving location description 
		location=repositorySites.getLocationName(locationNumber);
		
		//Getting the application main path
		uploadDirAux=repositorySettings.getAppPath("appHome");
		
				
		if(entity.getPeriodicalid()==null)
		{
			//Preparing to retrieve periodical ID
			String periodicalId=null;
			String seed=null;
			
			Long id;
			
			entity=repository.save(entity);
			
			seed=entity.getStrobe();
			
			periodicalId=repository.getPeriodicalId(seed);
			
			id=Long.parseLong(periodicalId);
						
			
			//Preparing the address for scan docs folder
			dirPath=uploadDirAux+dirTrail+periodicalId;
			
			
			//Setting up location description
			repository.setLocationDescription(id,location);
			
			//resetting strobe field
			repository.resetStrobe(id);
			
			//Creating the folder for scan documents
			File file=new File(dirPath);
			
			if(!file.exists())
			{
				
				if(file.mkdir())
				{
					System.out.println("Directory "+ dirPath +" has been created successfully!");
				}
				else
				{
					System.out.println("Directory "+ dirPath +" failed to be created!");
				}
			}
			
			return entity;
		}
		else
		{
			Optional<PeriodicalsEntity> periodical=repository.findById(entity.getPeriodicalid());
			
			if(periodical.isPresent())
			{
				
				String localIdString=null;
				
				Long localId=entity.getPeriodicalid();
				
				//Converting long Id to string
				localIdString=Long.toString(localId);
				
				PeriodicalsEntity newEntity=periodical.get();
				
				newEntity.setDescription(entity.getDescription());
				newEntity.setProvider(entity.getProvider());
				newEntity.setAmount(entity.getAmount());
				
				newEntity.setLocationNumber(entity.getLocationNumber());
				newEntity.setPonum(entity.getPonum());
				
				newEntity.setNotes(entity.getNotes());
				
				newEntity.setProvSite(entity.getProvSite());
				newEntity.setProvUser(entity.getProvUser());
				newEntity.setProvPass(entity.getProvPass());
				
				newEntity.setStatus(entity.getStatus());
				newEntity.setAccount(entity.getAccount());
				newEntity.setFrequency(entity.getFrequency());
				
				newEntity.setType(entity.getType());
								
				newEntity=repository.save(newEntity);
				
				//Setting up location description
				repository.setLocationDescription(localId,location);
				
				//Preparing the address for scan docs folder
				dirPath=uploadDirAux+dirTrail+localIdString;
				
				
				//Creating the folder for scan documents if does not exist already
				File file=new File(dirPath);
				
				if(!file.exists())
				{
					
					if(file.mkdir())
					{
						System.out.println("Directory "+ dirPath +" has been created successfully!");
					}
					else
					{
						System.out.println("Directory "+ dirPath +" failed to be created!");
					}
				}
				
				
				return newEntity;
				
			}
			else
			{
				entity=repository.save(entity);
				
				return entity;
				
			}
			
		}
				
		
	}
	
	public void deletePeriodicalById(Long id) throws RecordNotFoundException
	{
		Optional<PeriodicalsEntity> periodical=repository.findById(id);
		
		if(periodical.isPresent())
			repository.deleteById(id);
		else
			throw new RecordNotFoundException("No Periodical record exist for given id");
		
		
	}
	
	
	//Scan docs section
	
	public Set<String> getAllFiles(String dir) 
	{
	    return Stream.of(new File(dir).listFiles())
	      .filter(file -> !file.isDirectory())
	      .map(File::getName)
	      .collect(Collectors.toSet());
	}
	

}
